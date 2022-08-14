package com.expose.service;

import com.expose.service.modal.CommonModal;
import com.expose.service.modal.EmployeeModal;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.security.user.name=dulip",
                "spring.security.user.password=pw",
                "spring.security.user.roles=ADMIN"
        }
)
class RestApplicationTests {

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

    @Test
    void testWireMockServer(){
        System.out.println("port"+ wireMockServer.port());
        assertTrue(wireMockServer.isRunning());
    }

    @Test
    void getEmpModalTest(){
        wireMockServer.stubFor(
                WireMock.get("/api/v1/employees")
                        .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE,
                                MediaType.APPLICATION_JSON_VALUE)
                                        .withBody("{\n" +
                                                "  \"status\": \"success\",\n" +
                                                "  \"data\": [\n" +
                                                "    {\n" +
                                                "      \"id\": 5,\n" +
                                                "      \"employee_name\": \"Airi Satou\",\n" +
                                                "      \"employee_salary\": 162700,\n" +
                                                "      \"employee_age\": 33,\n" +
                                                "      \"profile_image\": \"\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 6,\n" +
                                                "      \"employee_name\": \"Brielle Williamson\",\n" +
                                                "      \"employee_salary\": 372000,\n" +
                                                "      \"employee_age\": 61,\n" +
                                                "      \"profile_image\": \"\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 7,\n" +
                                                "      \"employee_name\": \"Herrod Chandler\",\n" +
                                                "      \"employee_salary\": 137500,\n" +
                                                "      \"employee_age\": 59,\n" +
                                                "      \"profile_image\": \"\"\n" +
                                                "    },\n" +
                                                "    {\n" +
                                                "      \"id\": 8,\n" +
                                                "      \"employee_name\": \"Rhona Davidson\",\n" +
                                                "      \"employee_salary\": 327900,\n" +
                                                "      \"employee_age\": 55,\n" +
                                                "      \"profile_image\": \"\"\n" +
                                                "    }\n" +
                                                "  ],\n" +
                                                "  \"message\": \"Successfully! All records has been fetched.\"\n" +
                                                "}"))
        );

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

    @Test
    void getEmpModalTestError(){
        wireMockServer.stubFor(
                WireMock.get("/api/v1/employees")
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE,
                                        MediaType.APPLICATION_JSON_VALUE)
                                        .withStatus(400)
                                )
        );

        ExtractableResponse<Response> response = RestAssured
                .given()
                .auth().preemptive().basic("dulip", "pw")
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/api/modalmgt/employee/modals")
                .then()
                .statusCode(400)
                .extract();
        EmployeeModal responseModal =  response.body().as(EmployeeModal.class);
        assertTrue(responseModal.status.equals(HttpStatus.BAD_REQUEST.toString()));
    }

    @Test
    void testGetModalTest() {

        ExtractableResponse<Response> response = RestAssured
                .given()
                .auth().preemptive().basic("dulip", "pw")
                .contentType("application/json")
                .when()
                .get("http://localhost:" + port + "/api/modalmgt/auth/modals")
                .then()
                .statusCode(200)
                .extract();
        List<CommonModal> returnedmodals = Arrays.asList(response.body().as(CommonModal[].class));
        assertTrue(returnedmodals.stream().anyMatch(
                modals ->
                        modals.getCommonIndex() == 1
                && modals.getCommonDescription().equals("dc")
        ));

    }

    @Test
    void testPostModalTest() {
        ExtractableResponse<Response> response = RestAssured
                .given()
                .auth().preemptive().basic("dulip", "pw")
                .contentType("application/json")
                .body("{\"commonIndex\":1,\"commonDescription\":\"dc\"}")
                .when()
                .post("http://localhost:" + port + "/api/modalmgt/modal/create")
                .then()
                .statusCode(201)
                .extract();
        CommonModal returnedmodal = response.body().as(CommonModal.class);
        assertTrue(returnedmodal.getCommonIndex()==1);

    }

}
