drop table if exists TB_PMS_MENU;

drop table if exists TB_PMS_MENU_ROLE;

drop table if exists TB_PMS_OPERATOR;

drop table if exists TB_PMS_OPERATOR_LOG;

drop table if exists TB_PMS_PERMISSION;

drop table if exists TB_PMS_ROLE;

drop table if exists TB_PMS_ROLE_OPERATOR;

drop table if exists TB_PMS_ROLE_PERMISSION;

create table TB_PMS_MENU
(
   id                   bigint not null auto_increment,
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   is_leaf              varchar(20),
   level                smallint,
   parent_id            bigint not null,
   target_name          varchar(100),
   number               varchar(20),
   name                 varchar(100),
   url                  varchar(100),
   primary key (id)
)auto_increment = 1000;

alter table TB_PMS_MENU comment '菜单表';


alter table TB_PMS_MENU comment '菜单表';

create table TB_PMS_MENU_ROLE
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint,
   creater              varchar(50) comment '创建人',
   create_time          datetime comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20),
   remark               varchar(300),
   role_id              bigint not null,
   menu_id              bigint not null,
   primary key (id),
   key AK_KEY_2 (role_id, menu_id)
) auto_increment = 1000;

alter table TB_PMS_MENU_ROLE comment '权限与角色关联表';

create table TB_PMS_OPERATOR
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   real_name            varchar(50) not null,
   mobile_no            varchar(15) not null,
   login_name           varchar(50) not null,
   login_pwd            varchar(256) not null,
   type                 varchar(20) not null,
   salt                 varchar(50) not null,
   primary key (id),
   key AK_KEY_2 (login_name)
) auto_increment = 1000;

alter table TB_PMS_OPERATOR comment '操作员表';

create table TB_PMS_OPERATOR_LOG
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   operator_id          bigint not null,
   operator_name        varchar(50) not null,
   operate_type         varchar(50) not null comment '操作类型（1:增加，2:修改，3:删除，4:查询）',
   ip                   varchar(100) not null,
   content              varchar(3000),
   primary key (id)
) auto_increment = 1000;

alter table TB_PMS_OPERATOR_LOG comment '权限_操作员操作日志表';

create table TB_PMS_PERMISSION
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   permission_name      varchar(100) not null,
   permission           varchar(100) not null,
   primary key (id),
   key AK_KEY_2 (permission),
   key AK_KEY_3 (permission_name)
) auto_increment = 1000;

alter table TB_PMS_PERMISSION comment '权限表';

create table TB_PMS_ROLE
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint,
   creater              varchar(50) comment '创建人',
   create_time          datetime comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20),
   remark               varchar(300),
   role_code            varchar(20) not null comment '角色类型（1:超级管理员角色，0:普通操作员角色）',
   role_name            varchar(100) not null,
   primary key (id),
   key AK_KEY_2 (role_name)
) auto_increment = 1000;

alter table TB_PMS_ROLE comment '角色表';

create table TB_PMS_ROLE_OPERATOR
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint not null,
   creater              varchar(50) not null comment '创建人',
   create_time          datetime not null comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20) not null,
   remark               varchar(300),
   role_id              bigint not null,
   operator_id          bigint not null,
   primary key (id),
   key AK_KEY_2 (role_id, operator_id)
) auto_increment = 1000;

alter table TB_PMS_ROLE_OPERATOR comment '操作员与角色关联表';

create table TB_PMS_ROLE_PERMISSION
(
   id                   bigint not null auto_increment comment '主键',
   version              bigint,
   creater              varchar(50) comment '创建人',
   create_time          datetime comment '创建时间',
   editor               varchar(50) comment '修改人',
   edit_time            datetime comment '修改时间',
   status               varchar(20),
   remark               varchar(300),
   role_id              bigint not null,
   permission_id        bigint not null,
   primary key (id),
   key AK_KEY_2 (role_id, permission_id)
) auto_increment = 1000;

alter table TB_PMS_ROLE_PERMISSION comment '权限与角色关联表';





