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



INSERT INTO products (name, description, category_id)-- Thêm sản phẩm 1
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


INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) --ảnh sản phẩm 1
VALUES (1,6,'BLOUSON34BROWN.jpg',1),
       (1,7,'BLOUSON38DARKBROWN.jpg',0),
       (1,8,'BLOUSON08DARKGRAY.jpg',0)

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



INSERT INTO products (name, description, category_id)-- Thêm sản phẩm 2
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

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 2
VALUES ('2',9,'TSHIRT18WINE.jpg',1),
       ('2',7,'TSHIRT38DARKBROWN.jpg',0),
       ('2',10,'TSHIRT69NAVY.jpg',0)

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

select * from products

INSERT INTO products (name, description, category_id) -- Thêm sản phẩm 3
VALUES (N'Quần Dài Xếp Ly Ống Rộng', N'- Cải tiến với đường may suông thẳng.
- Thiết kế cạp thấp hơn để đáp lại phản hồi của khách hàng.
- Sợi vải được chọn lọc đặc biệt để tạo nên kết cấu trang nhã.
- Chất thun co giãn 2 chiều thoải mái.
- Thiết kế eo suông thoải mái.
- Dáng rộng vừa phải là lựa chọn hoàn hảo cho môi trường công sở hoặc hằng ngày.
- Chống nhăn để dễ chăm sóc. * Định dạng lại sản phẩm sau khi giặt.',
        (SELECT id
         FROM categories
         WHERE name = N'Quần dài dáng rộng'
           AND parent_id = (SELECT id
                            FROM categories
                            WHERE name = N'Quần'
                              AND parent_id = (SELECT id
                                               FROM categories
                                               WHERE name = N'Nam'))))

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 3
VALUES (3,11,'PANT06GRAY.jpg',1),
       (3,12,'PANT09BLACK.jpg',0),
       (3,13,'PANT32BEIGE.jpg',0),
       (3,14,'PANT70NAVY.jpg',0)

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

------------------Sản phẩm 4---------------------------

INSERT INTO attribute_values (value_name,value_img,attribute_id) -- Thêm 2 màu
VALUES ('01 OFF WHITE','01OFFWHITE.jpg',1),
        ('62 BLUE','62BLUE.jpg',1)

INSERT INTO products (name, description, category_id) -- Thêm sản phẩm 4
VALUES (
    N'DRY-EX Áo Khoác Siêu Co Giãn | Chống UV',
    N'- Đường viền đan thoáng khí.
- Công nghệ chống tia cực tím.
- Công nghệ ''DRY-EX''.
- Chất vải siêu co dãn.
- Tay áo Raglan giúp cánh tay cử động dễ dàng.
- Túi bên có khóa trượt.
- Có lỗ xỏ ngón tay cái.
-UPF50+.',
    (SELECT id
     FROM categories
     WHERE name = N'Áo khoác'
       AND parent_id = (SELECT id
                        FROM categories
                        WHERE name = N'Đồ mặc ngoài'
                          AND parent_id = (SELECT id
                                           FROM categories
                                           WHERE name = N'Nam')))
);

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 4
VALUES (4,15,'DRY01OFFWHITE.jpg',1),
       (4,16,'DRY62BLUE.jpg',0),
       (4,8,'DRY08DARKGRAY.jpg',0);

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (4,15,1,686000,686000,10),
       (4,15,2,686000,686000,15),--Áo khoác DRY màu 01 OFF WHITE SIZE XS S M L
       (4,15,3,686000,686000,20),
       (4,15,4,686000,686000,25),

       (4,16,1,686000,686000,10),
       (4,16,2,686000,686000,20),--Áo khoác DRY màu 62 BLUE SIZE XS S M L
       (4,16,3,686000,686000,30),
       (4,16,4,686000,686000,0),

       (4,8,2,686000,584000,15),--Áo Thun màu 08 DARK GRAY SIZE S M L
       (4,8,3,686000,584000,0),
       (4,8,4,686000,584000,0);

