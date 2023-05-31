import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
    @Test
    public void HomeWorkSeven(){
        Response response =
                given().
                        contentType(ContentType.URLENC).
                        redirects().follow(false).
                        when()
                        .get("https://playground.learnqa.ru/api/long_redirect");
        String headerLocationValus = response.getHeader("Location");
        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        System.out.println(headerLocationValus);

        for(; statusCode != 200 ;) {
            Response responseTwo =
                    given().
                            contentType(ContentType.URLENC).
                            redirects().follow(false).
                            when()
                            .get(headerLocationValus);
            headerLocationValus = responseTwo.getHeader("location");
            statusCode = responseTwo.getStatusCode();
            System.out.println(statusCode);
            System.out.println(headerLocationValus);
        }
    }
    @Test
    public void HomeWorkEight() throws InterruptedException {
        JsonPath responseForToken = RestAssured
                .given()
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String answer = responseForToken.get("token");
        //     System.out.println(answer);
        int time = responseForToken.get("seconds");
        Map<String, String> headers = new HashMap<>();
        headers.put("token", answer);

        JsonPath responseStatusJob = RestAssured
                .given()
                .params(headers)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String status = responseStatusJob.get("status");
        //  System.out.println(status);
        String statusJob = "Job is NOT ready";
        for(;status.equalsIgnoreCase(statusJob);){
            Thread.sleep((time * 1000));
            JsonPath responseResultJob = RestAssured
                    .given()
                    .params(headers)
                    .when()
                    .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                    .jsonPath();
            responseResultJob.prettyPrint();
            status = responseResultJob.get("status");
        }
    }
    @Test
    public void HomeWorkNine() throws InterruptedException {
        String[] seasons  = new String[] {"password ", "123456", "12345678", "qwerty", "abc123", "monkey", "1234567", "letmein", "trustno1", "dragon", "baseball",
                "111111", "iloveyou", "master", "sunshine", "ashley", "bailey", "passw0rd", "shadow", "123123", "654321", "superman", "qazwsx", "michael", "Football",
        "123456789", "12345", "123456789", "111111", "1234", "1234567890", "princess", "football", "adobe123", "welcome", "login", "admin",
                "qwerty123", "solo", "1q2w3e4r", "666666", "photoshop", "1qaz2wsx", "qwertyuiop", "mustang", "121212", "starwars", "access", "flower", "555555",
                "lovely", "7777777", "michael", "!@#$%^&*", "jesus", "password1", "hello", "charlie", "888888", "696969", "qwertyuiop", "hottie", "freedom",
                "aa123456", "ninja", "azerty", "solo", "loveme", "whatever", "donald", "batman", "zaq1zaq1", "password1", "000000", "qwerty123", "123qwe"};

        for(int i = 0; i != seasons.length; i++ ){
            Map<String, String> headers = new HashMap<>();
            headers.put("login", "super_admin");
            headers.put("password", seasons[i]);

            Response getAuthCookie = given()
                    .body(headers)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

            String cookie = getAuthCookie.getCookie("auth_cookie");
            System.out.println(seasons[i]);

            String checkAuthCookie = given()
                    .headers("auth_cookie", cookie)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .then()
                    .contentType(ContentType.HTML)
                    .extract()
                    .response().print();

            String yourAnswer = checkAuthCookie;
            String correctAnswer = "You are authorized";
            if(yourAnswer.equalsIgnoreCase(correctAnswer)){
                System.out.println(seasons[i]);
                System.out.println(yourAnswer+"FFFFFF");
                break;
            }

        }
    }
}

