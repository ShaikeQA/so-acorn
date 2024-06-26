package org.alfabank.soacorn.companyClient;

import io.qameta.allure.*;
import org.alfabank.soacorn.db.entity.CompanyEntity;
import org.alfabank.soacorn.db.repository.CompanyRepository;
import org.alfabank.soacorn.db.repository.EmployeeRepository;
import org.alfabank.soacorn.steps.api.companyClient.GetCompanyDeleteByIdApiSteps;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Epic("X-Clients - Сервис записи на прием к профильным специалистам.")
@Feature("company - Контроллер")
@Story("Удалить компанию")
@DisplayName("GET:/company/delete/{id}")
@Owner("Кшнякин Ринат")
public class GetCompanyDeleteByIdApiStepsTests {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    GetCompanyDeleteByIdApiSteps getCompanyDeleteByIdApiSteps;

    @Step("Предусловия")
    @BeforeEach
    public void setup() {
        employeeRepository.deleteAllEmployee();
        companyRepository.deleteAllCompany();
    }

    @Test
    @DisplayName("Удаление активной компании по ее id. Ожидается код ответа: 200")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteActiveCompanyById() {

        CompanyEntity activeCompany = companyRepository.saveCompany(
                new CompanyEntity()
                        .setName("companyWithIsActiveTrue")
                        .setIsActive(true)
        );

        getCompanyDeleteByIdApiSteps
                .deleteCompany(activeCompany.getId(), 200);

        Awaitility.await()
                .atMost(Duration.ofSeconds(10))
                .pollInterval(Duration.ofSeconds(2))
                .untilAsserted(() -> assertNotNull(
                                companyRepository.findCompanyById(activeCompany.getId()).getDeletedAt()
                ));

        CompanyEntity companyEntity = companyRepository
                .findCompanyById(activeCompany.getId());

        assertNotNull(companyEntity);
        assertTrue(
                companyEntity.getDeletedAt().isAfter(companyEntity.getCreateTimestamp()) &&
                        companyEntity.getDeletedAt().isBefore(companyEntity.getCreateTimestamp().plusSeconds(10))
        );

    }
}
