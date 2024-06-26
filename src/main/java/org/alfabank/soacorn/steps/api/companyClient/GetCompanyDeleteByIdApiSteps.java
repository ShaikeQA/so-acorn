package org.alfabank.soacorn.steps.api.companyClient;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.alfabank.soacorn.pojo.companyClient.GetCompanyDeleteByIdPojo;
import org.alfabank.soacorn.steps.api.authClient.AuthApiSteps;
import org.alfabank.soacorn.steps.core.RequestSteps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

@Service
@RequiredArgsConstructor
public class GetCompanyDeleteByIdApiSteps {

    private final RequestSteps requestSteps;

    private final AuthApiSteps authApiSteps;

    @Value("${urn.get.x-clients.company.delete.id}")
    private String GET_COMPANY_URN;

    @Step("Удаление компании с id == {companyId}. Ожидается код ответа {expectedStatusCode}")
    public GetCompanyDeleteByIdPojo deleteCompany(int companyId, int expectedStatusCode) {
        RequestSpecification requestSpecification = given()
                .pathParam("id", companyId)
                .header("x-client-token", authApiSteps.getAdminToken())
                .contentType(ContentType.JSON);

        return requestSteps.execute(
                Method.GET,
                GET_COMPANY_URN,
                requestSpecification,
                expectedStatusCode,
                GetCompanyDeleteByIdPojo.class
        );
    }
}
