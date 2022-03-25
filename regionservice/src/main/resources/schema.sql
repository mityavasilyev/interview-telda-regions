drop table if exists REGION;

create table REGION
(
    ID                BIGINT  not null,
    REGION_NAME       VARCHAR not null,
    REGION_SHORT_NAME VARCHAR not null,
    constraint REGIONS_PK
        primary key (ID)
);

