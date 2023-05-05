# docker基本概念

容器技术是一种轻量级的虚拟机，docker是容器技术的一个实现。容器具有启动快，占用资源少等特点。docker通过内存隔离，命名空间来分割各个容器的。  
docker镜像可以跨系统运行，不能跨cpu指令集，意味着x86下生成的docker镜像不能直接运行在arm的cpu下。

## container容器

docker运行的实例单位，运行一个容器相当于运行一个子系统。当删除一个容器时，容器中的一切数据都被删除了。

## image镜像

运行于容器中的那个系统里的一切数据构成了镜像，镜像可以在不同的容器中运行，镜像就像windows的快照，类似于某个时刻的磁盘的整体复制。

## volume卷

类似于系统上的u盘，指的是一片磁盘空间，这片空间存储与docker容器之外的地方，通过挂载的形式让docker容器使用。主要目的是持久化某些数据，比如数据库。

## Dockerfile

是用来构建docker镜像的文件，里面包含一系列的指令，通过`docker build`命令来构建镜像。

## tag镜像标签

用于表示处于某一个版本的镜像。在镜像版本迭代的过程中，会出现很多个不同版本，这时候就可以给某一个稳定的版本打上tag，以便于直接拉取到此版本。

## docker一些特性

### 容器关闭机制

如果容器中没有任何程序在前台运行，则会自动关闭该容器。比如某容器使用`java -jar xxx`运行，但是该java进程因为某种原因结束了，则容器会自动停止。

## docker常用命令

### 构建镜像

`docker build -t demo/demo:0.1 .` 使用当前目录的Dockerfile构建镜像，镜像命名为*demo/demo*并打一个*0.1*的tag  

### 打镜像标签

`docker tag [镜像ID] demo/demo:first` 给*demo/demo*这个镜像打一个*first*的tag

### 删除镜像

`docker rmi [镜像ID]` 删除某id的镜像，只能删除没有容器正在运行的镜像

### 启动容器并进入容器

`docker run -it demo/demo:0.1 /bin/bash` 启动镜像*demo/demo:1.0*，并使用bash进入这个容器。*-i*参数是交互式操作，*-t* 参数是使用终端。如果用了 *-it*参数，则不会执行Dockerfile中的CMD的指令

### 后台启动容器

`docker run -d demo/demo:0.1` 在后台启动镜像*demo/demo:0.1*，并执行Dockerfile中的CMD的指令

### 查看运行中的容器

`docker ps`

### 查看所有容器

`docker ps -a` 查看所有容器，包括停止的容器，在删除镜像前必须得删除所有容器。

### 启动容器

`docker start [容器ID]`启动一个关闭的容器，启动的时候会执行Dockerfile中的所有CMD的指令。

### 删除容器

`docker rm [容器ID]`删除某个容器，容器id可以只输前面几位，只要保证能唯一定位到一个容器即可。例如：容器id为*abc123*，可以使用`docker rm a`来删除该容器，若还有一个容器以a开头，则不会删除，需要使用`docker rm ab`来删除该容器。


