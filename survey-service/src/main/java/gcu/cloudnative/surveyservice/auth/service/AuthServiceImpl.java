package gcu.cloudnative.surveyservice.auth.service;

import gcu.cloudnative.surveyservice.auth.dto.RequestLoginDto;
import gcu.cloudnative.surveyservice.auth.dto.ResponseLoginDto;
import gcu.cloudnative.surveyservice.global.CustomException;
import gcu.cloudnative.surveyservice.global.CustomResponseCode;
import gcu.cloudnative.surveyservice.global.ResponseCode;
import gcu.cloudnative.surveyservice.survey.entity.Member;
import gcu.cloudnative.surveyservice.survey.entity.MemberState;
import gcu.cloudnative.surveyservice.survey.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final MemberRepository memberRepository;
    @Override
    public ResponseLoginDto requestLogin(RequestLoginDto dto) {
        Member member = findRequestLoginUser(dto.getEmail(), dto.getPassword(), MemberState.ACTIVE);
        return ResponseLoginDto.of(member.getId(), member.getRole());
    }

    @Override
    public ResponseEntity<CustomResponseCode> requestLogout(Long memberId) {

        checkConflictMember(memberId);
        return ResponseEntity.ok(CustomResponseCode.MEMBER_LOGOUT_SUCCESS);
    }

    private Member checkConflictMember(Long id) {
        if (memberRepository.findById(id).isEmpty()) {
            throw new CustomException(ResponseCode.MEMBER_NOT_FOUND);
        }
        if (memberRepository.findById(id).get().getState().equals(MemberState.INACTIVE)) {
            throw new CustomException(ResponseCode.MEMBER_DELETED);
        }
        return memberRepository.findById(id).get();
    }

    private Member findRequestLoginUser(String email, String password, MemberState state) {
        return memberRepository.findByEmailAndPasswordAndState(email,password,state)
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));
    }
}
