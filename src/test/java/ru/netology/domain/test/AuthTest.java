package ru.netology.domain.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeAll
    public static void setUpForSUT() {
        //DataHelper.clearSUTData();
    }

    @BeforeEach
    void shouldStart() {
        //DataHelper.requestCreateUser();
        open("http://localhost:9999/");
    }

    //!!!не получается получить код верификации
    @Test
    void shouldValidAuthTest() {
        //создание переменной для обращения к странице ввода логина и пароля
        var loginPage = new LoginPage();
        //создание переменной пользователя с логином и паролем
        var authInfo = DataHelper.getAuthInfo();
        System.out.println("authInfo: " + authInfo);
        //создание переменной страницы верификации, к которой попадаем через валидные логин и пароль
        var verificationPage = loginPage.validLogin(authInfo);
        //создание переменной с кодом верификации
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        System.out.println("verificationCode: " + verificationCode);
        //попадаем в личный кабинет, после успешно введеного кода верификации
        var dashboardPage = verificationPage.validVerify(verificationCode);
    }
}
