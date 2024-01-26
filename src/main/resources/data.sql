-- create table member
-- (
--     member_name          varchar not null,
--     member_email         varchar not null,
--     member_password      varchar not null,
--     member_profile_image varchar not null,
--     member_greetings     varchar not null,
--     created_at           timestamp not null,
--     created_by           varchar not null,
--     updated_at           timestamp not null,
--     updated_by           varchar null
-- );
--
insert into member
(member_name,
 member_email,
 member_password,
 member_profile_image,
 member_greetings,
 created_at,
 created_by,
 updated_at,
 updated_by)
values ('이승재','deepsj1012@naver.com','1234','image','안녕',now(),'이승재',now(),'이승재');