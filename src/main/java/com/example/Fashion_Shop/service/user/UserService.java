package com.example.Fashion_Shop.service.user;

import com.example.Fashion_Shop.component.JwtTokenUtil;
import com.example.Fashion_Shop.component.LocalizationUtils;
import com.example.Fashion_Shop.dto.UpdateUserDTO;
import com.example.Fashion_Shop.dto.UserDTO;
import com.example.Fashion_Shop.exception.DataNotFoundException;
import com.example.Fashion_Shop.exception.ExpiredTokenException;
import com.example.Fashion_Shop.exception.InvalidPasswordException;
import com.example.Fashion_Shop.exception.PermissionDenyException;
import com.example.Fashion_Shop.model.Role;
import com.example.Fashion_Shop.model.Token;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.RoleRepository;
import com.example.Fashion_Shop.repository.TokenRepository;
import com.example.Fashion_Shop.repository.UserRepository;
import com.example.Fashion_Shop.service.EmailService;
import com.example.Fashion_Shop.util.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final LocalizationUtils localizationUtils;

    @Override
    public User createUser(UserDTO userDTO) throws Exception {
        //register user
        String email = userDTO.getEmail();
        String phone = userDTO.getPhone();
        // Kiểm tra xem số điện thoại đã tồn tại hay chưa
        if (userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email đã tồn tại");
        }

        if (userRepository.existsByPhone(phone)) {
            throw new DataIntegrityViolationException("Số điện thoại đã tồn tại");
        }
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException(
                        localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS)));
        if (role.getName().toUpperCase().equals(Role.ADMIN)) {
            throw new PermissionDenyException("Không được phép đăng ký tài khoản Admin");
        }
        //convert from userDTO => user
        User newUser = User.builder()
                .name(userDTO.getName())
                .phone(userDTO.getPhone())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .active(true)
                .build();

        newUser.setRole(role);
        // Kiểm tra nếu có facebook/googoleId, không yêu cầu password
        if (userDTO.getFacebookAccountId() == null && userDTO.getGoogleAccountId() == null) {
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }

        return userRepository.save(newUser);
    }

    @Override
    public String login(String email, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.WRONG_EMAIL_PASSWORD));
        }
        //return optionalUser.get();//muốn trả JWT token ?
        User existingUser = optionalUser.get();
        //check password
        if (existingUser.getFacebookAccountId() == null &&
                existingUser.getGoogleAccountId() == null) {
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException
                        (localizationUtils.getLocalizedMessage(MessageKeys.WRONG_EMAIL_PASSWORD));
            }
        }
        /*
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS));
        }
        */
        if (!optionalUser.get().isActive()) {
            throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.USER_IS_LOCKED));
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password,
                existingUser.getAuthorities()
        );
        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtil.isTokenExpired(token)) {
            throw new ExpiredTokenException("Token is expired");
        }
        String email = jwtTokenUtil.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }

    @Override
    public User getUserDetailsFromRefreshToken(String refreshToken) throws Exception {
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);
        return getUserDetailsFromToken(existingToken.getToken());
    }

    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        return null;
    }

    @Override
    public Page<User> findAll(String keyword, Pageable pageable) throws Exception {
        return null;
    }

    @Override
    public void resetPassword(Long userId, String newPassword) throws InvalidPasswordException, DataNotFoundException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        existingUser.setPassword(encodedPassword);
        userRepository.save(existingUser);
        //reset password => clear token
        List<Token> tokens = tokenRepository.findByUser(existingUser);
        for (Token token : tokens) {
            tokenRepository.delete(token);
        }
    }

    @Override
    public void blockOrEnable(Long userId, Boolean active) throws DataNotFoundException {

    }

    public boolean checkIfEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkIfPhoneExists(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public void generateAndSendOTP(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy email."));

        String otp = String.valueOf((int) ((Math.random() * 900000) + 100000)); // Tạo OTP 6 chữ số
        user.setOneTimePassword(otp);
        user.setOtpRequestedTime(LocalDateTime.now());
        userRepository.save(user);

        // Gửi email
        String subject = "Khôi phục mật khẩu";
        String message = "Mã OTP của bạn là: " + otp + ". Vui lòng nhập mã này để đặt lại mật khẩu.";
        emailService.sendEmail(user.getEmail(), subject, message);
    }

    public boolean verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy email."));

        // Kiểm tra xem OTP có đúng và chưa hết hạn + 5 phút
        if (user.getOneTimePassword().equals(otp) &&
                user.getOtpRequestedTime().plusMinutes(5).isAfter(LocalDateTime.now())) {
            return true;
        }

        return false;
    }

    public void resetPassword(String email, String newPassword) throws DataNotFoundException {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        String encodedPassword = passwordEncoder.encode(newPassword);
        existingUser.setPassword(encodedPassword);
        userRepository.save(existingUser);
        //reset password => clear token
        List<Token> tokens = tokenRepository.findByUser(existingUser);
        for (Token token : tokens) {
            tokenRepository.delete(token);
        }

        String subject = "Khôi phục mật khẩu";
        String message = "Bạn đã đổi mật khẩu thành công!";
        emailService.sendEmail(existingUser.getEmail(), subject, message);
    }




}
