package fi.museo2015.lidottaja;

import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

import fi.museo2015.lidottaja.model.Mapping;
import fi.museo2015.lidottaja.service.DocumentationService;
import fi.museo2015.lidottaja.service.MappingService;

public class MappingTests extends AbstractXmlFlowExecutionTests {

	private MappingService mappingService;
	private DocumentationService documentationService;

	protected void setUp() {
		mappingService = EasyMock.createMock(MappingService.class);
		documentationService = EasyMock.createMock(DocumentationService.class);
	}

	@Override
	protected FlowDefinitionResource getResource(
			FlowDefinitionResourceFactory resourceFactory) {
		return resourceFactory
				.createFileResource("src/main/webapp/WEB-INF/mapping/map/map-flow.xml");
	}

	@Override
	protected void configureFlowBuilderContext(
			MockFlowBuilderContext builderContext) {
		builderContext.registerBean("mappingService", mappingService);
		builderContext.registerBean("documentationService",
				documentationService);
	}

	@Test
	public void testConfigureMapping_Proceed() {
		setCurrentState("configureMapping");
		getFlowScope().put("mapping", createTestMapping());

		MockExternalContext context = new MockExternalContext();
		context.setCurrentUser("aapeli");
		context.setEventId("proceed");
		resumeFlow(context);

		assertCurrentStateEquals("mappingDone");
		assertResponseWrittenEquals("mappingDone", context);
	}

	private Mapping createTestMapping() {
		Resource resource = new ClassPathResource("lido-template.xml");
		return new Mapping(resource);
	}
}
