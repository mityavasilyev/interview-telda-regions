drop table if exists REGION;

create table REGION
(
    ID                BIGINT SERIAL not null,
    REGION_NAME       VARCHAR       not null,
    REGION_SHORT_NAME VARCHAR       not null,
    REGION_CODE       BIGINT unique not null,
    constraint REGIONS_PK
        primary key (ID)
);
