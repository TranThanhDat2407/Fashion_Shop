package com.example.Fashion_Shop.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*bộ lọc CORS (Cross-Origin Resource Sharing)
tùy chỉnh được triển khai trong ứng dụng Spring Boot.
Nó đảm bảo rằng các yêu cầu HTTP từ các nguồn (domain) khác nhau có thể tương tác với backend
bằng cách thiết lập các header phù hợp.*/

@Component
//Xác định thứ tự ưu tiên cao nhất trong chuỗi các bộ lọc (filter). Do đó, bộ lọc này sẽ được thực thi đầu tiên.
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter extends OncePerRequestFilter {
    @Override
    //Phương thức này xử lý các yêu cầu HTTP và phản hồi để áp dụng các quy tắc CORS.
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        //Cho phép các yêu cầu từ mọi nguồn (domain).
        response.setHeader("Access-Control-Allow-Origin", "*");
        //Xác định các phương thức HTTP được phép khi truy cập tài nguyên.
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        //request có thể được lưu trong 1 giờ (3600 giây).
        response.setHeader("Access-Control-Max-Age", "3600");
        //Xác định các header mà client được phép gửi kèm trong yêu cầu.
        //Đảm bảo các header như authorization và content-type được chấp nhận bởi server.
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
        //Chỉ định các header trong phản hồi mà trình duyệt có thể hiển thị cho client (FE).
        //Ở đây, header xsrf-token được hiển thị.
        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
        if ("OPTIONS".equals(request.getMethod())) {
            //Nếu phương thức của yêu cầu là OPTIONS, bộ lọc sẽ:
            //Phản hồi ngay lập tức với mã trạng thái HTTP 200 OK.
            //Bỏ qua việc xử lý tiếp yêu cầu trong chuỗi filter.
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}