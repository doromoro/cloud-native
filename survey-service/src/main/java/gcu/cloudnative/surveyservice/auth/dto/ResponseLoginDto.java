package gcu.cloudnative.surveyservice.auth.dto;

import gcu.cloudnative.surveyservice.survey.entity.MemberRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseLoginDto {
    private Long memberId;
    private MemberRole role;

    public static ResponseLoginDto of(Long memberId, MemberRole role){
        ResponseLoginDto response = new ResponseLoginDto();
        response.setMemberId(memberId);
        response.setRole(role);
        return response;
    }
}
