package org.alfabank.soacorn.steps.front;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Service
@RequiredArgsConstructor
public class AuthPage {

    @Value("${selenide.auth.url}")
    private String LOGIN_URL;

    private final SelenideElement userNameField = $("#userName");
    private final SelenideElement passwordField = $("#password");
    private final SelenideElement loginButton = $("#login");

    @Step("Войти в личный кабинет с login = {login} и password = {password}")
    public PersonalAccountPage signIn(String login, String password) {
        userNameField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        return new PersonalAccountPage();
    }

    @Step("Открыть страницу авторизации")
    public void openPage() {
        open(LOGIN_URL);
    }
}
