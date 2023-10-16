--  공지사항 첨부 파일
DROP TABLE IF EXISTS `NOTICE_UPLOAD_FILE` RESTRICT;

-- 상품 첨부 파일
CREATE TABLE `NOTICE_UPLOAD_FILE`
(
    `ID`             BIGINT       NOT NULL, -- 파일 ID
    `NOTICE_ID`      BIGINT       NOT NULL, -- 공지사항 ID
    `UPLOAD_FILE_NM` VARCHAR(100) NOT NULL, -- 업로드 파일명
    `STORE_FILE_NM`  VARCHAR(100) NOT NULL, -- 저장 파일명
    `CREATED_DT`     DATETIME     NOT NULL, -- 등록일
    `MODIFIED_DT`    DATETIME     NULL      -- 수정일
);

-- 상품 첨부 파일
ALTER TABLE `NOTICE_UPLOAD_FILE`
    ADD CONSTRAINT `PK_NOTICE_UPLOAD_FILE` -- 공지사항 첨부 파일 기본키
        PRIMARY KEY (
                     `id` -- 파일 ID
            );

ALTER TABLE `NOTICE_UPLOAD_FILE`
    MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `NOTICE_UPLOAD_FILE`
    AUTO_INCREMENT = 1;

#----------------------------------------------------------------------------------------------

-- 공지사항
DROP TABLE IF EXISTS `NOTICE` RESTRICT;

-- 공지사항
CREATE TABLE `NOTICE`
(
    `ID`          BIGINT         NOT NULL,           -- 공지사항 ID
    `TITLE`       VARCHAR(100)   NOT NULL,           -- 제목
    `CONTENTS`    VARCHAR(20000) NOT NULL,           -- 내용
    `HITS`        BIGINT         NOT NULL DEFAULT 0, -- 조회수
    `USER_ID`     BIGINT         NOT NULL,           -- 사용자 ID
    `CREATED_DT`  DATETIME       NOT NULL,           -- 등록일
    `MODIFIED_DT` DATETIME       NULL                -- 수정일
);

-- 공지사항
ALTER TABLE `NOTICE`
    ADD CONSTRAINT `PK_NOTICE` -- 공지사항 기본키
        PRIMARY KEY (
                     `id` -- 공지사항 ID
            );

ALTER TABLE `NOTICE`
    MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `NOTICE`
    AUTO_INCREMENT = 1;

-- 공지사항 첨부 파일
ALTER TABLE `NOTICE_UPLOAD_FILE`
    ADD CONSTRAINT `FK_NOTICE_TO_NOTICE_UPLOAD_FILE` -- 공지사항 -> 첨부 파일
        FOREIGN KEY (
                     `notice_id` -- 상품 ID
            )
            REFERENCES `NOTICE` ( -- 상품
                                 `id` -- 상품 ID
                )
            ON DELETE SET NULL ON UPDATE NO ACTION;

#----------------------------------------------------------------------------------------------

-- 고객의 소리
DROP TABLE IF EXISTS `VOICE` RESTRICT;

-- 고객의 소리
CREATE TABLE `VOICE`
(
    `ID`          BIGINT        NOT NULL, -- 고객의 소리 ID
    `USER_ID`     BIGINT        NULL,     -- 사용자 ID
    `NAME`        VARCHAR(30)   NULL,     -- 이름
    `CONTENTS`    VARCHAR(5000) NOT NULL, -- 내용
    `CREATED_DT`  DATETIME      NOT NULL, -- 등록일
    `MODIFIED_DT` DATETIME      NULL      -- 수정일
);

-- 고객의 소리
ALTER TABLE `VOICE`
    ADD CONSTRAINT `PK_VOICE` -- 고객의 소리 기본키
        PRIMARY KEY (
                     `id` -- 고객의 소리 ID
            );

ALTER TABLE `VOICE`
    MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `VOICE`
    AUTO_INCREMENT = 1;

#----------------------------------------------------------------------------------------------

DROP TABLE IF EXISTS `USERS` RESTRICT;

-- 사용자
CREATE TABLE `USERS`
(
    `ID`           BIGINT        NOT NULL,
    `USER_TYPE`    VARCHAR(10)   NOT NULL, -- 개인/기업

    -- PRIVATE
    `FIRST_NAME`   VARCHAR(30)   NOT NULL,
    `LAST_NAME`    VARCHAR(30)   NOT NULL,
    `EMAIL`        VARCHAR(50)   NOT NULL,
    `PASSWORD`     VARCHAR(5000) NOT NULL,
    `PHONE`        VARCHAR(20)   NOT NULL,

    -- COMPANY
    `COMPANY_NAME` VARCHAR(30)   NULL,
    `ADDRESS`      VARCHAR(1000) NULL,

    -- COMMON
    `ROLE`         VARCHAR(10)   NOT NULL,
    `PRD_CODE`     VARCHAR(100)  NOT NULL,
    `PRO_SN`       VARCHAR(100)  NOT NULL,
    `CREATED_DT`   DATETIME      NOT NULL,
    `MODIFIED_DT`  DATETIME      NULL
);

