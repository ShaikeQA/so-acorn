package org.alfabank.soacorn.db.repository;

import io.qameta.allure.Step;
import org.alfabank.soacorn.db.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

    @Step("Получение компании по id = {id}")
    default CompanyEntity findCompanyById(int id){
        return this.findById(id).orElse(null);
    }

    @Step("Удаление всех компаний из таблицы company")
    default void deleteAllCompany() {
        this.deleteAll();
    }

    @Step("Сохранение компании в базе company")
    default CompanyEntity saveCompany(CompanyEntity companyEntity) {
        return this.save(companyEntity);
    }

    @Step("Получение всех компаний в базе company с isActive == {isActive}")
    List<CompanyEntity> findByIsActive(Boolean isActive);

}