-- ------------------------------step 1  菜单-------------------------------------------------
-- 菜单初始化数据
--  -- 菜单的初始化数据
INSERT INTO TB_PMS_MENU (id,version,status,creater,create_time, editor, edit_time, remark, NAME, URL, NUMBER, IS_LEAF, LEVEL, PARENT_ID, TARGET_NAME) VALUES 
(1,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '权限管理', '##', '001', 'NO', 1, 0, '#'),
(2,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '菜单管理', 'TB_PMS/menu/list', '00101', 'YES', 2, 1, 'cdgl'),
(3,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '权限管理', 'TB_PMS/permission/list', '00102', 'YES',2, 1, 'qxgl'),
(4,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '角色管理', 'TB_PMS/role/list', '00103', 'YES',2, 1, 'jsgl'),
(5,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '操作员管理', 'TB_PMS/operator/list', '00104', 'YES',2, 1, 'czygl'),

(10,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '账户管理', '##', '002', 'NO', 1, 0, '#'),
(12,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '账户信息', 'account/list', '00201', 'YES',2, 10, 'zhxx'),
(13,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '账户历史信息', 'account/historyList', '00202', 'YES',2, 10, 'zhlsxx'),


(20,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '用户管理', '##', '003', 'NO', 1, 0, '#'),
(22,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '用户信息', 'user/info/list', '00301', 'YES',2, 20, 'yhxx'),

(30,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '支付管理', '##', '004', 'NO', 1, 0, '#'),
(32,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '支付产品信息', 'pay/product/list', '00401', 'YES',2, 30, 'zfcpgl'),
(33,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '用户支付配置', 'pay/config/list', '00402', 'YES',2, 30, 'yhzfpz'),

(40,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '交易管理', '##', '005', 'NO', 1, 0, '#'),
(42,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '支付订单管理', 'trade/listPaymentOrder', '00501', 'YES',2, 40, 'zfddgl'),
(43,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '支付记录管理', 'trade/listPaymentRecord', '00502', 'YES',2, 40, 'zfjjgl'),

(50,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '结算管理', '##', '006', 'NO', 1, 0, '#'),
(52,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '结算记录管理', 'sett/list', '00601', 'YES',2, 50, 'jsjlgl'),

(60,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '对账管理', '##', '007', 'NO', 1, 0, '#'),
(62,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '对账差错列表', 'reconciliation/list/mistake', '00701', 'YES',2, 60, 'dzcclb'),
(63,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '对账批次列表', 'reconciliation/list/checkbatch', '00702', 'YES',2, 60, 'dzpclb'),
(64,0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '', '对账缓冲池列表', 'reconciliation/list/scratchPool', '00703', 'YES',2, 60, 'dzhcclb');

-- ------------------------------step 2：权限功能点-------------------------------------------------
-- 权限功能点的初始化数据


