select * from users;
select * from skus;
select * from roles;
DELETE FROM users
where email = 'datttps37451@gmail.com'

DELETE FROM users
where id = 29
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