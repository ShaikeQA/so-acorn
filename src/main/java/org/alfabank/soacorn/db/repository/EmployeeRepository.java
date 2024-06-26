package org.alfabank.soacorn.db.repository;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.alfabank.soacorn.db.entity.EmployeeEntity;
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
        Allure.attachment("Найдено записей", String.valueOf(result.size()));
        return result;
    }

    @Step("Сохранение сотрудника в таблицу employee")
    default EmployeeEntity saveEmployee(EmployeeEntity employee) {
        return this.save(employee);
    }

    @Step("Получение всех сотрудников в базе employee с isActive == {isActive}")
    List<EmployeeEntity> findByIsActive(Boolean isActive);
}
