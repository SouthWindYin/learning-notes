# docker私有仓库

docker拉取镜像时，默认是从中央仓库docker.io拉取的，但是如果网络不通（墙或内网），则没办法拉取镜像。用户需要在网络通的地方搭建私有仓库以存放需要的镜像。  
docker官方提供了registry镜像供用户搭建私有仓库，需要先在能访问到docker.io的地方拉好这个镜像，并保存成tar，再加载至docker运行的网络中以部署。  
注：docker官方没有提供tar的下载方式，只能通过docker pull的方式拉下来，或者用第三方的网站下载tar包。  
注2：拉取镜像的机器的cpu架构要和内网搭建私有仓库的机器架构一样，比如都是x86或都是arm

## 在内网部署私有仓库

* `docker login`登录docker hub的账号，以使用docker中央仓库
* `docker pull registry`在可以访问docker hub的机器拉取registry镜像到本地
* `docker save registry > registry.tar`保存registry镜像为tar包
* 上传registry的tar包至内网
* `docker load < registry.tar`加载registry镜像包
* `docker run -d -p 5000:5000 --restart=always registry`运行registry镜像，并开放5000端口，设置docker重启后自动启动该容器，后台启动一个私有仓库的容器。

## 上传镜像至私有仓库

以*henry/demo*镜像为例
* 在`/etc/docker/daemon.json`配置文件中增加`insecure-registries`，其值为私有仓库的地址组成的列表
* `systemctl restart docker`重启docker以应用修改的配置
* `docker login 127.0.0.1:5000`登录至本机5000的镜像仓库
* `docker tag henry/demo 127.0.0.1:5000/henry/demo`给镜像打一个标签，并包含要存的仓库地址
* `docker push 127.0.0.1:5000/henry/demo`推送镜像至对应仓库

## 使用sonatype nexus镜像仓库时的坑

* nexus创建docker镜像仓库时如果打开了允许匿名拉取镜像的设置，就没法推送镜像和`docker login`，不知道为啥，建议关了这个设置。
* 如果内网，一般都要设置http的端口，然后要把http这个端口的地址放进daemon.json配置中
 