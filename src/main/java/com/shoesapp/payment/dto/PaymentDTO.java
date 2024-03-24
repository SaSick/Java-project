package com.shoesapp.payment.dto;

import com.shoesapp.order.dto.OrderDTO;
import lombok.Data;

@Data
public class PaymentDTO {
    private Long paymentId;
    private String paymentMethod;
}
