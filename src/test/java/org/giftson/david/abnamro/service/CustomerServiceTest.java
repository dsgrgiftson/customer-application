package org.giftson.david.abnamro.service;

import java.util.stream.Stream;
import org.giftson.david.abnamro.model.request.CustomerRegistrationRequest;
import org.giftson.david.abnamro.model.response.CustomerLoginResponse;
import org.giftson.david.abnamro.model.response.CustomerRegistrationResponse;
import org.giftson.david.abnamro.repository.CustomerDetailsRepository;
import org.giftson.david.abnamro.entity.CustomerDetails;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.giftson.david.abnamro.util.TestUtils.USERNAME;
import static org.giftson.david.abnamro.util.TestUtils.PASSWORD;
import static org.giftson.david.abnamro.util.TestUtils.REGISTRATION_REQUEST;

class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDetailsRepository customerDetailsRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        when(customerDetailsRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        ResponseEntity<CustomerRegistrationResponse> result = customerService.register(REGISTRATION_REQUEST);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Registration successful", result.getBody().message());

        verify(customerDetailsRepository, times(1)).findByUsername(anyString());
        verify(customerDetailsRepository, times(1)).save(any(CustomerDetails.class));
    }

    @Test
    void testRegisterFailure_ValidationFailed_NullValues() {
        ResponseEntity<CustomerRegistrationResponse> result = customerService.register(new CustomerRegistrationRequest(USERNAME, PASSWORD, null, null, null));

        assertEquals(500, result.getStatusCode().value());
        assertNotNull(result.getBody().message());

        verify(customerDetailsRepository, times(0)).findByUsername(anyString());
        verify(customerDetailsRepository, times(0)).save(any(CustomerDetails.class));
    }

    @ParameterizedTest
    @MethodSource("getValidationFailedRequestsAndResponses")
    void testRegisterFailure_ValidationFailed_EmptyValues(CustomerRegistrationRequest customerRegistrationRequest, int expectedStatusCode, String expectedMessage) {
        ResponseEntity<CustomerRegistrationResponse> result = customerService.register(customerRegistrationRequest);

        assertEquals(expectedStatusCode, result.getStatusCode().value());
        assertTrue(result.getBody().message().contains(expectedMessage));

        verify(customerDetailsRepository, times(0)).findByUsername(anyString());
        verify(customerDetailsRepository, times(0)).save(any(CustomerDetails.class));
    }

    @Test
    void testRegisterFailure_UserExists() {
        when(customerDetailsRepository.findByUsername(anyString())).thenReturn(Optional.of(new CustomerDetails()));

        ResponseEntity<CustomerRegistrationResponse> result = customerService.register(REGISTRATION_REQUEST);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Username provided already exists", result.getBody().message());

        verify(customerDetailsRepository, times(1)).findByUsername(anyString());
        verify(customerDetailsRepository, times(0)).save(any(CustomerDetails.class));
    }

    @Test
    void testRegisterRuntimeException() {
        when(customerDetailsRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        doThrow(new RuntimeException("Test Exception")).when(customerDetailsRepository).save(any(CustomerDetails.class));

        ResponseEntity<CustomerRegistrationResponse> result = customerService.register(REGISTRATION_REQUEST);

        assertEquals(500, result.getStatusCode().value());
        assertEquals("Test Exception", result.getBody().message());

        verify(customerDetailsRepository, times(1)).findByUsername(anyString());
        verify(customerDetailsRepository, times(1)).save(any(CustomerDetails.class));
    }

    @Test
    void testLogin() {
        when(customerDetailsRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(new CustomerDetails()));

        ResponseEntity<CustomerLoginResponse> result = customerService.login(USERNAME, PASSWORD);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Login successful", result.getBody().message());

        verify(customerDetailsRepository, times(1)).findByUsernameAndPassword(anyString(), anyString());
    }

    @Test
    void testLoginFailure() {
        when(customerDetailsRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.empty());

        ResponseEntity<CustomerLoginResponse> result = customerService.login(USERNAME, PASSWORD);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Invalid username or password", result.getBody().message());

        verify(customerDetailsRepository, times(1)).findByUsernameAndPassword(anyString(), anyString());
    }

    public static Stream<Arguments> getValidationFailedRequestsAndResponses() {
        return Stream.of(
                Arguments.of(new CustomerRegistrationRequest(USERNAME, PASSWORD, null, null, null),
                        500,
                        "Cannot invoke \"String.isEmpty()\""),
                Arguments.of(new CustomerRegistrationRequest(USERNAME, PASSWORD, "", "", ""),
                        400,
                        "All fields are mandatory. One or more fields are empty")
        );
    }
}