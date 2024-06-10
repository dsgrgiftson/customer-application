set
mode Oracle;

create sequence CUSTOMER_DETAILS_SEQUENCE start with 1 increment by 1;

create table CUSTOMER_DETAILS
(
    CUSTOMER_ID     int primary key,
    USERNAME        varchar(255),
    PASSWORD        varchar(255),
    FULL_NAME       varchar(255),
    ADDRESS         varchar(255),
    DATE_OF_BIRTH   date,
    DOCUMENT_NUMBER varchar(255),
    IBAN_NUMBER     varchar(255)
);

ALTER TABLE CUSTOMER_DETAILS
    add CONSTRAINT CUSTOMER_DETAILS_UNIQUE_USERNAME UNIQUE (USERNAME);

ALTER TABLE CUSTOMER_DETAILS
    add CONSTRAINT CUSTOMER_DETAILS_UNIQUE_IBAN UNIQUE (IBAN_NUMBER);