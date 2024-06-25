package org.alfabank.soacorn;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.*;
import org.alfabank.soacorn.db.entity.CompanyEntity;
import org.alfabank.soacorn.db.entity.EmployeeEntity;
import org.alfabank.soacorn.db.repository.CompanyRepository;
import org.alfabank.soacorn.db.repository.EmployeeRepository;
import org.alfabank.soacorn.pojo.employee.GetEmployeeResponsePojo;
import org.alfabank.soacorn.pojo.employee.PostEmployeErrorResponsePojo;
import org.alfabank.soacorn.pojo.employee.PostEmployeeRequestPojo;
import org.alfabank.soacorn.steps.api.EmployeeApiSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Создание сотрудника")
@DisplayName("POST:/employee")
@Owner("Кшнякин Ринат")
@SpringBootTest
class EmployeeTest {

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EmployeeApiSteps employeeApiSteps;

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

        PostEmployeErrorResponsePojo errorResponsePojo = employeeApiSteps.createEmployeeWithUnknowCompanyId(
                postEmployeeRequestPojo, 500);

        step("Валдиация выходных параметров метода", () -> {
            assertEquals("500", errorResponsePojo.getStatusCode());
            assertEquals("Internal server error", errorResponsePojo.getMessage());
        });

        step("В базе не создалось записей", () -> {
            assertEquals(0, employeeRepository.findAllEmployee().size());
        });
    }

    @Test
    @DisplayName("Получение списка сотрудников. Неактивный сотрудник не отображается в списке. Ожидается код ответа: 200")
    @Severity(SeverityLevel.CRITICAL)
    void getListOfEmployee() {

        CompanyEntity activeCompany = companyRepository.saveCompany(
                new CompanyEntity()
                        .setName("companyWithIsActiveTrue")
                        .setIsActive(true)
        );

        EmployeeEntity activeEmployee = employeeRepository.saveEmployee(
                new EmployeeEntity()
                        .setIsActive(true)
                        .setFirstName("Тест")
                        .setLastName("Тестов")
                        .setMiddleName("Тестович")
                        .setPhone("+79998887766")
                        .setCompany(activeCompany)
        );
        EmployeeEntity inactiveEmployee = employeeRepository.saveEmployee(
                new EmployeeEntity()
                        .setIsActive(false)
                        .setFirstName("Иванов")
                        .setLastName("Иван")
                        .setMiddleName("Иванович")
                        .setPhone("+79998887755")
                        .setCompany(activeCompany)
        );

        List<GetEmployeeResponsePojo> employeeResponsePojo = employeeApiSteps.getEmployee(
                activeCompany.getId(), 200);


    }
}
