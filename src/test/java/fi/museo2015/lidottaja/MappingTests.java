package fi.museo2015.lidottaja;

import org.easymock.EasyMock;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

import fi.museo2015.lidottaja.model.Mapping;
import fi.museo2015.lidottaja.service.MappingService;

public class MappingTests extends AbstractXmlFlowExecutionTests {

	private MappingService mappingService;

	protected void setUp() {
		mappingService = EasyMock.createMock(MappingService.class);
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
	}

	public void testStartBookingFlow() {
		Mapping mapping = createTestMapping();

		EasyMock.expect(bookingService.createBooking(1L, "keith")).andReturn(
				booking);

		EasyMock.replay(bookingService);

		MutableAttributeMap input = new LocalAttributeMap();
		input.put("hotelId", "1");
		MockExternalContext context = new MockExternalContext();
		context.setCurrentUser("keith");
		startFlow(input, context);

		assertCurrentStateEquals("enterBookingDetails");
		assertResponseWrittenEquals("enterBookingDetails", context);
		assertTrue(getRequiredFlowAttribute("booking") instanceof Booking);

		EasyMock.verify(bookingService);
	}

	public void testEnterBookingDetails_Proceed() {
		setCurrentState("enterBookingDetails");
		getFlowScope().put("booking", createTestBooking());

		MockExternalContext context = new MockExternalContext();
		context.setEventId("proceed");
		resumeFlow(context);

		assertCurrentStateEquals("reviewBooking");
		assertResponseWrittenEquals("reviewBooking", context);
	}

	public void testReviewBooking_Confirm() {
		setCurrentState("reviewBooking");
		getFlowScope().put("booking", createTestBooking());
		MockExternalContext context = new MockExternalContext();
		context.setEventId("confirm");
		resumeFlow(context);
		assertFlowExecutionEnded();
		assertFlowExecutionOutcomeEquals("bookingConfirmed");
	}

	private Mapping createTestMapping() {
		Resource template = resourceFactory
		.createFileResource("src/main/webapp/WEB-INF/mapping/map/map-flow.xml");
		Mapping mapping = new Mapping(template, );
		hotel.setId(1L);
		hotel.setName("Jameson Inn");
		User user = new User("keith", "pass", "Keith Donald");
		Booking booking = new Booking(hotel, user);
		return booking;
	}
}
