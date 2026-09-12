import DataController.Booking.DataControllerBooker;
import Steps.Booking.BookerSteps;
import org.testng.annotations.Test;

public class BookerTests {
    BookerSteps bookerSteps = new BookerSteps();
    DataControllerBooker dataControllerBooker = new DataControllerBooker();

    @Test(description = "lecture booking test")
    public void AddDeleteBookingModel() {
        bookerSteps.setBooking(dataControllerBooker.getBookModel())
                .addBookingModel()
                .auth()
                .deleteBooking()
                .checkDeletedBooking();
        ;
    }

    @Test
    public void AddBookingModelGeneric() {
        bookerSteps.setData(dataControllerBooker.getBookModel())
                .addBookingModelGeneric()
                .auth()
                .deleteBooking()
                .checkDeletedBooking();
        ;
    }


}