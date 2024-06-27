package org.alfabank.soacorn.steps.front;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Data;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Data
public class PersonalAccountPage {

    private SelenideElement emptyTableElement = $(byClassName("rt-noData"));
    private SelenideElement selectElement = $(".select-wrap.-pageSizeOptions select[aria-label='rows per page']");

    @Step("Выбрать {row} в таблице")
    public void selectCountOfRow(int row) {
        selectElement.click();
        selectElement.selectOptionContainingText(row + " rows");
    }

    @Step("Кол-во книг в таблице")
    public Integer countOfBookInTable() {
        ElementsCollection elements = $$(".rt-tr-group:has(.action-buttons)");
        return elements.size();
    }

}
