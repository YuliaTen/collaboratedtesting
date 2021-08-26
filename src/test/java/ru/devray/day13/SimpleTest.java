package ru.devray.day13;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {

    @Test
    void simpleTest(){
        Response response = RestAssured.get("https://ya.ru");

        System.out.println(response.asString());
        assertNotNull(response);
    }

    @Test
    void testReqResApi(){
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");

        System.out.println(response.asString());
        assertNotNull(response);
        assertEquals(200, response.getStatusCode(), "Сервер должен был вернуть код ошибки 4**");
    }

    @Test
    void testHeaders(){
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");

        List<Header> headers = response.headers().asList();
        headers.stream().forEach(System.out::println);

        String contentType = response.header("Content-Type");
        assertEquals("application/json", contentType, "Сервер вернул неожиданный COntent-Type");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode(), "Сервер должен был вернуть код ошибки 4**");
    }

    @Test
    void testJsonContentsWrongWay(){
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");

        assertTrue(response.asString().contains("page"));
    }

    @Test
    void testJsonContentsRightWay(){
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");

        JsonPath path = response.jsonPath();
        //"$.data[2].email" JsonPath
        //"data[2].email" GPath
        String fieldResult = path.get("data[2].email").toString(); //GPath

        System.out.println(fieldResult.toString());
    }





}
