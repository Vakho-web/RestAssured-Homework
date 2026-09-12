package Calls.Booking;

import Models.Booking.BookModel;
import Specs.Booking.RequestSpecificationBooking;
import Utils.Config;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BookerCalls {


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
                .post(Config.BOOKING_BASE_URL + Config.BOOKING_BASE_PATH);
    }

    // Task 2
    public Response createBookingWithModel(BookModel bookModel) {
        return given()
                .spec(RequestSpecificationBooking.requestSpecification())
                .body(bookModel)
                .when()
                .post();
    }

    public Response addBookingModel(BookModel bookModel) {
        return given()
                .spec(RequestSpecificationBooking.requestSpecification())
                .body(bookModel)
                .when()
                .post()
                .then()
                .extract()
                .response();
    }

    public Response auth() {
        return given()
                .contentType("application/json")
                .body("""
                        {
                          "username": "admin",
                          "password": "password123"
                        }
                        """)
                .when()
                .post(Config.BOOKING_BASE_URL + "/auth")
                .then()
                .extract()
                .response();
    }

    public Response deleteBookingModel(int id, String token) {
        return given()
                .spec(RequestSpecificationBooking.requestSpecification())
                .cookie("token", token)
                .pathParam("id", id)
                .when()
                .delete("/{id}")
                .then()
                .extract()
                .response();
    }

    // Tasks 3 & 4
    public Response getBookingById(int bookingId) {
        return given()
                .pathParam("bookingId", bookingId)
                .when()
                .get(Config.BOOKING_BASE_URL + Config.BOOKING_BASE_PATH + "/{bookingId}")
                .then()
                .extract()
                .response();
    }
}
