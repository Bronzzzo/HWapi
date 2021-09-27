package restapiexample;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import groovy.json.StringEscapeUtils;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;

@Execution(ExecutionMode.CONCURRENT)
public class EmployeeTest {

    //    данные о работнике по ФИ и возрасту
    //    mvn clean test -Dtest=EmployeeTest

    private final String URL = "https://dummy.restapiexample.com/api/v1/employees";

    @ParameterizedTest
    @MethodSource("dataForGetEmployeeName")

    public void getEmployeeByName(String name, String expectedStatus) {
        RestAssured
                .given()
                .log().uri()
                .when()
                .get(URL + "?name=" + name)
                .then()
                .log().status()
                .log().body()
                .spec(
                        new ResponseSpecBuilder()
                                .expectStatusCode(200)
                                .expectBody("data[0].employee_name", equalTo(expectedStatus))
                                .build()
                );

    }

    private static Stream<Arguments> dataForGetEmployeeName() {
        return Stream.of(

                Arguments.of("Tiger Nixon","Tiger Nixon")
        );

    }
    @ParameterizedTest
    @MethodSource("dataForGetEmployeeAge")

    public void getEmployeeAgeByName(String name, int expectedStatus) {
        RestAssured
                .given()
                .log().uri()
                .when()
                .get(URL + "?name=" + name)
                .then()
                .log().status()
                .log().body()
                .spec(
                        new ResponseSpecBuilder()
                                .expectStatusCode(200)
                                .expectBody("data[0].employee_age".toString(), equalTo(expectedStatus))
                                .build()
                );

    }

    private static Stream<Arguments> dataForGetEmployeeAge() {
        return Stream.of(

                Arguments.of("Tiger Nixon", 61)
        );

    }

}
