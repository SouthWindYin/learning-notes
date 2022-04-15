# Spring容器启动流程

spring容器启动的核心在于：创建一个ApplicationContext实例，然后刷新它。

## 启动流程

1. 通过new创建ApplicationContext接口实例（xml、注解、springboot的实现类不一样）
2. 