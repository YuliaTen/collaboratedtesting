package ru.devray.day13;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BDDTest {
    //BDD - Behavior Driven Development
    @Test
    void simpleBddTest(){
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/users?page=2")

                //когда система
        .when().get()

        .then().statusCode(200)
                .and().header("Content-Type", "application/json; charset=utf-8");
    }

    //TODO регистрация

    @Test
    void testRegister(){
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/register")
                .header(new Header("Content-Type", "application/json"))
                .body("{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}")

        .when().post()

        .then()
                        .statusCode(200)
                        .body("id", Matchers.notNullValue())
                        .body("token", Matchers.notNullValue());
    }
    @Test
    void testRegisterNegative(){
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/register")
                .header(new Header("Content-Type", "application/json"))
                .body("{\"email\": \"eve.holt@reqres.in\"}")

        .when().post()

        .then()
                        .statusCode(400)
                        .body("error", Matchers.notNullValue());
    }

    @Test
    void testRegisterAndPrintResponse(){
        Response response =
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/register")
                .header(new Header("Content-Type", "application/json"))
                .body("{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}")

        .when().post();
        System.out.println((String) response.jsonPath().get("token"));
//        .then()
//                        .statusCode(200)
//                        .body("id", Matchers.notNullValue())
//                        .body("token", Matchers.notNullValue());
    }



    //TODO логин
}
