package gcu.cloudnative.surveyservice.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomResponseCode {
    MEMBER_CREATE_SUCCESS("회원 생성에 성공하였습니다."),
    MEMBER_DELETE_SUCCESS("회원 삭제에 성공하였습니다."),
    MEMBER_LOGOUT_SUCCESS("로그아웃에 성공하였습니다."),
    USER_ANS_DELETE_SUCCESS("설문 삭제에 성공하였습니다."),
    SURVEY_CREATE_SUCCESS("설문 생성에 성공하였습니다."),
    SURVEY_UPDATE_SUCCESS("설문 수정에 성공하였습니다."),
    SURVEY_DELETE_SUCCESS("설문 삭제에 성공하였습니다.");
    private final String message;
}
