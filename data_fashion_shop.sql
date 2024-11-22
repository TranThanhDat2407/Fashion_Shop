SELECT DISTINCT p.*
FROM Products p
JOIN SKUs sku ON sku.product_id = p.id
JOIN attribute_values c ON c.id = sku.color_value_id
JOIN attribute_values s ON s.id = sku.size_value_id
JOIN categories cate on cate.id = p.category_id
WHERE 1 IS NULL
   OR p.category_id = 1
   OR cate.parent_id = 1;

INSERT INTO categories (name, parent_id) VALUES (N'Nam', NULL);
INSERT INTO categories (name, parent_id) VALUES (N'Đồ mặc ngoài', (SELECT id FROM categories WHERE name = N'Nam'));
INSERT INTO categories (name, parent_id) VALUES (N'Áo khoác', (SELECT id FROM categories WHERE name = N'Đồ mặc ngoài' AND parent_id = (SELECT id FROM categories WHERE name = N'Nam')));
INSERT INTO categories (name, parent_id) VALUES (N'Áo Blouson', (SELECT id FROM categories WHERE name = N'Đồ mặc ngoài' AND parent_id = (SELECT id FROM categories WHERE name = N'Nam')));
INSERT INTO categories (name, parent_id) VALUES (N'Áo thun, áo nỉ & áo giả lông cừu', (SELECT id FROM categories WHERE name = N'Nam'));
INSERT INTO categories (name, parent_id) VALUES (N'Áo thun', (SELECT id FROM categories WHERE name = N'Áo thun, áo nỉ & áo giả lông cừu' AND parent_id = (SELECT id FROM categories WHERE name = N'Nam')));
INSERT INTO categories (name, parent_id) VALUES (N'Áo nỉ & Hoodie', (SELECT id FROM categories WHERE name = N'Áo thun, áo nỉ & áo giả lông cừu' AND parent_id = (SELECT id FROM categories WHERE name = N'Nam')));
INSERT INTO categories (name, parent_id) VALUES (N'Quần', (SELECT id FROM categories WHERE name = N'Nam'));
INSERT INTO categories (name, parent_id) VALUES (N'Quần dài dáng rộng', (SELECT id FROM categories WHERE name = N'Quần' AND parent_id = (SELECT id FROM categories WHERE name = N'Nam')));
INSERT INTO categories (name, parent_id) VALUES (N'Quần Chino', (SELECT id FROM categories WHERE name = N'Quần' AND parent_id = (SELECT id FROM categories WHERE name = N'Nam')));

-- Đối với phân loại Nữ
INSERT INTO categories (name, parent_id) VALUES (N'Nữ', NULL);
INSERT INTO categories (name, parent_id) VALUES (N'Đồ mặc ngoài', (SELECT id FROM categories WHERE name = N'Nữ'));
INSERT INTO categories (name, parent_id) VALUES (N'Áo khoác', (SELECT id FROM categories WHERE name = N'Đồ mặc ngoài' AND parent_id = (SELECT id FROM categories WHERE name = N'Nữ')));
INSERT INTO categories (name, parent_id) VALUES (N'Áo Blazer', (SELECT id FROM categories WHERE name = N'Đồ mặc ngoài' AND parent_id = (SELECT id FROM categories WHERE name = N'Nữ')));
INSERT INTO categories (name, parent_id) VALUES (N'Áo sơ mi & Áo Kiểu', (SELECT id FROM categories WHERE name = N'Nữ'));
INSERT INTO categories (name, parent_id) VALUES (N'Áo sơ mi', (SELECT id FROM categories WHERE name = N'Áo sơ mi & Áo Kiểu' AND parent_id = (SELECT id FROM categories WHERE name = N'Nữ')));
INSERT INTO categories (name, parent_id) VALUES (N'Áo kiểu', (SELECT id FROM categories WHERE name = N'Áo sơ mi & Áo Kiểu' AND parent_id = (SELECT id FROM categories WHERE name = N'Nữ')));
INSERT INTO categories (name, parent_id) VALUES (N'Chân Váy & Đầm', (SELECT id FROM categories WHERE name = N'Nữ'));
INSERT INTO categories (name, parent_id) VALUES (N'Chân váy', (SELECT id FROM categories WHERE name = N'Chân Váy & Đầm' AND parent_id = (SELECT id FROM categories WHERE name = N'Nữ')));
INSERT INTO categories (name, parent_id) VALUES (N'Đầm', (SELECT id FROM categories WHERE name = N'Chân Váy & Đầm' AND parent_id = (SELECT id FROM categories WHERE name = N'Nữ')));

