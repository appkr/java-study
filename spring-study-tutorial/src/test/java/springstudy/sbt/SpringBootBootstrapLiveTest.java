package springstudy.sbt;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringStudyTutorialApplication.class },
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT = "http://localhost:8080/api/books";

    @Test
    public void whenGetAllBooks_thenOk() {
        get(API_ROOT).then().statusCode(HttpStatus.OK.value());
    }

    @Test
    @Transactional
    public void whenGetBooksByTitle_thenOk() {
        Book book = createRandomBook();
        createBookAsUri(book);

        Response response = get(API_ROOT + "/title/" + book.getTitle());

        assertEquals(HttpStatus.OK.value(), response.statusCode());
        assertTrue(response.as(List.class).size() > 0);
    }

    @Test
    @Transactional
    public void whenGetCreatedBookById_thenOk() {
        Book book = createRandomBook();
        createBookAsUri(book);

        Response response = get(API_ROOT + "/title/" + book.getTitle());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        /**
         * NOTE. jsonPath always returns array even if it is a single string value
         * @see https://stackoverflow.com/questions/23608050/getting-a-single-value-from-a-json-object-using-jsonpath
         */
        assertEquals(book.getTitle(), response.jsonPath().get("title[0]"));
    }

    @Test
    public void whenGetNotExistBookById_thenNotFound() {
        get(API_ROOT + "/100").then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Transactional
    public void whenCreateNewBook_thenCreated() {
        Book book = createRandomBook();

        Response response = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    @Transactional
    public void whenInvalidBook_thenError() {
        Book book = createRandomBook();
        book.setAuthor(null);

        Response response = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    @Transactional
    public void whenUpdatedCreatedBook_thenUpdated() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);

        book.setId(Long.parseLong(location.split("api/books/")[1]));
        book.setAuthor("newAuthor");

        Response response = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        Response response1 = get(location);

        assertEquals(HttpStatus.OK.value(), response1.getStatusCode());
        assertEquals("newAuthor", response1.jsonPath().get("author"));
    }

    @Test
    @Transactional
    public void whenDeleteCreatedBook_thenOk() {
        Book book = createRandomBook();
        String location = createBookAsUri(book);
        Response response = delete(location);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode());
        get(location).then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    private Book createRandomBook() {
        Book book = new Book();
        book.setTitle(randomAlphabetic(10));
        book.setAuthor(randomAlphabetic(15));
        return book;
    }

    private String createBookAsUri(Book book) {
        Response response = given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(book)
            .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }
}
