create table USER
(
    ID           INT auto_increment PRIMARY KEY NOT NULL,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    AVATAR_URL   VARCHAR(100),
    USERNAME     VARCHAR(36),
    PASSWORD     VARCHAR(36),
);