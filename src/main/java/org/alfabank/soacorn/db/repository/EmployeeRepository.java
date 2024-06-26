package org.alfabank.soacorn.db.repository;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.alfabank.soacorn.db.entity.EmployeeEntity;
import org.alfabank.soacorn.steps.core.JsonPretty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Step("Удаление всех сотрудников из таблицы employee")
    default void deleteAllEmployee() {
        this.deleteAll();
    }

    @Step("Поиск всех записей из таблицы employee")
    default List<EmployeeEntity> findAllEmployee() {
        List<EmployeeEntity> result = this.findAll();
        Allure.addAttachment("Результат запроса", JsonPretty.objectToJson(result));
        return result;
    }

    @Step("Сохранение сотрудника в таблицу employee")
    default EmployeeEntity saveEmployee(EmployeeEntity employee) {
        EmployeeEntity result = this.save(employee);
        Allure.addAttachment("Результат запроса", JsonPretty.objectToJson(result));
        return result;
    }

    @Step("Получение всех сотрудников в базе employee с isActive == {isActive}")
    default List<EmployeeEntity> findEmployeeByIsActive(Boolean isActive) {
        List<EmployeeEntity> result = this.findByIsActive(isActive);
        Allure.addAttachment("Результат запроса", JsonPretty.objectToJson(result));
        return result;
    }

    List<EmployeeEntity> findByIsActive(Boolean isActive);
}
