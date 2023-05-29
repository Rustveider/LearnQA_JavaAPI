import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;

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
}
