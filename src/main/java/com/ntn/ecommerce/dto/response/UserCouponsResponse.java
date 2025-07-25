package com.ntn.ecommerce.dto.response;

import com.ntn.ecommerce.entity.Coupon;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCouponsResponse {
    Long id;
    Coupon coupon;
    Integer quantity;
}
