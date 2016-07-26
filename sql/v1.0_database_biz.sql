drop table if exists tb_account;

drop table if exists tb_account_history;

drop table if exists tb_pay_product;

drop table if exists tb_pay_way;

drop table if exists tb_sett_daily_collect;

drop table if exists tb_sett_record;

drop table if exists tb_sett_record_annex;

drop table if exists tb_user_bank_account;

drop table if exists tb_user_info;

drop table if exists tb_user_pay_config;

drop table if exists tb_user_pay_info;

drop table if exists tb_account_check_batch;

drop table if exists tb_account_check_mistake;

drop table if exists tb_account_check_mistake_scratch_pool;

drop table if exists tb_notify_record;

drop table if exists tb_notify_record_log;

drop table if exists tb_refund_record;

drop table if exists tb_trade_payment_order;

drop table if exists tb_trade_payment_record;

drop table if exists tb_seq_table;

/*==============================================================*/
/* table: tb_account                                            */
/*==============================================================*/
create table tb_account
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   account_no           varchar(50) not null,
   balance              decimal(20,6) not null,
   unbalance            decimal(20,6) not null,
   security_money       decimal(20,6) not null,
   status               varchar(36) not null,
   total_income         decimal(20,6) not null,
   total_expend         decimal(20,6) not null,
   today_income         decimal(20,6) not null,
   today_expend         decimal(20,6) not null,
   account_type         varchar(50) not null,
   sett_amount          decimal(20,6) not null,
   user_no              varchar(50),
   primary key (id)
);

alter table tb_account comment '资金账户表';

/*==============================================================*/
/* table: tb_account_history                                    */
/*==============================================================*/
create table tb_account_history
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   account_no           varchar(50) not null,
   amount               decimal(20,6) not null,
   balance              decimal(20,6) not null,
   fund_direction       varchar(36) not null,
   is_allow_sett        varchar(36) not null,
   is_complete_sett     varchar(36) not null,
   request_no           varchar(36) not null,
   bank_trx_no          varchar(30),
   trx_type             varchar(36) not null,
   risk_day             int,
   user_no              varchar(50),
   primary key (id)
);

alter table tb_account_history comment '资金账户流水表';

/*==============================================================*/
/* table: tb_pay_product                                        */
/*==============================================================*/
create table tb_pay_product
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   status               varchar(36) not null,
   product_code         varchar(50) not null comment '支付产品编号',
   product_name         varchar(200) not null comment '支付产品名称',
   audit_status         varchar(45),
   primary key (id)
);

alter table tb_pay_product comment '支付产品表';

/*==============================================================*/
/* table: tb_pay_way                                            */
/*==============================================================*/
create table tb_pay_way
(
   id                   varchar(50) not null comment 'id',
   version              bigint not null default 0 comment 'version',
   create_time          datetime not null comment '创建时间',
   edit_time            datetime comment '修改时间',
   pay_way_code         varchar(50) not null comment '支付方式编号',
   pay_way_name         varchar(100) not null comment '支付方式名称',
   pay_type_code        varchar(50) not null comment '支付类型编号',
   pay_type_name        varchar(100) not null comment '支付类型名称',
   pay_product_code     varchar(50) comment '支付产品编号',
   status               varchar(36) not null comment '状态(100:正常状态,101非正常状态)',
   sorts                int default 1000 comment '排序(倒序排序,默认值1000)',
   pay_rate             double not null comment '商户支付费率',
   primary key (id)
);

alter table tb_pay_way comment '支付方式';

/*==============================================================*/
/* table: tb_sett_daily_collect                                 */
/*==============================================================*/
create table tb_sett_daily_collect
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '版本号',
   create_time          datetime not null comment '创建时间',
   edit_time            datetime not null comment '修改时间',
   account_no           varchar(20) not null comment '账户编号',
   user_name            varchar(200) comment '用户姓名',
   collect_date         date not null comment '汇总日期',
   collect_type         varchar(50) not null comment '汇总类型(参考枚举:settdailycollecttypeenum)',
   total_amount         decimal(24,10) not null comment '交易总金额',
   total_count          int not null comment '交易总笔数',
   sett_status          varchar(50) not null comment '结算状态(参考枚举:settdailycollectstatusenum)',
   remark               varchar(300) comment '备注',
   risk_day             int comment '风险预存期天数',
   primary key (id)
);

