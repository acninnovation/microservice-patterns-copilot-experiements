CREATE
USER customers WITH PASSWORD 'abcd1234';
CREATE
USER ordering WITH PASSWORD 'abcd1234';
CREATE
USER warehouse WITH PASSWORD 'abcd1234';
CREATE
USER shipping WITH PASSWORD 'abcd1234';
CREATE
USER billing WITH PASSWORD 'abcd1234';

CREATE
DATABASE customers WITH OWNER customers;
CREATE
DATABASE ordering WITH OWNER ordering;
CREATE
DATABASE warehouse WITH OWNER warehouse;
CREATE
DATABASE shipping WITH OWNER shipping;
CREATE
DATABASE billing WITH OWNER billing;
