# Food Trucks
Developer Coding Challenge Guidelines
Food Trucks

Create a service that tells the user what types of food trucks might be found near a specific location on a map.
The data is available on [DataSF](https://data.sfgov.org/Permitting/Mobile-Food-Facility-Permit/rqzj-sfat)

[![Build Status](https://travis-ci.org/icanfly/myapp.svg)](https://travis-ci.org/icanfly/myapp)
[![codecov](https://codecov.io/gh/icanfly/myapp/branch/master/graph/badge.svg)](https://codecov.io/gh/icanfly/myapp)

# 功能描述

本Demo提供两种模式的搜索服务：在线/离线。

在线版本在搜索时会实时查询远程数据仓(data.sfgov.org)中的数据。

离线版本会在本地构建一个简单的基于Lucene地理空间的索引，并根据请求对该索引进行空间查询。

# 使用到的技术

## 后端

Java语言体系
- SpringBoot                        整个Demo工程的骨架
- Guava                             一些方便的工具类库
- Jackson                           JSON序列化工具类库
- Lucene                            离线数据索引工具
- Spatial4j                         离线数据空间索引工具
- Apache Common Lang3               Apache工具类库
- OkHttp                            Http客户端类库

## 前端

JQuery                              前端工具类库
BaiduMap                            百度地图API
layerUI                             前端类库
showLoading                         JQuery showLoading plugin

# 编译

## 使用wrapper

- Linux or Mac  

  执行./mvnw即可

- Windows

  执行./mvnw.cmd

## 使用外置maven

mvn clean package

# 本地运行

可直接在IDE环境中切换到FootstrucksDemoApplication类点击右键“Run”或者"Debug"即可。

# 部署

本Demo打包后会在target下生成.war文件。部署方式可分为两种：

- 基于标准的Servlet容器部署
  
  直接将war包部署到servlet容器启动即可

- 基于内嵌容器启动

  可以使用java -jar xxxx.war 这种方式直接使用内嵌容器运行