------------------Sản phẩm 5---------------------------

INSERT INTO attribute_values (value_name,value_img,attribute_id) -- Thêm 2 màu
VALUES ('02 LIGHT GRAY','02LIGHTGRAY.jpg',1),
       ('56 OLIVE','56OLIVE.jpg',1);

INSERT INTO products (name, description, category_id) -- Thêm sản phẩm 5
VALUES (
    N'Áo Gile Phao Lông Vũ Siêu Nhẹ',
    N'- Lông vũ ấm áp, nhẹ, chất lượng cao với công suất lấp đầy 750+.
- Lớp hoàn thiện chống thấm nước. * Vải được phủ một chất chống thấm nước nên hiệu quả kéo dài hơn. Sự kết thúc không phải là vĩnh viễn.
- Thiết kế dạng túi tiện lợi. Túi đựng được buộc chặt bằng khóa và có thể gắn hoặc tháo ra chỉ bằng một cú chạm.
- Lớp lót chống tĩnh điện.
- Kiểu dáng vừa vặn tạo nên lớp ngoài hoặc lớp giữa tuyệt vời',
    (SELECT id
     FROM categories
     WHERE name = N'Áo khoác'
       AND parent_id = (SELECT id
                        FROM categories
                        WHERE name = N'Đồ mặc ngoài'
                          AND parent_id = (SELECT id
                                           FROM categories
                                           WHERE name = N'Nam')))
);

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 5
VALUES (5,17,'GILE02LIGHTGRAY.jpg',1),
       (5,12,'GILE09BLACK.jpg',0),
       (5,6,'GILE34BROWN.jpg',0),
       (5,18,'GILE56OLIVE.jpg',0);

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (5,17,1,980000,980000,10), --Áo Gile màu 02 LIGHT GRAY SIZE XS S M L
       (5,17,2,980000,980000,12),
       (5,17,3,980000,980000,13),
       (5,17,4,980000,980000,15),

       (5,12,1,980000,980000,25), --Áo Gile màu 09 BLACK SIZE XS S M L
       (5,12,2,980000,980000,23),
       (5,12,3,980000,980000,24),
       (5,12,4,980000,980000,28),

       (5,6,1,980000,980000,34), --Áo Gile màu 34 BROWN SIZE XS S M L
       (5,6,2,980000,980000,35),
       (5,6,3,980000,980000,36),
       (5,6,4,980000,980000,37),

       (5,18,1,980000,980000,0), --Áo Gile màu 56 OLIVE SIZE XS S M L
       (5,18,2,980000,980000,12),
       (5,18,3,980000,980000,23),
       (5,18,4,980000,980000,45);
GO
------------------Sản phẩm 6---------------------------

INSERT INTO attribute_values (value_name,value_img,attribute_id) -- Thêm 2 màu
VALUES ('39 DARK BROWN','39DARKBROWN.jpg',1),
       ('06 GRAY CARO','06GRAYCARO.jpg',1)
INSERT INTO products (name, description, category_id) -- Thêm sản phẩm 6
VALUES (
    N'Áo Khoác Kiểu Sơ Mi Vải Double Face',
    N'- Chất liệu vải 2 mặt nhẹ, mềm, không gây ngứa.
- Túi có đường may bên hông giúp đựng được nhiều đồ.
- Đường khâu tối thiểu mang lại vẻ ngoài tinh tế.
- Hoàn hảo để mặc một mình hoặc mặc như một lớp áo để tăng thêm độ ấm. Thiết kế unisex.',
    (SELECT id
     FROM categories
     WHERE name = N'Áo khoác'
       AND parent_id = (SELECT id
                        FROM categories
                        WHERE name = N'Đồ mặc ngoài'
                          AND parent_id = (SELECT id
                                           FROM categories
                                           WHERE name = N'Nam')))
);

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 6
VALUES (6,11,'AOKHOACSOMI06GRAYCARO.jpg',1),
       (6,19,'AOKHOACSOMI39DARKBROWN.jpg',0);

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (6,20,1,1275000,1275000,15), --Áo Khoác Kiểu Sơ Mi màu 06 GRAY SIZE XS S M L
       (6,20,2,1275000,1275000,17),
       (6,20,3,1275000,1275000,19),
       (6,20,4,1275000,1275000,23),


       (6,19,1,1275000,1275000,21), --Áo Khoác Kiểu Sơ Mie màu 39 DARK BROWN SIZE XS S M L
       (6,19,2,1275000,1275000,0),
       (6,19,3,1275000,1275000,27),
       (6,19,4,1275000,1275000,30);
