package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Integer orderId);
}
