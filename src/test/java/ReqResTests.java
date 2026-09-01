import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqResTests {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    // 1. გამოიძახეთ GET /api/users?page=1 და დაბეჭდეთ მიღებული Response Body.
    @Test
    public void test1_printResponseBody() {
        Response response = given()
                .when()
                .get("/users?page=1");

        System.out.println("Task: Response Body");
        response.prettyPrint();
    }

    /*
     * 2. იგივე Request გაუშვით BDD სტილით given() / when() / then() და
     *    გადაამოწმეთ, რომ Status Code არის 200.
     */
    @Test
    public void test2_verifyStatusCodeBDD() {
        given()
                .when()
                .get("/users?page=1")
                .then()
                .statusCode(200);
    }

    /*
     * 3. then().body() და Hamcrest equalTo()-ს გამოყენებით გადაამოწმეთ, რომ
     *    Response-ში page = 1 და total_pages > 0.
     */
    @Test
    public void test3_verifyBodyHamcrest() {
        given()
                .when()
                .get("/users?page=1")
                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("total_pages", greaterThan(0));
    }

    /*
     * 4. URL-ში ?page=2-ის ხელით მიწერის ნაცვლად გამოიყენეთ
     *    .queryParam("page", 2) და გადაამოწმეთ, რომ Response-ში page = 2.
     */
    @Test
    public void test4_verifyQueryParam() {
        given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("page", equalTo(2));
    }

    /*
     * 5. jsonPath()-ის გამოყენებით Response-იდან წამოიღეთ:
     *    - page
     *    - total_pages
     *    - პირველი მომხმარებლის id
     *    - პირველი მომხმარებლის email
     *
     *    მიღებული მნიშვნელობები დაბეჭდეთ კონსოლში.
     */
    @Test
    public void test5_extractAndPrintValues() {
        Response response = given()
                .queryParam("page", 1)
                .when()
                .get("/users");

        JsonPath jsonPath = response.jsonPath();

        int page = jsonPath.getInt("page");
        int totalPages = jsonPath.getInt("total_pages");
        int firstUserId = jsonPath.getInt("data[0].id");
        String firstUserEmail = jsonPath.getString("data[0].email");

        System.out.println("Task: Extracted Values");
        System.out.println("Page: " + page);
        System.out.println("Total Pages: " + totalPages);
        System.out.println("First User ID: " + firstUserId);
        System.out.println("First User Email: " + firstUserEmail);
    }

    /*
     * 6. data.email JSON Path-ის გამოყენებით ყველა email შეინახეთ List ტიპის
     *    ცვლადში. ციკლით გადაუარეთ სიას და გადაამოწმეთ, რომ არცერთი email არ
     *    არის null ან ცარიელი.
     */
    @Test
    public void test6_verifyEmailListNotNullOrEmpty() {
        Response response = given()
                .queryParam("page", 1)
                .when()
                .get("/users");

        List<String> emails = response.jsonPath().getList("data.email");

        for (String email : emails) {
            Assert.assertNotNull(email, "Email should not be null");
            Assert.assertFalse(email.trim().isEmpty(), "Email should not be empty");
        }
    }

    /*
     * 7. ყველა email-ს გადაუარეთ ციკლით და SoftAssert-ის გამოყენებით
     *    გადაამოწმეთ, რომ თითოეული შეიცავს @ სიმბოლოს. შეცდომის შემთხვევაში
     *    შეტყობინებაში დაბეჭდეთ პრობლემური email-ის მნიშვნელობაც.
     */
    @Test
    public void test7_verifyEmailContainsAtSign() {
        Response response = given()
                .queryParam("page", 1)
                .when()
                .get("/users");

        List<String> emails = response.jsonPath().getList("data.email");
        SoftAssert softAssert = new SoftAssert();

        for (String email : emails) {
            softAssert.assertTrue(email.contains("@"), "Email does not contain '@': " + email);
        }

        softAssert.assertAll();
    }

    /*
     * 8. მთლიანად data შეინახეთ List<Map<String, Object>> ტიპის ცვლადში.
     *    ციკლით გადაუარეთ მომხმარებლებს და თითოეულზე დაბეჭდეთ:
     *    - id
     *    - first_name
     *    - last_name
     *    - email
     */
    @Test
    public void test8_printAllUsersFromListMap() {
        Response response = given()
                .queryParam("page", 1)
                .when()
                .get("/users");

        List<Map<String, Object>> users = response.jsonPath().getList("data");

        System.out.println("Task: User List");
        for (Map<String, Object> user : users) {
            System.out.println("ID: " + user.get("id"));
            System.out.println("First Name: " + user.get("first_name"));
            System.out.println("Last Name: " + user.get("last_name"));
            System.out.println("Email: " + user.get("email"));
            System.out.println();
        }
    }

    /*
     * 9. პირველი Request-იდან წამოიღეთ total_pages. შემდეგ ციკლით გამოიძახეთ
     *    ყველა გვერდი და თითოეული გვერდიდან წამოიღეთ ყველა მომხმარებელი.
     *    გადაამოწმეთ, რომ თითოეული მომხმარებლის id > 0.
     */
    @Test
    public void test9_verifyUserIdAcrossAllPages() {
        int totalPages = given()
                .when()
                .get("/users")
                .then()
                .extract()
                .path("total_pages");

        for (int page = 1; page <= totalPages; page++) {
            List<Integer> ids = given()
                    .queryParam("page", page)
                    .when()
                    .get("/users")
                    .then()
                    .extract()
                    .path("data.id");

            for (int id : ids) {
                Assert.assertTrue(id > 0, "User ID should be greater than 0");
            }
        }
    }

    /*
     * 10. total_pages-ის მიხედვით გამოიძახეთ ყველა გვერდი და ყველა
     *     მომხმარებელზე SoftAssert-ის გამოყენებით გადაამოწმეთ:
     *     - id > 0
     *     - email არ არის null
     *     - email არ არის ცარიელი
     *     - email შეიცავს @ სიმბოლოს
     *     - first_name არ არის null ან ცარიელი
     *     - last_name არ არის null ან ცარიელი
     *
     *     ყველა SoftAssert შემოწმების დასრულების შემდეგ გამოიყენეთ:
     *     softAssert.assertAll();
     */
    @Test
    public void test10_validateAllUsersAllPagesSoftAssert() {
        SoftAssert softAssert = new SoftAssert();

        int totalPages = given()
                .when()
                .get("/users")
                .then()
                .extract()
                .path("total_pages");

        for (int page = 1; page <= totalPages; page++) {
            List<Map<String, Object>> users = given()
                    .queryParam("page", page)
                    .when()
                    .get("/users")
                    .then()
                    .extract()
                    .path("data");

            for (Map<String, Object> user : users) {
                int id = (Integer) user.get("id");
                String email = (String) user.get("email");
                String firstName = (String) user.get("first_name");
                String lastName = (String) user.get("last_name");

                softAssert.assertTrue(id > 0, "ID should be > 0 for user ID: " + id);
                softAssert.assertNotNull(email, "Email is null for user ID: " + id);
                softAssert.assertFalse(email.trim().isEmpty(), "Email is empty for user ID: " + id);
                softAssert.assertTrue(email.contains("@"), "Email missing '@' symbol: " + email);
                softAssert.assertNotNull(firstName, "First name is null for user ID: " + id);
                softAssert.assertFalse(firstName.trim().isEmpty(), "First name is empty for user ID: " + id);
                softAssert.assertNotNull(lastName, "Last name is null for user ID: " + id);
                softAssert.assertFalse(lastName.trim().isEmpty(), "Last name is empty for user ID: " + id);
            }
        }

        softAssert.assertAll();
    }
}