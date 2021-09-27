package rest;


import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.json.JSONObject;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;

@Execution(ExecutionMode.CONCURRENT)
public class TestRestClass {

    private final String URL = "http://dummy.restapiexample.com/api/v1/";

    private final Headers HEADERS = new Headers(
            new Header("Content-Type","application/json"),
            new Header("Authorization","Api-Key AQVN1HLWCOruKGZnVPDl-mN_sJM7HH3o03FWjYS1")
    );


    @ParameterizedTest
    @MethodSource("dataForGetArgsName")
    public void postmanFirstGetTest(String name, String expectedStatus){
        RestAssured
                .given()
                .log().uri()
                .log().body()
                .body(
                        new JSONObject()
                                .put("", "")
                                .put("","")
                                .put("","")
                )
                .headers(HEADERS)
                .when()
                .post(URL)
                .then()
                .log().status()
                .log().body()
                .spec(
                        new ResponseSpecBuilder()
                                .expectStatusCode(200)
                                .expectBody("headers.host", equalTo(expectedStatus))
                                .build()
                );
    }
    // Генератор тестовых данных для getCharacterByNameAndCheckStatus
    private static Stream<Arguments> dataForGetArgsName() {
        return Stream.of(
                Arguments.of("postman-echo.com", "postman-echo.com")
        );
    }


}

