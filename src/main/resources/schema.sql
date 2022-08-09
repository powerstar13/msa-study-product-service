DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS layout;

CREATE TABLE IF NOT EXISTS layout
(
    `layoutId`      VARCHAR(36)     NOT NULL    DEFAULT UUID() COMMENT '레이아웃 식별키',
    `name`          VARCHAR(45)     NOT NULL    COMMENT '레이아웃 이름',
    `createdAt`     TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`     TIMESTAMP       NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (layoutId)
);

CREATE TABLE IF NOT EXISTS product
(
    `productId`     VARCHAR(36)     NOT NULL    DEFAULT UUID() COMMENT '상품 식별키',
    `name`          VARCHAR(45)     NOT NULL    COMMENT '상품명',
    `price`         INT             NOT NULL    COMMENT '금액',
    `layoutId`      VARCHAR(36)     NOT NULL    COMMENT '레이아웃 식별키',
    `createdAt`     TIMESTAMP       NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updatedAt`     TIMESTAMP       NULL        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (productId),
    CONSTRAINT FK_product_layoutId_layout_layoutId FOREIGN KEY (layoutId)
        REFERENCES layout (layoutId) ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO layout (layoutId, name)
VALUES ('492bc213-5c47-422f-9390-1b0c2206bc01', 'FLO가 처음이라면 2개월간 100원');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab01', '무제한 듣기 정기결제', 100, '492bc213-5c47-422f-9390-1b0c2206bc01');

INSERT INTO layout (layoutId, name)
VALUES ('492bc213-5c47-422f-9390-1b0c2206bc02', '무제한 듣기+오프라인 재생');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab02', '정기결제', 10900, '492bc213-5c47-422f-9390-1b0c2206bc02');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab03', 'T멤버십 최소 100원 6개월', 100, '492bc213-5c47-422f-9390-1b0c2206bc02');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab04', '1개월권', 11000, '492bc213-5c47-422f-9390-1b0c2206bc02');

INSERT INTO layout (layoutId, name)
VALUES ('492bc213-5c47-422f-9390-1b0c2206bc03', '무제한 듣기');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab05', '정기결제', 7900, '492bc213-5c47-422f-9390-1b0c2206bc03');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab06', 'T멤버십 최소 100원 6개월', 100, '492bc213-5c47-422f-9390-1b0c2206bc03');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab07', '1개월권', 8000, '492bc213-5c47-422f-9390-1b0c2206bc03');

INSERT INTO layout (layoutId, name)
VALUES ('492bc213-5c47-422f-9390-1b0c2206bc04', '300회 듣기');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab08', '1개월권', 4800, '492bc213-5c47-422f-9390-1b0c2206bc04');

INSERT INTO layout (layoutId, name)
VALUES ('492bc213-5c47-422f-9390-1b0c2206bc05', '모바일 무제한 듣기');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab09', '정기결제', 6900, '492bc213-5c47-422f-9390-1b0c2206bc05');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab10', 'T멤버십 최소 100원 6개월', 100, '492bc213-5c47-422f-9390-1b0c2206bc05');
INSERT INTO product (productId, name, price, layoutId)
VALUES ('108358d3-72cc-47eb-8fb2-d1635c95ab11', '1개월권', 7000, '492bc213-5c47-422f-9390-1b0c2206bc05');
