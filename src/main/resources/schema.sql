CREATE TABLE IF NOT EXISTS layout
(
    `layoutId`    VARCHAR(36)     NOT NULL    COMMENT '레이아웃 식별키',
    `name`        VARCHAR(45)     NOT NULL    COMMENT '레이아웃 이름',
    PRIMARY KEY (layoutId)
);

CREATE TABLE IF NOT EXISTS product
(
    `productId`     VARCHAR(36)     NOT NULL    COMMENT '상품 식별키',
    `name`          VARCHAR(45)     NOT NULL    COMMENT '상품명',
    `price`         INT             NOT NULL    COMMENT '금액',
    `layoutId`      VARCHAR(36)     NOT NULL    COMMENT '레이아웃 식별키',
    PRIMARY KEY (productId),
    CONSTRAINT FK_product_layoutId_layout_layoutId FOREIGN KEY (layoutId)
        REFERENCES layout (layoutId) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO layout (layoutId, name)
VALUES ('layout-1', 'FLO가 처음이라면 2개월간 100원');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-1', '무제한 듣기 정기결제', 100, 'layout-1');

INSERT INTO layout (layoutId, name)
VALUES ('layout-2', '무제한 듣기+오프라인 재생');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-2', '정기결제', 10900, 'layout-2');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-3', 'T멤버십 최소 100원 6개월', 100, 'layout-2');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-4', '1개월권', 11000, 'layout-2');

INSERT INTO layout (layoutId, name)
VALUES ('layout-3', '무제한 듣기');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-5', '정기결제', 7900, 'layout-3');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-6', 'T멤버십 최소 100원 6개월', 100, 'layout-3');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-7', '1개월권', 8000, 'layout-3');

INSERT INTO layout (layoutId, name)
VALUES ('layout-4', '300회 듣기');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-8', '1개월권', 4800, 'layout-4');

INSERT INTO layout (layoutId, name)
VALUES ('layout-5', '모바일 무제한 듣기');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-9', '정기결제', 6900, 'layout-5');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-10', 'T멤버십 최소 100원 6개월', 100, 'layout-5');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('product-11', '1개월권', 7000, 'layout-5');
