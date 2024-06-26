package com.site.reon.aggregate.member.controller;

import com.site.reon.aggregate.member.command.domain.Member;
import com.site.reon.aggregate.member.command.dto.ApiOAuth2SignUpRequest;
import com.site.reon.aggregate.member.command.dto.ApiRegisterMemberSerialNo;
import com.site.reon.aggregate.member.command.dto.ApiWithdrawRequest;
import com.site.reon.aggregate.member.command.service.MemberCommandApiService;
import com.site.reon.aggregate.member.command.service.MemberCommandService;
import com.site.reon.aggregate.member.controller.dto.*;
import com.site.reon.aggregate.member.infra.service.MemberEmailAuthCodeService;
import com.site.reon.aggregate.member.query.dto.ApiEmailVerifyRequest;
import com.site.reon.aggregate.member.query.dto.MemberDto;
import com.site.reon.aggregate.member.query.service.MemberFindApiService;
import com.site.reon.aggregate.member.query.service.MemberFindService;
import com.site.reon.aggregate.member.command.service.MemberEmailLoginService;
import com.site.reon.aggregate.member.command.dto.LoginRequest;
import com.site.reon.global.common.constant.redis.KeyPrefix;
import com.site.reon.global.common.dto.BasicResponse;
import com.site.reon.global.security.oauth2.dto.OAuth2Client;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.site.reon.global.common.constant.Result.SUCCESS;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class MemberLoginApiController {

    private final MemberFindService memberFindService;
    private final MemberFindApiService memberFindApiService;
    private final MemberCommandService memberCommandService;
    private final MemberCommandApiService memberCommandApiService;
    private final MemberEmailLoginService memberEmailLoginService;
    private final MemberEmailAuthCodeService memberEmailAuthCodeService;

    @ApiOperation(value = "소셜 로그인 가입 여부 확인", notes = "앱에서 소셜 로그인 가입 여부를 확인합니다.")
    @PostMapping("/verify/email")
    public ResponseEntity verifyEmail(@Valid @RequestBody final ApiEmailVerifyRequest request) {
        final boolean result = memberFindApiService.verifySocialEmail(request);
        return BasicResponse.ok(result);
    }

    @ApiOperation(value = "신규 소셜 가입", notes = "앱에서 신규로 소셜 가입을 합니다.")
    @PostMapping("/oauth2/sign-up")
    public ResponseEntity signUpOAuth2(@Valid @RequestBody final ApiOAuth2SignUpRequest request) {
        final MemberDto member = memberCommandApiService.oAuth2SignUp(request);
        return BasicResponse.ok(member);
    }

    @ApiOperation(value = "이메일 가입", notes = "앱에서 이메일로 가입합니다.")
    @PostMapping("/email/sign-up")
    public ResponseEntity signUpEmail(@Valid @RequestBody final ApiSignUpRequest request) {
        memberEmailLoginService.signUpWithEmail(request);
        return BasicResponse.ok(SUCCESS);
    }

    @ApiOperation(value = "이메일 인증번호 발송", notes = "앱에서 이메일로 가입 시 인증번호를 발송합니다.")
    @PostMapping("/email/auth-code")
    public ResponseEntity sendAuthenticationCodeByEmail(@Valid @RequestBody final ApiEmailAuthCodeRequest request) {
        memberEmailAuthCodeService.sendAuthenticationCodeByEmail(KeyPrefix.SIGN_UP, request.getPurpose(), request.getEmail());
        return BasicResponse.ok(SUCCESS);
    }

    @ApiOperation(value = "이메일 인증번호 검증", notes = "앱에서 이메일로 가입 시 발송된 인증번호를 검증합니다.")
    @PostMapping("/email/auth-code/verify")
    public ResponseEntity verifyAuthenticationCode(@Valid @RequestBody final ApiEmailAuthCodeVerifyRequest request) {
        memberEmailAuthCodeService.verifyAuthenticationCode(KeyPrefix.SIGN_UP, request.getEmail(), request.getAuthCode());
        return BasicResponse.ok(SUCCESS);
    }

    @ApiOperation(value = "이메일 로그인", notes = "앱에서 이메일로 로그인합니다.")
    @PostMapping("/email")
    public ResponseEntity loginEmail(@Valid @RequestBody final ApiLoginRequest request) {
        memberEmailLoginService.emailAuthenticate(LoginRequest.from(request));
        final Member member = memberFindService.getMemberWithAuthorities(request.getEmail(), OAuth2Client.EMPTY);
        return BasicResponse.ok(MemberDto.from(member));
    }

    @ApiOperation(value = "회원 정보 조회", notes = "앱에서 회원 정보를 조회합니다.")
    @PostMapping("/info")
    public ResponseEntity loginInfo(@Valid @RequestBody final ApiMyPageRequest request) {
        final OAuth2Client oAuthClient = OAuth2Client.of(request.getAuthClientName().toLowerCase());
        final Member member = memberFindService.getMemberWithAuthorities(request.getEmail(), oAuthClient);
        return BasicResponse.ok(MemberDto.from(member));
    }

    @ApiOperation(value = "회원 탈퇴", notes = "앱에서 회원을 탈퇴합니다.")
    @PostMapping("/withdraw")
    public ResponseEntity withdraw(@Valid @RequestBody final ApiWithdrawRequest request) {
        final boolean result = memberCommandService.withdraw(request.toBaseRequest());
        return BasicResponse.ok(result);
    }

    @ApiOperation(value = "회원 S/N 등록", notes = "앱에서 회원의 S/N를 등록합니다.")
    @PostMapping("/member/{id}/serial-no")
    public ResponseEntity registerMemberSerialNo(@PathVariable(name = "id") final long id,
                                    @Valid @RequestBody final ApiRegisterMemberSerialNo request) {
        final boolean result = memberCommandService.registerSerialNo(id, request);
        return BasicResponse.ok(result);
    }
}
