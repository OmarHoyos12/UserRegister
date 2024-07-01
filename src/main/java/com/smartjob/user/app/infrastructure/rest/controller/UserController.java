package com.smartjob.user.app.infrastructure.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartjob.user.app.application.service.UserService;
import com.smartjob.user.app.domain.model.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/users")
@Api
public class UserController {
	@Autowired
    private UserService userService;

    @PostMapping("/register")
    @ApiOperation("Registrar un usuario")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Usuario registrado correctamente"),
        @ApiResponse(code = 400, message = "Error al registrar el usuario")
    })
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"mensaje\": \"" + e.getMessage() + "\"}");
        }
    }
}
