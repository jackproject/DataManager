/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016-3-28-ÐÇÆÚÒ» 22:12:11                       */
/*==============================================================*/


drop table if exists t_data;

drop table if exists t_item;

drop table if exists t_othername;

drop table if exists t_validate;

/*==============================================================*/
/* Table: t_data                                                */
/*==============================================================*/
create table t_data
(
   data_id              Integer not null,
   item_id              Integer not null,
   content              varchar(1024),
   primary key (data_id, item_id)
);

/*==============================================================*/
/* Table: t_item                                                */
/*==============================================================*/
create table t_item
(
   item_id              Integer not null auto_increment,
   name                 varchar(255),
   type                 Integer,
   order_num            Integer,
   maxlength            integer,
   primary key (item_id),
   key AK_UQ_ItemName (name)
);

/*==============================================================*/
/* Table: t_othername                                           */
/*==============================================================*/
create table t_othername
(
   othername_id         Integer not null auto_increment,
   item_id              Integer,
   name                 varchar(255),
   primary key (othername_id)
);

/*==============================================================*/
/* Table: t_validate                                            */
/*==============================================================*/
create table t_validate
(
   validate_id          Integer not null auto_increment,
   item_id              Integer,
   validate_item        varchar(255),
   primary key (validate_id)
);

alter table t_data add constraint FK_Reference_2 foreign key (item_id)
      references t_item (item_id) on delete restrict on update restrict;

alter table t_othername add constraint FK_Reference_1 foreign key (item_id)
      references t_item (item_id) on delete restrict on update restrict;

alter table t_validate add constraint FK_Reference_4 foreign key (item_id)
      references t_item (item_id) on delete restrict on update restrict;

