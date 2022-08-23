package com.expose.service.stub;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class StubApplication {

    @LocalServerPort
    private Integer port;

    @DynamicPropertySource
    static void overrideRemoteBaseUrl(DynamicPropertyRegistry dr){
        dr.add("remote.baseUrl",wireMockServer::baseUrl);
    }

    private static WireMockServer wireMockServer;
    public static void startStubServer(){

        wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig()
                .port(8081)
                .extensions(new ResponseTemplateTransformer(true)));
        wireMockServer.start();
    }

    public static void stopStubServer(){
        wireMockServer.stop();
    }
}
