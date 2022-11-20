package com.teletrade.App.repositories;

import com.teletrade.App.dtos.OrderDtoInterface;
import com.teletrade.App.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT price, SUM(amount) amount, type FROM Orders o WHERE o.type='sell' GROUP BY price ORDER BY o.price ASC LIMIT 10", nativeQuery = true)
    List<OrderDtoInterface> top10Sell();

    @Query(value = "SELECT price, SUM(amount) amount, type FROM Orders o WHERE o.type='buy' GROUP BY price ORDER BY o.price DESC LIMIT 10", nativeQuery = true)
    List<OrderDtoInterface> top10Buy();
}

