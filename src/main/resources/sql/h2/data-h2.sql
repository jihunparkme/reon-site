insert into notice (TITLE, CONTENTS, HITS, USER_ID, CREATED_DT)
values ('제목1', '내용1', 0L, 2L, PARSEDATETIME('2023-10-01 12:00:00','yyyy-MM-dd hh:mm:ss'));





insert into VOICE (USER_ID, NAME, CONTENTS, CREATED_DT)
values (2L, 'admin', 'contents', PARSEDATETIME('2023-10-01 12:00:00','yyyy-MM-dd hh:mm:ss'));




insert into users (USER_TYPE, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PHONE, COMPANY_NAME, ADDRESS, ROLE, PRD_CODE, PRO_SN, CREATED_DT)
values ('PRIVATE', 'admin', 'park', 'admin@gmail.com', 'admin', '010-1234-1234', null, null, 'ADMIN', 'admin', 'admin', PARSEDATETIME('2023-10-01 12:00:00','yyyy-MM-dd hh:mm:ss'));

insert into users (USER_TYPE, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PHONE, COMPANY_NAME, ADDRESS, ROLE, PRD_CODE, PRO_SN, CREATED_DT)
values ('PRIVATE', 'aaron', 'park', 'aaron@gmail.com', 'aaron', '010-1234-1234', null, null, 'USER', 'ASGFDSAGASGDAS', 'ASDFSAF4352qADFASF345251', PARSEDATETIME('2023-10-01 12:00:00','yyyy-MM-dd hh:mm:ss'));





insert into NEWS_LETTER (TITLE, CONTENTS, HITS, USER_ID, CREATED_DT)
values ('제목1', '내용1', 0L, 2L, PARSEDATETIME('2023-10-01 12:00:00','yyyy-MM-dd hh:mm:ss'));
