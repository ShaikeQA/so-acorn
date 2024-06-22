package org.alfabank.soacorn;

import org.alfabank.soacorn.db.entity.CompanyEntity;
import org.alfabank.soacorn.db.repository.CompanyRepository;
import org.alfabank.soacorn.db.repository.EmployeeRepository;
import org.alfabank.soacorn.steps.api.RequestSteps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class SoAcornApplicationTests {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RequestSteps requestSteps;

    @Test
    void contextLoads() {
        System.out.println(employeeRepository.findById(6735));
        CompanyEntity companyEntity = companyRepository.findById(10359)
                .orElseThrow(() -> {
                    fail("Company not found");
                    return null;
                });
        System.out.println(companyEntity);
    }
}
