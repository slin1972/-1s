## 客户端接口文档 ##

[TOC]

#### 接口说明 1、heart 

- **请求URL**
> [api/v1/heart](#)

- **请求方式** 
>**POST**

- **请求参数**

| 请求参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| device_no|   String,不可为空|  设备唯一标识，每个接口需一致| 
| ip|   String,不可为空|  设备所处IP号 多IP用逗号隔开| 
| cpu|   String,不可为空|  CPU号码| 
| ram|   String,不可为空|  RAM号码| 
| os|   String,不可为空|  设备系统| 
| av|   String,不可为空|  当前客户端版本号| 
| name|   String,不可为空|  设备登陆用户| 
| seq|   String,不可为空|  心跳包计数| 

- **请求示例**
>    
```shell 
curl 'http(s)://<host>/api/v1/heart' -d '{ "device_no" : "123456", "ip": "192.168.0.1",  "cpu": "123456", "ram": "123456", "os": "windowxp", "name": "administor"}' -H 'Content-Type:application/json'
```

- **返回参数**

| 返回参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| code|   Integer|  执行结果code| 
| message|   String|  执行结果消息| 
| update| String| 升级地址 若返回 则终端需从该地址下载最新版本| 
| script| String|脚本指令 暂时先用心跳seq作为唯一标示

- **返回示例**
>    
```java 
{
  "code": 0,
  "message": "成功",
  "update": "http://localhost:8080/a.exe",
  "create_socket": false
}
```

#### 接口说明 2、upload 

- **请求URL**
> [api/v1/upload](#)

- **请求方式** 
>**POST**

- **请求参数**

| 请求参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| device_no|   String,不可为空|  设备唯一标识，每个接口需一致| 
| n|   String,不可为空|  文件名| 
| v|   String,不可为空|  文件内容| 

- **请求示例**
>    
```shell 
curl 'http(s)://<host>/api/v1/upload' -d 'device_no=123456&n=xxx.jpg&v=asfdoasdfiosadfjosadifjdasjofsaofjosadjiofasidf'
```

- **返回参数**

| 返回参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| code|   Integer|  执行结果code| 
| message|   String|  执行结果消息| 

- **返回示例**
>    
```java 
{
  "code": 0,
  "message": "成功"
}
```
#### 接口说明 3、script_response

- **请求URL**
> [api/v1/script_response](#)

- **请求方式** 
>**POST**

- **请求参数**

| 请求参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| device_no|   String,不可为空|  设备唯一标识，每个接口需一致| 
| seq|   String,不可为空|  指令序列号| 
| content|   String,不可为空|  返回内容| 

- **请求示例**
>    
```shell 
curl 'http(s)://<host>/api/v1/script_response' -d '{"device_no": "12345", "seq": "123", "content": "123123213213213"}'
```

- **返回参数**

| 返回参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| code|   Integer|  执行结果code| 
| message|   String|  执行结果消息| 

- **返回示例**
>    
```java 
{
  "code": 0,
  "message": "成功"
}
```


