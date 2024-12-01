package com.example.Fashion_Shop.service.order_detail;


import com.example.Fashion_Shop.dto.OrderDetailDTO;
import com.example.Fashion_Shop.model.Order;
import com.example.Fashion_Shop.model.OrderDetail;
import com.example.Fashion_Shop.repository.OrderDetailRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {

        if (orderDetail.getOrder() == null || orderDetail.getOrder().getId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }


        OrderDetail order = orderDetailRepository.findById(Long.valueOf(orderDetail.getOrder().getId()))
                .orElseThrow(() -> new RuntimeException("Order not found"));


        orderDetail.setOrder(order.getOrder());


        return orderDetailRepository.save(orderDetail);
    }


    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailRepository.findAll();
    }


    public Optional<OrderDetail> getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id);
    }


//    @Transactional
//    public List<OrderDetail> getOrderDetailsByOrderId(Integer orderId) {
//        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
//        if (orderDetails.isEmpty()) {
//            System.out.println("No order details found for orderId: " + orderId);
//        }
//        return orderDetails;
//    }
@Transactional
public List<OrderDetailDTO> getOrderDetailsByOrderId(Integer orderId) {
    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderIdWithSku(orderId);
    return orderDetails.stream().map(orderDetail -> {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(orderDetail.getId());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setPrice(orderDetail.getPrice());
        dto.setTotalMoney(orderDetail.getTotalMoney());


        if (orderDetail.getSku() != null) {
            dto.setSkuId(orderDetail.getSku().getId());
        } else {
            dto.setSkuId(null);
        }

        return dto;
    }).collect(Collectors.toList());
}




    public void deleteOrderDetail(Long id) {
        orderDetailRepository.deleteById(id);
    }




}
