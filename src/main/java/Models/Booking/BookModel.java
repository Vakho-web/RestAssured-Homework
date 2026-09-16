package Models.Booking;

import lombok.Data;

@Data
public class BookModel {
    public String firstname;
    public String lastname;
    public int totalprice;
    public boolean depositpaid;
    public BookingDates bookingdates;
    public String additionalneeds;
}