# SpringWeb开发环境搭建文档(windows版)



## 1、JDK下载安装及环境变量配置

### 1.1 JDK简介

JDK(Java SE Development kit Java开发工具包)，即java标准版开发包，是Oracle提供的一套用于开发java应用程序的开发包，它提供编译，运行java程序所需要的各种工具和资源，包括java编译器，java运行时环境，以及常用的java类库等。

### 1.2 JDK下载

点击[下载地址](https://www.oracle.com/java/technologies/javase-downloads.html)，根据系统的版本下载相对应的JDK

![点击JDK Download](etc/images/JDK_download_1.png)



![image-20210829143854382](etc/images/JDK_download_2.png)



### 1.3 安装JDK&配置环境变量

下载好JDK，根据安装指引安装到自己所需路径即可。接下来开始配置**环境变量**

1、右键我的电脑——属性——高级系统设置，进入如下界面，选择环境变量

![image-20210829145240632](etc/images/JDK_3.png)

​																												              【环境变量】



2、点击**系统变量**下的新建按钮，变量名JAVA_HOME（代表你的JDK安装路径），值是你的JDK安装路径。

![image-20210829150039238](etc/images/JDK_4.png)

​																												【新建系统变量JAV_HOME 】

3、然后在系统变量中继续新建一个CLASSPASH变量，值为

```shell
 .;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;
```

![image-20210829145938956](etc/images/JDK_5.png)

​                                                                                                          【新建系统变量CLASS_PATH 】

4、在你的系统变量里面找一个变量名是PATH的变量，需要在它的值域里面追加一段如下的代码

```
%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;
```

![](etc/images/JDK_6.png)

5、测试环境变量是否正确配置，WINDOWS+R,输入cmd打开命令行界面，输入 java -version，可以查看自己所装JDK版本，如果如下图所示，则表示安装正确。

![image-20210829150501426](etc/images/JDK_7.png)





## 2、IDEA下载安装及使用

IDEA 在业界被公认为最好的Java开发工具之一，接下来我们安装IDEA

### 2.1 下载IDEA

点击[下载地址](https://www.jetbrains.com/zh-cn/idea/download/)选择Ultimate版本

![image-20210829151304765](etc/images/IDEA_1.png)

​																							【下载IDEA】

### 2.2 安装IDEA

![image-20210829151545582](etc/images/IDEA_2.png)

![image-20210829151735288](etc/images/IDEA_3.png)

![image-20210829151932317](etc/images/IDEA_4.png)

![image-20210829152001886](etc/images/IDEA_5.png)

![image-20210829152529492](etc/images/IDEA_6.png)





## 3、maven下载安装及使用

maven是 Apache软件基金会组织维护的一款自动化 构建工具，专注服务于 Java 平台的项目构建 ，帮助开发者进行**项目构建和依赖管理**

### 3.1 maven下载

1、点击[下载链接]([Maven – Download Apache Maven](https://maven.apache.org/download.cgi))，选择左侧Download

![image-20210829152832669](etc/images/MAVEN_1.png)

2、点击如下链接进行下载

![image-20210829152944531](etc/images/MAVEN_2.png)

### 3.2 maven安装及环境变量配置

1、下载完成后，选择一个位置进行解压即可，然后按照上述创建JAVA_HOME的方式创建MAVEN_HOME,值为maven解压的路径

2、在path中追加如下内容

```
%MAVEN_HOME%\bin
```

3、WINDOWS+R，输入cmd，打开命令行，输入mvn -version ，如图所示则成功

![image-20210829162549602](etc/images/MAVEN_3.png)

## 4、spring-web-demo
### 4.1 clone项目
到此为止，我们已经完成了所有的环境准备，接下来导入我们的模板项目。

依次点击 File->New->Project from Version control
![img.png](etc/images/DEMO_1.png)
输入以下URL

```
https://gitlab.tapd.cn/javierjin/spring-web-demo.git
```
点击clone即可看到项目成功导入。

### 4.2 连接数据库

MyBatis 是一款优秀的持久层框架，它支持定制化 SQL、存储过程以及高级映射。MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。下面我们将介绍如何通过mybatis连接并操作数据库

 **Step1：pom文件中引入依赖**

```c
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.4</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
```

**Step2：填写配置文件**

与pom.xml文件同一级目录下创建 application.yaml 文件，填入以下配置信息

（将数据库名、端口号、用户名和密码换成自己的）

```c
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://127.0.0.1:3306/testdatabase
    driver-class-name: com.mysql.cj.jdbc.Driver
 
mybatis:
  type-aliases-package: com.example.aliases
```

### 4.3 demo

通过刚刚的配置我们已经成功连接了数据库，接下来通过一个简单的demo，来测试一下，项目整体结构如下

![image-20210916162501615](etc/images/DEMO_2.png)

**Step0：创建测试数据库表**

首先在数据库中准备一张student的表，先简单存放username和password

**Step1：编写实体类**

```Java
package com.example.demo.entity;

public class User {
    private Integer id;
    private String userName;
    private String passWord;
   
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}

```

**Step2：编写mapper**

```java
package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {

    User getUser(int id);
}
```

**Step3：编写UserMapper.xml文件**

```java
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.example.demo.mapper.UserMapper">

    <resultMap id="BaseResultMap" type="com.example.demo.entity.User">
        <result column="id" jdbcType="INTEGER" property="id" />
        <result column="userName" jdbcType="VARCHAR" property="userName" />
        <result column="passWord" jdbcType="VARCHAR" property="passWord" />
        <result column="realName" jdbcType="VARCHAR" property="realName" />
    </resultMap>

    <select id="getUser" resultType="com.example.demo.entity.User">
        select * from user where id = #{id}
    </select>

</mapper>
```

**Step4: 编写Service**

```java
package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public User getUser(int id){
        return userMapper.getUser(id);
    }
}
```

**Step5：编写Controller**

```java
package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testBoot/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("getUser/{id}")
    public String getUser(@PathVariable int id){
        return userService.getUser(id).toString();
    }
}
```

**Step6：启动类加上MapperScan注解**

```java
package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.example.demo.mapper")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

> 注意
>
> <mapper namespace="com.example.demo.mapper.UserMapper"> 要与UserMapper类路径一模一样
>
> <resultMap id="BaseResultMap" type="com.example.demo.entity.User">要与User类路径一模一样

## 5、代理配置

接下来介绍一下 http://gitlab.tapd.cn 的代理配置方法

### 5.1 代理配置

打开网络和Internet设置，按照下图所示步骤操作，即可访问http://gitlab.tapd.cn

![image-20210916192246964](etc/images/AGENCY_1.png)

设置完成后即可访问 https://gitlab.tapd.cn/javierjin/spring-web-demo

### 5.2 代码提交

提交代码时还需要在终端设置代理，操作流程如下：

```
//使用HTTPS：在终端执行下列指令
git config —global http.http://gitlab.tapd.cn.proxy "http://106.52.214.51:21869"
git config —global http.https://gitlab.tapd.cn.proxy "https://106.52.214.51:21869"
//使用SSH：在此地址(~/.ssh/config)的文件添加如下信息(没有就新建)
Host gitlab.tapd.cn
    HostName gitlab.tapd.cn
    User git
    Port 22
    IdentityFile ~/.ssh/javier-id_rsa
    ProxyCommand nc -X connect -x 106.52.214.51:21869 %h %p
```

然后按照git使用方式提交代码即可。