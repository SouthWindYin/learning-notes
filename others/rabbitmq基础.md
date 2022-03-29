# RabbitMQ基础

RabbitMQ是一个实现了AMQP的开源消息中间件（message broker），常用的替代品有kafka、RocketMQ、ActiveMQ。redis作为非关系型数据库，也可以实现消息队列功能。在同类型产品中，RabbitMQ的特点在于可靠性高，吞吐量不高容易积压消息导致性能下降，支持最多语言，有特色的路由（exchange）功能。

## RabbitMQ概念

RabbitMQ消息处理流程：`Producer -> Exchange -> Queue -> Consumer`\
使用时需要事先建好Exchange（交换器）和Queue（队列）。\
每一个rabbitmq的实例都会有一个名字为空的默认Exchange，每一个新创建的Queue都会默认绑定到这个Exchange上，有需要时再显式地绑定到其他Exchange上。

### virtual host 虚拟主机

一个rabbit实例上的逻辑分组，用来划分不同权限的用户，通常一个应用一个vhost。

### Binding 绑定

一个Exchange和一个Queue之间的关系的实体。Binding有一个名字叫Binding Key。

### Message 消息

Message由Properties和Body组成，Properties有点类似于http头，可以携带content type，消息发送时间，长度，接收者等信息。

### RabbitMQ的几种模式

#### 简单模式/工作模式

生产者发送消息至队列，队列中的消息由一个或多个消费者争用消费。简单/工作模式中并不是没有Exchange，而是用的默认Exchange。

#### 路由模式

类型为Direct的交换器被显示的定义出来，可以理解为是简单/工厂模式增加一个交换器。通过Routing Key来选择交换器和队列的绑定来投送消息。Routing Key等于上文提到的Binding Key。

#### 发布/订阅模式

该模式中的Exchange类型为fanout，一个fanout类型的交换器绑定着多个队列，每个队列对应由一个消费者监听。每个投递到该交换器的消息都会被分发到所有与之绑定的队列中，从而被多个消费者消费。

#### 主题模式

主题模式使用类型为topic的Exchange，基本结构和路由模式一样。不同的是Exchange投递到队列时，可以使用Binding Key中带有的通配符以实现多对多的投递。比如某绑定的Key是\*.apple.\*，则routing key为a.apple.com和b.apple.xyz的消息都会投递到该队列；某绑定Key是\*.apple.cn，则routing key为a.apple.cn的消息会同时投递到以上两个队列中。

#### 远程调用模式