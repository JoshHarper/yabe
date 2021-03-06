# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blog_comments (
  id                        bigint not null,
  author                    varchar(255),
  posted_at                 timestamp,
  content                   clob,
  post_id                   bigint,
  constraint pk_blog_comments primary key (id))
;

create table blog_post (
  id                        bigint not null,
  title                     varchar(255),
  posted_at                 timestamp,
  content                   clob,
  author_id                 bigint,
  constraint pk_blog_post primary key (id))
;

create table blog_user (
  id                        bigint not null,
  email                     varchar(255),
  password                  varchar(255),
  full_name                 varchar(255),
  is_admin                  boolean,
  constraint pk_blog_user primary key (id))
;

create sequence blog_comments_seq;

create sequence blog_post_seq;

create sequence blog_user_seq;

alter table blog_comments add constraint fk_blog_comments_post_1 foreign key (post_id) references blog_post (id) on delete restrict on update restrict;
create index ix_blog_comments_post_1 on blog_comments (post_id);
alter table blog_post add constraint fk_blog_post_author_2 foreign key (author_id) references blog_user (id) on delete restrict on update restrict;
create index ix_blog_post_author_2 on blog_post (author_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists blog_comments;

drop table if exists blog_post;

drop table if exists blog_user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists blog_comments_seq;

drop sequence if exists blog_post_seq;

drop sequence if exists blog_user_seq;

