package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.SKU;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkuRepository extends JpaRepository<SKU, Long> {

}
