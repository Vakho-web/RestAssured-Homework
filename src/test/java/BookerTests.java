import DataController.Booking.DataControllerBooker;
import Models.Booking.BookModel;
import Steps.Booking.BookerSteps;
import org.testng.annotations.Test;

public class BookerTests {

    @Test(description = "task 1")
    public void CreateBookingWithRawJson() {
        String firstname = "Alex";
        String lastname = "Smith";
        int totalprice = 180;

        new BookerSteps()
                .createBookingWithRawJson(firstname, lastname, totalprice);
    }

    @Test(description = "task 2")
    public void CreateBookingWithModel() {
        BookModel sampleBook = DataControllerBooker.getSampleBookModel();

        new BookerSteps()
                .createBookingWithModel(sampleBook);
    }

    @Test(description = "task 3")
    public void VerifyBookingWithJsonPath() {
        String firstname = "Maria";
        String lastname = "Garcia";
        int totalprice = 300;

        new BookerSteps()
                .createBookingWithRawJson(firstname, lastname, totalprice)
                .verifyBookingDetailsWithJsonPath(firstname, lastname, totalprice, "Breakfast");
    }

    @Test(description = "task 4")
    public void VerifyBookingWithDeserialization() {
        BookModel sampleBook = DataControllerBooker.getSampleBookModel();

        new BookerSteps()
                .createBookingWithModel(sampleBook)
                .verifyBookingDetailsWithModelDeserialization();
    }
}