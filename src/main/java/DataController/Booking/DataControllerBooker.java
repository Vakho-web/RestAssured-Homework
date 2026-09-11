package DataController.Booking;

import Models.Booking.BookModel;
import Models.Booking.BookingDates;

public class DataControllerBooker {

    public BookModel getBookModel(){
        BookModel bookModel = new BookModel();
        BookingDates bookingDates = new BookingDates();
        return bookModel;
    }


    public static BookModel getSampleBookModel() {
        BookingDates dates = new BookingDates();
        dates.checkin = "2026-09-01";
        dates.checkout = "2026-09-10";

        BookModel book = new BookModel();
        book.firstname = "John";
        book.lastname = "Doe";
        book.totalprice = 250;
        book.depositpaid = true;
        book.bookingdates = dates;
        book.additionalneeds = "Breakfast";

        return book;
    }

}