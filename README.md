## 客户端接口文档##

[TOC]

#### 接口说明 **1、**heart

- **请求URL**
> [api/v1/heart](#)

- **请求方式** 
>**GET**

- **请求参数**
> | 请求参数      |     参数类型 |   参数说明   |
| :-------- | :--------| :------ |
| device_no|   String,不可为空|  设备唯一标识，每个接口需一致|
| ip|   String,不可为空|  设备所处IP号 多IP用逗号隔开|
| cpu|   String,不可为空|  CPU号码|
| ram|   String,不可为空|  RAM号码|
| os|   String,不可为空|  设备系统|
| name|   String,不可为空|  设备登陆用户|



- **返回参数**
> | 返回参数      |     参数类型 |   参数说明   |
| :-------- | :--------| :------ |
| code|   Integer|  执行结果code|
| message|   String|  执行结果消息|
| update| String| 升级地址 若返回 则终端需从该地址下载最新版本|
| create_socket|Boolean|是否建立长连接

- **返回示例**
>    
```java 
{
  "code": 0,
  "message": "成功"
  "update": "http://localhost:8080/a.exe"
}
```

