package com.lazarin.sample.wiremocktutorial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.lazarin.sample.wiremocktutorial.resource.StockResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.unauthorized;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("wiremock")
class WiremockTutorialApplicationTests {

	private static WireMockServer wireMockServer;

	@Autowired
	private MockMvc mockMvc;

	private static String urlToStub;
	private static String stockCode;

	@BeforeAll
	static void init() {
		wireMockServer = new WireMockServer(
				new WireMockConfiguration()
						.port(7070)
		);
		wireMockServer.start();
		WireMock.configureFor("localhost", 7070);

		stockCode = "appl";
		urlToStub = String.format("/financial-facts/%s", stockCode);
	}

	@Test
	void shouldCallGetStockDetailsAndReturnItsData() throws Exception {

		StockResource resource = getExpectedStockResourceResponse();

		stubFor(WireMock.get(urlMatching(urlToStub))
				.willReturn(aResponse()
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBody("{ \"id\": 1, \"stockCode\": \"APPL\", \"companyName\": \"Apple Inc.\"" +
								",\"currentPrice\": 179.35, \"previousClosePrice\": 180.10, \"yield\": 0.49, " +
								"\"priceToEarningsRatio\":31.98 }")
						.withStatus(OK.value())));


		mockMvc.perform(get("/api/stocks/{code}", stockCode))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(parseToJson(resource)));

		verify(getRequestedFor(urlPathEqualTo(urlToStub)));
	}

	@Test
	void shouldCallGetStockDetailsWithWireMockRespFileAndReturnItsData() throws Exception {

		StockResource resource = getExpectedStockResourceResponse();

		stubFor(WireMock.get(urlMatching(urlToStub))
				.willReturn(aResponse()
						.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withBodyFile("financial-facts-resp-appl.json")
						.withStatus(OK.value())));

		mockMvc.perform(get("/api/stocks/{code}", stockCode))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(parseToJson(resource)));

		verify(getRequestedFor(urlPathEqualTo(urlToStub)));
	}

	@Test
	void shouldCallGetStockDetailsAndReturnAnErrorDueUnauthorizedRequest() throws Exception {

		stubFor(WireMock.get(urlMatching(urlToStub))
				.willReturn(unauthorized()));

		mockMvc.perform(get("/api/stocks/{code}", stockCode))
				.andDo(print()).andExpect(status().isInternalServerError());

		verify(getRequestedFor(urlPathEqualTo(urlToStub)));
	}

	@Test
	void shouldCallGetStockDetailsAndReturnAnErrorDueNotFoundRequest() throws Exception {

		stubFor(WireMock.get(urlMatching(urlToStub))
				.willReturn(notFound()));

		mockMvc.perform(get("/api/stocks/{code}", stockCode))
				.andDo(print()).andExpect(status().isNotFound());

		verify(getRequestedFor(urlPathEqualTo(urlToStub)));
	}

	private static String parseToJson(StockResource stockResource) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(stockResource);
	}

	private StockResource getExpectedStockResourceResponse(){
		return StockResource.builder()
				.stockCode("APPL")
				.companyName("Apple Inc.")
				.yield(0.49)
				.priceToEarningsRatio(31.98F)
				.currentPrice(179.35D)
				.previousClosePrice(180.10D)
				.oneMonthTargetPrice(179.35D)
				.threeMonthsTargetPrice(179.35D)
				.oneYearTargetPrice(179.35D)
				.build();

	}
}
