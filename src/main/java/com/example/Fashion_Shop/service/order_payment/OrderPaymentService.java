package com.example.Fashion_Shop.service.order_payment;
import com.example.Fashion_Shop.model.OrderPayment;
import com.example.Fashion_Shop.repository.OrderPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderPaymentService {
    @Autowired
    private OrderPaymentRepository orderPaymentRepository;


    public OrderPayment saveOrderPayment(OrderPayment orderPayment) {
        return orderPaymentRepository.save(orderPayment);
    }


    public List<OrderPayment> getAllOrderPayments() {
        return orderPaymentRepository.findAll();
    }


    public Optional<OrderPayment> getOrderPaymentById(Long id) {
        return orderPaymentRepository.findById(id);
    }


    public void deleteOrderPayment(Long id) {
        orderPaymentRepository.deleteById(id);
    }
}
