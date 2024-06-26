package org.alfabank.soacorn.employeeClient;

import io.qameta.allure.*;
import org.alfabank.soacorn.db.entity.CompanyEntity;
import org.alfabank.soacorn.db.entity.EmployeeEntity;
import org.alfabank.soacorn.db.repository.CompanyRepository;
import org.alfabank.soacorn.db.repository.EmployeeRepository;
import org.alfabank.soacorn.pojo.employeeClient.GetEmployeeResponsePojo;
import org.alfabank.soacorn.steps.api.employeeClient.GetEmployeeApiSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Feature("Создание сотрудника")
@DisplayName("GET:/employee")
@Owner("Кшнякин Ринат")
@SpringBootTest
class GetEmployeeTests {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    GetEmployeeApiSteps getEmployeeApiSteps;

    @Step("Предусловия")
    @BeforeEach
    public void setup() {
        employeeRepository.deleteAllEmployee();
        companyRepository.deleteAllCompany();
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

        List<GetEmployeeResponsePojo> employeeResponsePojo = getEmployeeApiSteps.getEmployee(
                activeCompany.getId(), 200);


    }
}
