package org.alfabank.soacorn.db.repository;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.alfabank.soacorn.db.entity.CompanyEntity;
import org.alfabank.soacorn.steps.core.JsonPretty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

    @Step("Получение компании по id = {id} в таблице company")
    default CompanyEntity findCompanyById(int id) {
        CompanyEntity result = this.findById(id).orElse(null);
        Allure.addAttachment("Результат запроса", JsonPretty.objectToJson(result));
        return result;
    }

    @Step("Удаление всех компаний из таблицы company")
    default void deleteAllCompany() {
        this.deleteAll();
    }

    @Step("Сохранение компании в базе company")
    default CompanyEntity saveCompany(CompanyEntity companyEntity) {
        CompanyEntity result = this.save(companyEntity);
        Allure.addAttachment("Результат запроса", JsonPretty.objectToJson(result));
        return result;
    }

    @Step("Получение всех компаний в базе company с isActive == {isActive}")
    default List<CompanyEntity> findCompanyByIsActive(Boolean isActive) {
        List<CompanyEntity> result = this.findByIsActive(isActive);
        Allure.addAttachment("Результат запроса", JsonPretty.objectToJson(result));
        return result;
    }

    List<CompanyEntity> findByIsActive(Boolean isActive);

}
