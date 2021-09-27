package reqresin;

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

public class LastName_FirstName_Test {

    private final String URL = "https://reqres.in/api/users";


    // Кейс. Получение данных по фамилии
    // Не будет работать - ищет по соответствию данных 1 пользователю(data[0])
    // mvn clean test -Dtest=LastName_FirstName_Test
    @ParameterizedTest
    @MethodSource("dataForGetLastname")
    public void getCharacterByLastnameAndCheckStatus(String name, String expectedStatus) {
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
                                .expectBody("data[0].last_name", equalTo(expectedStatus))
                                .build()
                );
    }

    // Генератор тестовых данных для getCharacterByNameAndCheckStatus
    private static Stream<Arguments> dataForGetLastname() {
        return Stream.of(
                Arguments.of("Bluth", "Bluth"),
                Arguments.of("Weaver", "Weaver")
        );
    }

    // Кейс 2. Получение данных о пользователе и проверка в body
    @ParameterizedTest
    @MethodSource("dataForGetFirstname")
    public void getCharacterByFirstname(String name, String expectedStatus) {
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
                                .expectBody("data[0].first_name", equalTo(expectedStatus))
                                .build()
                );
    }

    // Генератор тестовых данных для getCharacterByNameAndCheckStatus
    private static Stream<Arguments> dataForGetFirstname() {
        return Stream.of(
                Arguments.of("Bluth", "George"),
                Arguments.of("Weaver", "Janet")
        );
    }


}
