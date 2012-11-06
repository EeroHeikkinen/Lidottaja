package fi.museo2015.lidottaja.service;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import fi.museo2015.lidottaja.model.BindingBlock;
import fi.museo2015.lidottaja.model.BindingTarget;
import fi.museo2015.lidottaja.model.Chunk;
import fi.museo2015.lidottaja.model.Mapping;

/**
 * A service for creating documentation for a Mapping.
 */
@Service("documentationService")
public class DocumentationService {
	static Logger log = Logger.getLogger(DocumentationService.class.getName());
	private String docFolder;

	@Value("#{props.docFolder}")
	public void setDocFolder(String docFolder) {
		this.docFolder = docFolder;
	}

	private String xoccoFolder;

	@Value("#{props.xoccoFolder}")
	public void setXoccoFolder(String xoccoFolder) {
		this.xoccoFolder = xoccoFolder;
	}

	/**
	 * Generates documentation for a given mapping. Invokes xocco, a python
	 * docco-based tool for the generation of the actual HTML documentation.
	 */
	public void generateDocumentation(Mapping mapping) {
		String result = null;
		try {
			// Building an XML string suitable for parsing by xocco
			// We need to generate comments and place them strategically

			List<Chunk> stack = mapping.getBinder().getStack();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < stack.size(); i++) {
				Chunk bc = stack.get(i);
				if (bc instanceof BindingBlock) {
					List<Chunk> blockStack = ((BindingBlock) bc).getStack();
					for (int j = 0; j < blockStack.size(); j++) {
						Chunk blockBc = blockStack.get(j);
						if (blockBc instanceof BindingTarget) {
							BindingTarget bt = (BindingTarget) blockBc;
							if (bt.getField() != null) {
								sb.append("<!-- " + bt.getField() + " -->\n");
								sb.append(bt.toString());
							}
						} else {
							sb.append(blockBc.toString());
						}
					}

				} else {
					sb.append(bc.toString());
				}
			}

			// Conversion to document and back in order to fix indents
			// TODO: indents still don't work perfectly
			Document doc = Mapping.loadXMLFromString(sb.toString());
			String xml = BindingBlock.nodeToXMLString(doc);

			// Saving the xml to a file which we then point to xocco
			File dir = new File(docFolder + File.separatorChar
					+ mapping.getName());
			if (!dir.exists()) {
				dir.mkdir();
				dir.setWritable(true, false);
				dir.setReadable(true, false);
				dir.setExecutable(true, false);
			}
			if (!dir.isDirectory())
				return;

			File xmlFile = new File(dir, "source.xml");
			BufferedWriter out = new BufferedWriter(new FileWriter(xmlFile),
					32768);
			out.write(xml);
			out.close();

			// We have the xml file, time to do the actual invoking
			result = execToString("node " + xoccoFolder + File.separatorChar
					+ "generate " + xmlFile.getCanonicalPath());

			return;

		} catch (Exception e) {
			if (result != null)
				log.error("Generating documentation with xocco failed with output: "
						+ result);
			else
				e.printStackTrace();
		}
	}

	public String execToString(String command) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			CommandLine commandline = CommandLine.parse(command);
			DefaultExecutor exec = new DefaultExecutor();
			// exec.setWorkingDirectory(workDir);
			PumpStreamHandler streamHandler = new PumpStreamHandler(
					outputStream);
			exec.setStreamHandler(streamHandler);
			exec.execute(commandline);
		} catch (Exception e) {
			return outputStream.toString();
		}
		return (outputStream.toString());
	}

	public String getDocumentation(Mapping mapping) {
		return getDocumentation(mapping.getName());
	}

	public String getDocumentation(String name) {
		File docs = new File(docFolder + File.separatorChar + name
				+ File.separatorChar + "source.xml.html");
		try {
			FileInputStream stream = new FileInputStream(docs);
			try {
				FileChannel fc = stream.getChannel();
				MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
						fc.size());
				return Charset.defaultCharset().decode(bb).toString();
			} finally {
				stream.close();
			}
		} catch (IOException e) {
			return null;
		}
	}

	public String getSource(String name) {
		File docs = new File(docFolder + File.separatorChar + name
				+ File.separatorChar + "source.xml");
		try {
			FileInputStream stream = new FileInputStream(docs);
			try {
				FileChannel fc = stream.getChannel();
				MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0,
						fc.size());
				return Charset.defaultCharset().decode(bb).toString();
			} finally {
				stream.close();
			}
		} catch (IOException e) {
			return null;
		}
	}
}