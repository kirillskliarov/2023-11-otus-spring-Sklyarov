package ru.otus.project.budgeting.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.project.budgeting.models.dto.JwtAuthenticationResponse;
import ru.otus.project.budgeting.models.dto.SignInRequest;
import ru.otus.project.budgeting.models.dto.SignUpRequest;
import ru.otus.project.budgeting.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
//@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

//    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/auth/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

//    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/auth/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