GO

------------------Sản phẩm 7---------------------------

INSERT INTO attribute_values (value_name,value_img,attribute_id) -- Thêm 1 màu
VALUES ('07 GRAY','07GRAY.jpg',1)

INSERT INTO products (name, description, category_id) -- Thêm sản phẩm 7
VALUES (
    N'Áo Khoác Thoải Mái',
    N'- Chất vải jersey nhẹ và có đường viền tạo cảm giác thoải mái.
- Kiểu dáng đẹp vừa vặn ít ôm sát ở phần eo.
- Kiểu dáng bo tròn sành điệu từ vai đến tay áo, thích hợp mặc đi làm và đi chơi.
- Có thể giặt bằng tay.',
    (SELECT id
     FROM categories
     WHERE name = N'Áo khoác'
       AND parent_id = (SELECT id
                        FROM categories
                        WHERE name = N'Đồ mặc ngoài'
                          AND parent_id = (SELECT id
                                           FROM categories
                                           WHERE name = N'Nam')))
);

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 7
VALUES (7,21,'AOKHOAC07GRAY.jpg',1),
       (7,7,'AOKHOAC38DARKBROWN.jpg',0);

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (7,21,1,1471000,1471000,15), --Áo Khoác thoải mái màu 07 GRAY SIZE XS S M L
       (7,21,2,1471000,1471000,17),
       (7,21,3,1471000,1471000,0),
       (7,21,4,1471000,1471000,23),

       (7,7,1,1471000,1471000,10), --Áo Khoác thoải mái màu 38 DARK BROWN SIZE XS S M L
       (7,7,2,1471000,1471000,9),
       (7,7,3,1471000,1471000,5),
       (7,7,4,1471000,1471000,19);
GO
------------------Sản phẩm 8---------------------------

INSERT INTO products (name, description, category_id) -- Thêm sản phẩm 8
VALUES (
    N'Miracle Air Áo Khoác | Vải Wool-like',
    N'- Chiếc áo khoác hiệu suất cao với chất liệu nhẹ, co giãn và nhanh khô nhờ loại vải độc đáo do Toray và UNIQLO đồng phát triển.
- Chất thun co giãn 2 chiều.
- Với công nghệ DRY khô nhanh.
- Kiểu dáng đẹp vừa vặn, thiết kế ít gò bó hơn ở phần eo.
- Áo khoác đa năng với kiểu dáng bo tròn tự nhiên từ vai đến tay áo.',
    (SELECT id
     FROM categories
     WHERE name = N'Áo khoác'
       AND parent_id = (SELECT id
                        FROM categories
                        WHERE name = N'Đồ mặc ngoài'
                          AND parent_id = (SELECT id
                                           FROM categories
                                           WHERE name = N'Nam')))
);

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 8
VALUES (8,8,'MIRACLEAIR08DARKGRAY.jpg',1),
       (8,12,'MIRACLEAIR09BLACK.jpg',0),
       (8,10,'MIRACLEAIR69NAVY.jpg',0);

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (8,8,2,1471000,1471000,15), --Miracle Air Áo Khoác màu 08 DARK GRAY SIZE S M L
       (8,8,3,1471000,1471000,17),
       (8,8,4,1471000,1471000,21),

       (8,12,2,1471000,1471000,12), --Miracle Air Áo Khoác màu 09 BLACK SIZE S M L
       (8,12,3,1471000,1471000,16),
       (8,12,4,1471000,1471000,20),

       (8,10,2,1471000,1471000,23), --Miracle Air Áo Khoác màu 69 NAVY SIZE S M L
       (8,10,3,1471000,1471000,27),
       (8,10,4,1471000,1471000,31);

