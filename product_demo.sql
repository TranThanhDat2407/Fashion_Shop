CREATE TABLE cart(
    id       INT IDENTITY (1,1),
    product_variant_id   INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_variant_id) REFERENCES product_variants (variant_id)
)



SELECT * FROM attributes

INSERT INTO attributes (attribute_name)
VALUES ('Color'),
       ('Size')


SELECT * FROM products
INSERT INTO products (name, description)-- sản phẩm 5
VALUES (N'Áo Khoác Blouson Vải Brushed Jersey Kéo Khóa', N'-Mặt trong và mặt ngoài Áo Khoác Blouson Vải Brush Jersey đều được chải nhẹ, mang đến cảm giác mềm mại, thoải mái tuyệt đối. * Lớp lót bên trong được chải nhẹ, tạo nên độ mềm mại khác biệt so với bề mặt áo.
- Dải thun mềm mại ở gấu áo và cổ tay ôm sát cơ thể, giúp giữ ấm và ngăn gió lùa, đồng thời cố định ống tay áo khi cần.');

INSERT INTO products (name, description) --sản phẩm 6
VALUES (N'Áo Thun Vải Dạ Mềm Cổ Ba Phân', N'- Soft and comfy brushed lining.
- High collar design keeps the neck warm.
- Ribbed cuffs make it easy to roll up the sleeves.
- Versatile mock neck design with dropped shoulders and a relaxed fit, suitable for wearing alone or layered');

INSERT INTO products (name, description) --sản phẩm 7
VALUES (N'Quần Dài Xếp Ly Ống Rộng', N'- Cải tiến với đường may suông thẳng.
- Thiết kế cạp thấp hơn để đáp lại phản hồi của khách hàng.
- Sợi vải được chọn lọc đặc biệt để tạo nên kết cấu trang nhã.
- Chất thun co giãn 2 chiều thoải mái.
- Thiết kế eo suông thoải mái.
- Dáng rộng vừa phải là lựa chọn hoàn hảo cho môi trường công sở hoặc hằng ngày.
- Chống nhăn để dễ chăm sóc. * Định dạng lại sản phẩm sau khi giặt.');


SELECT * FROM attribute_values
INSERT INTO attribute_values (value_name,swatch_image_url,attribute_id)
VALUES ('XS',null,4), --size
       ('S',null,4),
       ('M',null,4),
       ('L',null,4),
       ('XL',null,4),
       ('34 BROWN','34BROWN.jpg',3),--color sản phẩm 5
       ('38 DARK BROWN','38DARKBROWN.jpg',3),
        ('08 DARK GRAY','08DARKGRAY.jpg',3)

SELECT * FROM attribute_values
INSERT INTO attribute_values (value_name,swatch_image_url,attribute_id)
VALUES ('18 WINE','18WINE.jpg',3),--color sản phẩm 6
        ('69 NAVY','69NAVY.jpg',3)

SELECT * FROM attribute_values
INSERT INTO attribute_values (value_name,swatch_image_url,attribute_id)
VALUES ('06 GRAY','06GRAY.jpg',3),
       ('09 BLACK','09BLACK.jpg',3),
       ('32 BEIGE','32BEIGE.jpg',3),--color sản phẩm 7
        ('70 NAVY','70NAVY.jpg',3)

SELECT * FROM product_images
WHERE product_id = 6;

INSERT INTO images(product_id, color_value_id, image_url) --ảnh sản phẩm 5
VALUES ('5',12,'BLOUSON34BROWN.jpg'),
       ('5',13,'BLOUSON38DARKBROWN.jpg'),
       ('5',14,'BLOUSON08DARKGRAY.jpg')

INSERT INTO images(product_id, color_value_id, image_url) -- ảnh sản phẩm 6
VALUES ('6',15,'TSHIRT18WINE.jpg'),
       ('6',13,'TSHIRT38DARKBROWN.jpg'),
       ('6',16,'TSHIRT69NAVY.jpg')

INSERT INTO images(product_id, color_value_id, image_url) -- ảnh sản phẩm 7
VALUES ('7',17,'PANT06GRAY.jpg'),
       ('7',18,'PANT09BLACK.jpg'),
       ('7',19,'PANT32BEIGE.jpg'),
       ('7',20,'PANT70NAVY.jpg')

select * from attribute_values
SELECT * FROM product_variants
WHERE product_id = 5;

INSERT INTO product_variants(product_id, color_value_id, size_value_id, price, quantity)
VALUES ('5',12,8,780000,15),--Blounse màu 34 BROWN SIZE S M L
       ('5',12,9,780000,20),
       ('5',12,10,780000,35),

       ('5',13,8,980000,50),--Blounse màu 38 BROWN SIZE S M L
       ('5',13,9,980000,10),
       ('5',13,10,980000,20),

        ('5',14,8,780000,11),--Blounse màu 08 GRAY SIZE S M L
       ('5',14,9,780000,12),
       ('5',14,10,780000,30)

INSERT INTO product_variants(product_id, color_value_id, size_value_id, price, quantity)
VALUES ('6',15,8,389000,15),--Áo Thun màu 18 Wine SIZE S M L
       ('6',15,9,389000,20),
       ('6',15,10,389000,35),

       ('6',13,8,389000,15),--Áo Thun màu 38 DARKBROWN SIZE S M L
       ('6',13,9,389000,20),
       ('6',13,10,389000,35),

       ('6',16,8,289000,15),--Áo Thun màu 69 NAVY SIZE S M L
       ('6',16,9,289000,20),
       ('6',16,10,289000,0)

INSERT INTO product_variants(product_id, color_value_id, size_value_id, price, quantity)
VALUES ('7',17,8,784000,15),--Áo Thun màu 18 Wine SIZE S M L
       ('7',17,9,784000,20),
       ('7',17,10,784000,35),

       ('7',18,8,784000,15),--Áo Thun màu 38 DARKBROWN SIZE S M L
       ('7',18,9,784000,20),
       ('7',18,10,784000,35),

       ('7',19,8,588000,15),--Áo Thun màu 69 NAVY SIZE S M L
       ('7',19,9,588000,0),
       ('7',19,10,289058800000,0),

       ('7',20,8,784000,15),--Áo Thun màu 69 NAVY SIZE S M L
       ('7',20,9,784000,20),
       ('7',20,10,784000,0)


UPDATE products
SET product_name = N'Áo Khoác Blouson Vải Brushed Jersey'
WHERE product_id = 5;

UPDATE products
SET description = N' -Mặt trong và mặt ngoài Áo Khoác Blouson Vải Brush Jersey đều được chải nhẹ, mang đến cảm giác mềm mại, thoải mái tuyệt đối. ' +
                  N' * Lớp lót bên trong được chải nhẹ, tạo nên độ mềm mại khác biệt so với bề mặt áo.' +
                  N' - Dải thun mềm mại ở gấu áo và cổ tay ôm sát cơ thể, giúp giữ ấm và ngăn gió lùa, đồng thời cố định ống tay áo khi cần.'
WHERE product_id = 5;


SELECT pv.*
FROM product_variants pv
JOIN products p ON pv.product_id = p.product_id
JOIN attribute_values color ON pv.color_value_id = color.value_id
JOIN attribute_values size ON pv.size_value_id = size.value_id
WHERE p.product_id = 7 AND color.value_id = 19 AND size.value_id = 10

UPDATE product_variants
SET price = 588000
where variant_id = 43

DELETE FROM categories

SELECT * FROM attribute_values

SELECT * FROM categories

INSERT INTO categories(name,parent_id)
VALUES (N'Nữ',null);
DELETE FROM categories

DBCC CHECKIDENT (categories, RESEED, 0);