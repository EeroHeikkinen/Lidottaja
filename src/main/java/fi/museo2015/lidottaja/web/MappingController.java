package fi.museo2015.lidottaja.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.museo2015.lidottaja.model.Mapping;
import fi.museo2015.lidottaja.service.DocumentationService;
import fi.museo2015.lidottaja.service.MappingService;
import fi.museo2015.lidottaja.service.PreviewService;

@Controller
@SessionAttributes({ "preview" })
public class MappingController {

	private MappingService mappingService;
	private PreviewService previewService;
	private DocumentationService documentationService;

	@Autowired
	public MappingController(MappingService mappingService,
			PreviewService previewService,
			DocumentationService documentationService) {
		this.mappingService = mappingService;
		this.previewService = previewService;
		this.documentationService = documentationService;
	}

	@RequestMapping(value = "/mapping/start", method = RequestMethod.GET)
	public void search(Mapping Mapping, Principal currentUser, Model model) {
		if (currentUser != null) {
			List<Mapping> mappings = mappingService.findMappings(currentUser
					.getName());
			model.addAttribute(mappings);
		}
	}

	@RequestMapping(value = "/preview/{name}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String preview(@PathVariable String name) {
		return previewService.getPreview(name);
	}

	@RequestMapping(value = "/docs/{name}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String docs(@PathVariable String name) {
		return documentationService.getDocumentation(name);
	}

	@RequestMapping(value = "/src/{name}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String src(@PathVariable String name) {
		return documentationService.getSource(name);
	}
}
