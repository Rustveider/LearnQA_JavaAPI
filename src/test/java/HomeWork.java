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
}

