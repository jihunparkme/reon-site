package com.site.reon.aggregate.catalog.command.service;

import com.site.reon.aggregate.catalog.command.domain.dto.SaveProductRequest;
import com.site.reon.aggregate.catalog.command.domain.product.Color;
import com.site.reon.aggregate.catalog.command.domain.product.ProductRepository;
import com.site.reon.aggregate.catalog.command.domain.product.RatedVoltage;
import com.site.reon.aggregate.common.model.SerialNo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductCommandServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductCommandService productCommandService;

    @BeforeEach
    void beforeEach() {
        productCommandService = new ProductCommandServiceImpl(productRepository);
    }

    @Test
    void when_save_product_success_then_return_serial_no_list() {
        final SaveProductRequest request = SaveProductRequest.builder()
                .manufacturedDt(LocalDate.of(2024, 3, 25))
                .categoryId(1L)
                .name("R200")
                .productNo("R2N0BK")
                .color(Color.BLACK)
                .ratedVoltage(RatedVoltage.V220)
                .size(3)
                .build();
        given(productRepository.quantityOfProductsProducedToday(any(), any(), any()))
                .willReturn(3);

        final List<SerialNo> serialNos = productCommandService.saveProducts(request);
        Assertions.assertEquals("R2N0BK-0004-20240325", serialNos.get(0).getNo());
        Assertions.assertEquals(false, serialNos.get(0).isActivated());
        Assertions.assertEquals("R2N0BK-0005-20240325", serialNos.get(1).getNo());
        Assertions.assertEquals(false, serialNos.get(1).isActivated());
        Assertions.assertEquals("R2N0BK-0006-20240325", serialNos.get(2).getNo());
        Assertions.assertEquals(false, serialNos.get(2).isActivated());
    }
}