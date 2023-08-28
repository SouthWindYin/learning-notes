# 什么是elasticsearch

一个分布式搜索引擎、数据分析引擎，主要用于全文检索和数据分析，是当下流行的开源技术。

## elasticsearch数据结构

* es的最小单位是**field属性**
* 多个field组成一个**document文档**
* 多个document组成一个**type类型**
* 多个type汇总在一个**index索引**下面
* 单个节点上就是存储的多个索引

## ECS(Elastic Common Schema)

elastic定义的一组标准化的字段，用于标准化的存储日志、指标等数据到elastic中。

## 常用命令

* `bin/elasticsearch-reset-password -u elastic -i --url "https://localhost:9200"`重置**elastic**用户的密码，使用交互式操作