insert into TB_PMS_PERMISSION (id,version,status,creater,create_time, editor, edit_time, remark, permission_name, permission) values 
 (1, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-菜单-查看','权限管理-菜单-查看','TB_PMS:menu:view'),
 (2, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-菜单-添加','权限管理-菜单-添加','TB_PMS:menu:add'),
 (3, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-菜单-查看','权限管理-菜单-修改','TB_PMS:menu:edit'),
 (4, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-菜单-删除','权限管理-菜单-删除','TB_PMS:menu:delete'),

 (11, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-权限-查看','权限管理-权限-查看','TB_PMS:permission:view'),
 (12, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-权限-添加','权限管理-权限-添加','TB_PMS:permission:add'),
 (13, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-权限-修改','权限管理-权限-修改','TB_PMS:permission:edit'),
 (14, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-权限-删除','权限管理-权限-删除','TB_PMS:permission:delete'),

 (21, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-查看','权限管理-角色-查看','TB_PMS:role:view'),
 (22, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-添加','权限管理-角色-添加','TB_PMS:role:add'),
 (23, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-修改','权限管理-角色-修改','TB_PMS:role:edit'),
 (24, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-删除','权限管理-角色-删除','TB_PMS:role:delete'),
 (25, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-角色-分配权限','权限管理-角色-分配权限','TB_PMS:role:assignpermission'),

 (31, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-查看','权限管理-操作员-查看','TB_PMS:operator:view'),
 (32, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-添加','权限管理-操作员-添加','TB_PMS:operator:add'),
 (33, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-查看','权限管理-操作员-修改','TB_PMS:operator:edit'),
 (34, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-冻结与解冻','权限管理-操作员-冻结与解冻','TB_PMS:operator:changestatus'),
 (35, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','权限管理-操作员-重置密码','权限管理-操作员-重置密码','TB_PMS:operator:resetpwd'),


 (51, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户-查看','账户管理-账户-查看','account:accountInfo:view'),
 (52, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户-添加','账户管理-账户-添加','account:accountInfo:add'),
 (53, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户-查看','账户管理-账户-修改','account:accountInfo:edit'),
 (54, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户-删除','账户管理-账户-删除','account:accountInfo:delete'),

 (61, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','账户管理-账户历史-查看','账户管理-账户历史-查看','account:accountHistory:view'),

 (71, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','用户管理-用户信息-查看','用户管理-用户信息-查看','user:userInfo:view'),
 (72, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','用户管理-用户信息-添加','用户管理-用户信息-添加','user:userInfo:add'),
 (73, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','用户管理-用户信息-查看','用户管理-用户信息-修改','user:userInfo:edit'),
 (74, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','用户管理-用户信息-删除','用户管理-用户信息-删除','user:userInfo:delete'),

 (81, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付产品-查看','支付管理-支付产品-查看','pay:product:view'),
 (82, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付产品-添加','支付管理-支付产品-添加','pay:product:add'),
 (83, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付产品-查看','支付管理-支付产品-修改','pay:product:edit'),
 (84, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付产品-删除','支付管理-支付产品-删除','pay:product:delete'),

 (85, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付方式-查看','支付管理-支付方式-查看','pay:way:view'),
 (86, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付方式-添加','支付管理-支付方式-添加','pay:way:add'),
 (87, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付方式-查看','支付管理-支付方式-修改','pay:way:edit'),
 (88, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付方式-删除','支付管理-支付方式-删除','pay:way:delete'),

 (91, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付配置-查看','支付管理-支付配置-查看','pay:config:view'),
 (92, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付配置-添加','支付管理-支付配置-添加','pay:config:add'),
 (93, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付配置-查看','支付管理-支付配置-修改','pay:config:edit'),
 (94, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','支付管理-支付配置-删除','支付管理-支付配置-删除','pay:config:delete'),

 (101, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-订单-查看','交易管理-订单-查看','trade:order:view'),
 (102, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-订单-添加','交易管理-订单-添加','trade:order:add'),
 (103, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-订单-查看','交易管理-订单-修改','trade:order:edit'),
 (104, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-订单-删除','交易管理-订单-删除','trade:order:delete'),

 (111, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-记录-查看','交易管理-记录-查看','trade:record:view'),
 (112, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-记录-添加','交易管理-记录-添加','trade:record:add'),
 (113, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-记录-查看','交易管理-记录-修改','trade:record:edit'),
 (114, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','交易管理-记录-删除','交易管理-记录-删除','trade:record:delete'),

 (121, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','结算管理-记录-查看','结算管理-记录-查看','sett:record:view'),
 (122, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','结算管理-记录-添加','结算管理-记录-添加','sett:record:add'),
 (123, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','结算管理-记录-查看','结算管理-记录-修改','sett:record:edit'),
 (124, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','结算管理-记录-删除','结算管理-记录-删除','sett:record:delete'),

 (131, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-差错-查看','对账管理-差错-查看','recon:mistake:view'),
 (132, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-差错-添加','对账管理-差错-添加','recon:mistake:add'),
 (133, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-差错-查看','对账管理-差错-修改','recon:mistake:edit'),
 (134, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-差错-删除','对账管理-差错-删除','recon:mistake:delete'),

 (141, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-批次-查看','对账管理-批次-查看','recon:batch:view'),
 (142, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-批次-添加','对账管理-批次-添加','recon:batch:add'),
 (143, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-批次-查看','对账管理-批次-修改','recon:batch:edit'),
 (144, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-批次-删除','对账管理-批次-删除','recon:batch:delete'),

 (151, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-缓冲池-查看','对账管理-缓冲池-查看','recon:scratchPool:view'),
 (152, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-缓冲池-添加','对账管理-缓冲池-添加','recon:scratchPool:add'),
 (153, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-缓冲池-查看','对账管理-缓冲池-修改','recon:scratchPool:edit'),
 (154, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','对账管理-缓冲池-删除','对账管理-缓冲池-删除','recon:scratchPool:delete');

-- -----------------------------------step3：操作员--------------------------------------------
-- -- 操作员的初始化数据
--  admin 超级管理员
insert into TB_PMS_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark, login_name, login_pwd,real_name,mobile_no,type,salt) 
values (1, 0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'admin','2016-06-03 11:07:43', '超级管理员', 'admin', 'd3c59d25033dbf980d29554025c23a75','超级管理员', '18620936193', 'ADMIN','8d78869f470951332959580424d4bf4f');

--  guest  游客
insert into TB_PMS_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark, login_name, login_pwd,real_name,mobile_no,type,salt) 
values (2, 0, 'ACTIVE','roncoo','2016-06-03 11:07:43', 'guest','2016-06-03 11:07:43', '游客', 'guest', '3f0dbf580ee39ec03b632cb33935a363','游客', '18926215592', 'USER','183d9f2f0f2ce760e98427a5603d1c73');

-- ------------------------------------step4：角色-------------------------------------------
-- -- 角色的初始化数据
insert into TB_PMS_ROLE (id,version,status,creater,create_time, editor, edit_time, remark, role_code, role_name) 
values (1, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'admin', '2016-06-03 11:07:43','超级管理员角色','admin', '超级管理员角色');

insert into TB_PMS_ROLE (id,version,status,creater,create_time, editor, edit_time, remark, role_code, role_name) 
values (2, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'guest', '2016-06-03 11:07:43','游客角色','guest', '游客角色');

-- ------------------------------------step5：操作员和角色关联-------------------------------------------
-- -- 操作员与角色关联的初始化数据

--  admin  关联admin 和test两个角色
insert into TB_PMS_ROLE_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark,role_id,operator_id) values (1, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',1,1);
insert into TB_PMS_ROLE_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark,role_id,operator_id) values (2, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,1);

-- guest  关联游客角色  （游客角色只有查看交易记录的信息）
insert into TB_PMS_ROLE_OPERATOR (id,version,status,creater,create_time, editor, edit_time, remark,role_id,operator_id) values (3, 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,2);

-- -------------------------------------step6：角色和权限关联------------------------------------------
-- -- 角色与用户功能点关联的初始化数据

-- admin（拥有所有的权限点）
insert into TB_PMS_ROLE_PERMISSION  (role_id, permission_id) select 1,id from TB_PMS_PERMISSION;


-- guest （只有所有的查看权限）
insert into TB_PMS_ROLE_PERMISSION (version,status,creater,create_time, editor, edit_time, remark,role_id,permission_id) 
values 
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,1),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,11),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,21),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,31),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,41),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,51),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,61),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,71),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,81),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,85),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,91),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,101),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,111),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,121),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,131),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,141),
 ( 0,'ACTIVE', 'roncoo','2016-06-03 11:07:43', 'test', '2016-06-03 11:07:43','',2,151);

-- -------------------------------------step7：角色和菜单关联------------------------------------------
--  角色与信息关联初始化数据
-- admin

insert into TB_PMS_MENU_ROLE(role_id, menu_id) select 1,id from TB_PMS_MENU;

-- guest  所有的菜单（只有查看权限）
insert into TB_PMS_MENU_ROLE (role_id, menu_id) select 2,id from TB_PMS_MENU;