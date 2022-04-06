package com.expose.service.controler;

import com.expose.service.CommonModelService;
import com.expose.service.modal.CommonModal;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(GenaricControler.class)
class GenaricControlerTest {

    @MockBean
    private CommonModelService commonModelService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc(mockMvc);

        Mockito.when(commonModelService.saveCommonModal(any())).thenReturn(
                Optional.of(new CommonModal(1, "dc"))
        );
    }

    @Test
    void testGetModalTest(){
        Mockito.when(commonModelService.getCommonModalList()).thenReturn(
                Optional.of(new ArrayList<CommonModal>(Set.of(new CommonModal(1, "dc"),
                        new CommonModal(2, "mn"))))
        );

        RestAssuredMockMvc
                .given()
                .auth().none()
                .when()
                .get("/api/modalmgt/modals")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("$.size()", Matchers.equalTo(2))
                .body("[0].commonIndex",Matchers.equalTo(1));
    }

    @Test
    void testAuthError(){
        RestAssuredMockMvc
                .given()
                .auth().none()
                .contentType("application/json")
                .body("{\"commonIndex\":1,\"commonDescription\":\"dc\"}")
                .when()
                .post("/api/modalmgt/modal/create")
                .then()
                .statusCode(401);
    }

    @Test
    void testSuccessWithError(){
        RestAssuredMockMvc
                .given()
                .auth().with(SecurityMockMvcRequestPostProcessors
                        .user("dulip").roles("ADMIN","USER"))
                .contentType("application/json")
                .body("{\"commonIndex\":1,\"commonDescription\":\"dc\"}")
                .when()
                .post("/api/modalmgt/modal/create")
                .then()
                .statusCode(201)
                .body("commonIndex",Matchers.equalTo(1));;
    }

}