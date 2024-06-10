package org.giftson.david.abnamro.model.request;

import jakarta.annotation.Nonnull;

public record CustomerRegistrationRequest(@Nonnull String username,
                                          @Nonnull String fullName,
                                          @Nonnull String address,
                                          @Nonnull String dateOfBirth,
                                          @Nonnull String documentNumber) {
}
