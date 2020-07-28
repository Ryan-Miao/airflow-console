Airflow Console
================

[Apache Airflow](https://github.com/apache/airflow)扩展组件,
可以辅助生成dag, 并存储到git仓库.

Airflow提供了基于python语法的dag任务管理,我们可以定制任务内容
和任务依赖. 但对于很多数据分析人员来说,操作还是过于复杂. 期望可以
通过简单的页面配置去管理dag. 
即本项目提供了一个dag可视化配置管理方案.


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
