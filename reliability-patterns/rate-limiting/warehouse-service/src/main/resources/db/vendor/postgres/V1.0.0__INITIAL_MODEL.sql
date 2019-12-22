CREATE TABLE PRODUCTS
( ID                                        INTEGER                     GENERATED BY DEFAULT AS IDENTITY
, PRODUCT_ID                                VARCHAR(36)                 NOT NULL
, NAME                                      VARCHAR(100)                NOT NULL
, DESCRIPTION                               VARCHAR(4000)
, STOCK                                     INTEGER                     NOT NULL
, PRICE                                     DECIMAL(20,4)               NOT NULL
, CURRENCY                                  VARCHAR(5)                  NOT NULL
, CREATED                                   TIMESTAMP(6)                NOT NULL
, MODIFIED                                  TIMESTAMP(6)
, CONSTRAINT PRODUCTS_PK                    PRIMARY KEY (ID)
, CONSTRAINT PRODUCTS_PRODUCT_ID            UNIQUE (PRODUCT_ID)
, CONSTRAINT PRODUCTS_NAME_UC               UNIQUE (NAME)
);

CREATE TABLE RESERVATIONS
( ID                                           INTEGER                     GENERATED BY DEFAULT AS IDENTITY
, RESERVATION_ID                               VARCHAR(36)                 NOT NULL
, PRODUCT_ID                                   INTEGER
, ORDER_ID                                     VARCHAR(36)                 NOT NULL
, QUANTITY                                     INTEGER                     NOT NULL
, STATUS                                       VARCHAR(20)                 NOT NULL
, CREATED                                      TIMESTAMP(6)                NOT NULL
, MODIFIED                                     TIMESTAMP(6)
, CONSTRAINT RESERVATIONS_PK                   PRIMARY KEY (ID)
, CONSTRAINT RESERVATIONS_PRODUCT_ID_FK        FOREIGN KEY (PRODUCT_ID)    REFERENCES PRODUCTS (ID)
, CONSTRAINT INVENTORY_PRODUCT_ID_ORDER_ID_UC  UNIQUE (PRODUCT_ID, ORDER_ID)
);
