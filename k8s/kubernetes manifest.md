# kubernetes manifest描述文件

是一个k8s集群对象的描述文件，它描述了该对象的多方面的特征，k8s根据manifest文件的描述来在集群中创建该对象。
manifest文件包含了kubernetes api的版本、对象类型、对象元数据、对象特性等信息。  
可以用来描述Service、ReplicaSet、Deployment等对象  
这个文件作用类似于maven的pom.xml，maven程序负责创建pom中描述的程序

## 文件结构

### apiVersion

```
apiVersion: [group]/[version]
```
  
指定api的组和版本。k8s的所有api是分组的，就根据这个apiVersion来分的

### kind

```
kind: [Deployment|ReplicaSet|StatefulSet|DaemonSet|Job|CronJob|ReplicationController]
```
创建的api资源的类型，可以是Deployment、ReplicaSet、StatefulSet、DaemonSet、Job、CronJob、ReplicationController等。

### metadata

元数据，用于唯一标识Kubernetes集群中的资源。在这里面对资源进行命名、分配标签、指定命名空间等。可以扩展自定义的唯一标识。

### spec

定义该对象的各种特性和配置，比如要使用的容器镜像、ReplicaSet中副本的数量、selector的条件等，还可以扩展自定义的配置。


