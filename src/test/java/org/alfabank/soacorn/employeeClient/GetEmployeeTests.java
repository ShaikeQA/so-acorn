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

import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Epic("X-Clients - Сервис записи на прием к профильным специалистам.")
@Feature("employee - Контроллер")
@Story("Получить список сотрудников для компании")
@DisplayName("GET:/employee")
@Owner("Кшнякин Ринат")
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

        List<GetEmployeeResponsePojo> listEmployee = getEmployeeApiSteps.getEmployee(
                activeCompany.getId(), 200);

        step("Все сотрудники с параметром active == true", () -> {
            var sumOfIsActives = false;
            for (GetEmployeeResponsePojo employeeResponsePojo : listEmployee) {
                if (!employeeResponsePojo.isActive()) {
                    sumOfIsActives = false;
                    break;
                } else sumOfIsActives = true;
            }
            assertTrue(sumOfIsActives);
        });

        step("Количество сотрудников в ответе api совпадает с количеством сотрудников с isActive == true из БД", () ->
                assertEquals(listEmployee.size(), employeeRepository.findByIsActive(true).size()));

        step("В ответе api есть сотрудник, добавленный в бд с параметром isActive == true", () ->
                assertEquals(1,
                        listEmployee.stream().filter(employee -> employee.getId() == activeEmployee.getId()).count()
                ));

        step("В ответе api нет сотрудника, добавленного в бд с параметром isActive == false", () ->
                assertEquals(
                        0,
                        listEmployee.stream().filter(employee -> employee.getId() == inactiveEmployee.getId()).count()
                ));
    }
}

