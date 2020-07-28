
drop TABLE if EXISTS  public.ext_dag_category;

CREATE TABLE public.ext_dag_category (
                                         id serial NOT NULL primary key,
                                         name character varying(100) NOT NULL,
                                         description character varying(100) DEFAULT ''::character varying NOT NULL,
                                         status character varying(10) DEFAULT 'active'::character varying NOT NULL,
                                         create_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                         update_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE UNIQUE INDEX "uniq_dag_cate" ON "public"."ext_dag_category" (
                                                                    "name"
    );

COMMENT ON TABLE public.ext_dag_category IS 'dag分类表';
COMMENT ON COLUMN public.ext_dag_category.name IS '分类名称';
COMMENT ON COLUMN public.ext_dag_category.status IS '状态, active正常, deleted删除';


drop TABLE if EXISTS  public.ext_dag_conf;
CREATE TABLE public.ext_dag_conf (
                                     id bigserial NOT NULL PRIMARY key,
                                     name character varying NOT NULL,
                                     description character varying,
                                     category_id integer DEFAULT 0 NOT NULL,
                                     cron character varying,
                                     owners character varying,
                                     depends_on_past boolean,
                                     start_date character varying,
                                     alert_ding character varying,
                                     alert_mail_receivers character varying,
                                     success_alert character varying,
                                     failure_alert character varying,
                                     task_dependency character varying,
                                     create_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     update_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);


COMMENT ON TABLE public.ext_dag_conf IS 'dag定义表';


COMMENT ON COLUMN public.ext_dag_conf.name IS 'The DAG id, uniq';
COMMENT ON COLUMN public.ext_dag_conf.category_id IS 'The category id';
COMMENT ON COLUMN public.ext_dag_conf.cron IS 'The DAG cron';
COMMENT ON COLUMN public.ext_dag_conf.owners IS 'DAG负责人, hive计算的用户, 只能一个人';
COMMENT ON COLUMN public.ext_dag_conf.depends_on_past IS 'true则依赖上一次构建';
COMMENT ON COLUMN public.ext_dag_conf.start_date IS 'yyyy-MM-dd, dag开始日期';
COMMENT ON COLUMN public.ext_dag_conf.alert_ding IS '钉钉通知, null则不通知';
COMMENT ON COLUMN public.ext_dag_conf.alert_mail_receivers IS '邮件通知, 统一用户名, 英文逗号分割, null则不通知';
COMMENT ON COLUMN public.ext_dag_conf.success_alert IS '成功后通知方式, ding,mail,用英文逗号分割';
COMMENT ON COLUMN public.ext_dag_conf.failure_alert IS '失败后通知方式, ding,mail,用英文逗号分割';
COMMENT ON COLUMN public.ext_dag_conf.task_dependency IS 'dag内部的任务依赖,用英文分号分割:  t1 >>t2 >>t3;  [t4, t5] >> t6';

drop  TABLE if EXISTS  public.ext_dag_task;
CREATE TABLE public.ext_dag_task (
                                     dag_id bigint  NOT NULL,
                                     task_id character varying(100) NOT NULL,
                                     description character varying(100) NOT NULL,
                                     task_type character varying(100) NOT NULL,
                                     param character varying,
                                     create_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                                     update_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);
CREATE UNIQUE INDEX "uniq_dag_task" ON "public"."ext_dag_task" (
                                                                "dag_id",
                                                                "task_id"
    );

COMMENT ON COLUMN public.ext_dag_task.task_type IS '任务类型, operator或者Sensor的名字';
COMMENT ON COLUMN public.ext_dag_task.param IS '任务参数, json格式, 不同插件对应的参数映射';