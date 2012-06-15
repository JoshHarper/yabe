# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blog_user (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  full_name                 varchar(255),
  is_admin                  boolean,
  constraint pk_blog_user primary key (id))
;

create sequence blog_user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists blog_user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists blog_user_seq;

