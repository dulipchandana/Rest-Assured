package com.expose.service.controler;

import com.expose.service.dao.EmployeeModalDao;
import com.expose.service.modal.CommonModal;
import com.expose.service.modal.EmployeeModal;
import com.expose.service.service.CommonModelService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.security.user.name=dulip",
                "spring.security.user.password=pw",
                "spring.security.user.roles=ADMIN"
        }
)
public class ExternalMockControllerTest {
    @LocalServerPort
    private Integer port;

    @DynamicPropertySource
    static void overrideRemoteBaseUrl(DynamicPropertyRegistry dr){
        dr.add("remote.baseUrl",wireMockServer::baseUrl);
    }

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void startWireMockServer(){
        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .dynamicPort());
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock(){
        wireMockServer.stop();
    }

    public void getStub(){
        wireMockServer.stubFor(
                WireMock.get("/api/v1/employees")
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader(HttpHeaders.CONTENT_TYPE,
                                        MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("employee.json")));
    }

    @Test()
    void testGetEmployeeWithExternalFile(){
        getStub();
        ExtractableResponse<Response> response = RestAssured
                .given()
                .auth().preemptive().basic("dulip", "pw")
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/api/modalmgt/employee/modals")
                .then()
                .statusCode(200)
                .extract();
        EmployeeModal responseModal =  response.body().as(EmployeeModal.class);
        assertTrue(responseModal.getData().size()==4);
    }
}
