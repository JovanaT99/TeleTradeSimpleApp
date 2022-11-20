package com.teletrade.App.controllers;


import com.teletrade.App.dtos.OrderbookDto;
import com.teletrade.App.dtos.OrderDto;
import com.teletrade.App.models.Order;
import com.teletrade.App.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;


@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/orders")
    ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) throws Exception {
        return new ResponseEntity<>(
                orderService.createOrder(
                        orderDto.getPrice(),
                        orderDto.getAmount(),
                        orderDto.getType()
                ),
                HttpStatus.CREATED);

    }

    @GetMapping("/orderbook")
    public ResponseEntity<OrderbookDto> orderBook() throws ExecutionException, InterruptedException {
        return new ResponseEntity<>(orderService.orderbook(), HttpStatus.OK);

    }
}

