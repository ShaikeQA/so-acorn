package org.alfabank.soacorn.demoQa;

import com.codeborne.selenide.WebDriverRunner;
import org.alfabank.soacorn.steps.front.AuthPage;
import org.alfabank.soacorn.steps.front.PersonalAccountPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codeborne.selenide.Condition.text;
import static io.qameta.allure.Allure.step;

@DisplayName("Авторизация")
@SpringBootTest
public class FrontAuthTests {

    @Autowired
    AuthPage authPage;

    @Value("${selenide.auth.login}")
    private String AUTH_LOGIN;

    @Value("${selenide.auth.password}")
    private String AUTH_PASSWORD;

    @BeforeEach
    public void setUp() {
        authPage.openPage();
        WebDriverRunner.getWebDriver().manage().window().maximize();

    }

    @Test
    @DisplayName("Проверка пустого списка книг в таблице")
    public void emptyBooksTable() {
        PersonalAccountPage personalAccountPage = authPage.signIn(AUTH_LOGIN, AUTH_PASSWORD);
        step("Таблица с книгами пустая", () ->
                personalAccountPage.getEmptyTableElement().shouldHave(text("No rows found")));

    }
}
