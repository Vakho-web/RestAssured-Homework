package Steps.Booking;

import Calls.Booking.BookerCalls;
import Models.Booking.BookModel;
import io.restassured.response.Response;
import org.testng.Assert;

public class BookerSteps {

    BookerCalls bookerCalls = new BookerCalls();
    int createdBookingId;
    BookModel expectedBookModel;

    // Task 1
    public BookerSteps createBookingWithRawJson(String firstname, String lastname, int totalprice) {
        Response response = bookerCalls.createBookingRawJson(firstname, lastname, totalprice);
        Assert.assertEquals(response.getStatusCode(), 200);

        createdBookingId = response.jsonPath().getInt("bookingid");
        return this;
    }

    // Task 2
    public BookerSteps createBookingWithModel(BookModel bookModel) {
        expectedBookModel = bookModel;
        Response response = bookerCalls.createBookingWithModel(bookModel);
        Assert.assertEquals(response.getStatusCode(), 200);

        createdBookingId = response.jsonPath().getInt("bookingid");
        return this;
    }

    // Task 3
    public BookerSteps verifyBookingDetailsWithJsonPath(String firstname, String lastname, int totalprice, String additionalneeds) {
        Response response = bookerCalls.getBookingById(createdBookingId);
        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(response.jsonPath().getString("firstname"), firstname);
        Assert.assertEquals(response.jsonPath().getString("lastname"), lastname);
        Assert.assertEquals(response.jsonPath().getInt("totalprice"), totalprice);
        Assert.assertEquals(response.jsonPath().getString("additionalneeds"), additionalneeds);
        return this;
    }

    // Task 4
    public BookerSteps verifyBookingDetailsWithModelDeserialization() {
        Response response = bookerCalls.getBookingById(createdBookingId);
        Assert.assertEquals(response.getStatusCode(), 200);

        BookModel actualBook = response.as(BookModel.class);

        Assert.assertEquals(actualBook.firstname, expectedBookModel.firstname);
        Assert.assertEquals(actualBook.lastname, expectedBookModel.lastname);
        Assert.assertEquals(actualBook.totalprice, expectedBookModel.totalprice);
        Assert.assertEquals(actualBook.depositpaid, expectedBookModel.depositpaid);
        Assert.assertEquals(actualBook.additionalneeds, expectedBookModel.additionalneeds);
        Assert.assertEquals(actualBook.bookingdates.checkin, expectedBookModel.bookingdates.checkin);
        Assert.assertEquals(actualBook.bookingdates.checkout, expectedBookModel.bookingdates.checkout);
        return this;
    }
}
