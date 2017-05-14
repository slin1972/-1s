## Getting Started
```shell 
git clone  --depth=1 https://github.com/slin1972/-1s.git  #克隆仓库到本地 
```
> 1.[Java 服务器](https://github.com/slin1972/-1s/tree/master/server/java/app-server) 

> 用IntelliJ IDEA 打开当前目录 

> 修改plication.yml配置 

>  url: jdbc:mysql://120.25.146.138:33060/db_langrensha?useUnicode=true&characterEncoding=utf8 #line: 21   数据库地址 

>2.[C#被控端](https://github.com/slin1972/-1s/tree/master/controlled/c%23/controlled) 

> 直接从vs打开解决方案 

> 修改Core.cs 代码  

> public static string ROOT_URL = "http://localhost:8089/api/v1"; //服务器地址 

It will be work. 

如果你想调试主控端接口，那么你可通过[curl](https://curl.haxx.se/download.html) 测试，或者下载[Postman](https://www.getpostman.com/)测试

## 路径说明

>controlled 被控端相关

>master 主控端相关

>db 数据库相关


## 接口文档 

>[1. 被控端接口](https://github.com/slin1972/-1s/blob/master/doc/被控端API.md) 

>[2. 主控端接口](https://github.com/slin1972/-1s/blob/master/doc/主控端API.md) 

>[3. 数据库设计](https://github.com/slin1972/-1s/blob/master/doc/数据库设计.md) 

