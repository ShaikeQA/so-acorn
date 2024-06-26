package org.alfabank.soacorn.steps.api.authClient;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.alfabank.soacorn.pojo.authClient.AuthLoginRequest;
import org.alfabank.soacorn.pojo.authClient.AuthLoginResponse;
import org.alfabank.soacorn.steps.core.RequestSteps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;
@Service
@RequiredArgsConstructor
public class AuthApiSteps {

    private final RequestSteps requestSteps;

    @Value("${auth.admin.username}")
    private String adminUsername;

    @Value("${auth.admin.username.password}")
    private String adminPassword;

    @Value("${urn.post.x-clients.login.auth}")
    private String URN;

    @Step("Авторизация")
    public String getAdminToken() {
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON)
                .body(new AuthLoginRequest(adminUsername, adminPassword));
        AuthLoginResponse response = requestSteps.execute(Method.POST, URN, requestSpecification, 201, AuthLoginResponse.class);
        return response.getUserToken();
    }
}
