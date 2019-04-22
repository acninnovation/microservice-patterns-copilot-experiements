CREATE TABLE ORDERS
( ID                                        NUMBER(19)                GENERATED BY DEFAULT AS IDENTITY
, ORDER_ID                                  VARCHAR2(36 CHAR)         NOT NULL
, CUSTOMER_ID                               VARCHAR2(36 CHAR)         NOT NULL
, NAME                                      VARCHAR2(50 CHAR)         NOT NULL
, DESCRIPTION                               VARCHAR2(255 CHAR)
, STATUS                                    VARCHAR2(20 CHAR)         NOT NULL
, CREATED                                   TIMESTAMP(6)              NOT NULL
, MODIFIED                                  TIMESTAMP(6)
, CONSTRAINT ORDERS_ORDER_ID                UNIQUE (ORDER_ID)
, CONSTRAINT ORDERS_PK                      PRIMARY KEY (ID)
);

CREATE TABLE ITEMS
( ID                                        NUMBER(19)                GENERATED BY DEFAULT AS IDENTITY
, ORDER_ID                                  NUMBER(19)                NOT NULL
, PRODUCT_ID                                VARCHAR2(36 CHAR)         NOT NULL
, QUANTITY                                  NUMBER (19)               NOT NULL
, STATUS                                    VARCHAR2(20 CHAR)         NOT NULL
, CREATED                                   TIMESTAMP(6)              NOT NULL
, MODIFIED                                  TIMESTAMP(6)
, CONSTRAINT ITEMS_PK                       PRIMARY KEY (ID)
, CONSTRAINT ITEMS_ORDER_ID_PRODUCT_ID_UC   UNIQUE (ORDER_ID, PRODUCT_ID)
, CONSTRAINT ITEMS_ORDER_ID_FK              FOREIGN KEY (ORDER_ID)    REFERENCES ORDERS (ID)
);
