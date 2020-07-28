
drop table if exists ext_dag_category;
create table if not exists ext_dag_category(
    id serial primary key,
    name varchar(100) not null,
    description varchar(100) not null default '',
    status varchar(10) not null default 'active',
    create_time timestamptz not null default current_timestamp,
    update_time timestamptz not null default current_timestamp
);
create unique index if not exists idx_dag_cate on ext_dag_category(name);
comment on table ext_dag_category is 'dag分类表';
comment on column ext_dag_category.name is '分类名称';
comment on column ext_dag_category.status is '状态, active正常, deleted删除';


insert into ext_dag_category(name) values ('poi'), ('elbs');

-- select * from ext_dag_category;


drop table if exists ext_dag_conf;
create table if not exists ext_dag_conf(
    id bigserial not null primary key,
    name varchar not null,
    description varchar,
    category_id int not null default 0,
    cron varchar,
    owners varchar,
    depends_on_past boolean,
    start_date varchar,
    alert_ding varchar,
    alert_mail_receivers varchar,
    success_alert varchar,
    failure_alert varchar,
    task_dependency varchar,
    create_time timestamptz not null default current_timestamp,
    update_time timestamptz not null default current_timestamp
);

comment on column ext_dag_conf.name is 'The DAG id, uniq';
comment on column ext_dag_conf.category_id is 'The category id';
comment on column ext_dag_conf.cron is 'The DAG cron';
comment on column ext_dag_conf.owners is 'DAG负责人, hive计算的用户, 只能一个人';
comment on column ext_dag_conf.depends_on_past is 'true则依赖上一次构建';
comment on column ext_dag_conf.task_dependency is 'dag内部的任务依赖,用英文分号分割:  t1 >>t2 >>t3;  [t4, t5] >> t6';
comment on column ext_dag_conf.start_date is 'yyyy-MM-dd, dag开始日期';
comment on column ext_dag_conf.alert_ding is '钉钉通知, null则不通知';
comment on column ext_dag_conf.alert_mail_receivers is '邮件通知, 统一用户名, 英文逗号分割, null则不通知';
comment on column ext_dag_conf.success_alert is '成功后通知方式, ding,mail,用英文逗号分割';
comment on column ext_dag_conf.failure_alert is '失败后通知方式, ding,mail,用英文逗号分割';
comment on table ext_dag_conf is 'dag定义表';

create index if not exists idx_dag_owner on ext_dag_conf(owners);
create unique index if not exists idx_dag_name on ext_dag_conf(name);


drop table if exists ext_dag_task;
create table if not exists ext_dag_task(
    dag_id bigint not null,
    task_id varchar(100) not null,
    description varchar(100) not null,
    task_type varchar(100) not null,
    param varchar,
    create_time timestamptz not null default current_timestamp,
    update_time timestamptz not null default current_timestamp,
    primary key (dag_id, task_id)
);
create index if not exists idx_dag_task_plugin on ext_dag_task(task_type);

comment on column ext_dag_task.task_type is '任务类型, operator或者Sensor的名字';
comment on column ext_dag_task.param is '任务参数, json格式, 不同插件对应的参数映射';

