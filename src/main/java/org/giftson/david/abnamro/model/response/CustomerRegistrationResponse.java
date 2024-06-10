package org.giftson.david.abnamro.model.response;

public record CustomerRegistrationResponse(String username,
                                           String password,
                                           String ibanNumber,
                                           boolean isRegistered,
                                           String message) {
}
