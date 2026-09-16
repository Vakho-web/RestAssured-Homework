package DataController.Booking;

import Models.Booking.BookModel;
import Models.Booking.BookingDates;

public class DataControllerBooker {

    public BookModel getBookModel(){
        BookModel bookModel = new BookModel();
        BookingDates bookingDates = new BookingDates();
        bookModel.setFirstname("John");
        bookModel.setLastname("Doe");
        bookModel.setTotalprice(250);
        bookModel.setDepositpaid(true);
        bookModel.setAdditionalneeds("Breakfast");

        bookingDates.setCheckin("2026-09-01");
        bookingDates.setCheckout("2026-09-10");
        bookModel.setBookingdates(bookingDates);

        return bookModel;
    }


}