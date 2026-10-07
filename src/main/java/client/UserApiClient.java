package client;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import resources.EnvConfig;

import static io.restassured.RestAssured.given;
import static io.restassured.config.HttpClientConfig.httpClientConfig;

public class UserApiClient {
    public static final String BASE_PATH = "/api";

    public static RequestSpecification setUp() {
        return given()
                .config(RestAssuredConfig.config()
                        .httpClient(httpClientConfig()
                                .setParam("http.connection.timeout", 5000)   // 5 секунд на соединение
                                .setParam("http.socket.timeout", 5000)))
                .log().all()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .baseUri(EnvConfig.BASE_URI)
                .basePath(BASE_PATH);
    }

    @Step("Получение bearer токена")
    public String getBearerToken(ValidatableResponse loginResp) {
        return loginResp
                .extract()
                .path("accessToken");
    }

    @Step("Логин пользователя в системе")
    public ValidatableResponse loginUser(String email, String password) {
        String json = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        return setUp()
                .body(json)
                .when()
                .post(EnvConfig.LOGIN)
                .then();
    }

    @Step("Удаление пользователя")
    public void deleteUser(String bearerToken) {
        setUp()
                .header("Authorization", bearerToken)
                .when()
                .delete(EnvConfig.USER)
                .then()
                .log().all();
    }

}
