package org.giftson.david.abnamro.model.response;

public record CustomerLoginResponse(String username,
                                    boolean isLoggedIn,
                                    String message) {
}
