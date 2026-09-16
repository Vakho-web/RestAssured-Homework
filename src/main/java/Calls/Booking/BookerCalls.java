package Calls.Booking;

import Models.Booking.BookModel;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookerCalls {

    private final String BASE_URL = "https://restful-booker.herokuapp.com";

    // Task 1
    public Response createBookingRawJson(String firstname, String lastname, int totalprice) {
        String body = "{\n" +
                "  \"firstname\": \"" + firstname + "\",\n" +
                "  \"lastname\": \"" + lastname + "\",\n" +
                "  \"totalprice\": " + totalprice + ",\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2026-09-01\",\n" +
                "    \"checkout\": \"2026-09-10\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Breakfast\"\n" +
                "}";

        return given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(BASE_URL + "/booking");
    }

    // Task 2
    public Response createBookingWithModel(BookModel bookModel) {
        return given()
                .contentType("application/json")
                .body(bookModel)
                .when()
                .post(BASE_URL + "/booking");
    }

    // Tasks 3 & 4
    public Response getBookingById(int bookingId) {
        return given()
                .when()
                .get(BASE_URL + "/booking/" + bookingId);
    }
}
