package com.edtech.elearning.api;

import com.edtech.elearning.annotations.StandardResponse;
import com.edtech.elearning.model.User;
import com.edtech.elearning.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
@Tag(name = "Users", description = "APIs for User Management")
@StandardResponse
@RequiredArgsConstructor
@SecurityRequirements
public class UsersController {
    private final UserService userService;

    @Operation(summary = "Create a new User (For Test)", description = "Creates a new User for Authentication")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCourse(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

}
