package org.alfabank.soacorn.demoQa;

import com.codeborne.selenide.WebDriverRunner;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.alfabank.soacorn.pojo.books.CollectionOfIsbn;
import org.alfabank.soacorn.pojo.books.GetBooksResponse;
import org.alfabank.soacorn.pojo.books.PostBookRequest;
import org.alfabank.soacorn.steps.core.RequestSteps;
import org.alfabank.soacorn.steps.front.AuthPage;
import org.alfabank.soacorn.steps.front.PersonalAccountPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Добавление книг")
@SpringBootTest
public class FrontAddGetBooksResponseTests {

    @Autowired
    RequestSteps requestSteps;

    @Autowired
    AuthPage authPage;

    @Value("${selenide.auth.login}")
    private String AUTH_LOGIN;

    @Value("${selenide.auth.password}")
    private String AUTH_PASSWORD;

    @Value("${selenid.get.bookStore.v1.books}")
    private String GET_BOOKS_URL;

    @Value("${selenid.post.bookStore.v1.books}")
    private String POST_BOOKS_URL;

    @Value("${selenid.delete.bookStore.v1.books}")
    private String DELETE_BOOK_URL;

    @Value("${selenide.auth.userId}")
    private String USER_ID;


    @BeforeEach
    public void setUp() {
        DeleteAllBookFromUser();
    }

    @AfterEach
    public void tearDown() {
        DeleteAllBookFromUser();
    }

    @Test
    @DisplayName("В таблицу добавлено 6 книг")
    public void emptyBooksTable() throws InterruptedException {

        RequestSpecification requestSpecification = given().auth().preemptive().basic(AUTH_LOGIN, AUTH_PASSWORD)
                .contentType(ContentType.JSON);
        GetBooksResponse responseBook = requestSteps.execute(
                Method.GET,
                GET_BOOKS_URL,
                requestSpecification,
                200,
                GetBooksResponse.class);

        PostBookRequest postBookRequest = new PostBookRequest();
        postBookRequest.setUserId(USER_ID);

        for (int i = 0; i < 6; i++) {
            postBookRequest.getCollectionOfIsbns()
                    .add(new CollectionOfIsbn(responseBook.getBooks().get(i).isbn));
        }
        requestSpecification = requestSpecification.body(postBookRequest);
        requestSteps.execute(
                Method.POST,
                POST_BOOKS_URL,
                requestSpecification,
                201
        );

        authPage.openPage();
        WebDriverRunner.getWebDriver().manage().window().maximize();
        PersonalAccountPage personalAccountPage = authPage.signIn(AUTH_LOGIN, AUTH_PASSWORD);
        JavascriptExecutor js = (JavascriptExecutor) WebDriverRunner.getWebDriver();
        Thread.sleep(1000);
        js.executeScript("window.scrollBy(0,500)");
        personalAccountPage.selectCountOfRow(10);

        step("В таблице 6 строк", () -> assertEquals(6, personalAccountPage.countOfBookInTable()));


    }

    private void DeleteAllBookFromUser() {
        RequestSpecification requestSpecification =
                given()
                        .queryParam("UserId", USER_ID)
                        .auth().preemptive().basic(AUTH_LOGIN, AUTH_PASSWORD);

        requestSteps.execute(
                Method.DELETE,
                DELETE_BOOK_URL,
                requestSpecification,
                204
        );
    }

}

