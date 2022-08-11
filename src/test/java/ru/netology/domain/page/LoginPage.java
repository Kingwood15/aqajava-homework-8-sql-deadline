package ru.netology.domain.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.domain.data.DataHelper;
import ru.netology.mode.User;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("button.button");

    public VerificationPage validLogin(User info) {
        loginField.setValue(info.getLogin());
        //passwordField.setValue(info.getPassword());
        passwordField.setValue(DataHelper.getValidPass());
        loginButton.click();
        return new VerificationPage();
    }
}