alter table tb_sett_daily_collect comment '每日待结算汇总';

/*==============================================================*/
/* table: tb_sett_record                                        */
/*==============================================================*/
create table tb_sett_record
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '版本号',
   create_time          datetime not null comment '创建时间',
   edit_time            datetime not null comment '修改时间',
   sett_mode            varchar(50) comment '结算发起方式(参考settmodetypeenum)',
   account_no           varchar(20) not null comment '账户编号',
   user_no              varchar(20) comment '用户编号',
   user_name            varchar(200) comment '用户姓名',
   user_type            varchar(50) comment '用户类型',
   sett_date            date comment '结算日期',
   bank_code            varchar(20) comment '银行编码',
   bank_name            varchar(100) comment '银行名称',
   bank_account_name    varchar(60) comment '开户名',
   bank_account_no      varchar(20) comment '开户账户',
   bank_account_type    varchar(50) comment '开户账户',
   country              varchar(200) comment '开户行所在国家',
   province             varchar(50) comment '开户行所在省份',
   city                 varchar(50) comment '开户行所在城市',
   areas                varchar(50) comment '开户行所在区',
   bank_account_address varchar(300) comment '开户行全称',
   mobile_no            varchar(20) comment '收款人手机号',
   sett_amount          decimal(24,10) comment '结算金额',
   sett_fee             decimal(16,6) comment '结算手续费',
   remit_amount         decimal(16,2) comment '结算打款金额',
   sett_status          varchar(50) comment '结算状态(参考枚举:settrecordstatusenum)',
   remit_confirm_time   datetime comment '打款确认时间',
   remark               varchar(200) comment '描述',
   remit_remark         varchar(200) comment '打款备注',
   operator_loginname   varchar(50) comment '操作员登录名',
   operator_realname    varchar(50) comment '操作员姓名',
   primary key (id)
);

alter table tb_sett_record comment '结算记录';

/*==============================================================*/
/* table: tb_sett_record_annex                                  */
/*==============================================================*/
create table tb_sett_record_annex
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   is_delete            varchar(36) not null,
   annex_name           varchar(200),
   annex_address        varchar(500) not null,
   settlement_id        varchar(50) not null,
   primary key (id)
);

/*==============================================================*/
/* table: tb_user_bank_account                                  */
/*==============================================================*/
create table tb_user_bank_account
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   status               varchar(36) not null,
   user_no              varchar(50) not null,
   bank_name            varchar(200) not null,
   bank_code            varchar(50) not null,
   bank_account_name    varchar(100) not null,
   bank_account_no      varchar(36) not null,
   card_type            varchar(36) not null,
   card_no              varchar(36) not null,
   mobile_no            varchar(50) not null,
   is_default           varchar(36),
   province             varchar(20),
   city                 varchar(20),
   areas                varchar(20),
   street               varchar(300),
   bank_account_type    varchar(36) not null,
   primary key (id)
);

alter table tb_user_bank_account comment '用户银行账户表';

/*==============================================================*/
/* table: tb_user_info                                          */
/*==============================================================*/
create table tb_user_info
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   status               varchar(36) not null,
   user_no              varchar(50),
   user_name            varchar(100),
   account_no           varchar(50) not null,
   primary key (id),
   unique key ak_key_2 (account_no)
);

alter table tb_user_info comment '该表用来存放用户的基本信息';

/*==============================================================*/
/* table: tb_user_pay_config                                    */
/*==============================================================*/
create table tb_user_pay_config
(
   id                   varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   status               varchar(36) not null,
   audit_status         varchar(45),
   is_auto_sett         varchar(36) not null default 'no',
   product_code         varchar(50) not null comment '支付产品编号',
   product_name         varchar(200) not null comment '支付产品名称',
   user_no              varchar(50),
   user_name            varchar(100),
   risk_day             int,
   pay_key              varchar(50),
   fund_into_type       varchar(50),
   pay_secret           varchar(50),
   primary key (id)
);

