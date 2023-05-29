import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class HomeWork {

    @Test
    public void HomeWorkFive(){

        JsonPath responseForCheck = RestAssured
                .given()
                .when()
                .post("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        LinkedHashMap answer = responseForCheck.get("messages.find {it.message == 'And this is a second message'}");
        System.out.println(answer);
    }
    @Test
    public void HomeWorkSix(){
        Response response =
                given().
                        contentType(ContentType.URLENC).
                        redirects().follow(false).
                        expect().
                        statusCode(301).
                        when()
                        .get("https://playground.learnqa.ru/api/long_redirect");
        Header headerLocationValue = response.getHeaders().get("Location");
        System.out.println(headerLocationValue);
    }
}
