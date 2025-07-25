package com.ntn.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManufactureProductsRequest {

    @NotNull
    Long manufactureId;

    @NotEmpty
    String productId;

    @NotNull
    @Min(value = 1, message = "Quantity must be greater than 0")
    Integer quantity;

    @NotNull
    @Min(value = 1, message = "Price of units must be greater than 0")
    Long priceOfUnits;
}
