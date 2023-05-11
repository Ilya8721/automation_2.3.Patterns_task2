package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    SelenideElement loginInput = $("[data-test-id='login'] input");
    SelenideElement passwordInput = $("[data-test-id='password'] input");
    SelenideElement continueButton = $(".button");
    SelenideElement personalAccount = $("[id='root'] h2");
    SelenideElement errorPopUp = $("[data-test-id='error-notification'] .notification__content");

    String personalAccountTitle = "Личный кабинет";
    String errorMassage = "Неверно указан логин или пароль";
    String blockedUserMassage = "Пользователь заблокирован";

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser

        loginInput.setValue(registeredUser.getLogin());
        passwordInput.setValue(registeredUser.getPassword());
        continueButton.click();
        personalAccount
                .shouldHave(Condition.text(personalAccountTitle))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser

        loginInput.setValue(notRegisteredUser.getLogin());
        passwordInput.setValue(notRegisteredUser.getPassword());
        continueButton.click();
        errorPopUp
                .shouldHave(Condition.text(errorMassage))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser

        loginInput.setValue(blockedUser.getLogin());
        passwordInput.setValue(blockedUser.getPassword());
        continueButton.click();
        errorPopUp
                .shouldHave(Condition.text(blockedUserMassage))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser

        loginInput.setValue(wrongLogin);
        passwordInput.setValue(registeredUser.getPassword());
        continueButton.click();
        errorPopUp
                .shouldHave(Condition.text(errorMassage))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword

        loginInput.setValue(registeredUser.getLogin());
        passwordInput.setValue(wrongPassword);
        continueButton.click();
        errorPopUp
                .shouldHave(Condition.text(errorMassage))
                .shouldBe(Condition.visible);
    }
}