alter table tb_user_pay_config comment '支付设置表';

/*==============================================================*/
/* table: tb_user_pay_info                                      */
/*==============================================================*/
create table tb_user_pay_info
(
   id_                  varchar(50) not null,
   create_time          datetime not null,
   edit_time            datetime,
   version              bigint not null,
   remark               varchar(200),
   status               varchar(36) not null,
   app_id               varchar(50) not null,
   app_sectet           varchar(100),
   merchant_id          varchar(50),
   app_type             varchar(50),
   user_no              varchar(50),
   user_name            varchar(100),
   partner_key          varchar(100),
   pay_way_code         varchar(50) not null comment '支付方式编号',
   pay_way_name         varchar(100) not null comment '支付方式名称',
   primary key (id_)
);

alter table tb_user_pay_info comment '该表用来存放用户开通的第三方支付信息';


create table tb_account_check_batch
(
   id                   varchar(50) not null,
   version              int unsigned not null,
   create_time          datetime not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   status               varchar(30) not null,
   remark               varchar(500),
   batch_no             varchar(30) not null,
   bill_date            date not null,
   bill_type            varchar(30),
   handle_status        varchar(10),
   bank_type            varchar(30),
   mistake_count        int(8),
   unhandle_mistake_count int(8),
   trade_count          int(8),
   bank_trade_count     int(8),
   trade_amount         decimal(20,6),
   bank_trade_amount    decimal(20,6),
   refund_amount        decimal(20,6),
   bank_refund_amount   decimal(20,6),
   bank_fee             decimal(20,6),
   org_check_file_path  varchar(500),
   release_check_file_path varchar(500),
   release_status       varchar(15),
   check_fail_msg       varchar(300),
   bank_err_msg         varchar(300),
   primary key (id)
);

alter table tb_account_check_batch comment '对账批次表 tb_account_check_batch';

create table tb_account_check_mistake
(
   id                   varchar(50) not null,
   version              int unsigned not null,
   create_time          datetime not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   status               varchar(30),
   remark               varchar(500),
   account_check_batch_no varchar(50) not null,
   bill_date            date not null,
   bank_type            varchar(30) not null,
   order_time           datetime,
   merchant_name        varchar(100),
   merchant_no          varchar(50),
   order_no             varchar(40),
   trade_time           datetime,
   trx_no               varchar(20),
   order_amount         decimal(20,6),
   refund_amount        decimal(20,6),
   trade_status         varchar(30),
   fee                  decimal(20,6),
   bank_trade_time      datetime,
   bank_order_no        varchar(40),
   bank_trx_no          varchar(40),
   bank_trade_status    varchar(30),
   bank_amount          decimal(20,6),
   bank_refund_amount   decimal(20,6),
   bank_fee             decimal(20,6),
   err_type             varchar(30) not null,
   handle_status        varchar(10) not null,
   handle_value         varchar(1000),
   handle_remark        varchar(1000),
   operator_name        varchar(100),
   operator_account_no  varchar(50),
   primary key (id)
);

alter table tb_account_check_mistake comment '对账差错表 tb_account_check_mistake';

