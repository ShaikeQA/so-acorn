package org.alfabank.soacorn.employeeClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import org.alfabank.soacorn.db.repository.CompanyRepository;
import org.alfabank.soacorn.db.repository.EmployeeRepository;
import org.alfabank.soacorn.pojo.employeeClient.PostEmployeErrorResponsePojo;
import org.alfabank.soacorn.pojo.employeeClient.PostEmployeeRequestPojo;
import org.alfabank.soacorn.steps.api.employeeClient.PostEmployeeApiSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Создание сотрудника")
@DisplayName("POST:/employee")
@Owner("Кшнякин Ринат")
@SpringBootTest
public class PostEmployeeTests {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    PostEmployeeApiSteps postEmployeeApiSteps;

    @Step("Предусловия")
    @BeforeEach
    public void setup() {
        employeeRepository.deleteAllEmployee();
        companyRepository.deleteAllCompany();
    }

    @Test
    @DisplayName("Передан несуществующий companyId, ожидается ошибка,  Ожидается код ответа: 500")
    @Severity(SeverityLevel.CRITICAL)
    void createEmployeeWithUnknownCompanyId() throws IOException {

        PostEmployeeRequestPojo postEmployeeRequestPojo = mapper.readValue(
                Path.of("src/test/resources/body/PostEmployeeRequestWithUnknowCompanyId.json").toFile(),
                PostEmployeeRequestPojo.class);

        PostEmployeErrorResponsePojo errorResponsePojo = postEmployeeApiSteps.createEmployeeWithUnknowCompanyId(
                postEmployeeRequestPojo, 500);

        step("Валдиация выходных параметров метода", () -> {
            assertEquals("500", errorResponsePojo.getStatusCode());
            assertEquals("Internal server error", errorResponsePojo.getMessage());
        });

        step("В базе не создалось записей", () ->
                assertEquals(0, employeeRepository.findAllEmployee().size()));
    }
}