-- Đối với phân loại Trẻ em
INSERT INTO categories (name, parent_id) VALUES (N'Trẻ em', NULL);
INSERT INTO categories (name, parent_id) VALUES (N'Đồ mặc ngoài', (SELECT id FROM categories WHERE name = N'Trẻ em'));
INSERT INTO categories (name, parent_id) VALUES (N'Áo khoác', (SELECT id FROM categories WHERE name = N'Đồ mặc ngoài' AND parent_id = (SELECT id FROM categories WHERE name = N'Trẻ em')));
INSERT INTO categories (name, parent_id) VALUES (N'Áo Blouson', (SELECT id FROM categories WHERE name = N'Đồ mặc ngoài' AND parent_id = (SELECT id FROM categories WHERE name = N'Trẻ em')));
INSERT INTO categories (name, parent_id) VALUES (N'Áo thun, áo nỉ & áo giả lông cừu', (SELECT id FROM categories WHERE name = N'Trẻ em'));
INSERT INTO categories (name, parent_id) VALUES (N'Áo thun', (SELECT id FROM categories WHERE name = N'Áo thun, áo nỉ & áo giả lông cừu' AND parent_id = (SELECT id FROM categories WHERE name = N'Trẻ em')));
INSERT INTO categories (name, parent_id) VALUES (N'Áo nỉ & Hoodie', (SELECT id FROM categories WHERE name = N'Áo thun, áo nỉ & áo giả lông cừu' AND parent_id = (SELECT id FROM categories WHERE name = N'Trẻ em')));
INSERT INTO categories (name, parent_id) VALUES (N'Quần', (SELECT id FROM categories WHERE name = N'Trẻ em'));
INSERT INTO categories (name, parent_id) VALUES (N'Quần dài dáng rộng', (SELECT id FROM categories WHERE name = N'Quần' AND parent_id = (SELECT id FROM categories WHERE name = N'Trẻ em')));
INSERT INTO categories (name, parent_id) VALUES (N'Quần Chino', (SELECT id FROM categories WHERE name = N'Quần' AND parent_id = (SELECT id FROM categories WHERE name = N'Trẻ em')));

SELECT * FROM categories
-- thêm attribute
INSERT INTO attributes (attribute_name) VALUES ('Color'), ('Size');
select * From attributes
-- thêm attribute value
SELECT * FROM attribute_values
INSERT INTO attribute_values (value_name,value_img,attribute_id)
VALUES ('XS',null,2), --size
       ('S',null,2),
       ('M',null,2),
       ('L',null,2),
       ('XL',null,2),
       ('34 BROWN','34BROWN.jpg',1),
       ('38 DARK BROWN','38DARKBROWN.jpg',1),
        ('08 DARK GRAY','08DARKGRAY.jpg',1)

INSERT INTO attribute_values (value_name,value_img,attribute_id)
VALUES ('18 WINE','18WINE.jpg',1),
        ('69 NAVY','69NAVY.jpg',1)

INSERT INTO attribute_values (value_name,value_img,attribute_id)
VALUES ('06 GRAY','06GRAY.jpg',1),
       ('09 BLACK','09BLACK.jpg',1),
       ('32 BEIGE','32BEIGE.jpg',1),
        ('70 NAVY','70NAVY.jpg',1)

SELECT * FROM products
DELETE FROM products
DBCC CHECKIDENT (products, RESEED, 0);
INSERT INTO products (name, description, category_id)
VALUES (
    N'Áo Khoác Blouson Vải Brushed Jersey Kéo Khóa',
    N'- Mặt trong và mặt ngoài Áo Khoác Blouson Vải Brush Jersey đều được chải nhẹ, mang đến cảm giác mềm mại, thoải mái tuyệt đối.
    * Lớp lót bên trong được chải nhẹ, tạo nên độ mềm mại khác biệt so với bề mặt áo.
    - Dải thun mềm mại ở gấu áo và cổ tay ôm sát cơ thể, giúp giữ ấm và ngăn gió lùa, đồng thời cố định ống tay áo khi cần.',
    (SELECT id
     FROM categories
     WHERE name = N'Áo Blouson'
       AND parent_id = (SELECT id
                        FROM categories
                        WHERE name = N'Đồ mặc ngoài'
                          AND parent_id = (SELECT id
                                           FROM categories
                                           WHERE name = N'Nam')))
);

