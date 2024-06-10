package org.giftson.david.abnamro.util;

import java.util.Date;
import org.giftson.david.abnamro.entity.CustomerDetails;
import org.giftson.david.abnamro.model.request.CustomerRegistrationRequest;

public class TestUtils {

    public final static String USERNAME = "testuser";
    public final static String PASSWORD = "testpassword";

    public final static CustomerRegistrationRequest REGISTRATION_REQUEST = new CustomerRegistrationRequest(
            USERNAME,
            "Test User",
            "Test House 1, 1234AB Amsterdam, Netherlands",
            "01-01-1993",
            "DOC001");

    public final static CustomerDetails CUSTOMER_DETAILS = new CustomerDetails();
    static {
        CUSTOMER_DETAILS.setCustomerId(1L);
        CUSTOMER_DETAILS.setUsername(USERNAME);
        CUSTOMER_DETAILS.setPassword(PASSWORD);
        CUSTOMER_DETAILS.setFullName("Test User");
        CUSTOMER_DETAILS.setAddress("Test House 1, 1234AB Amsterdam, Netherlands");
        CUSTOMER_DETAILS.setDateOfBirth(new Date(1993, 1, 1));
        CUSTOMER_DETAILS.setDocumentNumber("DOC001");
        CUSTOMER_DETAILS.setIbanNumber("NL01ABNA0123456789");
    }

}
