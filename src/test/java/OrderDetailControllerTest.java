

import com.example.Fashion_Shop.controller.OrderDetailController;
import com.example.Fashion_Shop.dto.OrderDetailDTO;
import com.example.Fashion_Shop.model.OrderDetail;
import com.example.Fashion_Shop.service.order_detail.OrderDetailService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class OrderDetailControllerTest {

    @InjectMocks
    private OrderDetailController orderDetailController;

    @Mock
    private OrderDetailService orderDetailService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() {
        // Reset mocks if needed
    }

    // Test for createOrUpdateOrderDetail
    @Test
    public void testCreateOrUpdateOrderDetailSuccess() {
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailService.saveOrderDetail(orderDetail)).thenReturn(orderDetail);

        ResponseEntity<OrderDetail> response = orderDetailController.createOrUpdateOrderDetail(orderDetail);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 201); // Created
        assertEquals(response.getBody(), orderDetail);
    }

    @Test
    public void testCreateOrUpdateOrderDetailBadRequest() {
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailService.saveOrderDetail(orderDetail)).thenThrow(new IllegalArgumentException("Invalid input"));

        ResponseEntity<OrderDetail> response = orderDetailController.createOrUpdateOrderDetail(orderDetail);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 400); // Bad Request
        assertNull(response.getBody());
    }

    @Test
    public void testCreateOrUpdateOrderDetailNotFound() {
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailService.saveOrderDetail(orderDetail)).thenThrow(new RuntimeException("OrderDetail not found"));

        ResponseEntity<OrderDetail> response = orderDetailController.createOrUpdateOrderDetail(orderDetail);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 404); // Not Found
        assertNull(response.getBody());
    }

    // Test for getAllOrderDetails
    @Test
    public void testGetAllOrderDetails() {
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailService.getAllOrderDetails()).thenReturn(Collections.singletonList(orderDetail));

        List<OrderDetail> response = orderDetailController.getAllOrderDetails();

        assertNotNull(response);
        assertEquals(response.size(), 1);
        assertEquals(response.get(0), orderDetail);
    }

    @Test
    public void testGetAllOrderDetailsEmpty() {
        when(orderDetailService.getAllOrderDetails()).thenReturn(Collections.emptyList());

        List<OrderDetail> response = orderDetailController.getAllOrderDetails();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    // Test for getOrderDetailById
    @Test
    public void testGetOrderDetailByIdSuccess() {
        Long id = 1L;
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailService.getOrderDetailById(id)).thenReturn(Optional.of(orderDetail));

        ResponseEntity<OrderDetail> response = orderDetailController.getOrderDetailById(id);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 200); // OK
        assertEquals(response.getBody(), orderDetail);
    }

    @Test
    public void testGetOrderDetailByIdNotFound() {
        Long id = 1L;
        when(orderDetailService.getOrderDetailById(id)).thenReturn(Optional.empty());

        ResponseEntity<OrderDetail> response = orderDetailController.getOrderDetailById(id);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 404); // Not Found
    }

    // Test for getOrderDetailsByOrderId
    @Test
    public void testGetOrderDetailsByOrderIdSuccess() {
        Integer orderId = 1;
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        when(orderDetailService.getOrderDetailsByOrderId(orderId)).thenReturn(Collections.singletonList(orderDetailDTO));

        List<OrderDetailDTO> response = orderDetailController.getOrderDetailsByOrderId(orderId);

        assertNotNull(response);
        assertEquals(response.size(), 1);
        assertEquals(response.get(0), orderDetailDTO);
    }

    @Test
    public void testGetOrderDetailsByOrderIdEmpty() {
        Integer orderId = 1;
        when(orderDetailService.getOrderDetailsByOrderId(orderId)).thenReturn(Collections.emptyList());

        List<OrderDetailDTO> response = orderDetailController.getOrderDetailsByOrderId(orderId);

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    // Test for deleteOrderDetail
    @Test
    public void testDeleteOrderDetailSuccess() {
        Long id = 1L;

        doNothing().when(orderDetailService).deleteOrderDetail(id);

        ResponseEntity<Void> response = orderDetailController.deleteOrderDetail(id);

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), 204); // No Content
    }

    @Test
    public void testDeleteOrderDetailFailure() {
        Long id = 1L;

        doThrow(new RuntimeException("OrderDetail not found")).when(orderDetailService).deleteOrderDetail(id);

        try {
            orderDetailController.deleteOrderDetail(id);
            fail("Expected exception not thrown");
        } catch (RuntimeException ex) {
            assertEquals(ex.getMessage(), "OrderDetail not found");
        }

        verify(orderDetailService, times(1)).deleteOrderDetail(id);
    }
}
