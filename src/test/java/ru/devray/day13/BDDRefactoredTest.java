package ru.devray.day13;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BDDRefactoredTest {

    static RequestSpecification spec;

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



    @BeforeAll
    static void setUp(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri("https://reqres.in/api")
                .setBasePath("/register")
                .addHeader("Content-Type", "application/json");

        spec = builder.build();

    }

    @Test
    void testRegister(){
        given().spec(spec)
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
                .spec(spec)
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

    @ParameterizedTest
    @MethodSource("dataProvider")
    void testLogin(String body, String expectedStatusCode, String fieldName, String regexp){
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/login")
                .header(new Header("Content-Type", "application/json"))
                .body(body)
        .when()
                .post()
        .then()
                .statusCode(Integer.parseInt(expectedStatusCode))
                .body(fieldName, Matchers.matchesPattern(regexp));
    }

    public static List<String[]> dataProvider(){
        String[] strings1 = new String[]{"{\"email\": \"eve.holt@reqres.in\",\"password\": \"cityslicka\"}","200", "token","\\w{17}"};
        String[] strings2 = new String[]{"{\"email\": \"eve.holt@reqres.in\",\"password\": \"cityslicka\"}","200", "token","\\w{17}"};
        String[] strings3 = new String[]{"{\"email\": \"eve.holt@reqres.in\"}","400", "error","[a-zA-Z\\s]+"};
        List<String[]> list =  List.of(strings1, strings2, strings3);

        return list;
    }

    @Test
    void testNegativeLogin(){
        given()
                .baseUri("https://reqres.in/api")
                .basePath("/login")
                .header(new Header("Content-Type", "application/json"))
                .body("{\"email\": \"eve.holt@reqres.in\"}")
                .when()
                .post()
                .then()
                .statusCode(400)
                .body("error", Matchers.matchesPattern("\\w{17}"));
    }
}
