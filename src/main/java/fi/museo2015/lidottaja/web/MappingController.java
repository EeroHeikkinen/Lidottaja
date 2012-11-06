package fi.museo2015.lidottaja.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.museo2015.lidottaja.service.DocumentationService;
import fi.museo2015.lidottaja.service.MappingService;
import fi.museo2015.lidottaja.service.PreviewService;

/*
 * A web layer for the mapping related functions
 * Allows retrieval of HTML previews, XML sources and HTML documentations.
 */
@Controller
@SessionAttributes({ "preview" })
public class MappingController {
	private PreviewService previewService;
	private DocumentationService documentationService;

	@Autowired
	public MappingController(MappingService mappingService,
			PreviewService previewService,
			DocumentationService documentationService) {
		this.previewService = previewService;
		this.documentationService = documentationService;
	}

	/*
	 * Retrieve a previously stored preview under a mapping name TODO: as long
	 * as cached, users can view each other's previews of mappings this hole
	 * could be closed if deemed too undesirable
	 */
	@RequestMapping(value = "/preview/{name}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String preview(@PathVariable String name) {
		return previewService.getPreview(name);
	}

	/*
	 * Retrieve the documentation for a given mapping.
	 * 
	 * @param name The name of the mapping
	 */
	@RequestMapping(value = "/docs/{name}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String docs(@PathVariable String name) {
		String docs = documentationService.getDocumentation(name);
		if (docs != null)
			return docs;
		// TODO: JSP error page with localization
		else
			return "Documentation for mapping could not be found";
	}

	@RequestMapping(value = "/src/{name}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String src(@PathVariable String name) {
		String xml = documentationService.getDocumentation(name);
		if (xml != null)
			return xml;
		else
			return "Example source for mapping could not be found";
	}
}
