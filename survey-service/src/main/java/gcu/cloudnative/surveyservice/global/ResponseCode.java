package gcu.cloudnative.surveyservice.global;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    //GLOBAL
    BAD_REQUEST("GLB-ERR-001", HttpStatus.NOT_FOUND, "잘못된 요청입니다."),
    METHOD_NOT_ALLOWED("GLB-ERR-002", HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
    INTERNAL_SERVER_ERROR("GLB-ERR-003", HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    //MEMBER
    MEMBER_NOT_FOUND("MEM-ERR-001", HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다."),
    MEMBER_DELETED("MEM-ERR-02", HttpStatus.BAD_REQUEST, "삭제된 회원입니다."),

    // SURVEY
    SURVEY_NOT_FOUND("SRV-ERR-001", HttpStatus.NOT_FOUND, "설문지를 찾을 수 없습니다."),
    SURVEY_QST_NOT_FOUND("SRV-ERR-002", HttpStatus.NOT_FOUND, "설문에 맞는 질문을 찾을 수 없습니다."),
    SURVEY_ANS_NOT_FOUND("SRV-ERR-003", HttpStatus.NOT_FOUND, "질문에 맞는 답변을 찾을 수 없습니다."),
    SURVEY_USER_ANS_NOT_FOUND("SRV-ERR-004", HttpStatus.NOT_FOUND, "사용자가 작성한 질문을 찾을 수 없습니다."),
    SURVEY_DELETED("SRV-ERR-005", HttpStatus.BAD_REQUEST, "삭제된 설문입니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;


    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }
}