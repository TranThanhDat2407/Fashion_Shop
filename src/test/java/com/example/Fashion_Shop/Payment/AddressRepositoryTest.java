package com.example.Fashion_Shop.Payment;

import com.example.Fashion_Shop.model.Address;
import com.example.Fashion_Shop.model.User;
import com.example.Fashion_Shop.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @Transactional
    public void testSaveAddress() {

        Address address = new Address();
        address.setCity("Hồ Chí Minh");
        address.setWard("Ward 1");
        address.setStreet("123 Hoàng Hoa Thám");
        address.setIsDefault(true);


        Address savedAddress = addressRepository.save(address);


        assertThat(savedAddress.getId()).isNotNull();

        // Kiểm tra dữ liệu đã được lưu đúng
        assertThat(savedAddress.getCity()).isEqualTo("Hồ Chí Minh");
        assertThat(savedAddress.getWard()).isEqualTo("Ward 1");
        assertThat(savedAddress.getStreet()).isEqualTo("123 Hoàng Hoa Thám");
        assertThat(savedAddress.getIsDefault()).isTrue();

        // Kiểm tra việc lưu thành công trong cơ sở dữ liệu
        Address foundAddress = addressRepository.findById(savedAddress.getId()).orElse(null);
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getCity()).isEqualTo("Hồ Chí Minh");
        assertThat(foundAddress.getWard()).isEqualTo("Ward 1");
        assertThat(foundAddress.getStreet()).isEqualTo("123 Hoàng Hoa Thám");
        assertThat(foundAddress.getIsDefault()).isTrue();
    }
}