-- 사용자
ALTER TABLE `USERS`
    ADD CONSTRAINT `PK_USERS` -- 사용자 기본키
        PRIMARY KEY (
                     `id` -- 사용자 ID
            );

ALTER TABLE `USERS`
    MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `USERS`
    AUTO_INCREMENT = 1;


ALTER TABLE `VOICE`
    ADD CONSTRAINT `FK_USERS_TO_VOICE` -- 사용자 -> 고객의 소리
        FOREIGN KEY (
                     `user_id` -- 사용자 ID
            )
            REFERENCES `USERS` ( -- 사용자
                                `id` -- 사용자 ID
                )
            ON DELETE SET NULL ON UPDATE NO ACTION;

#----------------------------------------------------------------------------------------------

-- 문의하기
DROP TABLE IF EXISTS `CONTACT` RESTRICT;

-- 문의하기
CREATE TABLE `CONTACT`
(
    `ID`          BIGINT         NOT NULL,
    `NAME`        VARCHAR(30)    NOT NULL,
    `EMAIL`       VARCHAR(50)    NULL,     -- 이메일
    `PHONE`       VARCHAR(20)    NOT NULL, -- 전화번호
    `TITLE`       VARCHAR(100)   NOT NULL, -- 제목
    `CONTENTS`    VARCHAR(20000) NOT NULL, -- 내용
    `CREATED_DT`  DATETIME       NOT NULL, -- 등록일
    `MODIFIED_DT` DATETIME       NULL      -- 수정일
);

-- 문의하기
ALTER TABLE `CONTACT`
    ADD CONSTRAINT `PK_CONTACT` -- 문의하기 기본키
        PRIMARY KEY (
                     `id` -- 문의하기 ID
            );

ALTER TABLE `CONTACT`
    MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `CONTACT`
    AUTO_INCREMENT = 1;

#----------------------------------------------------------------------------------------------

--  뉴스레터 첨부 파일
DROP TABLE IF EXISTS `NEWS_LETTER_UPLOAD_FILE` RESTRICT;

CREATE TABLE `NEWS_LETTER_UPLOAD_FILE`
(
    `ID`             BIGINT       NOT NULL,
    `NEWS_LETTER_ID` BIGINT       NOT NULL, -- NEWS_LETTER ID
    `UPLOAD_FILE_NM` VARCHAR(100) NOT NULL,
    `STORE_FILE_NM`  VARCHAR(100) NOT NULL,
    `CREATED_DT`     DATETIME     NOT NULL,
    `MODIFIED_DT`    DATETIME     NULL
);

ALTER TABLE `NEWS_LETTER_UPLOAD_FILE`
    ADD CONSTRAINT `PK_NEWS_LETTER_UPLOAD_FILE`
        PRIMARY KEY (
                     `id`
            );

ALTER TABLE `NEWS_LETTER_UPLOAD_FILE`
    MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `NEWS_LETTER_UPLOAD_FILE`
    AUTO_INCREMENT = 1;

#----------------------------------------------------------------------------------------------

-- 뉴스레터
DROP TABLE IF EXISTS `NEWS_LETTER` RESTRICT;

CREATE TABLE `NEWS_LETTER`
(
    `ID`          BIGINT         NOT NULL,           -- 공지사항 ID
    `TITLE`       VARCHAR(100)   NOT NULL,           -- 제목
    `CONTENTS`    VARCHAR(20000) NOT NULL,           -- 내용
    `HITS`        BIGINT         NOT NULL DEFAULT 0, -- 조회수
    `USER_ID`     BIGINT         NOT NULL,           -- 사용자 ID
    `CREATED_DT`  DATETIME       NOT NULL,           -- 등록일
    `MODIFIED_DT` DATETIME       NULL                -- 수정일
);

-- 공지사항
ALTER TABLE `NEWS_LETTER`
    ADD CONSTRAINT `PK_NEWS_LETTER`
        PRIMARY KEY (
                     `id`
            );

ALTER TABLE `NEWS_LETTER`
    MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `NEWS_LETTER`
    AUTO_INCREMENT = 1;

-- 공지사항 첨부 파일
ALTER TABLE `NEWS_LETTER_UPLOAD_FILE`
    ADD CONSTRAINT `FK_NEWS_LETTER_TO_NEWS_LETTER_UPLOAD_FILE`
        FOREIGN KEY (
                     `news_letter_id`
            )
            REFERENCES `NEWS_LETTER` (
                                      `id`
                )
            ON DELETE SET NULL ON UPDATE NO ACTION;

#----------------------------------------------------------------------------------------------