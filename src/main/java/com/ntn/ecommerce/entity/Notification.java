package com.ntn.ecommerce.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(columnDefinition = "TEXT")
    String content;

    LocalDateTime timestamp;

    @Column(columnDefinition = "TINYINT(1)")
    boolean isRead;
}
