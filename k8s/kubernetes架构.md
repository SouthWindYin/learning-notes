# kubernetes架构

一个k8s集群，是由很多个节点组成的网络。某一节点上运行着一个控制平面（control plane），其他节点是可以运行容器的工作节点（node）。控制平面的kube-controller-manager暴露出一系列的kubernetes API，用于管理整个集群的所有资源。

## 节点

加入k8s网络的每一台主机或虚拟机，就是一个节点，互相之间可以在k8s网络中直接通信

### kubelet

节点上运行的一个组件，暴露出一套kubelet api，用于监控该节点上的所有容器，并和控制平面通信，根据控制平面的指令来维护该节点的所有容器

### 容器运行时

k8s支持很多容器运行环境，containerd、CRI-O及Kubernetes CRI的其他实现。docker就是用的containerd运行环境。只要是满足k8s定义的容器运行时接口CRI，就可以运行在k8s集群中

## k8s网络模型

* k8s集群内部是一个互通的内网
* 每个pod都有在集群网络中唯一的ip，意味着k8s集群中所有的pod互相之间可以轻松通信
* 一个pod内部可以运行多个容器，他们使用同一个ip，意味着一个pod内的容器之间都是通过localhost访问的，需要协调以防端口冲突
* 整个k8s集群通过Service API暴露端口到外网
* 假如a、b、c主机组成了一个k8s集群，a是控制平面，且k8s集群中定义了b和c的service，则集群外部服务通过a的ip加上service中的接口映射访问到b和c的应用

## k8s的CRD和自定义controller

* k8s的默认架构是由内置的多种资源和kube-controller-manager组成的，默认资源就是pod、node、deployment等东西，kube-controller-manager用于管理他们。
* CRD和自定义controller就是自定义的资源和管理这些自定义资源的控制器
* tmforum的canvas、istio就是通过CRD和自定义controller扩展的k8s，增加了自己的组件资源