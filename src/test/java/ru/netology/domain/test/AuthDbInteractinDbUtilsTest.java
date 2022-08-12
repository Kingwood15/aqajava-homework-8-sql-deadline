package ru.netology.domain.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthDbInteractinDbUtilsTest {

    @BeforeAll
    public static void setUpForSUT() {
        //DataHelper.clearSUTData();
    }

    @BeforeEach
    void shouldStart() {
        //DataHelper.requestCreateUser();
        open("http://localhost:9999/");
    }

    @Test
    void shouldValidAuthForVasyaAccountTest() {
        String vasyaLogin = "vasya";
        String vasyaPass = "qwerty123";

        var loginPage = new LoginPage();
        var authInfoForVasya = DataHelper.getAuthInfo(vasyaLogin);
        var verificationPage = loginPage.validLogin(vasyaLogin, vasyaPass);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfoForVasya);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldCheckThreeTimesInvalidPassInputForVasyaTest() {
        String vasyaLogin = "vasya";
        String vasyaPass = "qwerty123";
        String invalidPass1 = DataHelper.getRandPass();
        String invalidPass2 = DataHelper.getRandPass();
        String invalidPass3 = DataHelper.getRandPass();

        var loginPage = new LoginPage();
        var authInfoForVasya = DataHelper.getAuthInfo(vasyaLogin);
        System.out.println("authInfo: " + authInfoForVasya);

        loginPage.invalidLogin(vasyaLogin, invalidPass1);
        loginPage.invalidLogin(vasyaLogin, invalidPass2);
        loginPage.invalidLogin(vasyaLogin, invalidPass3);
        loginPage.invalidLogin(vasyaLogin, vasyaPass);
    }
}