create table tb_account_check_mistake_scratch_pool
(
   id                   varchar(50) not null,
   version              int unsigned not null,
   create_time          datetime not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   product_name         varchar(50) comment '商品名称',
   merchant_order_no    varchar(30) not null comment '商户订单号',
   trx_no               char(20) not null comment '支付流水号',
   bank_order_no        char(20) comment '银行订单号',
   bank_trx_no          varchar(30) comment '银行流水号',
   order_amount         decimal(20,6) default 0 comment '订单金额',
   plat_income          decimal(20,6) comment '平台收入',
   fee_rate             decimal(20,6) comment '费率',
   plat_cost            decimal(20,6) comment '平台成本',
   plat_profit          decimal(20,6) comment '平台利润',
   status               varchar(30) comment '状态(参考枚举:paymentrecordstatusenum)',
   pay_way_code         varchar(50) comment '支付通道编号',
   pay_way_name         varchar(100) comment '支付通道名称',
   pay_success_time     datetime comment '支付成功时间',
   complete_time        datetime comment '完成时间',
   is_refund            varchar(30) default '101' comment '是否退款(100:是,101:否,默认值为:101)',
   refund_times         smallint default 0 comment '退款次数(默认值为:0)',
   success_refund_amount decimal(20,6) comment '成功退款总金额',
   remark               varchar(500) comment '备注',
   batch_no             varchar(50),
   bill_date            datetime
);

alter table tb_account_check_mistake_scratch_pool comment '差错暂存池';

create table tb_notify_record
(
   id                   varchar(50) not null,
   version              int not null,
   create_time          datetime not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   notify_times         int not null,
   limit_notify_times   int not null,
   url                  varchar(2000) not null,
   merchant_order_no    varchar(50) not null,
   merchant_no          varchar(50) not null,
   status               varchar(50) not null comment '100:成功 101:失败',
   notify_type          varchar(30) comment '通知类型',
   primary key (id),
   key ak_key_2 (merchant_order_no)
);

alter table tb_notify_record comment '通知记录表 tb_notify_record';

create table tb_notify_record_log
(
   id                   varchar(50) not null,
   version              int not null,
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   create_time          datetime not null,
   notify_id            varchar(50) not null,
   request              varchar(2000) not null,
   response             varchar(2000) not null,
   merchant_no          varchar(50) not null,
   merchant_order_no    varchar(50) not null comment '商户订单号',
   http_status          varchar(50) not null comment 'http状态',
   primary key (id)
);

alter table tb_notify_record_log comment '通知记录日志表 tb_notify_record_log';

create table tb_refund_record
(
   id                   varchar(50) not null comment 'id',
   version              int not null comment '版本号',
   create_time          datetime comment '创建时间',
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   org_merchant_order_no varchar(50) comment '原商户订单号',
   org_trx_no           varchar(50) comment '原支付流水号',
   org_bank_order_no    varchar(50) comment '原银行订单号',
   org_bank_trx_no      varchar(50) comment '原银行流水号',
   merchant_name        varchar(100) comment '商家名称',
   merchant_no          varchar(100) not null comment '商家编号',
   org_product_name     varchar(60) comment '原商品名称',
   org_biz_type         varchar(30) comment '原业务类型',
   org_payment_type     varchar(30) comment '原支付方式类型',
   refund_amount        decimal(20,6) comment '订单退款金额',
   refund_trx_no        varchar(50) not null comment '退款流水号',
   refund_order_no      varchar(50) not null comment '退款订单号',
   bank_refund_order_no varchar(50) comment '银行退款订单号',
   bank_refund_trx_no   varchar(30) comment '银行退款流水号',
   result_notify_url    varchar(500) comment '退款结果通知url',
   refund_status        varchar(30) comment '退款状态',
   refund_from          varchar(30) comment '退款来源（分发平台）',
   refund_way           varchar(30) comment '退款方式',
   refund_request_time  datetime comment '退款请求时间',
   refund_success_time  datetime comment ' 退款成功时间',
   refund_complete_time datetime comment '退款完成时间',
   request_apply_user_id varchar(50) comment '退款请求,申请人登录名',
   request_apply_user_name varchar(90) comment '退款请求,申请人姓名',
   refund_reason        varchar(500) comment '退款原因',
   remark               varchar(3000) comment '备注',
   primary key (id),
   unique key ak_key_2 (refund_trx_no)
);

alter table tb_refund_record comment '退款记录表';

