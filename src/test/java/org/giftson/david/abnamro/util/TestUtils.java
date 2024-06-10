package org.giftson.david.abnamro.util;

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

}
