package com.expose.service.controler;

import com.expose.service.modal.EmployeeModal;
import com.expose.service.stub.StubApplication;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.security.user.name=dulip",
                "spring.security.user.password=pw",
                "spring.security.user.roles=ADMIN"
        }
)
@ActiveProfiles("test")
public class ExternalWiremockServerTest {

        @LocalServerPort
        private Integer port;

        @BeforeAll
        static void startStubServer(){
                StubApplication.startStubServer();
        }
        @AfterAll
        static void stopStubServer(){
                StubApplication.stopStubServer();
        }

        @Test()
        void testGetEmployeeWithExternalFile(){
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
