package com.example.Fashion_Shop.component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    // kiểm tra
    // mỗi khi một yêu cầu HTTP đi qua filter này.
    // Nó có nhiệm vụ kiểm tra JWT trong tiêu đề của yêu cầu và xác thực người dùng
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws  IOException {
        try {
            //Nếu yêu cầu nằm trong danh sách bypassTokens, bỏ qua bước xác thực JWT và tiếp tục xử lý các filter khác.
            if(isBypassToken(request)) {
            filterChain.doFilter(request, response); //enable bypass
            return;
        }
            //Kiểm tra xem tiêu đề Authorization có tồn tại và bắt đầu bằng chuỗi Bearer hay không.
            //Nếu không, trả về lỗi 401 Unauthorized.
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        // cắt chuỗi bearer để lấy token
        final String token = authHeader.substring(7);
        //Sử dụng tiện ích JwtTokenUtil để lấy thông tin email hoặc số điện thoại từ token.
        final String email = jwtTokenUtil.extractEmail(token);
        System.out.println(email);
        //Kiểm tra xem người dùng có tồn tại và chưa được xác thực trong SecurityContextHolder. và có email
        if (!email.isEmpty()
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails =  userDetailsService.loadUserByUsername(email);
            System.out.println(userDetails.getUsername());
            //Nếu token hợp lệ:
            //Tạo đối tượng UsernamePasswordAuthenticationToken.
            //Đặt thông tin xác thực vào SecurityContextHolder,
            //giúp các phần tiếp theo trong ứng dụng biết rằng yêu cầu đã được xác thực.
            if(jwtTokenUtil.validateToken(token, userDetails)) {
                // token đã xác thc
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,// Principal: Thông tin người dùng
                                null,// Credentials: Không cần mật khẩu do đã xác thực qua validateToken
                                userDetails.getAuthorities()// Authorities: Quyền của người dùng
                        );
                //Lớp này được sử dụng để thêm thông tin bổ sung vào đối tượng xác thực,
                // chẳng hạn như địa chỉ IP hoặc thông tin user agent từ yêu cầu HTTP hiện tại (request).
                // Thông tin này sẽ được lưu trữ trong authenticationToken.
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                /*
                Đây là một công cụ trung tâm của Spring Security để lưu trữ thông tin bảo mật trong ngữ cảnh hiện tại.
                Mục đích: Giữ thông tin xác thực (authentication) trong suốt quá trình xử lý yêu cầu HTTP.
                Nó sử dụng ThreadLocal để lưu dữ liệu, giúp mỗi luồng xử lý một yêu cầu có dữ liệu bảo mật riêng.
                */
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        //Sau khi xử lý, yêu cầu sẽ được truyền đến các filter tiếp theo
            // (hoặc tới endpoint nếu không còn filter nào).
            filterChain.doFilter(request, response); //enable bypass
        }catch (Exception e) {
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }

    }

    //Các endpoint trong danh sách này không yêu cầu xác thực JWT.
    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/roles**", apiPrefix), "GET"),
                Pair.of(String.format("%s/cart**", apiPrefix), "GET"),
                Pair.of(String.format("%s/cart**", apiPrefix), "PUT"),
                Pair.of(String.format("%s/cart**", apiPrefix), "POST"),
                Pair.of(String.format("%s/cart**", apiPrefix), "DELETE"),
                Pair.of(String.format("%s/reviews**", apiPrefix), "GET"),
                Pair.of(String.format("%s/products**", apiPrefix), "GET"),
                Pair.of(String.format("%s/category**", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/check-email", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/check-phone", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/forgot-password", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/verify-otp", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/reset-password", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/refreshToken", apiPrefix), "POST")
        );
        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> token : bypassTokens) {
            String path = token.getFirst();
            String method = token.getSecond();
            if (requestPath.matches(path.replace("**", ".*"))
                    && requestMethod.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
}
