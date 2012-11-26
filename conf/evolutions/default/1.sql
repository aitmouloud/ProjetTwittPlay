# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table message (
  id                        bigint auto_increment not null,
  label                     varchar(255),
  user_id                   bigint,
  constraint pk_message primary key (id))
;

create table user_account (
  id                        bigint auto_increment not null,
  mail                      varchar(255),
  nickname                  varchar(255),
  password                  varchar(255),
  constraint uq_user_account_nickname unique (nickname),
  constraint pk_user_account primary key (id))
;

alter table message add constraint fk_message_user_1 foreign key (user_id) references user_account (id) on delete restrict on update restrict;
create index ix_message_user_1 on message (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists message;

drop table if exists user_account;

SET REFERENTIAL_INTEGRITY TRUE;

