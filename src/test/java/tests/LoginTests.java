package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.login.LoginBodyModel;
import models.login.LoginResponseErrorModel;
import models.login.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.*;

@Owner("Nikita Postnikov")
@Epic("API Reqres")
@Feature("User authorization")
public class LoginTests extends TestBase {

    @Test
    @DisplayName("Проверка успешной авторизации")
    void successLoginTest() {

        LoginBodyModel user = LoginBodyModel.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();

        LoginResponseModel response = step("Вызов метода авторизации", () ->
                given(requestSpec)
                        .body(user)
                        .when()
                        .post("/login")
                        .then()
                        .spec(response200)
                        .extract().as(LoginResponseModel.class)
        );
        step("Проверка данных из ответа", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    @DisplayName("Проверка неуспешной авторизации без пароля")
    void unSuccessLoginTest() {

        LoginBodyModel user = LoginBodyModel.builder()
                .email("peter@klaven")
                .build();

        LoginResponseErrorModel response = step("Вызов метода авторизации", () ->
                given(requestSpec)
                        .body(user)
                        .when()
                        .post("/login")
                        .then()
                        .spec(response400)
                        .extract().as(LoginResponseErrorModel.class)
        );
        step("Проверка данных из ответа", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    @DisplayName("Проверка успешной регистрации")
    void successRegisterTest() {

        LoginBodyModel user = LoginBodyModel.builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();

        LoginResponseModel response = step("Вызов метода регистрации", () ->
                given(requestSpec)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(response200)
                        .extract().as(LoginResponseModel.class)
        );
        step("Проверка данных из ответа", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
        assertEquals(4, response.getId());
    }

    @Test
    @DisplayName("Проверка неуспешной регистрации без пароля")
    void unSuccessRegisterTest() {

        LoginBodyModel user = LoginBodyModel.builder()
                .email("sydney@fife")
                .build();

        LoginResponseErrorModel response = step("Вызов метода регистрации", () ->
                given(requestSpec)
                        .body(user)
                        .when()
                        .post("/register")
                        .then()
                        .spec(response400)
                        .extract().as(LoginResponseErrorModel.class)
        );
        step("Проверка данных из ответа", () ->
                assertEquals("Missing password", response.getError()));
    }
}