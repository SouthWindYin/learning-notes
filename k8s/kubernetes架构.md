# kubernetes架构

一个k8s集群，是由一个控制平面（control plane）和很多工作节点（node）组成的。控制平面暴露出一系列的kubernetes API，用于管理整个集群，工作节点运行和维护容器，是完成具体工作的机器。

## 节点

### kubelet

节点上运行的一个组件，提供了一套kubelet api用于监控该节点上的所有容器，并和控制平面通信，根据控制平面的指令来维护该节点的所有容器

### 容器运行时

k8s支持很多容器运行环境，containerd、CRI-O及Kubernetes CRI的其他实现。docker就是用的containerd运行环境。

## k8s网络模型

* 每个pod都有自己独一无二的ip，意味着k8s集群中所有的pod互相之间可以轻易的通信
* pod内部可以运行多个容器，他们共享一个ip，意味着一个pod内的容器之间都是通过localhost访问的，需要协调以防端口冲突
* 整个k8s集群通过Service API暴露端口到外网

## k8s的CRD和自定义controller

* k8s的默认架构是由内置的多种资源和kube-controller-manager组成的，默认资源就是pod、node、deployment等东西，kube-controller-manager用于管理他们。
* CRD和自定义controller就是自定义的资源和管理这些自定义资源的控制器
* tmforum的canvas就是通过CRD和自定义controller扩展的k8s，增加了tmf自己的组件资源