SELECT DISTINCT p.*
FROM Products p
JOIN SKUs sku ON sku.product_id = p.id
JOIN attribute_values c ON c.id = sku.color_value_id
JOIN attribute_values s ON s.id = sku.size_value_id
JOIN categories cate on cate.id = p.category_id
WHERE 4 IS NULL
   OR p.category_id = 4
   OR cate.parent_id = 4;

DELETE FROM categories

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





