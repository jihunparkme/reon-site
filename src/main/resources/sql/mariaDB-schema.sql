create table roasting_record
(
    id          bigint        NOT NULL AUTO_INCREMENT,
    roaster_sn  varchar(100)  not null,
    member_id   bigint        not null,
    title       TEXT  not null,
    fan         TEXT not null,
    heater      TEXT not null,
    temp1       TEXT not null,
    temp2       TEXT not null,
    temp3       TEXT not null,
    temp4       TEXT not null,
    ror         TEXT not null,
    created_dt  timestamp(6),
    modified_dt timestamp(6),
    PRIMARY KEY (id)
);

-- show columns from roasting_record;
-- alter table roasting_record MODIFY COLUMN fan TEXT;

insert into roasting_record (TITLE, FAN, HEATER, TEMP1, TEMP2, TEMP3, TEMP4, ROR, ROASTER_SN, MEMBER_ID, CREATED_DT)
values ('test roasting222',
        '[80,80,80,80,80,85,85,85,90,90,95,95,100,100,110,120,130,140,150,150,150,150,150,160,160,160,170,170,170,180,180,180,190,190,190,200,200,210,210,220,220,200,200,190,190,180,170,160,150,140,130,120,120,110,110,100,100,100,90,90,80,80,70,60,50,50,40,40,50,50,60,60,70,80,90,100,110,80,50,40,30]',
        '[10,15,20,25,30,40,40,40,40,40,50,60,70,75,80,90,95,95,95,95,95,100,100,100,100,100,100,100,100,100,100,110,120,130,140,150,160,170,180,185,185,185,185,190,195,195,195,195,200,200,200,200,200,200,200,200,190,180,170,160,150,140,130,120,100,100,100,100,100,100,100,100,90,90,70,60,50,40,30,20,10]',
        '[0,5,10,12,14,16,18,19,20,23,25,27,28,29,30,34,38,39,47,48,58,59,67,69,72,73,84,86,93,95,98,100,120,130,140,150,160,170,180,190,200,210,215,210,200,200,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,190,200,180,170,160,150,140,130,120,110,100,90,80,70,60,50,40,30,20,10]',
        '[0,4,9,11,14,17,17,17,18,20,20,25,25,25,30,30,35,39,45,45,50,55,62,65,77,79,80,83,91,91,91,100,120,130,140,150,160,170,180,190,200,210,215,210,200,200,200,190,200,190,200,190,195,187,195,187,195,187,195,187,195,187,195,187,175,160,150,140,130,120,105,105,93,84,72,66,54,43,32,21,7]',
        '[0,3,8,10,13,15,15,15,16,19,19,23,23,23,27,27,31,37,43,43,48,51,61,61,74,73,81,82,94,94,94,104,125,135,145,157,167,177,187,197,207,217,219,211,202,203,204,188,193.188,193,188,193,188,193,188,193,188,193,188,193,188,193,188,193,173,151,151,144,133,122,101,101,91,81,71,61,51,41,31,20,2]',
        '[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,49,48,47,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,30,29,28,27,26,25,24,23,22,20,21,20,19]',
        '[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,49,48,47,45,44,43,42,41,40,39,38,37,36,35,34,33,32,31,30,29,28,27,26,25,24,23,22,20,21,20,19]',
        'ASFDASGAAS3512ASDGA', 2, STR_TO_DATE('2023-10-01 12:00:00', '%Y-%m-%d %H:%i:%S'));

CREATE TABLE member
(
    member_id   bigint(20)  NOT NULL AUTO_INCREMENT,
    type        varchar(10),
    first_name   varchar(30)  not null,
    last_name    varchar(30)  not null,
    email       varchar(50)  not null,
    password    varchar(100) not null,
    phone       varchar(20),
    company_name varchar(30),
    address     varchar(100),
    prd_code     varchar(100),
    roaster_sn   varchar(100),
    o_auth_client varchar(10),
    picture TEXT,
    activated   tinyint(1),
    created_dt  timestamp(6),
    modified_dt timestamp(6),
    PRIMARY KEY (member_id),
    constraint email_oAuthClient_unique unique (email, o_auth_client)
);

create table authority (
   authority_name varchar(50) not null,
   primary key (authority_name)
);

create table member_authority (
  member_id bigint not null,
  authority_name varchar(50) not null,
  primary key (member_id, authority_name)
);


insert into member (TYPE, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PHONE, COMPANY_NAME, ADDRESS, PRD_CODE, ROASTER_SN, O_AUTH_CLIENT, PICTURE, ACTIVATED, CREATED_DT)
values ('PRIVATE', 'admin', 'park', 'admin@gmail.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', '010-1234-1234', null, null, 'admin', 'admin', 'EMPTY', null, true, DATE_FORMAT('2023-10-01', '%y-%m-%d'));

insert into member (TYPE, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PHONE, COMPANY_NAME, ADDRESS, PRD_CODE, ROASTER_SN, O_AUTH_CLIENT, PICTURE, ACTIVATED, CREATED_DT)
values ('PRIVATE', 'user', 'park', 'user@gmail.com', '$2a$10$Q9AWFqYicGA9m8OlmwDS8O6intHWsCf7e14DbAxAUIB6Pba/B/50y', '010-1234-1234', null, null, 'ASGFDSAGASGDAS', 'ASDFSAF4352qADFASF345251', 'EMPTY', null, true, DATE_FORMAT('2023-10-01 12:00:00','%y-%m-%d %H:%i:%s'));

insert into authority (authority_name) values ('ROLE_GUEST');
insert into authority (authority_name) values ('ROLE_USER');
insert into authority (authority_name) values ('ROLE_ADMIN');

insert into member_authority (member_id, authority_name) values (1, 'ROLE_USER');
insert into member_authority (member_id, authority_name) values (1, 'ROLE_ADMIN');
insert into member_authority (member_id, authority_name) values (2, 'ROLE_USER');