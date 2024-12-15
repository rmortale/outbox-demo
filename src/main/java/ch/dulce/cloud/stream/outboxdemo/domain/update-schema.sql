CREATE SEQUENCE IF NOT EXISTS uploaded_files_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE uploaded_files
(
    id       BIGINT       NOT NULL,
    filename VARCHAR(255) NOT NULL,
    CONSTRAINT pk_uploaded_files PRIMARY KEY (id)
);