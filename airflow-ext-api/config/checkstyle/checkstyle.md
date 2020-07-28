checkstyle使用方法
=====


我们的项目需要有代码规范，checkstyle就是检查代码规范的工具。

## checkstyle 规则配置文件

google-checks-6.18.xml

此文件定义了规则，所以修改前需要争取大家的同意，通常不要修改。



## idea插件
搜索安装idea插件，然后，在设置里，点击other settings,添加我们的checkstyle
规则文件。

之后可以在当前java文件上邮件，check current file查看检查结果.



## idea format自动格式化

idea自带的格式化需要根据我们的checkstyle配置文件进行修改，
```
checkstyle\intellij-java-google-style.xml
```

上述就是idea对应的配置文件，导入方式为，settings-editor-code style
- scheme - 点击右边的设置按钮 - import idea code style xml.




## 提交前检查

项目提交到git之前，需要本地在根目录运行
```
mvn clean site
mvn  install
```
失败则检查失败原因，如果是checkstyle检查不过，修改格式.


## 报告位置

失败后会生成一个xml结果文件，比如

```
target/site/checkstyle.html
```




