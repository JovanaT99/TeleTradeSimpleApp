package com.teletrade.App.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderbookDto {
    private List<OrderDto> buy;
    private List<OrderDto> sell;
}
