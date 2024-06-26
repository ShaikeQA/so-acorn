package org.alfabank.soacorn.steps.api.employeeClient;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.alfabank.soacorn.pojo.employeeClient.PostEmployeErrorResponsePojo;
import org.alfabank.soacorn.pojo.employeeClient.PostEmployeeRequestPojo;
import org.alfabank.soacorn.steps.api.authClient.AuthApiSteps;
import org.alfabank.soacorn.steps.core.RequestSteps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;

@Service
@RequiredArgsConstructor
public class PostEmployeeApiSteps {

    private final AuthApiSteps authApiSteps;

    private final RequestSteps requestSteps;

    @Value("${urn.post.x-clients.employee}")
    private String POST_EMPLOYEE_URN;

    @Step("Создание клиента с несуществующим CompanyId. Ожидается код ответа {expectedStatusCode}")
    public PostEmployeErrorResponsePojo createEmployeeWithUnknowCompanyId(PostEmployeeRequestPojo postEmployeeRequestPojo, int expectedStatusCode) {
        RequestSpecification requestSpecification = given()
                .contentType(ContentType.JSON)
                .body(postEmployeeRequestPojo)
                .header("x-client-token", authApiSteps.getAdminToken());

        return requestSteps.execute(
                Method.POST,
                POST_EMPLOYEE_URN,
                requestSpecification,
                expectedStatusCode,
                PostEmployeErrorResponsePojo.class
        );
    }

}