create table tb_trade_payment_order
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '版本号',
   create_time          datetime not null comment '创建时间',
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   status               varchar(50) comment '状态(参考枚举:orderstatusenum)',
   product_name         varchar(300) comment '商品名称',
   merchant_order_no    varchar(30) not null comment '商户订单号',
   order_amount         decimal(20,6) default 0 comment '订单金额',
   order_from           varchar(30) comment '订单来源',
   merchant_name        varchar(100) comment '商家名称',
   merchant_no          varchar(100) not null comment '商户编号',
   order_time           datetime comment '下单时间',
   order_date           date comment '下单日期',
   order_ip             varchar(50) comment '下单ip(客户端ip,在网关页面获取)',
   order_referer_url    varchar(100) comment '从哪个页面链接过来的(可用于防诈骗)',
   return_url           varchar(600) comment '页面回调通知url',
   notify_url           varchar(600) comment '后台异步通知url',
   cancel_reason        varchar(600) comment '订单撤销原因',
   order_period         smallint comment '订单有效期(单位分钟)',
   expire_time          datetime comment '到期时间',
   pay_way_code         varchar(50) comment '支付方式编号',
   pay_way_name         varchar(100) comment '支付方式名称',
   remark               varchar(200) comment '支付备注',
   trx_type             varchar(30) comment '交易业务类型  ：消费、充值等',
   trx_no               varchar(50) comment '支付流水号',
   pay_type_code        varchar(50) comment '支付类型编号',
   pay_type_name        varchar(100) comment '支付类型名称',
   fund_into_type       varchar(30) comment '资金流入类型',
   is_refund            varchar(30) default '101' comment '是否退款(100:是,101:否,默认值为:101)',
   refund_times         int default 0 comment '退款次数(默认值为:0)',
   success_refund_amount decimal(20,6) comment '成功退款总金额',
   field1               varchar(500),
   field2               varchar(500),
   field3               varchar(500),
   field4               varchar(500),
   field5               varchar(500),
   primary key (id),
   unique key ak_key_2 (merchant_order_no, merchant_no)
);

alter table tb_trade_payment_order comment 'roncoo pay 龙果支付 支付订单表';

create table tb_trade_payment_record
(
   id                   varchar(50) not null comment 'id',
   version              int not null default 0 comment '版本号',
   create_time          datetime comment '创建时间',
   status               varchar(30) comment '状态(参考枚举:paymentrecordstatusenum)',
   editor               varchar(100) comment '修改者',
   creater              varchar(100) comment '创建者',
   edit_time            datetime comment '最后修改时间',
   product_name         varchar(50) comment '商品名称',
   merchant_order_no    varchar(50) not null comment '商户订单号',
   trx_no               varchar(50) not null comment '支付流水号',
   bank_order_no        varchar(50) comment '银行订单号',
   bank_trx_no          varchar(50) comment '银行流水号',
   merchant_name        varchar(300) comment '商家名称',
   merchant_no          varchar(50) not null comment '商家编号',
   payer_user_no        varchar(50) comment '付款人编号',
   payer_name           varchar(60) comment '付款人名称',
   payer_pay_amount     decimal(20,6) default 0 comment '付款方支付金额',
   payer_fee            decimal(20,6) default 0 comment '付款方手续费',
   payer_account_type   varchar(30) comment '付款方账户类型(参考账户类型枚举:accounttypeenum)',
   receiver_user_no     varchar(15) comment '收款人编号',
   receiver_name        varchar(60) comment '收款人名称',
   receiver_pay_amount  decimal(20,6) default 0 comment '收款方支付金额',
   receiver_fee         decimal(20,6) default 0 comment '收款方手续费',
   receiver_account_type varchar(30) comment '收款方账户类型(参考账户类型枚举:accounttypeenum)',
   order_ip             varchar(30) comment '下单ip(客户端ip,从网关中获取)',
   order_referer_url    varchar(100) comment '从哪个页面链接过来的(可用于防诈骗)',
   order_amount         decimal(20,6) default 0 comment '订单金额',
   plat_income          decimal(20,6) comment '平台收入',
   fee_rate             decimal(20,6) comment '费率',
   plat_cost            decimal(20,6) comment '平台成本',
   plat_profit          decimal(20,6) comment '平台利润',
   return_url           varchar(600) comment '页面回调通知url',
   notify_url           varchar(600) comment '后台异步通知url',
   pay_way_code         varchar(50) comment '支付方式编号',
   pay_way_name         varchar(100) comment '支付方式名称',
   pay_success_time     datetime comment '支付成功时间',
   complete_time        datetime comment '完成时间',
   is_refund            varchar(30) default '101' comment '是否退款(100:是,101:否,默认值为:101)',
   refund_times         int default 0 comment '退款次数(默认值为:0)',
   success_refund_amount decimal(20,6) comment '成功退款总金额',
   trx_type             varchar(30) comment '交易业务类型  ：消费、充值等',
   order_from           varchar(30) comment '订单来源',
   pay_type_code        varchar(50) comment '支付类型编号',
   pay_type_name        varchar(100) comment '支付类型名称',
   fund_into_type       varchar(30) comment '资金流入类型',
   remark               varchar(3000) comment '备注',
   field1               varchar(500),
   field2               varchar(500),
   field3               varchar(500),
   field4               varchar(500),
   field5               varchar(500),
   bank_return_msg      varchar(2000) comment '银行返回信息',
   primary key (id),
   unique key ak_key_2 (trx_no)
);

