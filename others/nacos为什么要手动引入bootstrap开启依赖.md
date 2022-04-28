# nacos配置中心为什么要手动引入bootstrap

从spring2.4之后需要手动开启bootstrap文档，可以通过增加spring-cloud-starter-bootstrap依赖来完成。  
查看依赖链发现nacos的pom中有依赖spring-cloud-starter-bootstrap，但是有一个`<optional>true</optional>`，所以没有在我们的项目中生效这个依赖，需要我们手动加入。