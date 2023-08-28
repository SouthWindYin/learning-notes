# ldap的概念

全称：轻量级目录访问协议

## ldap模型

ldap中的数据结构是一棵树，称为**目录树**，树上每一个节点是一个**条目**，每个条目有自己唯一的**DN（Distinguished Name可区别名称）**，DN是由多个**属性**组成的，每个属性可以是必须的或非必须的，每个属性也有他的属性值。

## DN的关键字

* dc（Domain Component域名部分）：将域名划分成几段，比如example.com，写作dc=example,dc=com
* uid（User Id用户id）：用户的唯一id
* ou（Organization Unit组织单位）：
* 