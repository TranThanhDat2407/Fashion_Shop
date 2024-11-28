select * from users;
select * from skus;
select * from roles;

ALTER TABLE carts
ADD create_at DATETIME DEFAULT GETDATE() NOT NULL,
    update_at DATETIME DEFAULT GETDATE() NOT NULL;

insert into roles (name)
values ('admin'),
       ('user')

insert into users (password, name, phone, email, role_id)
values ('Password123', 'Dat', '0931234567','datttps37451@fpt.edu.vn',2)

insert into users (password, name, phone, email, role_id)
values ('Password123', 'Chan', '0931234465','chanlps37451@fpt.edu.vn',2)

insert into carts(user_id, sku_id, quantity)
values (1,1,2),
       (1,8,5),
       (1,7,3)

Select * from carts
ORDER BY create_at DESC

Select SUM(c.quantity)
FROM carts c
WHERE c.user_id = 1

-------------------------------------------------------------------------------------------------------------------------------------------

    -- phần insert cart and orders

    INSERT INTO users (name, email, phone, password, role_id, one_time_password, otp_requested_time, is_active)
VALUES
    ('Van Tai',
    'taibvps37458@fpt.edu.vn',
    '1234567890',
    '12345',
    (SELECT id FROM roles WHERE roles.name = 'ADMIN'),
    'otp123',
    GETDATE(),
    1);


INSERT INTO users (name, email, phone, password, role_id)
VALUES
    ('Văn Tài 4',
     'tai3@gmail.com',
     '035435434',
     '12345',
     1
    );

select * from address



select * from users


    insert into carts(user_id, sku_id, quantity)
values (4,1,2),
    (3,8,5),
    (4,7,3)


INSERT INTO address (city, ward, street, is_Default, user_id)
VALUES
    (N'Hồ Chí Minh', N'Ward 1', N'123 Hoàng Hoa Thám', 1, 4)


INSERT INTO SKUs (product_id, color_value_id, size_value_id, original_price, sale_price, qty_in_stock)
VALUES
    (1, 1, 3, 200000, 180000, 50),
    (2, 2, 4, 1200000, 1000000, 30);


INSERT INTO carts (user_id, sku_id, quantity)
VALUES
    (4, 20, 2),
    (4, 30, 1);


INSERT INTO products (category_id, name, description)
VALUES
    (1, N'Áo thun', N'Áo thun cotton cao cấp'),
    (2, N'Giày thể thao', N'Giày thể thao nam');

INSERT INTO categories (parent_id, name)
VALUES
    (NULL, N'Quần áo'),
    (NULL, N'Giày dép');

INSERT INTO address (city, ward, street, is_Default, user_id)
VALUES
    (N'Hanoi', N'Dong Da', N'123 Pho Hue', 1, 1),
    (N'HCM', N'District 1', N'456 Nguyen Hue', 0, 2);

INSERT INTO attributes (attribute_name) VALUES (N'Color'), (N'Size');

INSERT INTO attribute_values (attribute_id, value_name)
VALUES
    (1, N'Red'),
    (1, N'Blue'),
    (2, N'M'),
    (2, N'L');


INSERT INTO address (city, ward, street, is_Default, user_id)
VALUES (N'Hanoi', N'Dong Da', N'123 Pho Hue', 1, 5);


INSERT INTO carts (user_id, sku_id, quantity)
VALUES
    (5, 1, 2),
    (5, 2, 1);

INSERT INTO carts (user_id, sku_id, quantity)
VALUES
    (1, 1, 2),  -- SKU ID 1, quantity: 2
    (1, 8, 5),  -- SKU ID 8, quantity: 5
    (1, 7, 3),  -- SKU ID 7, quantity: 3
    (1, 12, 8), -- SKU ID 12, quantity: 8
    (1, 19, 3), -- SKU ID 19, quantity: 3
    (1, 1, 2),  -- SKU ID 1, quantity: 2 (lặp lại)
    (1, 2, 1),  -- SKU ID 2, quantity: 1
    (1, 1, 2),  -- SKU ID 1, quantity: 2 (lặp lại)
    (1, 2, 1);  -- SKU ID 2, quantity: 1 (lặp lại)

INSERT INTO SKUs (product_id, color_value_id, size_value_id, original_price, sale_price, qty_in_stock)
VALUES
    (1, 1, 3, 980000, 780000, 50),  -- SKU ID 1
    (1, 2, 3, 980000, 780000, 50),  -- SKU ID 2
    (2, 1, 4, 389000, 389000, 30);  -- SKU ID 19


SELECT c.id, c.user_id, c.sku_id, s.original_price, s.sale_price, c.quantity
FROM carts c
         JOIN SKUs s ON c.sku_id = s.id
WHERE c.user_id = 1;

select * from users

select * from carts
select * from order_detail
select * from address




