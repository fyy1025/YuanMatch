-- auto-generated definition
-- 用户表
create table user
(
    id                bigint                             null comment '主键',
    user_Account      varchar(512)                       not null comment '登录账号',
    user_Password     varchar(512)                       not null comment '密码',
    user_Name         varchar(512)                       null comment '用户名称',
    role              bigint   default 0                 not null comment '用户权限',
    valid             tinyint  default 0                 not null comment '用户状态',
    create_Time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_Time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    logic_Delete      int      default 0                 not null comment '逻辑删除',
    invitation_Number varchar(128)                       null comment '邀请编号',
        tags         varchar(1024) null comment '标签 json 列表'
)
    comment '用户表';

-- 队伍表
create table team
(
    id          bigint auto_increment comment 'id' primary key,
    name        varchar(256)       not null comment '队伍名称',
    description varchar(1024) null comment '描述',
    max_Num      int      default 1 not null comment '最大人数',
    expire_Time  datetime null comment '过期时间',
    user_Id      bigint comment '用户id（队长 id）',
    status      int      default 0 not null comment '0 - 公开，1 - 私有，2 - 加密',
    password    varchar(512) null comment '密码',
    create_Time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_Time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    logic_Delete    tinyint  default 0 not null comment '是否删除'
) comment '队伍';

-- 用户队伍关系
create table user_team
(
    id         bigint auto_increment comment 'id'
        primary key,
    user_Id     bigint comment '用户id',
    team_Id     bigint comment '队伍id',
    join_Time   datetime null comment '加入时间',
    create_Time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_Time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    logic_Delete   tinyint  default 0 not null comment '是否删除'
) comment '用户队伍关系';

-- 标签表（可以不创建，因为标签字段已经放到了用户表中）
create table tag
(
    id         bigint auto_increment comment 'id'
        primary key,
    tag_Name    varchar(256) null comment '标签名称',
    user_Id     bigint null comment '用户 id',
    parent_Id   bigint null comment '父标签 id',
    is_Parent   tinyint null comment '0 - 不是, 1 - 父标签',
    create_Time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_Time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    logic_Delete   tinyint  default 0 not null comment '是否删除',
    constraint uniIdx_tagName
        unique (tag_Name)
) comment '标签';