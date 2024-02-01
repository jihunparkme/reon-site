package com.site.reon.aggregate.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.reon.aggregate.member.domain.Authority;
import com.site.reon.aggregate.member.domain.Member;
import com.site.reon.aggregate.member.service.MemberLoginService;
import com.site.reon.aggregate.member.service.MemberService;
import com.site.reon.aggregate.member.service.dto.ApiEmailVerifyDto;
import com.site.reon.aggregate.member.service.dto.ApiLoginDto;
import com.site.reon.global.common.constant.member.Role;
import com.site.reon.global.common.property.ReonAppProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableConfigurationProperties(ReonAppProperty.class)
@WebMvcTest(MemberLoginApiController.class)
@WithMockUser(username = "user@gmail.com", password = "user")
class MemberLoginApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberLoginService memberLoginService;

    @MockBean
    private MemberService memberService;

    private String CLIENT_NAME = "reon";
    private String CLIENT_ID = "235df110-bd70-11ee-aa8b-e30685fde2fa";

    private ApiEmailVerifyDto verifyDto = ApiEmailVerifyDto.builder()
            .clientId(CLIENT_ID)
            .clientName(CLIENT_NAME)
            .authClientName("KAKAO")
            .email("")
            .token(null)
            .build();

    private ApiEmailVerifyDto invalidClient = ApiEmailVerifyDto.builder()
            .clientId("aaaa")
            .clientName("bbbbbbbbbbbbbbbbb")
            .authClientName("KAKAO")
            .email("")
            .token(null)
            .build();

    private String email = "user@gmail.com";
    private Member member = Optional.of(Member.builder()
            .email(email)
            .firstName("aaron")
            .lastName("park")
            .authorities(Collections.singleton(Authority.generateAuthorityBy(Role.USER.key())))
            .build()).get();

    private ApiLoginDto apiLoginDto = ApiLoginDto.builder()
            .clientId(CLIENT_ID)
            .clientName(CLIENT_NAME)
            .email(email)
            .password("user")
            .build();

    @Test
    void verifyEmail_should_be_return_true() throws Exception {
        given(memberLoginService.verifyEmail(any()))
                .willReturn(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(verifyDto);

        ResultActions perform = mockMvc
                .perform(post("/api/login/verify/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()));

        perform
                .andExpect(status().isOk());

    }

    @Test
    void verifyEmail_should_be_return_false() throws Exception {
        given(memberLoginService.verifyEmail(any()))
                .willReturn(false);


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(verifyDto);

        ResultActions perform = mockMvc
                .perform(post("/api/login/verify/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()));

        perform
                .andExpect(status().isOk());
    }

    @Test
    void verifyEmail_should_be_response_400() throws Exception {
        given(memberLoginService.verifyEmail(any()))
                .willReturn(false);


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(invalidClient);

        ResultActions perform = mockMvc
                .perform(post("/api/login/verify/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()));

        perform
                .andExpect(status().is4xxClientError());
    }

    @Test
    void loginEmail_should_be_return_member() throws Exception {
        given(memberService.getMemberWithAuthorities(any()))
                .willReturn(member);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(apiLoginDto);

        ResultActions perform = mockMvc
                .perform(post("/api/login/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()));

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(email))
                .andExpect(jsonPath("$.data.firstName").value("aaron"))
                .andExpect(jsonPath("$.data.lastName").value("park"));
    }

    @Test
    void loginEmail_should_be_return_null() throws Exception {
        given(memberService.getMemberWithAuthorities(any()))
                .willReturn(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(apiLoginDto);

        ResultActions perform = mockMvc
                .perform(post("/api/login/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()));

        perform
                .andExpect(status().isOk());
    }

    @Test
    void loginEmail_should_be_respnose_400() throws Exception {
        given(memberService.getMemberWithAuthorities(any()))
                .willReturn(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(invalidClient);

        ResultActions perform = mockMvc
                .perform(post("/api/login/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()));

        perform
                .andExpect(status().is4xxClientError());
    }
}
