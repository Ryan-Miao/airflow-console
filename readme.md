Airflow Console
================

https://github.com/Ryan-Miao/airflow-console

[Apache Airflow](https://github.com/apache/airflow)扩展组件,
可以辅助生成dag, 并存储到git仓库.

Airflow提供了基于python语法的dag任务管理,我们可以定制任务内容
和任务依赖. 但对于很多数据分析人员来说,操作还是过于复杂. 期望可以
通过简单的页面配置去管理dag. 
即本项目提供了一个dag可视化配置管理方案.

## 如何使用

### 一些概念

**DAG**:  Airflow原生的dag， 多个任务依赖组成的有向无环图， 一个任务依赖链。

**Ext Dag**:  DAG扩展， DAG生成模板，通过页面配置Ext Dag可以一键生成DAG python配置。

**Ext Dag Category**:   Airflow原生不提供分类的概念，但Console我们扩展了分类功能, 我们创建不同Dag模板可以分属于不同的DAG分类。

**Ext Dag Task**:  Ext Dag的任务，真正任务的封装体，分为Operator和Sensor, 可以组装成Ext Dag.


### 1.创建业务分类.

我们的调度任务可以根据业务进行分类. 首先创建我们的业务类型.
![](https://img2020.cnblogs.com/blog/686418/202007/686418-20200728171242355-594842635.png)
![](https://img2020.cnblogs.com/blog/686418/202007/686418-20200728171411555-336313811.png)


### 2.创建dag
![](https://img2020.cnblogs.com/blog/686418/202007/686418-20200728171854815-536740287.png)


### 3.创建任务
点击task按钮进入task列表, 再点击add添加一个任务.

添加bash任务
![](https://img2020.cnblogs.com/blog/686418/202007/686418-20200729175302904-1912323827.png)

添加hive sql任务
![](https://img2020.cnblogs.com/blog/686418/202007/686418-20200729175448222-1249035148.png)

添加hive出库到mysql任务, 对应的插件为[hive_to_rdbms_operator](https://github.com/Ryan-Miao/docker-airflow/blob/master/plugins/operators/hive_to_rdbms_operator.py)
![](https://img2020.cnblogs.com/blog/686418/202007/686418-20200729175919490-890271750.png)

### 4.配置任务依赖关系

Airflow提供了任务上下游依赖的管理方案,具体就是使用python的 >> 语法

a >> b 表示a的{{ds}}的任务执行完毕才可以执行b.

![](https://img2020.cnblogs.com/blog/686418/202007/686418-20200729180142962-813610001.png)

点击更新按钮保存依赖关系.

### 5.生成dag.py脚本

点击提交按钮, 生成python脚本预览.
![](https://img2020.cnblogs.com/blog/686418/202007/686418-20200729180300371-693181066.png)

确认没有问题后, 提交就可以将dag保存的git仓库. Airflow那边定时拉取git更新即可.

![](https://img2020.cnblogs.com/blog/686418/202007/686418-20200729180444598-1205007044.png)





## 本地启动

通过[docker-airflow](https://github.com/puckel/docker-airflow)

启动airflow, 暴露pg端口和webserver端口, 
[docker-compose.yml](doc/docker-compose.yml)

```
cd doc
docker-compose up
```

启动后访问localhost:8090即airflow初始化完成.

1. 修改本项目db

修改application-dev.yml中DataSource的url host为localhost.

2. 导入db

将[schema.sql](doc/schema.sql)导入pg.

3. 启动本项目

访问localhost:8081/api 即swagger地址.

4. 启动web
