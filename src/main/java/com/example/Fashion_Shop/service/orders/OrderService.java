package com.example.Fashion_Shop.service.orders;

import com.example.Fashion_Shop.dto.OrderDTO;
import com.example.Fashion_Shop.dto.OrderDetailDTO;
import com.example.Fashion_Shop.model.*;
import com.example.Fashion_Shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;
    @Autowired
    private OrderRepository orderRepository;


    @Transactional
    public Order saveOrder(Order order) {
        // Gọi kiểm tra và cập nhật tồn kho
        updateInventory(order);

        // Liên kết các chi tiết đơn hàng với đơn hàng chính
        if (order.getOrderDetails() != null) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                orderDetail.setOrder(order);
            }
        }

        return orderRepository.save(order);
    }

    @Transactional
    public List<OrderDTO> getOrdersByUserId(Integer userId) {
        List<Order> orders = orderRepository.findByUser_Id(userId);

        // Chuyển đổi danh sách Order thành danh sách OrderDTO
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> convertToDTO(order)).collect(Collectors.toList());
    }
    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    public Optional<Order> getOrderById(Integer id) {
        return orderRepository.findById(id);
    }


    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setShippingAddress(updatedOrder.getShippingAddress());
                    existingOrder.setTotalMoney(updatedOrder.getTotalMoney());
                    existingOrder.setStatus(updatedOrder.getStatus());
                    existingOrder.setUser(updatedOrder.getUser());
                    return orderRepository.save(existingOrder);
                }).orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }

    public void updateOrderPayment(Long orderId, String transactionId, String paymentResponse, BigDecimal  totalAmount) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Cập nhật trạng thái đơn hàng
        order.setStatus("Paid");
        orderRepository.save(order);

        // Tạo bản ghi thanh toán mới
        OrderPayment payment = new OrderPayment();
        payment.setOrder(order);
        payment.setTransactionId(transactionId);
        payment.setTransactionDate(new Date());
        payment.setPaymentGatewayResponse(paymentResponse);
        payment.setAmount(totalAmount);
        payment.setStatus("Success");

        orderPaymentRepository.save(payment);
    }



    public OrderDTO convertToDTO(Order order) {
        List<OrderDetailDTO> orderDetailDTOs = order.getOrderDetails().stream()
                .map(detail -> OrderDetailDTO.builder()
                        .id(detail.getId())
                        .skuId(Math.toIntExact(detail.getSku().getId()))
                        .quantity(detail.getQuantity())
                        .price(detail.getPrice())
                        .totalMoney(detail.getTotalMoney())
                        .build())
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .shippingAddress(order.getShippingAddress())
                .phoneNumber(order.getPhoneNumber())
                .totalMoney(order.getTotalMoney())
                .status(order.getStatus())
                .orderDetails(orderDetailDTOs)
                .build();
    }

    public Order convertToEntity(OrderDTO orderDTO, User user) {
        List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                .map(dto -> {
                    OrderDetail detail = new OrderDetail();
                    detail.setSku(new SKU(dto.getSkuId()));  // Tạo SKU với ID
                    detail.setQuantity(dto.getQuantity());
                    detail.setPrice(dto.getPrice());
                    detail.setTotalMoney(dto.getTotalMoney());
                    return detail;
                }).collect(Collectors.toList());

        Order order = new Order();
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setPhoneNumber(orderDTO.getPhoneNumber());
        order.setTotalMoney(orderDTO.getTotalMoney());
        order.setStatus(orderDTO.getStatus());
        order.setUser(user);
        order.setOrderDetails(orderDetails);

        orderDetails.forEach(detail -> detail.setOrder(order));

        return order;
    }

    public OrderDTO updateOrder(Integer orderId, OrderDTO updateDTO) {
        // Tìm Order hiện tại
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Cập nhật thông tin Order
        existingOrder.setShippingAddress(updateDTO.getShippingAddress());
        existingOrder.setPhoneNumber(updateDTO.getPhoneNumber());
        existingOrder.setTotalMoney(updateDTO.getTotalMoney());
        existingOrder.setStatus(updateDTO.getStatus());


        existingOrder.getOrderDetails().clear();  // Xóa các chi tiết hiện tại
        List<OrderDetail> updatedDetails = updateDTO.getOrderDetails().stream()
                .map(dto -> {
                    OrderDetail detail = new OrderDetail();
                    detail.setSku(new SKU(dto.getSkuId()));  // Đặt SKU từ ID
                    detail.setQuantity(dto.getQuantity());
                    detail.setPrice(dto.getPrice());
                    detail.setTotalMoney(dto.getTotalMoney());
                    detail.setOrder(existingOrder);  // Liên kết với Order
                    return detail;
                }).collect(Collectors.toList());

        existingOrder.setOrderDetails(updatedDetails);

        // Lưu Order và các OrderDetails mới
        Order updatedOrder = orderRepository.save(existingOrder);
        return convertToDTO(updatedOrder);  // Chuyển về DTO để trả về
    }


    @Transactional
    public void updateInventory(Order order) {
        for (OrderDetail detail : order.getOrderDetails()) {
            SKU sku = skuRepository.findById(detail.getSku().getId())
                    .orElseThrow(() -> new RuntimeException("SKU not found with ID: " + detail.getSku().getId()));

            // Kiểm tra nếu số lượng trong kho đủ để đáp ứng đơn hàng
            if (sku.getQtyInStock() < detail.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + sku.getProduct().getName());
            }

            // Giảm số lượng tồn kho
            sku.setQtyInStock(sku.getQtyInStock() - detail.getQuantity());
            skuRepository.save(sku);  // Lưu cập nhật vào cơ sở dữ liệu
        }
    }


    @Transactional
    public void cancelOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Cập nhật lại tồn kho
        restockInventory(order);

        // Đặt trạng thái đơn hàng thành "Canceled"
        order.setStatus("Canceled");
        orderRepository.save(order);
    }


    @Transactional
    public void restockInventory(Order order) {
        for (OrderDetail detail : order.getOrderDetails()) {
            SKU sku = skuRepository.findById(detail.getSku().getId())
                    .orElseThrow(() -> new RuntimeException("SKU not found with ID: " + detail.getSku().getId()));

            sku.setQtyInStock(sku.getQtyInStock() + detail.getQuantity());
            skuRepository.save(sku);  // Cập nhật kho
        }
    }





}