SELECT * FROM attribute_values
SELECT * FROM product_images

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) --ảnh sản phẩm 5
VALUES (1,6,'BLOUSON34BROWN.jpg',true),
       (1,7,'BLOUSON38DARKBROWN.jpg',false),
       (1,8,'BLOUSON08DARKGRAY.jpg',false)

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price, sale_price, qty_in_stock)
VALUES (1,6,2,980000,780000,15),--Blounse màu 34 BROWN SIZE S M L
       (1,6,3,980000,780000,20),
       (1,6,4,980000,780000,35),

       (1,13,2,980000,980000,50),--Blounse màu 38 BROWN SIZE S M L
       (1,13,3,980000,980000,10),
       (1,13,4,980000,980000,20),

        (1,14,2,980000,780000,11),--Blounse màu 08 GRAY SIZE S M L
       (1,14,3,980000,780000,12),
       (1,14,4,980000,780000,30)


SELECT * FROM products

INSERT INTO products (name, description, category_id)
VALUES (N'Áo Thun Vải Dạ Mềm Cổ Ba Phân', N'- Soft and comfy brushed lining.
- High collar design keeps the neck warm.
- Ribbed cuffs make it easy to roll up the sleeves.
- Versatile mock neck design with dropped shoulders and a relaxed fit, suitable for wearing alone or layered',
    (SELECT id
     FROM categories
     WHERE name = N'Áo thun'
       AND parent_id = (SELECT id
                        FROM categories
                        WHERE name = N'Áo thun, áo nỉ & áo giả lông cừu'
                          AND parent_id = (SELECT id
                                           FROM categories
                                           WHERE name = N'Nam')))
);
select * from attribute_values
INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 6
VALUES ('2',9,'TSHIRT18WINE.jpg',0),
       ('2',7,'TSHIRT38DARKBROWN.jpg',1),
       ('2',10,'TSHIRT69NAVY.jpg',1)

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price, sale_price, qty_in_stock)
VALUES ('2',9,2,389000,389000,15),--Áo Thun màu 18 Wine SIZE S M L
       ('2',9,3,389000,389000,20),
       ('2',9,4,389000,389000,35),

       ('2',7,2,389000,389000,15),--Áo Thun màu 38 DARKBROWN SIZE S M L
       ('2',7,3,389000,389000,20),
       ('2',7,4,389000,389000,35),

       ('2',10,2,389000,289000,15),--Áo Thun màu 69 NAVY SIZE S M L
       ('2',10,3,389000,289000,20),
       ('2',10,4,389000,289000,0)

INSERT INTO products (name, description, category_id)
VALUES (N'Quần Dài Xếp Ly Ống Rộng', N'- Cải tiến với đường may suông thẳng.
- Thiết kế cạp thấp hơn để đáp lại phản hồi của khách hàng.
- Sợi vải được chọn lọc đặc biệt để tạo nên kết cấu trang nhã.
- Chất thun co giãn 2 chiều thoải mái.
- Thiết kế eo suông thoải mái.
- Dáng rộng vừa phải là lựa chọn hoàn hảo cho môi trường công sở hoặc hằng ngày.
- Chống nhăn để dễ chăm sóc. * Định dạng lại sản phẩm sau khi giặt.',
        (SELECT id
         FROM categories
         WHERE name = N'Quần'
           AND parent_id = (SELECT id
                            FROM categories
                            WHERE name = N'Quần dài dáng rộng'
                              AND parent_id = (SELECT id
                                               FROM categories
                                               WHERE name = N'Nam'))))

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 7
VALUES (3,11,'PANT06GRAY.jpg',0),
       (3,12,'PANT09BLACK.jpg',1),
       (3,13,'PANT32BEIGE.jpg',1),
       (3,14,'PANT70NAVY.jpg',1)

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (3,11,2,784000,784000,15),--Áo Thun màu 18 Wine SIZE S M L
       (3,11,3,784000,784000,20),
       (3,11,4,784000,784000,35),

       (3,12,2,784000,784000,15),--Áo Thun màu 38 DARKBROWN SIZE S M L
       (3,12,3,784000,784000,20),
       (3,12,4,784000,784000,35),

       (3,13,2,784000,584000,15),--Áo Thun màu 69 NAVY SIZE S M L
       (3,13,3,784000,584000,0),
       (3,13,4,784000,584000,0),

       (3,14,2,784000,784000,15),--Áo Thun màu 69 NAVY SIZE S M L
       (3,14,3,784000,784000,20),
       (3,14,4,784000,784000,0)


