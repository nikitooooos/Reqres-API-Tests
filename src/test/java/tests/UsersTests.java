package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import models.single_user.SingleUserResponseModel;
import models.list_users.ListUsersResponseModel;
import models.create_user.CreateUserModel;
import models.create_user.CreateUserResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.*;

@Owner("Nikita Postnikov")
@Feature("Действия с пользователем")
public class UsersTests extends TestBase {

    @Test
    @DisplayName("Получение списка пользователей")
    void checkListUsersTest() {
        ListUsersResponseModel response = step("Отправка запроса", () ->
                given(requestSpec)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(response200)
                        .extract().as(ListUsersResponseModel.class)
        );

        step("Проверка данных из ответа", () -> {
            assertEquals(12, response.getTotal());
            assertEquals("https://reqres.in/#support-heading", response.getSupport().getUrl());
            assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", response.getSupport().getText());
        });
    }

    @Test
    @DisplayName("Проверка создания пользователя")
    void checkCreateTest() {
        CreateUserModel createBody = CreateUserModel.builder()
                .name("morpheus")
                .job("leader")
                .build();

        CreateUserResponseModel response = step("Создание пользователя", () ->
                given(requestSpec)
                        .body(createBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(response201)
                        .extract().as(CreateUserResponseModel.class)
        );

        step("Проверка данных из ответа", () -> {
            assertEquals("morpheus", response.getName());
            assertEquals("leader", response.getJob());
        });
    }

    @Test
    @DisplayName("Проверка изменения данных пользователя")
    void checkUpdateTest() {
        CreateUserModel createBody = CreateUserModel.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        CreateUserResponseModel response = step("Изменение данных пользователя", () ->
                given(requestSpec)
                        .body(createBody)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(response200)
                        .extract().as(CreateUserResponseModel.class)
        );

        step("Проверка данных из ответа", () -> {
            assertEquals("morpheus", response.getName());
            assertEquals("zion resident", response.getJob());
        });
    }

    @Test
    @DisplayName("Проверка удаления пользователя")
    void checkDeleteTest() {
        step("Удаление пользователя", () ->
                given(requestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(response204)
        );
    }

    @DisplayName("Получение списка пользователей с задержкой")
    @Test
    void checkListUsersDelayTest() {
        ListUsersResponseModel users = step("Получение списка пользователей", () ->
                given(requestSpec)
                        .when()
                        .get("users?delay=3")
                        .then()
                        .spec(response200)
                        .extract().as(ListUsersResponseModel.class));

        step("Проверка данных из ответа", () -> {
            assertEquals(1, users.getPage());
            assertEquals("janet.weaver@reqres.in", users.getData().get(1).getEmail());
            assertEquals("https://reqres.in/#support-heading", users.getSupport().getUrl());
        });
    }

    @DisplayName("Получение списка данных одного пользователя")
    @Test
    void getSingleUserTest() {
        SingleUserResponseModel response = step("Отправка запроса", () ->
                given(requestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(response200)
                        .extract().as(SingleUserResponseModel.class));

        step("Проверка данных из ответа", () -> {
            assertEquals(2, response.getData().getId());
            assertEquals("Janet", response.getData().getFirstName());
        });
    }
}