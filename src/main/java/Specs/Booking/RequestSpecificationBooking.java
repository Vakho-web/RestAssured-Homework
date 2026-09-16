package Specs.Booking;

import Utils.Config;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecificationBooking {
    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(Config.BOOKING_BASE_URL)
                .setBasePath(Config.BOOKING_BASE_PATH)
                .build();
    }


}
