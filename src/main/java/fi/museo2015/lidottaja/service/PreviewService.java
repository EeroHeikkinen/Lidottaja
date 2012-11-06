package fi.museo2015.lidottaja.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service("previewService")
public class PreviewService {
	private Map<String, String> storedPreviews = new HashMap<String, String>();

	public void pushPreview(String name, String lido) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("data", lido);
			String result = doSubmit(
					"http://muisti.nba.fi/vufind-dev/Record/hackme/Preview?format=lido",
					map);
			if (result != null) {
				storedPreviews.put(name, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPreview(String name) {
		if (storedPreviews.containsKey(name))
			return storedPreviews.get(name);
		return null;
	}

	public String doSubmit(String url, Map<String, String> data)
			throws Exception {
		URL siteUrl = new URL(url);

		HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);

		DataOutputStream out = new DataOutputStream(conn.getOutputStream());

		Set keys = data.keySet();
		Iterator keyIter = keys.iterator();
		String content = "";
		for (int i = 0; keyIter.hasNext(); i++) {
			Object key = keyIter.next();
			if (i != 0) {
				content += "&";
			}
			content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
			// content += key + "=" + data.get(key);
		}
		System.out.println(content);
		out.writeBytes(content);
		out.flush();
		out.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String aux = "";

		String prefix = siteUrl.getProtocol() + "://" + siteUrl.getHost();

		while ((aux = in.readLine()) != null) {
			if (aux.indexOf("href") != -1) {
				aux = aux.replaceAll("href=\"/", "href=\"" + prefix + "/");
			}
			aux = aux.replaceAll("src=\"/", "src=\"" + prefix + "/");
			builder.append(aux);
		}

		in.close();

		return builder.toString();
	}
}