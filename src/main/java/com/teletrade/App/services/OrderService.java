package com.teletrade.App.services;


import com.teletrade.App.dtos.OrderbookDto;
import com.teletrade.App.dtos.OrderDto;
import com.teletrade.App.dtos.OrderDtoInterface;
import com.teletrade.App.exceptions.BadRequestException;
import com.teletrade.App.models.Order;
import com.teletrade.App.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class OrderService {
    public final OrderRepository orderRepository;

    public Order createOrder(double price, double amount, String type) throws Exception {
        if (amount <= 0) {
            throw new BadRequestException("Amount has to be positive!");
        }
        if (price <= 0) {
            throw new BadRequestException("Price has to be positive!");
        }
        if (!type.equals(Order.BUY) && !type.equals(Order.SELL)) {
            throw new BadRequestException("Order type has be buy or sell!");
        }
        return orderRepository.save(new Order(price, amount, type, LocalDateTime.now()));
    }

    private CompletableFuture<List<OrderDto>> top10Sell() {
        List<OrderDtoInterface> objects = orderRepository.top10Sell();
        List<OrderDto> orders = new ArrayList<>();

        for (OrderDtoInterface object : objects) {
            orders.add(new OrderDto(object.getPrice(), object.getAmount(), object.getType()));
        }
        return CompletableFuture.completedFuture(orders);
    }

    private CompletableFuture<List<OrderDto>> top10Buy() {
        List<OrderDtoInterface> objects = orderRepository.top10Buy();
        List<OrderDto> orders = new ArrayList<>();

        for (OrderDtoInterface object : objects) {
            orders.add(new OrderDto(object.getPrice(), object.getAmount(), object.getType()));
        }
        return CompletableFuture.completedFuture(orders);
    }

    public OrderbookDto orderbook() throws ExecutionException, InterruptedException {
        CompletableFuture<List<OrderDto>> top10Buy = this.top10Buy();
        CompletableFuture<List<OrderDto>> top10Sell = this.top10Sell();

        CompletableFuture.allOf(top10Buy, top10Sell).join();

        return new OrderbookDto(top10Buy.get(), top10Sell.get());
    }
}
