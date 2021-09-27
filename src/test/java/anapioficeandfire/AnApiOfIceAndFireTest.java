package anapioficeandfire;

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
public class AnApiOfIceAndFireTest {

    // mvn clean test -Dtest=AnApiOfIceAndFireTest

    private final String URL = "https://www.anapioficeandfire.com/api/books";

    // Кейс 1. Получение данных книги по названию
    @ParameterizedTest
    @MethodSource("dataForGetInfoByNameOfBook")
    public void getNameBook(String name, String expectedStatus) {
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
                                .expectBody("name[0]", equalTo(expectedStatus))
                                .build()
                );
    }

    // Генератор тестовых данных для getInfoInfoByNameOfBook
    private static Stream<Arguments> dataForGetInfoByNameOfBook() {
        return Stream.of(
                Arguments.of("A Game of Thrones", "A Game of Thrones"),
                Arguments.of("A Clash of Kings", "A Clash of Kings"),
                Arguments.of("The Princess and the Queen", "The Princess and the Queen")
        );
    }

    // Кейс 2. Получение данных о книге и проверка страны в body
    @ParameterizedTest
    @MethodSource("dataForGetBookByNameAndCheckStatus")
    public void getCountryByName(String name, String expectedStatus) {
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
                                .expectBody("country[0]", equalTo(expectedStatus))
                                .build()
                );
    }

    // Генератор тестовых данных для getCharacterByNameAndCheckStatus
    private static Stream<Arguments> dataForGetBookByNameAndCheckStatus() {
        return Stream.of(
                Arguments.of("A Game of Thrones", "United States"),
                Arguments.of("A Clash of Kings", "United States"),
                Arguments.of("The Princess and the Queen", "United States")
        );
    }


}
