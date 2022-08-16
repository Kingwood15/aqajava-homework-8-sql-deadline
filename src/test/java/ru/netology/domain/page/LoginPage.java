package ru.netology.domain.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.data.User;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("button.button");
    private SelenideElement errorMessage = $("[data-test-id='error-notification']");

    public LoginPage() {
        loginField.shouldBe(Condition.visible);
        passwordField.shouldBe(Condition.visible);
        loginButton.shouldBe(Condition.visible);
    }

    public VerificationPage validLogin(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidLogin(String login, String password) {
        cleanInputField(loginField);
        loginField.setValue(login);
        cleanInputField(passwordField);
        passwordField.setValue(password);
        loginButton.click();

        errorMessage.shouldBe(Condition.visible);
        errorMessage.find("button").click();
    }

    public void cleanInputField(SelenideElement field) {
        field.sendKeys(Keys.LEFT_CONTROL + "A");
        field.sendKeys(Keys.BACK_SPACE);
    }
}
