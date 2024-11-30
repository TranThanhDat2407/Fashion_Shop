package com.example.Fashion_Shop.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "phone", unique = true, nullable = false, length = 20)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "otp_requested_time")
    private LocalDateTime otpRequestedTime;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "facebook_account_id")
    private String facebookAccountId;

    @Column(name = "google_account_id")
    private String googleAccountId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> review;


    @Override
    //Trả về danh sách quyền hoặc vai trò (roles/authorities) của người dùng.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName().toUpperCase()));
        //authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorityList;
    }

    @Override
    // Trả về tên đăng nhập của người dùng.
    public String getUsername() {
        return email;
    }

    @Override
    //Trả về true nếu tài khoản chưa hết hạn.
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    //Trả về true nếu tài khoản chưa bị khóa.
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    //Trả về true nếu thông tin xác thực (mật khẩu) chưa hết hạn.
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    //Trả về true nếu tài khoản đang hoạt động.
    public boolean isEnabled() {
        return active;
    }
}
