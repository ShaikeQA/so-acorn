package org.alfabank.soacorn.steps.api;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Component
public class RequestSteps {

    @Value("${request.base.url}")
    private String BASE_URL;

    RestAssuredConfig restAssuredConfig = RestAssured.config()
            .httpClient(HttpClientConfig.httpClientConfig()
                    .setParam("http.connection.timeout", 30000)
                    .setParam("http.socket.timeout", 30000));


    public <T> T execute(Method method, String url, Headers headers, Object body, int expectedStatusCode, Class<T> responseClass) {
        if (!url.startsWith("http")) {
            url = BASE_URL + url;
        }
        try {
            Response response = given()
                    .config(restAssuredConfig)
                    .contentType(ContentType.JSON)
                    .accept("*/*")
                    .headers(headers)
                    .body(body)
                    .log().all()
                    .request(method, url);
            response.then().statusCode(expectedStatusCode).log().all();
            return response.as(responseClass);
        } catch (Exception e) {
            fail("Ошибка отправки запроса по причине: " + e);
            return null;
        }
    }

    public <T> T execute(Method method, String url, Object body, int expectedStatusCode, Class<T> responseClass) {
        if (!url.startsWith("http")) {
            url = BASE_URL + url;
        }
        try {
            Response response = given()
                    .config(restAssuredConfig)
                    .contentType(ContentType.JSON)
                    .accept("*/*")
                    .body(body)
                    .log().all()
                    .request(method, url);
            response.then().statusCode(expectedStatusCode).log().all();
            return response.as(responseClass);
        } catch (Exception e) {
            fail("Ошибка отправки запроса по причине: " + e);
            return null;
        }
    }

    public <T> T execute(Method method, String url, Headers headers, int expectedStatusCode, Class<T> responseClass) {
        if (!url.startsWith("http")) {
            url = BASE_URL + url;
        }
        try {
            Response response = given()
                    .config(restAssuredConfig)
                    .contentType(ContentType.JSON)
                    .accept("*/*")
                    .headers(headers)
                    .log().all()
                    .request(method, url);
            response.then().statusCode(expectedStatusCode).log().all();
            return response.as(responseClass);
        } catch (Exception e) {
            fail("Ошибка отправки запроса по причине: " + e);
            return null;
        }
    }

    public <T> T execute(Method method, String url, int expectedStatusCode, Class<T> responseClass) {
        if (!url.startsWith("http")) {
            url = BASE_URL + url;
        }
        try {
            Response response = given()
                    .config(restAssuredConfig)
                    .contentType(ContentType.JSON)
                    .accept("*/*")
                    .log().all()
                    .request(method, url);
            response.then().statusCode(expectedStatusCode).log().all();
            return response.as(responseClass);
        } catch (Exception e) {
            fail("Ошибка отправки запроса по причине: " + e);
            return null;
        }
    }

    public <T> T execute(Method method, String url, RequestSpecification requestSpecification, int expectedStatusCode, Class<T> responseClass) {
        if (!url.startsWith("http")) {
            url = BASE_URL + url;
        }
        try {
            Response response = requestSpecification
                    .log().all()
                    .request(method, url);
            response.then().statusCode(expectedStatusCode).log().all();
            return response.as(responseClass);
        } catch (Exception e) {
            fail("Ошибка отправки запроса по причине: " + e);
            return null;
        }
    }
}