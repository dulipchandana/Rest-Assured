package com.expose.service;

import com.expose.service.modal.CommonModal;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
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
