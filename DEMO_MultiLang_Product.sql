CREATE DATABASE DEMO_MultiLang
USE DEMO_MultiLang

CREATE TABLE Language (
    id CHAR(2) PRIMARY KEY,  -- ISO 639-1 Language Code
    LanguageName NVARCHAR(50) NOT NULL
);

CREATE TABLE Product (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    default_currency CHAR(3) NOT NULL -- ISO 4217 Currency Code (e.g., USD, EUR)
);

SELECT * FROM Product

CREATE TABLE ProductTextContent (
    id BIGINT IDENTITY(1,1)  PRIMARY KEY,
    AttributeName NVARCHAR(50) NOT NULL, -- 'name', 'description', etc.
    ProductId BIGINT NOT NULL,
    OriginalText NVARCHAR(MAX) NOT NULL,
    OriginalLanguageId CHAR(2) NOT NULL,
    FOREIGN KEY (ProductId) REFERENCES Product(id) ON DELETE CASCADE,
    FOREIGN KEY (OriginalLanguageId) REFERENCES Language(id),
    CONSTRAINT UQ_Product_Attribute UNIQUE (ProductId, AttributeName) -- Prevent duplicate attributes per product
);

CREATE TABLE ProductTranslatedText (
    id BIGINT IDENTITY(1,1)  PRIMARY KEY,
    TextId BIGINT NOT NULL,
    LanguageId CHAR(2) NOT NULL,
    TranslatedText NVARCHAR(MAX) NOT NULL,
    FOREIGN KEY (TextId) REFERENCES ProductTextContent(id) ON DELETE CASCADE,
    FOREIGN KEY (LanguageId) REFERENCES Language(id),
    CONSTRAINT UQ_Text_Language UNIQUE (TextId, LanguageId) -- Ensure unique translation per language
);

SELECT * FROM dbo.ProductTranslatedText;
CREATE TABLE ProductPrice (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    ProductId BIGINT NOT NULL,
    LanguageId CHAR(2) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Currency CHAR(3) NOT NULL, -- ISO 4217 Currency Code
    FOREIGN KEY (ProductId) REFERENCES Product(id) ON DELETE CASCADE,
    FOREIGN KEY (LanguageId) REFERENCES Language(id),
    CONSTRAINT UQ_Product_Language UNIQUE (ProductId, LanguageId) -- Ensure unique price per language
);


INSERT INTO Language (id, LanguageName)
VALUES
     ('vi', 'Vietnam'),
    ('en', 'English'),
    ('sp', 'Spanish'),
    ('it', 'Italian'),
    ('fr', 'French');

INSERT INTO Product (DefaultCurrency)
VALUES
     ('VND'),
    ('USD'),  -- Product 1: Base currency is USD
    ('EUR');  -- Product 2: Base currency is EUR

    -- Product 1 Original Texts (English)
INSERT INTO ProductTextContent (ProductId, OriginalText, AttributeName, OriginalLanguageId)
VALUES
    (1, 'Amazing Laptop', 'name', 'en'),
    (1, 'High-performance laptop with great features.', 'description', 'en');

-- Product 2 Original Texts (English)
INSERT INTO ProductTextContent (ProductId, OriginalText, AttributeName, OriginalLanguageId)
VALUES
    (2, 'Smartphone Pro', 'name', 'en'),
    (2, 'A premium smartphone with cutting-edge technology.', 'description', 'en');

-- Product 1 Translations
INSERT INTO ProductTranslatedText (TextId, LanguageId, TranslatedText)
VALUES

    -- Name Translations
    (1, 'vi', N'Laptop kì diệu'),
    (1, 'sp', 'Portátil increíble'),

    (1, 'it', 'Laptop incredibile'),
    (1, 'fr', 'Ordinateur portable incroyable'),
    -- Description Translations
    (2, 'vi', N'Laptop hiệu xuất cao với nhiều tính năng'),
    (2, 'sp', 'Portátil de alto rendimiento con grandes características.'),
    (2, 'it', 'Laptop ad alte prestazioni con grandi funzionalità.'),
    (2, 'fr', 'Ordinateur portable haute performance avec de superbes fonctionnalités.');

-- Product 2 Translations
INSERT INTO ProductTranslatedText (TextId, LanguageId, TranslatedText)
VALUES
    -- Name Translations
     (4, 'vi', N'Điện thoại thông minh premium'),
    (3, 'vi', N'Điện thoại thông minh chuyên nghiệp'),
    (3, 'sp', 'Smartphone Profesional'),
    (3, 'it', 'Smartphone Professionale'),
    (3, 'fr', 'Smartphone Professionnel'),
    -- Description Translations
    (4, 'sp', 'Un smartphone premium con tecnología de vanguardia.'),
    (4, 'it', 'Uno smartphone premium con tecnologia all’avanguardia.'),
    (4, 'fr', 'Un smartphone haut de gamme avec technologie de pointe.');

-- Product 1 Prices
INSERT INTO ProductPrice (ProductId, LanguageId, Price, Currency)
VALUES
       (1, 'vi', 2300000.00, 'VND'),
    (1, 'en', 100.00, 'USD'),
    (1, 'sp', 95.00, 'EUR'),
    (1, 'it', 98.50, 'EUR'),
    (1, 'fr', 102.00, 'EUR');

-- Product 2 Prices
INSERT INTO ProductPrice (ProductId, LanguageId, Price, Currency)
VALUES
     (2, 'vi', 22811837.00, 'VND'),
    (2, 'en', 900.00, 'USD'),
    (2, 'sp', 850.00, 'EUR'),
    (2, 'it', 870.00, 'EUR'),
    (2, 'fr', 880.00, 'EUR');

SELECT
    pc.AttributeName,
    pt.TranslatedText AS AttributeValue
FROM
    ProductTextContent pc
    JOIN ProductTranslatedText pt ON pc.id = pt.TextId
WHERE
    pc.ProductId = 1 AND pt.LanguageId = 'sp';

SELECT Price, Currency
FROM ProductPrice
WHERE ProductId = 1 AND LanguageId = 'vi';
