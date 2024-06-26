package org.alfabank.soacorn.companyClient;

import io.qameta.allure.*;
import org.alfabank.soacorn.db.entity.CompanyEntity;
import org.alfabank.soacorn.db.repository.CompanyRepository;
import org.alfabank.soacorn.db.repository.EmployeeRepository;
import org.alfabank.soacorn.pojo.companyClient.GetCompanyResponsePojo;
import org.alfabank.soacorn.steps.api.companyClient.GetCompanyApiSteps;
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
@Feature("company - Контроллер")
@Story("Получить список компаний")
@DisplayName("GET:/company")
@Owner("Кшнякин Ринат")
class GetCompanyTests {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    GetCompanyApiSteps getCompanyApiSteps;

    @Step("Предусловия")
    @BeforeEach
    public void setup() {
        employeeRepository.deleteAllEmployee();
        companyRepository.deleteAllCompany();
    }

    @Test
    @DisplayName("Фильтрация компаний по признаку active = true")
    @Severity(SeverityLevel.CRITICAL)
    void getListOfActiveCompany() {
        CompanyEntity activeCompany = companyRepository.saveCompany(
                new CompanyEntity()
                        .setName("companyWithIsActiveTrue")
                        .setIsActive(true)
        );
        CompanyEntity inActiveCompany = companyRepository.saveCompany(
                new CompanyEntity()
                        .setName("companyWithIsActiveFalse")
                        .setIsActive(false)
        );

        List<GetCompanyResponsePojo> companies = getCompanyApiSteps.getCompany(true, 200);

        step("Все компании с параметром active == true", () -> {
            var sumOfIsActives = false;
            for (GetCompanyResponsePojo company : companies) {
                if (!company.isActive()) {
                    sumOfIsActives = false;
                    break;
                } else sumOfIsActives = true;
            }
            assertTrue(sumOfIsActives);
        });

        step("Количество компаний с active == true в ответе api совпадает с БД", () ->
                assertEquals(companies.size(), companyRepository.findByIsActive(true).size()));

        step("В ответе api есть компания, добавленная в бд с параметром active == true", () ->
                assertEquals(1,
                        companies.stream().filter(company -> company.getId() == activeCompany.getId()).count()
                ));

        step("В ответе api нет компании, добавленной в бд с параметром active == false", () ->
                assertEquals(
                        0,
                        companies.stream().filter(company -> company.getId() == inActiveCompany.getId()).count()
                ));
    }
}
