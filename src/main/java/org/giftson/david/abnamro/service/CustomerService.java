package org.giftson.david.abnamro.service;

import jakarta.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.giftson.david.abnamro.entity.CustomerDetails;
import org.giftson.david.abnamro.model.request.CustomerRegistrationRequest;
import org.giftson.david.abnamro.model.response.CustomerLoginResponse;
import org.giftson.david.abnamro.model.response.CustomerRegistrationResponse;
import org.giftson.david.abnamro.repository.CustomerDetailsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerDetailsRepository customerDetailsRepository;

    @Transactional
    public ResponseEntity<CustomerRegistrationResponse> register(CustomerRegistrationRequest customerRegistrationRequest) {
        try {
            log.debug("Customer Registration Request: %s".formatted(customerRegistrationRequest.toString()));
            validateRegisterRequest(customerRegistrationRequest);

            String generatedPassword = generateRandomPassword();
            String generatedIban = generateRandomIban();

            saveRegisteredCustomer(customerRegistrationRequest, generatedPassword, generatedIban);

            log.info("Customer registered successfully");
            return ResponseEntity.ok(new CustomerRegistrationResponse(
                    customerRegistrationRequest.username(),
                    generatedPassword,
                    generatedIban,
                    true,
                    "Registration successful"));
        } catch (IllegalArgumentException e) {
            log.error("Error occurred while registering customer: %s".formatted(e.getMessage()));
            return ResponseEntity.badRequest().body(new CustomerRegistrationResponse(
                    customerRegistrationRequest.username(),
                    null,
                    null,
                    false,
                    e.getMessage()));
        } catch (Exception e) {
            log.error("Error occurred while registering customer: %s".formatted(e.getMessage()));
            return ResponseEntity.internalServerError().body(new CustomerRegistrationResponse(
                    customerRegistrationRequest.username(),
                    null,
                    null,
                    false,
                    e.getMessage()));
        }
    }

    private void validateRegisterRequest(CustomerRegistrationRequest customerRegistrationRequest) {
        if (customerRegistrationRequest.username().isEmpty() ||
                customerRegistrationRequest.fullName().isEmpty() ||
                customerRegistrationRequest.address().isEmpty() ||
                customerRegistrationRequest.dateOfBirth().isEmpty() ||
                customerRegistrationRequest.documentNumber().isEmpty()) {
            throw new IllegalArgumentException("All fields are mandatory. One or more fields are empty");
        }
        customerDetailsRepository.findByUsername(customerRegistrationRequest.username())
                .ifPresent(customerDetails -> {
                    throw new IllegalArgumentException("Username provided already exists");
                });
    }

    private void saveRegisteredCustomer(CustomerRegistrationRequest customerRegistrationRequest, String generatedPassword, String generatedIban) throws ParseException {
        customerDetailsRepository.save(
                new CustomerDetails(
                        null,
                        customerRegistrationRequest.username(),
                        generatedPassword,
                        customerRegistrationRequest.fullName(),
                        customerRegistrationRequest.address(),
                        new SimpleDateFormat("dd-MM-yyyy").parse(customerRegistrationRequest.dateOfBirth()),
                        customerRegistrationRequest.documentNumber(),
                        generatedIban
                ));
    }

    private String generateRandomIban() {
        // generate random IBAN starting with NL01ABNA1111111111 where all the digits are random
        return "NL"
                + RandomStringUtils.random(2, false, true)
                + "ABNA"
                + RandomStringUtils.random(10, false, true);
    }

    private String generateRandomPassword() {
        // generate random password of length 15 with numbers and alphabets
        return RandomStringUtils.random(15, true, true);
    }

    public ResponseEntity<CustomerLoginResponse> login(String username, String password) {
        Optional<CustomerDetails> customerDetail = customerDetailsRepository.findByUsernameAndPassword(username, password);
        if (customerDetail.isEmpty()) {
            log.error("Invalid username or password");
            return ResponseEntity.badRequest()
                    .body(new CustomerLoginResponse(username, false, "Invalid username or password"));
        }
        log.info("Login successful");
        return ResponseEntity.ok(new CustomerLoginResponse(username, true, "Login successful"));
    }

}