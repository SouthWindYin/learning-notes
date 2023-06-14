# kubernetes基本概念

## pod

pod是k8s中最小的执行单元，其中可以包含一个或多个容器

## node 节点

集群中的一台主机或者一个虚拟机，里面可以包含一个或多个pod

## control plane 控制平面

一台主机的角色，类似于master节点，由它来监测整个k8s网络的运行状况，比如某个pod里的某个容器挂了，则会从ReplicaSet中拿过来一个准备好的容器，立即替换掉。

## replica set 副本集

可以指定n个副本容器，副本集启动了很多备用容器，正常状态不会使用，当有容器挂掉的时候，则把副本集中的容器替换上去。

## cluster 集群

* 控制平面+节点+副本集=集群
* 一个集群内部的所有pod构成了一个网络，每个pod有一个独立的ip（内网ip），集群外部不能直接访问到每个pod，需要用service暴露

### [kubernetes manifest](./kubernetes%20manifest.md)

* kubernetes manifest是定义一个集群中的某个对象的描述文件，类似于docker的Dockerfile，k8s实现会根据这个描述文件来配置生成集群对象。
* 后缀名为yaml

## services 服务

service也是运行在某个node上的，他类似于网关的，指定一个对象，将它的某个端口映射到本节点的某个端口上。服务是面向集群外部的，用于配置将集群内部的节点暴露出去的规则。例如将名字为nginx的deployment的80端口映射到service所在节点的30002端口，命令为`kubectl expose deployment nginx --port=80 --target-port=30002`

### 为什么要有service

对于一个deployment，他背后的pod的节点ip可能会变，这样其他应用就无法准确找到该pod的地址，所以要用service来固定一个ip访问。

### NodePort 节点端口

将集群内部某个端口映射到外部某个端口

### Persistent Volume（pv） 持久卷 Persistent Volume Claim（pvc） 持久卷申领

* pv是由管理员创建的，外部的存储地址，可以是磁盘，可以是网络存储，可以是云存储
* pvc是应用（pod）创建的使用存储的声明，应用声明一个pvc之后pvc和pv绑定，应用就可以使用那个存储了。
* pvc用完后，释放pv，此时pv只能被删除或者fail，不能再次被其他pvc绑定
  
### StorageClass 存储类

* 用于自动创建pv，可以对某一整块磁盘创建一个存储类，然后应用创建一个pvc时，k8s自动从该磁盘中创建一个pv

## kubelet

节点上管理pod的程序

## deployment 部署

管理副本集，从而管理pod

## helm k8s包管理工具

* 就像apt之于ubuntu，helm是管理k8s集群的包管理工具，他的包叫chart。helm也有自己的仓库chart repository。chart运行在k8s集群中的实例，就称为一个release。
* 每一个release有他唯一的release name，如果你要安装两个数据库，就要产生两个数据库chart的release。
* helm创建chart的语法涉及到go语言
* oda部署canvas组件，使用的是部署helm chart的方式

### helm常用命令

* `helm install [chart名称] [chart路径]`在default命名空间根据指定路径创建一个chart，命名为指定的名称
* `helm status [release名称] --show-desc`查看指定名称的release

## k8s镜像仓库

* k8s拉取镜像时，使用的是容器的镜像仓库，例如docker、podman的镜像仓库。如果不想用互联网上的公共仓库，就需要建立私有镜像仓库（以docker为例）
* 集群内私有镜像仓库：docker官方提供了DockerRegistry镜像，方便用户自行搭建私有镜像仓库。更推荐使用sonatype nexus repository，是更成熟完善的镜像仓库。

# kubectl使用方法

kubectl是用来管理k8s集群的命令，调用kubectl的命令，等同于请求Kubernetes API的接口。

* `kubectl get [资源]`：获取集群中某个资源的列表
* `kubectl label [资源] [资源名] [标签名]=[标签值]`：给某个指定名称的资源增加一个键值对的标签
* `kubectl apply -f [manifest文件名]`：在k8s集群应用该manifest文件中描述的资源，可以是创建资源，也可以是更改资源

# minikube 使用方法和特性

minikube可以在windows上使用，是一个用于快速验证k8s功能的小型容器编排环境。

## minikube ip

minikube的ip不是宿主机的ip，而是他自己虚拟出来了一个ip。不像linux上运行的k8s用服务暴露出NodePort之后就可以通过外部访问pod了，minikube不行。要想访问minikube中的NodePort，需要用`minikube service [服务名称] --url`来把指定服务的NodePort暴露出来。

# windows上minikube部署应用步骤

1. maven打包java应用，生成jar文件
2. 编写Dockerfile，
3. 使用`docker build -t [image名称] .`生成docker image，以运行该应用
4. 使用`minikube start`启动minikube
5. 使用`minikube image load [image名称]`来加载镜像至minikube
6. 编写minikube manifest，创建一个deployment的manifest文件
7. 使用`kubectl apply -f [manifest文件]`命令创建一个deployment
8. 使用`kubectl expose service [service名称] --port=[暴露的端口] --target-port=[集群内部端口] --type=NodePort`对外暴露端口
9. 使用`minikube service [service名称]`访问集群