alter table tb_trade_payment_record comment '支付记录表';

CREATE TABLE tb_seq_table (SEQ_NAME varchar(50) NOT NULL, CURRENT_VALUE bigint DEFAULT '1000000002' NOT NULL, INCREMENT smallint DEFAULT '1' NOT NULL, REMARK varchar(100) NOT NULL, PRIMARY KEY (SEQ_NAME)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO tb_seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('ACCOUNT_NO_SEQ', 10000000, 1, '账户--账户编号');
INSERT INTO tb_seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('ACTIVITY_NO_SEQ', 10000006, 1, '活动--活动编号');
INSERT INTO tb_seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('USER_NO_SEQ', 10001113, 1, '用户--用户编号');
INSERT INTO tb_seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('TRX_NO_SEQ', 10000000, 1, '交易—-支付流水号');
INSERT INTO tb_seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('BANK_ORDER_NO_SEQ', 10000000, 1, '交易—-银行订单号');
INSERT INTO tb_seq_table (SEQ_NAME, CURRENT_VALUE, INCREMENT, REMARK) VALUES ('RECONCILIATION_BATCH_NO_SEQ', 10000000, 1, '对账—-批次号');

/*==============================================================*/
/* create function                                              */
/*==============================================================*/
CREATE FUNCTION `FUN_SEQ`(SEQ VARCHAR(50)) RETURNS BIGINT(20)
BEGIN
     UPDATE tb_seq_table
     SET CURRENT_VALUE = CURRENT_VALUE + INCREMENT
     WHERE  SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END;


CREATE FUNCTION `FUN_SEQ_CURRENT_VALUE`(SEQ VARCHAR(50)) RETURNS BIGINT(20)
BEGIN
    DECLARE VALUE INTEGER;
    SET VALUE=0;
    SELECT CURRENT_VALUE INTO VALUE
    FROM tb_seq_table 
    WHERE SEQ_NAME=SEQ;
    RETURN VALUE;
END;

CREATE FUNCTION `FUN_SEQ_SET_VALUE`(SEQ VARCHAR(50), VALUE INTEGER) RETURNS BIGINT(20)
BEGIN
     UPDATE tb_seq_table 
     SET CURRENT_VALUE=VALUE
     WHERE SEQ_NAME=SEQ;
     RETURN FUN_SEQ_CURRENT_VALUE(SEQ);
END;

CREATE FUNCTION  FUN_NOW()
 RETURNS DATETIME
BEGIN 
RETURN now();
END;


-- 时间函数

CREATE FUNCTION `FUN_DATE_ADD`(STR_DATE VARCHAR(10), STR_INTERVAL INTEGER) RETURNS DATE
BEGIN
     RETURN date_add(STR_DATE, INTERVAL STR_INTERVAL DAY);
END;