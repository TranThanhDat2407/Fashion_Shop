package com.example.Fashion_Shop.service.orders;

import com.example.Fashion_Shop.dto.CartItemDTO;
import com.example.Fashion_Shop.dto.OrderDTO;
import com.example.Fashion_Shop.dto.OrderDetailDTO;
import com.example.Fashion_Shop.model.*;
import com.example.Fashion_Shop.repository.*;
import com.example.Fashion_Shop.response.cart.CartItemResponse;
import com.example.Fashion_Shop.service.cart.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {

    @Autowired
    OrderPaymentRepository orderPaymentRepository;
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;


    // chưa được
//@Transactional
//public Order saveOrder(Order order) {
//    if(order.getOrderDetails() != null){
//        for(OrderDetail orderDetail : order.getOrderDetails()){
//            orderDetail.setOrder(order);
//        }
//    }
//    return orderRepository.save(order);
//}

    //    @Transactional
//    public Order saveOrder(Order order) {
//        if (order.getOrderDetails() != null) {
//            for (OrderDetail orderDetail : order.getOrderDetails()) {
//                orderDetail.setOrder(order);
//                // Giả sử bạn lấy giá từ SKU
//                if (orderDetail.getSku() != null) {
//                    orderDetail.setPrice(orderDetail.getSku().getSalePrice());
//                    orderDetail.setTotalMoney(orderDetail.getPrice() * orderDetail.getQuantity());
//                } else {
//                    throw new IllegalArgumentException("SKU is required for order detail");
//                }
//            }
//        }
//        return orderRepository.save(order);
//    }
    @Transactional
    public Order saveOrder(Order order) {
        if (order.getUser() != null && order.getUser().getId() != null) {
            User user = userRepository.findById(order.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            order.setUser(user);
        }
        if (order.getPhoneNumber() == null || order.getPhoneNumber().isEmpty()) {
            throw new RuntimeException("Phone number is required");
        }

        double totalMoney = 0;

        if (order.getOrderDetails() != null) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                orderDetail.setOrder(order);
                SKU sku = skuRepository.findById(orderDetail.getSku().getId())
                        .orElseThrow(() -> new RuntimeException("SKU not found"));

                orderDetail.setPrice(sku.getSalePrice());
                double orderDetailTotal = orderDetail.getPrice() * orderDetail.getQuantity();
                orderDetail.setTotalMoney(orderDetailTotal);
                totalMoney += orderDetailTotal;
            }
        }

        order.setTotalMoney(BigDecimal.valueOf(totalMoney));
        return orderRepository.save(order);
    }


    @Transactional
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> convertToDTO(order)).collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDTO> getOrdersByUserId(Integer userId) {
        List<Order> orders = orderRepository.findByUser_Id(userId);

        // Chuyển Order thành danh sách OrderDTO
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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


    @Transactional
    public Order createOrderFromCart(Long userId,Pageable pageable) {

        Page<Cart> cartItems = cartRepository.findByUserId(userId, pageable);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }

        // Tạo một đối tượng Order mới
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User không tồn tại"));
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(order.getShippingAddress());
        order.setStatus("Pending");
        order.setPhoneNumber(user.getPhone());
        order.setTotalMoney(order.getTotalMoney());
        order.setCreatedAt(new Date());



        double totalMoney = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getSku().getSalePrice() * cartItem.getQuantity())
                .sum();

        // Chuyển đổi từng CartItem thành OrderDetail
        List<OrderDetail> orderDetails = cartItems.stream().map(cartItem -> {
            SKU sku = cartItem.getSku();
            int quantity = cartItem.getQuantity();
            double price = sku.getSalePrice();

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setSku(sku);
            orderDetail.setQuantity(quantity);
            orderDetail.setPrice(price);
            orderDetail.setTotalMoney(price * quantity);
            orderDetail.setOrder(order);

            return orderDetail;
        }).collect(Collectors.toList());


        order.setTotalMoney(BigDecimal.valueOf(totalMoney));
        order.setOrderDetails(orderDetails);


        Order savedOrder = orderRepository.save(order);


        cartService.deleteAllCart(userId);

        return savedOrder;
    }



    private void sendOrderConfirmationEmail(Order savedOrder) {
        String recipientEmail = savedOrder.getUser().getEmail(); // Lấy email của khách hàng từ User
        String subject = "Xác Nhận Đơn Hàng - #" + savedOrder.getId();
        StringBuilder body = new StringBuilder();

        body.append("Chào ").append(savedOrder.getUser().getName()).append(",<br><br>");
        body.append("Cảm ơn bạn đã đặt hàng tại cửa hàng của chúng tôi. Đây là thông tin đơn hàng của bạn:<br>");
        body.append("<b>Đơn hàng ID: </b>").append(savedOrder.getId()).append("<br>");
        body.append("<b>Địa chỉ giao hàng: </b>").append(savedOrder.getShippingAddress()).append("<br>");
        body.append("<b>Tổng tiền: </b>").append(savedOrder.getTotalMoney()).append("<br><br>");
        body.append("<b>Chi tiết đơn hàng:</b><br>");

        savedOrder.getOrderDetails().forEach(orderDetail -> {
            body.append("Sản phẩm: ").append(orderDetail.getSku().getProduct().getName())
                    .append(" - Số lượng: ").append(orderDetail.getQuantity())
                    .append(" - Giá: ").append(orderDetail.getPrice()).append("<br>");
        });

        body.append("<br>Cảm ơn bạn đã mua sắm tại cửa hàng của chúng tôi!");

        // Tạo và gửi email
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(body.toString(), true);
            mailSender.send(helper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
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




    public void updateOrderPayment(Long orderId, String transactionId, String paymentResponse, BigDecimal totalAmount) {
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
                .map(detail -> {
                    // Kiểm tra nếu SKU không null
                    SKU sku = detail.getSku();
                    Long skuId = (sku != null) ? sku.getId() : null;
                    return OrderDetailDTO.builder()
                            .id(detail.getId())
                            .skuId(Math.toIntExact(skuId))
                            .quantity(detail.getQuantity())
                            .price(detail.getPrice())
                            .totalMoney(detail.getTotalMoney())
                            .build();
                })
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
                    detail.setSku(new SKU(Math.toIntExact(dto.getSkuId())));  // Đặt SKU từ ID
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

            // Giảm số lượng tồn kho
            int remainingQty = sku.getQtyInStock() - detail.getQuantity();
            if (remainingQty < 0) {
                throw new RuntimeException("Not enough stock for product: " + sku.getProduct().getName());
            }
            sku.setQtyInStock(remainingQty);
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



    private void checkAndUpdateInventory(SKU sku, int quantity) {
        // Kiểm tra số lượng tồn kho
        if (sku.getQtyInStock() < quantity) {
            throw new IllegalArgumentException("Không đủ hàng trong kho cho SKU: " + sku.getId());
        }

        // Giảm số lượng tồn kho
        sku.setQtyInStock(sku.getQtyInStock() - quantity);

        // Lưu cập nhật vào database nếu cần thiết
        skuRepository.save(sku);
    }


}
