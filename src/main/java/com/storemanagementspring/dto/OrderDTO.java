package com.storemanagementspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Double ammount;
    private String orderAddress;
    private LocalDate orderDate;
}
