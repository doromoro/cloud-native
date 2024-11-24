package gcu.cloudnative.surveyservice.auth.service;

import gcu.cloudnative.surveyservice.auth.dto.RequestLoginDto;
import gcu.cloudnative.surveyservice.auth.dto.ResponseLoginDto;
import gcu.cloudnative.surveyservice.global.CustomResponseCode;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseLoginDto requestLogin(RequestLoginDto dto);

    ResponseEntity<CustomResponseCode> requestLogout(Long memberId);
}
