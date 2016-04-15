/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016-4-15-ÐÇÆÚÎå 17:42:13                       */
/*==============================================================*/


drop table if exists t_data;

drop table if exists t_item;

drop table if exists t_othername;

drop table if exists t_pick;

drop table if exists t_pick_item;

drop table if exists t_user;

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
/* Table: t_pick                                                */
/*==============================================================*/
create table t_pick
(
   pick_id              integer not null auto_increment,
   pick_name            varchar(255),
   primary key (pick_id)
);

/*==============================================================*/
/* Table: t_pick_item                                           */
/*==============================================================*/
create table t_pick_item
(
   pick_item_id         integer not null auto_increment,
   pick_id              integer,
   item_id              integer,
   choice               integer,
   pick_value           varchar(255),
   primary key (pick_item_id)
);

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   user_id              integer not null auto_increment,
   username             varchar(255),
   password             varchar(255),
   primary key (user_id)
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

alter table t_pick_item add constraint FK_Reference_5 foreign key (item_id)
      references t_item (item_id) on delete restrict on update restrict;

alter table t_pick_item add constraint FK_Reference_6 foreign key (pick_id)
      references t_pick (pick_id) on delete restrict on update restrict;

alter table t_validate add constraint FK_Reference_4 foreign key (item_id)
      references t_item (item_id) on delete restrict on update restrict;

