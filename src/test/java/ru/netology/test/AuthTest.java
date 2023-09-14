package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class AuthTest {

    @BeforeEach
    void setup() {open("http://localhost:9999");}

    @AfterAll static  void teardown() {cleanDatabase();}



    @Test
    void shouldSuccessfulLoginIfRegisteredActiveUser() {

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisiblity();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldLoginIfNotRegisteredActiveUserPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        loginPage.invalidPassword(authInfo);
    }

    @Test
    void shouldLoginIfNotRegisteredActiveUserLogin() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
    }

    @Test
    void shouldIncorrectAuthCode() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerify();
    }

    @Test
    void shouldBlockedAppAfterLimitTries() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        VerificationPage verificationPage = loginPage.validLogin(authInfo);
        verificationPage.invalidVerify();
        open("http://localhost:9999");
        var loginPage2 = new LoginPage();
        var authInfo2 = DataHelper.getAuthInfo();
        VerificationPage verificationPage2 = loginPage2.validLogin(authInfo2);
        verificationPage2.blockedVerify();
    }
}



