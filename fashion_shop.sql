CREATE DATABASE FASHION_SHOP
GO

USE FASHION_SHOP
GO

CREATE TABLE roles
(
    id   INT IDENTITY (1,1) NOT NULL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE users
(
    id                 INT IDENTITY (1,1) not null,
    password           NVARCHAR(100)      not null,
    name               NVARCHAR(100)      not null,
    phone              NVARCHAR(20) UNIQUE,
    email              NVARCHAR(100) UNIQUE,
    one_time_password  nvarchar(20),
    otp_requested_time DATETIME DEFAULT GETDATE(),
    role_id            INT                NOT NULL,
    is_active          bit      default 0,
    create_at          DATETIME DEFAULT GETDATE(),
    update_at          DATETIME DEFAULT GETDATE(),
    primary key (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE address
(
    id         INT IDENTITY (1,1) NOT NULL,
    city       NVARCHAR(100)      NOT NULL,
    ward       NVARCHAR(100)      NOT NULL,
    street     NVARCHAR(200)      NOT NULL,
    is_Default BIT                NOT NULL DEFAULT 0,
    user_id    INT                NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
);
GO

CREATE TABLE categories
(
    id        INT IDENTITY (1,1) NOT NULL,
    parent_id INT                NULL,
    name      NVARCHAR(100)      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (parent_id) REFERENCES categories (id)
);
GO

CREATE TABLE products
(
    id          INT IDENTITY (1,1) NOT NULL,
    category_id INT                NOT NULL,
    name        NVARCHAR(100)      NOT NULL,
    description NVARCHAR(MAX)      NOT NULL,
    create_at   DATETIME DEFAULT GETDATE(),
    update_at   DATETIME DEFAULT GETDATE(),
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);
GO

CREATE TABLE attributes
(
    id             INT PRIMARY KEY IDENTITY (1,1),
    attribute_name NVARCHAR(100) NOT NULL
);

CREATE TABLE attribute_values
(
    id           INT PRIMARY KEY IDENTITY (1,1),
    attribute_id INT           NOT NULL,
    value_name   NVARCHAR(100) NOT NULL,
    value_img    NVARCHAR(100) NULL,
    FOREIGN KEY (attribute_id) REFERENCES attributes (id)
);

CREATE TABLE product_images
(
    id             INT PRIMARY KEY IDENTITY (1,1),
    product_id     INT           NOT NULL,
    color_value_id INT           NULL,
    image_url      NVARCHAR(255) NOT NULL,
    is_thumbnail   BIT DEFAULT 0,
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
    FOREIGN KEY (color_value_id) REFERENCES attribute_values (id) ON DELETE CASCADE
);

CREATE TABLE SKUs
(
    id             INT PRIMARY KEY IDENTITY (1,1),
    product_id     INT   NOT NULL,
    color_value_id INT   NULL,
    size_value_id  INT   NOT NULL,
    original_price FLOAT NOT NULL CHECK (original_price >= 0),
    sale_price     FLOAT NOT NULL CHECK (sale_price >= 0),
    qty_in_stock   INT   NOT NULL,
    create_at      DATETIME DEFAULT GETDATE(),
    update_at      DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (color_value_id) REFERENCES attribute_values (id),
    FOREIGN KEY (size_value_id) REFERENCES attribute_values (id)
);

CREATE TABLE carts
(
    id       INT IDENTITY (1,1),
    user_id  INT NOT NULL,
    sku_id   INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (sku_id) REFERENCES SKUs (id)
)

ALTER TABLE carts
ADD create_at DATETIME DEFAULT GETDATE() NOT NULL,
    update_at DATETIME DEFAULT GETDATE() NOT NULL;

CREATE TABLE wishlist
(
    id      INT IDENTITY (1,1),
    user_id INT NOT NULL,
    sku_id  INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (sku_id) REFERENCES SKUs (id)
)

CREATE TABLE reviews
(
    id           INT IDENTITY (1,1),
    skus_id      INT           NOT NULL,
    user_id      INT           NOT NULL,
    rating_value INT CHECK (rating_value BETWEEN 1 AND 5),
    comment      NVARCHAR(500) NOT NULL,
    create_at    DATETIME      NOT NULL DEFAULT GETDATE(),
    update_at    DATETIME      NOT NULL DEFAULT GETDATE(),
    PRIMARY KEY (id),
    FOREIGN KEY (skus_id) REFERENCES SKUs (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
)

CREATE TABLE orders
(
    id               INT IDENTITY (1,1) NOT NULL,
    shipping_address NVARCHAR(255)      NOT NULL,
    phone_number     nvarchar(20),
    total_money      FLOAT              NOT NULL,-- tổng giá trị
    status           VARCHAR(20)        NOT NULL CHECK (status IN ('pending', 'processing', 'shipped', 'delivered', 'cancelled')), -- trạng thái đơn hàng
    create_at        DATETIME DEFAULT GETDATE(),
    update_at        DATETIME DEFAULT GETDATE(),
    user_id          INT                NOT NULL,
    QR_code          NVARCHAR(100)      NULL,
    is_active        BIT      DEFAULT 0, -- xóa mềm
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE order_detail
(
    id          INT IDENTITY (1,1) NOT NULL,
    order_id    INT                NOT NULL,
    skus_id     INT                NOT NULL,
    quantity    INT                NOT NULL,
    price       FLOAT              NOT NULL,
    total_money FLOAT CHECK (total_money >= 0),
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (skus_id) REFERENCES SKUs (id)
);

CREATE TABLE user_payments
(
    id               INT PRIMARY KEY,
    user_id          INT,
    card_number      NVARCHAR(30),
    card_holder_name NVARCHAR(50),
    expiry_date      DATE,
    provider         NVARCHAR(50),
    is_default       BIT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE order_payments
(
    id                       INT PRIMARY KEY,
    order_id                 INT,
    transaction_id           NVARCHAR(100),
    transaction_date         DATE,
    payment_gateway_response NVARCHAR(MAX),
    amount                   MONEY,        -- tổng tiền thanh toán
    status                   NVARCHAR(20), -- trạng thái "thanh toán thành công" / "thanh toán thành công"
    FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE tokens (
    id int IDENTITY(1,1) PRIMARY KEY,
    token NVARCHAR(255) NOT NULL,
    refresh_token NVARCHAR(255) NOT NULL,
    token_type NVARCHAR(50) NOT NULL,
    expiration_date DATETIME NOT NULL,
    refresh_expiration_date DATETIME NOT NULL,
    is_mobile BIT NOT NULL,
    revoked BIT NOT NULL,
    expired BIT NOT NULL,
    user_id int FOREIGN KEY REFERENCES users(id)
);


ALTER TABLE users
ADD facebook_account_id varchar(255),
    google_account_id varchar(255);

SELECT * FROM users
SELECT * FROM tokens
SELECT * FROM Roles
drop table tokens

SELECT * FROM product_images
SELECT * FROM product_images WHERE product_id = 2 AND color_value_id = 9;
SELECT color_value_id FROM skus WHERE id = 4;
SELECT * FROM product_images WHERE product_id = 1;
SELECT * FROM SKUs
SELECT * FROM product_images
WHERE product_id = 1 AND color_value_id != 4;

SELECT c.id, c.sku_id,c.user_id, c.quantity
FROM carts c
WHERE c.user_id = 1;

SELECT * FROM carts where user_id = 1