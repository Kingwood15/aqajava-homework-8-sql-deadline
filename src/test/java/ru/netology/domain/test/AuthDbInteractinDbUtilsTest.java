package ru.netology.domain.test;

import org.junit.jupiter.api.*;
import ru.netology.domain.data.DataHelper;
import ru.netology.domain.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthDbInteractinDbUtilsTest {

    @BeforeAll
    public static void resetSUT() {
        DataHelper.clearSUTData();
        DataHelper.resetSUTData();
    }

    @BeforeEach
    void shouldStart() {
        open("http://localhost:9999/");
    }

    @AfterAll
    public static void cleanDatabase() {
        DataHelper.clearSUTData();
    }

    @Test
    void shouldValidAuthForVasyaAccountTest() {
        var loginPage = new LoginPage();
        var authInfoForVasya = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfoForVasya.getLogin(), authInfoForVasya.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authInfoForVasya);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldValidAuthForAddNewFakerAccountTest() {

        var loginPage = new LoginPage();
        var authIntoFaker = DataHelper.createUser();
        var verificationPage = loginPage.validLogin(authIntoFaker.getLogin(), authIntoFaker.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authIntoFaker);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldCheckThreeTimesInvalidPassInputForVasyaTest() {
        var authInfoForVasya = DataHelper.getAuthInfo();
        String invalidPass1 = DataHelper.getRandPass();
        String invalidPass2 = DataHelper.getRandPass();
        String invalidPass3 = DataHelper.getRandPass();

        var loginPage = new LoginPage();
        loginPage.invalidLogin(authInfoForVasya.getLogin(), invalidPass1);
        loginPage.invalidLogin(authInfoForVasya.getLogin(), invalidPass2);
        loginPage.invalidLogin(authInfoForVasya.getLogin(), invalidPass3);
        loginPage.invalidLogin(authInfoForVasya.getLogin(), authInfoForVasya.getPassword());
    }
}
