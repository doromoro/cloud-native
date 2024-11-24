package gcu.cloudnative.surveyservice.auth.controller;

import gcu.cloudnative.surveyservice.auth.dto.RequestLoginDto;
import gcu.cloudnative.surveyservice.auth.dto.ResponseLoginDto;
import gcu.cloudnative.surveyservice.auth.service.AuthService;
import gcu.cloudnative.surveyservice.global.CustomResponseCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "[인증] 인증 관련 API", description = "Auth API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/survey/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    ResponseLoginDto requestLogin(@RequestBody @Valid RequestLoginDto dto){

        return authService.requestLogin(dto);
    }

    @GetMapping("/logout")
    ResponseEntity<CustomResponseCode> requestLogout(@RequestParam("id") Long memberId) {

        return authService.requestLogout(memberId);
    }

}
