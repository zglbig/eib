# constraint '外键约束' foreign key ('要关联的外键(自身表)') references '要关联的表'('表里面的键');
# unique key '添加这个表的唯一键如uid';
alter table 表 add unique index (要追加的唯一索引);

CREATE SCHEMA ledian;
use ledian;
create table db_task(
  uid mediumtext comment '用户uid',
  extend json null comment '扩展对象'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '任务';

create table db_rebate_dial(
  uid mediumtext comment '用户uid',
  today int comment '时间标记',
  top_up mediumtext comment '当天充值数量',
  dice mediumtext comment '当天骰子场投注总额',
  to_room mediumtext comment '当天万人场投注总额',
  lottery mediumtext comment '当天彩票了投注总额',
  gold_award int comment '当天使用金币抽奖次数',
  top_up_award int comment '当天使用充值抽奖次数'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '返利轮盘';


create table db_to_up(
  uid mediumtext comment '用户uid',
  create_time mediumtext comment '购买时间',
  create_time_str varchar(20) comment '购买时间',
  money int comment '充值金额',
  product_id int comment '商品id'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '充值';

create table db_user_log(
  id int primary key not null auto_increment comment '主键',
  uid mediumtext comment '操作人的uid',
  hold_gold mediumtext comment '当前持有金币',
  exchange_gold mediumtext comment '变更乐多少金币',
  user_name varchar(500) comment '操作人的昵称',
  operation_time int comment '操作时间',
  operation_time_str varchar(20) comment '操作时间',
  position int comment '操作场景',
  card varchar(5000) comment '牌',
  card_type varchar(15) comment '牌型',
  operation_desc varchar(5000) comment '说明'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '用户操作日志';

create table db_gift_bag(
  id int primary key not null auto_increment comment '主键',
  uid mediumtext comment '用户uid',
  day_count int default 1 comment '第几天',
  last_login_time int default 1 comment '上次登陆时间',
  primary_count int comment '初级场',
  intermedite int comment '中级场',
  advanced int comment '高级场',
  is_done tinyint(1) comment '是否完成',
  is_award tinyint(1) comment '是否已经领取',
  extend json null comment '扩展对象'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '成长礼包';

create table db_activity(
  id int primary key not null auto_increment comment '主键',
  uid mediumtext comment '用户uid',
  extend json null comment '扩展对象'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '活动';

create table db_bank(
  uid mediumtext comment '用户uid',
  gold mediumtext null comment '存在银行的金币'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '银行';

create table db_generalize(
  num mediumtext comment '用户uid',
  self_uid mediumtext null comment '自己的uid',
  target_uid mediumtext null comment '自己邀请的人的uid',
  award mediumtext null comment '这个人创造的奖励',
  all_award mediumtext null comment '这个人创造的总奖励',
  target_user_name varchar(500) null comment '对方的用户名',
  create_time varchar(20) null comment '邀请时间'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '推广';

create table db_red_packet(
  id int primary key not null auto_increment comment '主键',
  uid mediumtext comment '发红包的uid',
  user_name varchar(500) comment '发红包的用户名',
  head_url varchar(10000) comment '发红包的头像',
  vip_lv int comment 'vip等级',
  all_num int comment '红包总个数',
  all_gold mediumtext comment '红包总金额',
  draw_num int comment '已经领取的红包个数',
  residue_gold mediumtext comment '剩余金额',
  create_time mediumtext comment '创建时间',
  create_time_str varchar(100) comment '创建时间字符串',
  extend json null comment '扩展对象'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '红包';

create table db_sigin(
  id int primary key not null auto_increment comment '主键',
  uid mediumtext comment '用户uid',
  sigin_day int comment '用户uid',
  lastTime_sigin_time mediumtext comment '上次签到时间',
  dial_all_num int comment '总的可以转多少次',
  dial_use_num int comment '阴茎转乐多少次',
  extend json null comment '扩展对象'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '签到';

create table db_friends(
  id int primary key not null auto_increment comment '主键',
  uid mediumtext comment '用户uid',
  friend_uid mediumtext comment '好友uid',
  user_name varchar(100) comment '好用用户名',
  head_url varchar(10000) comment '好友头像',
  vip_lv int comment '好友vip等级',
  extend json null comment '扩展对象'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '好友';


create table db_autos(
  id int primary key not null auto_increment comment '主键',
  uid mediumtext comment '座驾所属人',
  auto_id int comment '座驾id',
  create_time mediumtext comment '创建时间',
  create_time_str varchar(50) comment '创建时间字符串',
  extend json null comment '扩展对象'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '座驾';

create table db_weath(
  id int primary key not null auto_increment comment '主键，自增',
  uid mediumtext comment '对应的用户uid',
  gold mediumtext comment '金币',
  diamond mediumtext comment '钻石',
  integral mediumtext comment '积分',
  vip_lv int default 0 comment 'viplv',
  vip_exp mediumtext comment '',
  use_auto_id int default 0 comment '当前使用的座驾id',
  charm_num mediumtext comment '魅力值',
  first_buy tinyint(1) null comment '是否已经使用了首冲',
  first_buy_time int default 0 comment '首冲时间',
  extend json null comment '扩展对象'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '财富表';

create table db_prop(
  id int primary key not null auto_increment comment '主键，自增',
  uid mediumtext null comment 'uid',
  exchange_card int null comment '换牌卡',
  kicking_card int null comment '踢人卡',
  trumpet_card int null comment '喇叭卡',
  flower mediumtext null comment '鲜花',
  egg mediumtext null comment '鸡蛋',
  bomb mediumtext null comment '炸弹',
  diamond_ring int null comment '钻戒',
  sports_car int null comment '跑车',
  tower int null comment '城堡',
  streamer int null comment '轮船',
  aircraft int null comment '飞机',
  blue_succubus int null comment '蓝色妖姬',
  telephone_charge10 int null comment '10元话费',
  telephone_charge50 int null comment '50元话费',
  telephone_charge100 int null comment '100元话费',
  iphone_x int null comment '苹果x',
  ipone7p int null comment '苹果7p',
  mix2s int null comment '小米2x',
  extend json null comment '扩展对象'
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '道具';

create table db_player(
  id int primary key not null auto_increment comment '主键，自增',
  uid mediumtext null comment 'uid',
  account varchar(100) not null comment '账号',
  password varchar(20) null comment '密码',
  user_name varchar(500) null comment '用户名',
  head_img_url varchar(5000) null comment '头像',
  generalize_uid mediumtext null comment '推广人uid',
  addr varchar(20) null  comment '常用ip地址',
  signature varchar(2000) null comment '签名',
  contact_way varchar(50) null comment '联系方式',
  extend json null comment '扩展对象，用于以后多加的信息',

  weath_id int not null comment '要关联的weath表外键',
  sigin_id int not null comment '签到外键',
  prop_id int not null comment '道具表外键',

  create_time date null comment '创建时间',
  last_edit_time date null comment '最后更新时间',
  foreign key (weath_id) references db_weath(id),
  foreign key (sigin_id) references db_sigin(id),
  foreign key (prop_id) references db_prop(id)
)engine = InnoDB default charset = utf8mb4 collate=utf8mb4_bin comment = '玩家数据';


truncate table db_prop;
truncate table db_sigin;
truncate table db_player;
DELETE FROM db_prop ;
DELETE FROM db_sigin ;

INSERT INTO db_weath (user_uid,gold,diamond,integral,vip_lv,vip_exp,use_auto_id,charm_num,first_buy,first_buy_time,extend)
values(10,10,10,10,10,10,10,10,false ,10,'{}');
insert into db_player (account,password,user_name,head_img_url,generalize_uid,extend)
values('das','asd','asd','asd',10,'{}');