------------------Sản phẩm 9---------------------------

INSERT INTO products (name, description, category_id)-- Thêm sản phẩm 9
VALUES (
    N'Áo Khoác Cản Gió Cổ Trụ',
    N'- Vải chức năng có lớp phim chống gió.
- Chất vải sắc nét, có nếp nhăn tự nhiên rất phù hợp cho phong cách thường ngày.
- Lớp hoàn thiện chống bám nước. * Vải được phủ một chất chống bám nước nên hiệu quả kéo dài hơn. Tuy nhiên chất phủ này không chống bám vĩnh viễn.
- Kiểu dáng thoải mái và độ dài ngắn.
- Tay áo Raglan.
- Mũ trùm đầu có thể cuộn vào trong cổ áo.
- Cổ tay áo có dây buộc dạng móc và vòng cho phép bạn điều chỉnh độ vừa vặn.
- Có dây rút có thể điều chỉnh ở viền áo.
- Túi bên trong có dây kéo.
- Lớp lót lưới thoải mái.
- Một trang phục thiết yếu cho những ngày thời tiết chuyển đổi.',
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

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 9
VALUES (9,15,'AOKHOACCOTRU01OFFWHITE.jpg',1),
       (9,12,'AOKHOACCOTRU09BLACK.jpg',0),
       (9,7,'AOKHOACCOTRU38DARKBROWN.jpg',0);

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (9,15,2,1275000,1275000,15), --Áo Khoác Cản Gió Cổ Trụ màu 01 OFF WHITE SIZE S M L XL
       (9,15,3,1275000,1275000,17),
       (9,15,4,1275000,1275000,23),
       (9,15,5,1275000,1275000,12),

       (9,12,2,1275000,1275000,5), --Áo Khoác Cản Gió Cổ Trụ màu 09 BLACK SIZE S M L XL
       (9,12,3,1275000,1275000,10),
       (9,12,4,1275000,1275000,15),
       (9,12,5,1275000,1275000,20),

       (9,7,2,1275000,1275000,0), --Áo Khoác Cản Gió Cổ Trụ màu 38 DARK BROWN SIZE S M L XL
       (9,7,3,1275000,1275000,5),
       (9,7,4,1275000,1275000,14),
       (9,7,5,1275000,1275000,21);

------------------Sản phẩm 10---------------------------

INSERT INTO attribute_values (value_name,value_img,attribute_id) -- Thêm 3 màu
VALUES ('57 OLIVE','57OLIVE.jpg',1),
       ('35 BROWN','35BROWN.jpg',1),
       ('30 NATURAL','30NATURAL.jpg',1)

INSERT INTO products (name, description, category_id)-- Thêm sản phẩm 10
VALUES (
    N'Áo Khoác BLOCKTECH | Có Mũ',
    N'- Chất liệu vải 2,5 lớp nguyên bản có khả năng chống bám nước, cản gió và thấm hút ẩm cực tốt. * Vải được phủ một chất chống bám nước nên hiệu quả kéo dài hơn. Tuy nhiên lớp chống bám nước này không tồn tại vĩnh viễn. *Vải không hoàn toàn không bám nước.
- Co giãn tuyệt vời.
- Băng dính trong suốt ở các đường nối giúp ngăn hơi ẩm.
- Dây khóa kéo chống bám nước ở phía trước.
- Kiểu dáng vừa vặn mang lại cảm giác thoải mái và dễ vận động.
- Đường cắt 3D mang lại sự chuyển động linh hoạt quanh vai.
- Dây rút ở viền áo giúp điều chỉnh độ vừa vặn.
- Túi bên có khóa trượt.
- Thiết kế cổ điển cho mọi điều kiện thời tiết.
- Cổ tay áo có dây buộc dạng móc và vòng cho phép bạn điều chỉnh độ vừa vặn.',
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

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 10
VALUES (10,12,'BLOCKTECH09BLACK.jpg',1),
       (10,24,'BLOCKTECH30NATURAL.jpg',0),
       (10,23,'BLOCKTECH35BROWN.jpg',0),
       (10,22,'BLOCKTECH57OLIVE.jpg',0),
       (10,10,'BLOCKTECH69NAVY.jpg',0)

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (10,12,1,1471000,1471000,15), --Áo Khoác BLOCKTECH | Có Mũ màu 09 BLACK SIZE XS S M L XL
       (10,12,2,1471000,1471000,17),
       (10,12,3,1471000,1471000,19),
       (10,12,4,1471000,1471000,21),
       (10,12,5,1471000,1471000,23),

       (10,24,1,1471000,1471000,23), --Áo Khoác BLOCKTECH | Có Mũ màu 30 NATURAL SIZE XS S M L XL
       (10,24,2,1471000,1471000,25),
       (10,24,3,1471000,1471000,0),
       (10,24,4,1471000,1471000,27),
       (10,24,5,1471000,1471000,29),

       (10,23,1,1471000,1471000,31), --Áo Khoác BLOCKTECH | Có Mũ màu 35 BROWN SIZE XS S M L XL
       (10,23,2,1471000,1471000,33),
       (10,23,3,1471000,1471000,35),
       (10,23,4,1471000,1471000,37),
       (10,23,5,1471000,1471000,39),

       (10,22,1,1471000,1471000,11), --Áo Khoác BLOCKTECH | Có Mũ màu 57 OLIVE SIZE XS S M L XL
       (10,22,2,1471000,1471000,13),
       (10,22,3,1471000,1471000,15),
       (10,22,4,1471000,1471000,19),
       (10,22,5,1471000,1471000,0),

       (10,10,1,1471000,1471000,10), --Áo Khoác BLOCKTECH | Có Mũ màu 69 NAVY SIZE XS S M L XL
       (10,10,2,1471000,1471000,20),
       (10,10,3,1471000,1471000,30),
       (10,10,4,1471000,1471000,3),
       (10,10,5,1471000,1471000,5);

------------------Sản phẩm 11---------------------------


INSERT INTO products (name, description, category_id)-- Thêm sản phẩm 11
VALUES (
    N'Áo Khoác Lông Vũ Siêu Nhẹ | Có Mũ',
    N'- Được làm từ vải nylon cứng cáp trọng lượng nhẹ 10-denier có độ bóng sáng.
- Lớp lót chống tĩnh điện.
- Băng dính trên cổ tay áo được chải từ bên trong để tạo cảm giác mềm mại.
- Kiểu dáng đẹp vừa vặn quanh eo và rộng rãi hơn quanh vai và phần ngực.
- Kiểu dáng cắt 3D giúp di chuyển dễ dàng.
- Cổ áo đứng không chạm cằm.
- Thiết kế độc đáo đảm bảo mũ trùm kín toàn bộ phần đầu.
- Chiều rộng ôm dần từ trên xuống dưới để tạo kiểu dáng đẹp mắt.
- Dây buộc phía trước được thiết kế để tăng thêm độ ấm.
- Túi đựng được buộc chặt bằng khóa để gắn vào và tháo ra dễ dàng chỉ bằng một cú chạm. Ultra Light Down Áo Khoác Phao Lông Vũ',
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

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 11
VALUES (11,12,'LONGVU09BLACK.jpg',1),
       (11,22,'LONGVU57OLIVE.jpg',0),
       (11,10,'LONGVU69NAVY.jpg',0);

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (11,12,1,1471000,1471000,15), --Áo Khoác Lông Vũ Siêu Nhẹ | Có Mũ màu 09 BLACK SIZE XS S M L XL
       (11,12,2,1471000,1471000,17),
       (11,12,3,1471000,1471000,0),
       (11,12,4,1471000,1471000,13),
       (11,12,5,1471000,1471000,11),

       (11,22,1,1471000,1471000,9), --Áo Khoác Lông Vũ Siêu Nhẹ | Có Mũ màu 57 OLIVE SIZE XS S M L XL
       (11,22,2,1471000,1471000,7),
       (11,22,3,1471000,1471000,5),
       (11,22,4,1471000,1471000,10),
       (11,22,5,1471000,1471000,12),

       (11,10,1,1471000,1471000,14), --Áo Khoác Lông Vũ Siêu Nhẹ | Có Mũ màu 69 NAVY SIZE XS S M L XL
       (11,10,2,1471000,1471000,15),
       (11,10,3,1471000,1471000,16),
       (11,10,4,1471000,1471000,17),
       (11,10,5,1471000,1471000,18);

------------------Sản phẩm 12---------------------------

INSERT INTO products (name, description, category_id)-- Thêm sản phẩm 12
VALUES (
    N'Seamless Down Áo Khoác | Có Mũ',
    N'- Lông vũ hiệu suất cao, chống thấm nước bền bỉ với lông vũ cao cấp có khả năng lấp đầy 750+*. *Đo bằng phương pháp IDFB.
- Không có đường may ngăn không khí lạnh lọt vào, có dây buộc chống thấm nước ở mặt trước. * Đường may ở tay áo, hai bên và mũ trùm đầu.
- Vải túi bên trong túi thắt lưng được làm bằng vải chải kỹ để giữ ấm.
- Giữ ấm cổ với cấu trúc mũ trùm đầu có viền cổ có đệm giúp ngăn không khí lạnh thổi vào.
- Túi thắt lưng được may đường may tạo kiểu dáng đẹp.',
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

INSERT INTO product_images(product_id, color_value_id, image_url,is_thumbnail) -- ảnh sản phẩm 12
VALUES (12,17,'SEAMLESSDOWN02LIGHTGRAY.jpg',1),
       (12,12,'SEAMLESSDOWN09BLACK.jpg',0),
       (12,6,'SEAMLESSDOWN34BROWN.jpg',0),
       (12,22,'SEAMLESSDOWN57OLIVE.jpg',0),
       (12,10,'SEAMLESSDOWN69NAVY.jpg',0);

INSERT INTO SKUs(product_id, color_value_id, size_value_id, original_price,sale_price, qty_in_stock)
VALUES (12,17,2,2944000,2944000,15), --Seamless Down Áo Khoác | Có Mũ màu 02 LIGHT GRAY SIZE S M L XL
       (12,17,3,2944000,2944000,17),
       (12,17,4,2944000,2944000,19),
       (12,17,5,2944000,2944000,21),

       (12,12,2,2944000,2944000,10), --Seamless Down Áo Khoác | Có Mũ màu 09 BLACK SIZE S M L XL
       (12,12,3,2944000,2944000,12),
       (12,12,4,2944000,2944000,14),
       (12,12,5,2944000,2944000,15),

       (12,6,2,2944000,2944000,16), --Seamless Down Áo Khoác | Có Mũ màu 34 BROWN SIZE S M L XL
       (12,6,3,2944000,2944000,18),
       (12,6,4,2944000,2944000,20),
       (12,6,5,2944000,2944000,22),

       (12,22,2,2944000,2944000,5), --Seamless Down Áo Khoác | Có Mũ màu 57 OLIVE SIZE S M L XL
       (12,22,3,2944000,2944000,10),
       (12,22,4,2944000,2944000,15),
       (12,22,5,2944000,2944000,20),

       (12,10,2,2944000,2944000,23), --Seamless Down Áo Khoác | Có Mũ màu 69 NAVY SIZE S M L XL
       (12,10,3,2944000,2944000,25),
       (12,10,4,2944000,2944000,27),
       (12,10,5,2944000,2944000,29);


