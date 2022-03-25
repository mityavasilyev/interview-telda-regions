drop table if exists REGION;

create table REGION
(
    ID                BIGINT SERIAL not null,
    REGION_NAME       VARCHAR       not null,
    REGION_SHORT_NAME VARCHAR       not null,
    constraint REGIONS_PK
        primary key (ID)
);

-- insert into REGION (REGION_NAME, REGION_SHORT_NAME) VALUES ( 'Lenoblast', 'LO' );
-- insert into REGION (REGION_NAME, REGION_SHORT_NAME) VALUES ( 'Moscow', 'MSC' );
-- insert into REGION (REGION_NAME, REGION_SHORT_NAME) VALUES ( 'St.Petersburg', 'SPB' );
-- insert into REGION (REGION_NAME, REGION_SHORT_NAME) VALUES ( 'Arhangelskaya Oblast', 'AO' );
