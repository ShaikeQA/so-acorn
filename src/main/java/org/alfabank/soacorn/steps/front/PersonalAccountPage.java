package org.alfabank.soacorn.steps.front;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selenide.$;

@Data
public class PersonalAccountPage {

    private SelenideElement emptyTableElement = $(byClassName("rt-noData"));

}
