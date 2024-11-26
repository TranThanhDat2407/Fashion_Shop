package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUser_Id(Integer userId);

    Optional<Order> findById(Long id);
    @Query("SELECT o FROM Order o JOIN FETCH o.orderDetails WHERE o.id = :id")
    Optional<Order> findOrderByIdWithDetails(@Param("id") Long id);


}
