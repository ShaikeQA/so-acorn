package org.alfabank.soacorn.steps.api;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.alfabank.soacorn.pojo.companyController.GetCompanyResponsePojo;
import org.alfabank.soacorn.pojo.employeeController.GetEmployeeResponsePojo;
import org.alfabank.soacorn.pojo.employeeController.PostEmployeErrorResponsePojo;
import org.alfabank.soacorn.pojo.employeeController.PostEmployeeRequestPojo;
import org.alfabank.soacorn.steps.api.core.RequestSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@Service
@RequiredArgsConstructor
public class EmployeeApiSteps {

    private final AuthApiSteps authApiSteps;

    private final RequestSteps requestSteps;

    @Value("${urn.post.x-clients.employee}")
    private String POST_EMPLOYEE_URN;

    @Value("${urn.get.x-clients.employee}")
    private String GET_EMPLOYEE_URN;

    @Step("Создание клиента с несуществующим CompanyId. Ожидается код ответа {expectedStatusCode}")
    public PostEmployeErrorResponsePojo createEmployeeWithUnknowCompanyId(PostEmployeeRequestPojo postEmployeeRequestPojo, int expectedStatusCode) {
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON)
                .body(postEmployeeRequestPojo)
                .header("x-client-token", authApiSteps.getAdminToken());

        PostEmployeErrorResponsePojo result = requestSteps.execute(
                Method.POST,
                POST_EMPLOYEE_URN,
                requestSpecification,
                expectedStatusCode,
                PostEmployeErrorResponsePojo.class
        );

        Allure.addAttachment("Результат", result.toString());

        return result;
    }

    @Step("Получение сотрудников по CompanyId. Ожидается код ответа {expectedStatusCode}")
    public List<GetEmployeeResponsePojo> getEmployee(int companyId, int expectedStatusCode) {
        RequestSpecification requestSpecification = given()
                .queryParam("company", companyId);

        Response response = requestSteps.execute(
                Method.GET,
                GET_EMPLOYEE_URN,
                requestSpecification,
                expectedStatusCode
        );

        List<GetEmployeeResponsePojo> result = response.as(new TypeRef<>() {
        });

        Allure.addAttachment("Результат", result.toString());

        return result;
    }

}
