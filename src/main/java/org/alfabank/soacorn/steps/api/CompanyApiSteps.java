package org.alfabank.soacorn.steps.api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.alfabank.soacorn.pojo.company.GetCompanyResponsePojo;
import org.alfabank.soacorn.steps.api.core.RequestSteps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;

@Service
@RequiredArgsConstructor
public class CompanyApiSteps {

    private final RequestSteps requestSteps;

    @Value("${urn.get.x-clients.company}")
    private String GET_COMPANY_URN;

    @Step("Получение списка компаний с параметром active == {active}. Ожидается код ответа {expectedStatusCode}")
    public List<GetCompanyResponsePojo> getCompany(Boolean active, int expectedStatusCode) {
        RequestSpecification requestSpecification = given()
                .queryParam("active", active);

        Response response = requestSteps.execute(
                Method.GET,
                GET_COMPANY_URN,
                requestSpecification,
                expectedStatusCode
        );

        List<GetCompanyResponsePojo> result = response.as(new TypeRef<>() {
        });

        for (GetCompanyResponsePojo getCompanyResponsePojo : result) {
            Allure.addAttachment("Результат", getCompanyResponsePojo.toString());
        }

        return result;
    }

}