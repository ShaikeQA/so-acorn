package org.alfabank.soacorn.steps.api.employeeClient;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.alfabank.soacorn.pojo.employeeClient.GetEmployeeResponsePojo;
import org.alfabank.soacorn.steps.core.RequestSteps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.restassured.RestAssured.given;

@Service
@RequiredArgsConstructor
public class GetEmployeeApiSteps {

    private final RequestSteps requestSteps;

    @Value("${urn.get.x-clients.employee}")
    private String GET_EMPLOYEE_URN;

    @Step("Получение сотрудников по CompanyId. Ожидается код ответа {expectedStatusCode}")
    public List<GetEmployeeResponsePojo> getEmployee(int companyId, int expectedStatusCode) {
        RequestSpecification requestSpecification = given()
                .queryParam("company", companyId);

        return requestSteps.execute(
                Method.GET,
                GET_EMPLOYEE_URN,
                requestSpecification,
                expectedStatusCode
        ).as(new TypeRef<>() {
        });
    }

}
