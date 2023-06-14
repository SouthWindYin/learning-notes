# docker私有仓库

docker拉取镜像时，默认是从中央仓库docker.io拉取的，但是如果网络不通（墙或内网），则没办法拉取镜像。用户需要在网络通的地方搭建私有仓库以存放需要的镜像。  
docker官方提供了registry镜像供用户搭建私有仓库，需要先在能访问到docker.io的地方拉好这个镜像，并保存成tar，再加载至docker运行的网络中以部署。  
注：docker.io官方没有提供tar的下载方式，只能通过docker pull的方式拉下来，或者用第三方的网站下载tar包。  
注2：拉取镜像的客户端的cpu架构要和内网搭建私有仓库的机器架构一样，比如都是x86或都是arm

## 在内网部署私有仓库

* `docker login`登录docker hub的账号，以使用docker中央仓库
* `docker pull registry`在可以访问docker hub的机器拉取registry镜像到本地
* `docker save registry > registry.tar`保存registry镜像为tar包
* 上传registry的tar包至内网
* `docker load < registry.tar`加载registry镜像包
* `docker run -d -p 5000:5000 --restart=always registry`开放5000端口，设置docker重启后自动启动该容器，后台启动一个运行docker私有仓库的容器。

## 上传镜像至私有仓库

以*henry/demo*镜像为例
* 在`/etc/docker/daemon.json`配置文件中增加`insecure-registries`（windows则在docker-desktop的 *设置-Docker Engine* 中配置），其值为私有仓库的地址组成的列表。例：`insecure-registries: ["127.0.0.1:5000"]`
* `systemctl restart docker`重启docker以应用修改的配置
* `docker login 127.0.0.1:5000`登录至本机5000的镜像仓库（默认配置的docker registry不用登录）
* `docker tag henry/demo 127.0.0.1:5000/henry/demo`给镜像打一个标签，指明私有仓库地址，不指定仓库地址则默认docker.io中央仓库
* `docker push 127.0.0.1:5000/henry/demo`推送镜像至对应私有仓库

## 使用sonatype nexus镜像仓库时的坑

* 创建镜像仓库时需要配置一个额外的端口，比如http的8082。如果使用的docker镜像方式启动的nexus，则需要在创建容器时就把8082端口开放出来，再在nexus系统中创建镜像仓库并绑定容器内的8082端口。
* nexus创建了docker镜像仓库之后，需要在*设置-Security-Realms-Active*中增加*Docker Bearer Token Realm*这个选项，否则无法在docker中使用`docker login`登录，也就无法推镜像
* 虽然*localhost*和*127.0.0.1*都是本机，但他们是两个主机地址。故`docker login`和配置*insecure-registries*时都要注意，用相同的主机地址。
* 如果内网，一般都要设置http的端口，然后要把http这个端口的地址放进daemon.json配置中
 