package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findById(Integer id);
    List<Address> findAllByUser_Id(Long id);
    List<Address> findByCity(String city);

    Optional<Address> findDefaultAddressByUserId(Long userId);


    @Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.isDefault = TRUE")
    Address findByUserIdAndIsDefaultTrue(@Param("userId") Long userId);

}
