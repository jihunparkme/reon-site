package com.site.reon.aggregate.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.site.reon.aggregate.catalog.command.domain.dto.RegisterSerialNoRequest;
import com.site.reon.aggregate.catalog.command.domain.dto.RegisterSerialNoResponse;
import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;
import com.site.reon.aggregate.catalog.command.domain.dto.SerialNoRegisterResult;
import com.site.reon.aggregate.catalog.command.domain.product.Color;
import com.site.reon.aggregate.catalog.command.domain.product.RatedVoltage;
import com.site.reon.aggregate.catalog.command.service.ProductCommandService;
import com.site.reon.aggregate.common.model.ProductNo;
import com.site.reon.aggregate.common.model.SerialNo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(ProductAdminController.class)
@WithMockUser(roles = "ADMIN")
class ProductAdminControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductCommandService productCommandService;

    @Test
    void when_saveProduct_then_created() throws Exception {
        final SaveProductRequest request = SaveProductRequest.builder()
                .categoryId(1L)
                .name("R200")
                .productNo("R2N0BK")
                .color(Color.BLACK)
                .ratedVoltage(RatedVoltage.V220)
                .size(2)
                .manufacturedDt(LocalDate.of(2024, 04, 15))
                .build();
        final SerialNo serialNo1 = SerialNo.builder()
                .productNo(ProductNo.of("R2N0BK"))
                .createdNo(1)
                .date(LocalDate.now())
                .build();
        final SerialNo serialNo2 = SerialNo.builder()
                .productNo(ProductNo.of("R2N0BK"))
                .createdNo(2)
                .date(LocalDate.now())
                .build();

        when(productCommandService.saveProducts(any(SaveProductRequest.class)))
                .thenReturn(List.of(serialNo1, serialNo2));

        mockMvc.perform(post("/admin/products")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("201"))
                .andExpect(jsonPath("$.httpStatusCode").value("CREATED"))
                .andExpect(jsonPath("$.count").value("2"))
                .andExpect(jsonPath("$.data[0].no").value("R2N0BK-0001-20240415"))
                .andExpect(jsonPath("$.data[0].activated").value("false"))
                .andExpect(jsonPath("$.data[1].no").value("R2N0BK-0002-20240415"))
                .andExpect(jsonPath("$.data[1].activated").value("false"));
    }

    @Test
    void when_deleteProduct_then_success() throws Exception {
        mockMvc.perform(delete("/admin/products/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"));
    }

    @Test
    void when_registerSerialNo_then_serialNo_list() throws Exception {
        final String serialNo1 = "R2N0BK-0001-20240415";
        final String serialNo2 = "R2N0BK-0002-20240415";
        List<String> serialNos = List.of(serialNo1, serialNo2);
        final RegisterSerialNoRequest request = new RegisterSerialNoRequest(serialNos);

        final SerialNoRegisterResult register1 = SerialNoRegisterResult.builder()
                .serialNo(serialNo1)
                .result(true)
                .build();
        final SerialNoRegisterResult register2 = SerialNoRegisterResult.builder()
                .serialNo(serialNo2)
                .result(true)
                .build();
        when(productCommandService.activateSerialNo(request))
                .thenReturn(RegisterSerialNoResponse.builder()
                        .registerResult(List.of(register1, register2))
                        .build());

        mockMvc.perform(put("/admin/products/register/serial-no")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.httpStatusCode").value("OK"))
                .andExpect(jsonPath("$.count").value("1"))
                .andExpect(jsonPath("$.data.registerResult[0].serialNo").value(serialNo1))
                .andExpect(jsonPath("$.data.registerResult[0].result").value(true))
                .andExpect(jsonPath("$.data.registerResult[1].serialNo").value(serialNo2))
                .andExpect(jsonPath("$.data.registerResult[1].result").value(true));
    }
}