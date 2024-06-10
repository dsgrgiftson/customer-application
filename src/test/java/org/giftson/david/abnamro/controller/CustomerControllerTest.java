package org.giftson.david.abnamro.controller;

import org.giftson.david.abnamro.model.request.CustomerRegistrationRequest;
import org.giftson.david.abnamro.model.response.CustomerLoginResponse;
import org.giftson.david.abnamro.model.response.CustomerRegistrationResponse;
import org.giftson.david.abnamro.service.CustomerService;
import static org.giftson.david.abnamro.util.TestUtils.USERNAME;
import static org.giftson.david.abnamro.util.TestUtils.PASSWORD;
import static org.giftson.david.abnamro.util.TestUtils.REGISTRATION_REQUEST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        CustomerRegistrationResponse response = new CustomerRegistrationResponse(
                USERNAME,
                PASSWORD,
                "NL01ABNA0123456789",
                true,
                "Registration successful");

        when(customerService.register(any(CustomerRegistrationRequest.class))).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<CustomerRegistrationResponse> result = customerController.register(REGISTRATION_REQUEST);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Registration successful", result.getBody().message());

        verify(customerService, times(1)).register(any(CustomerRegistrationRequest.class));
    }

    @Test
    void testRegisterFailure() {
        CustomerRegistrationResponse response = new CustomerRegistrationResponse(
                USERNAME,
                null,
                null,
                false,
                "Registration failed");

        when(customerService.register(any(CustomerRegistrationRequest.class))).thenReturn(ResponseEntity.badRequest().body(response));

        ResponseEntity<CustomerRegistrationResponse> result = customerController.register(REGISTRATION_REQUEST);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Registration failed", result.getBody().message());

        verify(customerService, times(1)).register(any(CustomerRegistrationRequest.class));
    }

    @Test
    void testLogin() {
        CustomerLoginResponse response = new CustomerLoginResponse(USERNAME, true, "Login successful");

        when(customerService.login(anyString(), anyString())).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<CustomerLoginResponse> result = customerController.login(USERNAME, PASSWORD);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Login successful", result.getBody().message());

        verify(customerService, times(1)).login(anyString(), anyString());
    }

    @Test
    void testLoginFailure() {
        CustomerLoginResponse response = new CustomerLoginResponse(USERNAME, false, "Invalid username or password");

        when(customerService.login(anyString(), anyString())).thenReturn(ResponseEntity.badRequest().body(response));

        ResponseEntity<CustomerLoginResponse> result = customerController.login(USERNAME, PASSWORD);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Invalid username or password", result.getBody().message());

        verify(customerService, times(1)).login(anyString(), anyString());
    }
}