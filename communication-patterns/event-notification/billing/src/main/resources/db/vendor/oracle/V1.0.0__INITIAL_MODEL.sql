CREATE TABLE INVOICES
( ID                                        NUMBER(19)                GENERATED BY DEFAULT AS IDENTITY
, INVOICE_ID                                VARCHAR2(36 CHAR)         NOT NULL
, CUSTOMER_ID                               VARCHAR2(36 CHAR)         NOT NULL
, ORDER_ID                                  VARCHAR2(36 CHAR)         NOT NULL
, STATUS                                    VARCHAR2(20 CHAR)         NOT NULL
, CREATED                                   TIMESTAMP(6)              NOT NULL
, MODIFIED                                  TIMESTAMP(6)
, CONSTRAINT INVOICES_PK                    PRIMARY KEY (ID)
);