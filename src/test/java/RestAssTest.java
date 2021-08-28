import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.Serializable;
import java.util.List;

import static io.restassured.RestAssured.*;

/*
на сайте https://financialmodelingprep.com/developer/docs в левом списке на
странице выбрать себе любую незанятую другими участниками команды категорию
функционала, в чате предупредите коллег о вашем выборе.
Разработать на выбранную функциональность как минимум 5 API тестов, причем:
1) один из них обязан быть с параметризацией тестовых данных.
2) два из них так или иначе должны осуществлять проверку данных внутри JSON
3) (опционально) приветствуется использование десериализации
 */

public class RestAssTest implements Serializable {

    final static String TOKEN = "dd59bd43e1bf0b34364ed913dff32c66";
    final static String URI = "https://financialmodelingprep.com/api/v3";
    final static String PATH = "/stock";
    final static String PATHTOKEN = "/stock/list?apikey="+TOKEN;

    @Test
    @DisplayName("Проверка корректности возвращаемых данных ")
    void testSymbolsList(){

        given()
                .baseUri(URI)
                .basePath(PATH)
                .log().all()
                .queryParam("apikey", TOKEN)
       .when().get("/list")
       .then().log().ifError()
                .body("[0].symbol", Matchers.matchesRegex("[A-Z]+"))
                .body("[0].name", Matchers.notNullValue())
                .body("[0].price", Matchers.notNullValue())
                .body("[0].exchange", Matchers.notNullValue());
    }

    @Test
    @DisplayName("Проверка того,что ссылка не пустая ")
    void linkNoyNullTest(){
        Response response = RestAssured.get(URI+PATHTOKEN);
        //System.out.println(response.asString());
        Assertions.assertNotNull(response,"Ссылка никуда не ведет");
        System.out.println("");
    }


    @Test
    @DisplayName("Проверка того,что мы можем получить данные из JSon ")
    public void testWithObjectSerialization(){
        List<Symbol> symbols = null;
        Response response =RestAssured.get(URI+PATHTOKEN);
        JsonPath symbolJson = response.jsonPath();
        symbols =symbolJson.getList("$",Symbol.class);
        symbols.stream().limit(10).forEach(System.out::println);
        Assertions.assertNotNull(symbols,"Список пустой");
    }




}
