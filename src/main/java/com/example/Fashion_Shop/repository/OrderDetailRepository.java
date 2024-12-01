package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Integer orderId);

    @Query("SELECT od FROM OrderDetail od JOIN FETCH od.sku WHERE od.order.id = :orderId")
    List<OrderDetail> findByOrderIdWithSku(@Param("orderId") Integer orderId);
}
