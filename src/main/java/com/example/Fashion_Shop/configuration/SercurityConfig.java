package com.example.Fashion_Shop.configuration;

import com.example.Fashion_Shop.repository.UserRepository;
import com.example.Fashion_Shop.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SercurityConfig {
    private final UserRepository userRepository;

    @Bean
    //UserDetailsService để tìm kiếm thông tin người dùng từ cơ sở dữ liệu.
    public UserDetailsService userDetailsService() {
        return email -> userRepository     //Tìm kiếm người dùng bằng email.
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Cannot find user with email = " + email));
    }

    @Bean
    //Cung cấp một bean dùng để mã hóa mật khẩu bằng thuật toán BCrypt.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    //Trả về đối tượng Authentication với thông tin người dùng và quyền hạn (roles/authorities).
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        //gọi UserDetailsService để tìm kiếm thông tin người dùng từ cơ sở dữ liệu.
        authProvider.setUserDetailsService(userDetailsService());
       //Sử dụng một PasswordEncoder (như BCryptPasswordEncoder)
        // để kiểm tra mật khẩu do người dùng nhập có khớp với mật khẩu được lưu trữ (sau khi mã hóa) hay không.
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    //Là thành phần chịu trách nhiệm xác thực người dùng trong Spring Security.
    //Khi một yêu cầu đăng nhập được gửi, thông tin đăng nhập của người dùng (username/password)
    // sẽ được chuyển tới AuthenticationManager để xác thực.
    //Dựa trên cấu hình, nó sẽ sử dụng AuthenticationProvider (như DaoAuthenticationProvider) để xử lý xác thực.
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}
