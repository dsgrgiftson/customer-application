package org.giftson.david.abnamro.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.giftson.david.abnamro.model.request.CustomerRegistrationRequest;
import org.giftson.david.abnamro.model.response.CustomerLoginResponse;
import org.giftson.david.abnamro.model.response.CustomerRegistrationResponse;
import org.giftson.david.abnamro.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "Customer Controller", description = "Endpoints for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("register")
    @Operation(summary = "Register a new customer", description = "Registers a new customer in the system")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful, returns customer registration response"),
            @APIResponse(responseCode = "400", description = "Failure, fails registration due to invalid input"),
            @APIResponse(responseCode = "500", description = "Internal server error due to unexpected condition")
    })
    public ResponseEntity<CustomerRegistrationResponse> register(
            @RequestBody CustomerRegistrationRequest customerRegistrationRequest) {
        log.info("Customer registration request received: %s".formatted(customerRegistrationRequest.username()));
        return customerService.register(customerRegistrationRequest);
    }

    @GetMapping("logon")
    @Operation(summary = "Log in a customer", description = "Logs in a customer into the system")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful, returns customer login response"),
            @APIResponse(responseCode = "401", description = "Failure, failed to authenticate due to invalid credentials"),
            @APIResponse(responseCode = "500", description = "Internal server error due to unexpected condition")
    })
    public ResponseEntity<CustomerLoginResponse> login(@Parameter(description = "Username that was used during registration") @RequestParam String username,
                                                       @Parameter(description = "Password that was generated during registration") @RequestParam String password) {
        log.info("Customer login request received: %s".formatted(username));
        return customerService.login(username, password);
    }

}
