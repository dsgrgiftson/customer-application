set
mode Oracle;

insert into CUSTOMER_DETAILS
(CUSTOMER_ID,
 USERNAME,
 PASSWORD,
 FULL_NAME,
 ADDRESS,
 DATE_OF_BIRTH,
 DOCUMENT_NUMBER,
 IBAN_NUMBER)
values (
    CUSTOMER_DETAILS_SEQUENCE.nextval,
    'davidgi',
    'dummypassword',
    'Giftson David',
    'House 11, 2514 JH, Den Haag',
    '1993-01-01',
    'DOC1234567',
    'NL01ABNA1234122244');

commit;
