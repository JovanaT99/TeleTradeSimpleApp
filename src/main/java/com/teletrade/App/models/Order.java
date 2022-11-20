package com.teletrade.App.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders") //Order is reserved word in SQL, so I called it "orders"
public class Order {
    public static final String BUY = "buy";
    public static final String SELL = "sell";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private double price;
    private double amount;
    private String type;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    public Order(double price, double amount, String type, LocalDateTime createdAt) {
        this.price = price;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }
}
