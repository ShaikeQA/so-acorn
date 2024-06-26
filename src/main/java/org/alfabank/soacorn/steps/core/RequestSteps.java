package org.alfabank.soacorn.steps.core;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.fail;

@Component
@RequiredArgsConstructor
public class RequestSteps {

    private final JsonPretty jsonPretty;

    RestAssuredConfig restAssuredConfig;
    /**
     * Базовый url для отправки запроса.
     */
    @Value("${rest.assured.request.base.url}")
    private String BASE_URL;
    /**
     * Максимальное время ожидания (в миллисекундах), в течение которого
     * HTTP-клиент будет пытаться установить соединение с сервером.
     */
    @Value("${rest.assured.http.connection.timeout}")
    private Integer HTTP_CONNECT_TIMEOUT;
    /**
     * Максимальное время ожидания (в миллисекундах), доступное для чтения
     * данных из открытого сокета или для записи в открытый сокет после установки соединения.
     */
    @Value("${rest.assured.http.socket.timeout}")
    private Integer HTTP_SOCKET_TIMEOUT;

    @PostConstruct
    public void init() {
        restAssuredConfig = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", HTTP_CONNECT_TIMEOUT)
                        .setParam("http.socket.timeout", HTTP_SOCKET_TIMEOUT));
    }

    /**
     * Отправка http запроса.
     *
     * @param method               Тип запрос.
     * @param url                  Адрес запроса.
     *                             Если передананный url начинается на http, то запрос отправляется на переданный url.
     *                             Иначе к переданному урлу в начало добавляется из application.properties по пути request.base.url.
     * @param requestSpecification Подготовленный запрос c помощью билдера RequestSpecification.
     * @param expectedStatusCode   Ожидаемый http код ответа.
     * @param responseClass        Класс, в который нужно преобразовать ответ. Например: AnyClassPojo.class
     * @return Объект класса responseClass.
     * При возникновении исключения. Вызов метода JUnit5 fail().
     * Тест прекращает работу и будет отмечен как не пройденный
     */
    public <T> T execute(Method method, String url, RequestSpecification requestSpecification, int expectedStatusCode, Class<T> responseClass) {
        if (!url.startsWith("http")) {
            url = BASE_URL + url;
        }
        try {
            Response response = requestSpecification
                    .config(restAssuredConfig)
                    .log().all()
                    .request(method, url);

            Allure.addAttachment(
                    "Ответ сервиса.json", "application/json",
                    jsonPretty.pretty(response.getBody().asString())
            );
            response.then().log().all().statusCode(expectedStatusCode);
            return response.as(responseClass);
        } catch (Exception e) {
            fail("Ошибка отправки запроса по причине: " + e);
            return null;
        }
    }

    /**
     * Отправка http запроса.
     *
     * @param method               Тип запрос.
     * @param url                  Адрес запроса.
     *                             Если передананный url начинается на http, то запрос отправляется на переданный url.
     *                             Иначе к переданному урлу в начало добавляется из application.properties по пути request.base.url.
     * @param requestSpecification Подготовленный запрос c помощью билдера RequestSpecification.
     * @param expectedStatusCode   Ожидаемый http код ответа.
     * @return Объект класса Response.class.
     * При возникновении исключения. Вызов метода JUnit5 fail().
     * Тест прекращает работу и будет отмечен как не пройденный
     */
    public Response execute(Method method, String url, RequestSpecification requestSpecification, int expectedStatusCode) {
        if (!url.startsWith("http")) {
            url = BASE_URL + url;
        }
        try {
            Response response = requestSpecification
                    .config(restAssuredConfig)
                    .log().all()
                    .request(method, url);

            Allure.addAttachment(
                    "Ответ сервиса.json", "application/json",
                    jsonPretty.pretty(response.getBody().asString())
            );
            response.then().statusCode(expectedStatusCode).log().all();
            return response;
        } catch (Exception e) {
            fail("Ошибка отправки запроса по причине: " + e);
            return null;
        }
    }
}



