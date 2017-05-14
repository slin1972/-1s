## 主控端接口文档 ##

#### 发送指令 1、script 

- **请求URL**
> [api/v1/script?token=<token>](#)

- **请求方式** 
>**POST**

- **请求参数**

| 请求参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| token|   String,不可为空|  用户token, 由api/v1/login 获得, 测试token master|
| device_no|   String,不可为空|  设备唯一标识，每个接口需一致| 
| script|   String,不可为空|  screen, uploadFile, downloadFile, dir ...| 
- **请求示例**
>    
```shell 
curl 'http(s)://<host>/api/v1/script?token=master' -d '{"token": "master", "device_no": "12345", "script": "screen"}'
```

- **返回参数**

| 返回参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| code|   Integer|  执行结果code| 
| message|   String|  执行结果消息|
| data.id |Integer| 脚本 id|

- **返回示例**
>    
```java 
{
  "code": 0,
  "message": "成功",
  "data": {
    "id": 1
  }
}
```

#### 获取指令结果 2、script_status

- **请求URL**
> [api/v1/script_status?token=<token>](#)

- **请求方式** 
>**GET**

- **请求参数**

| 请求参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| token|   String,不可为空|  用户token, 由api/v1/login 获得, 测试token master|
| script_id|   Integer,不可为空|  脚本id| 
- **请求示例**
>    
```shell 
curl 'http(s)://<host>/api/v1/script_status?token=master&script_id=1'
```

- **返回参数**

| 返回参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| code|   Integer|  执行结果code| 
| message|   String|  执行结果消息|
| data.script |int| 脚本结果|

- **返回示例**
>    
```java 
{
  "code": 0,
  "message": "成功",
  "data": {
    "script": {
      "id": 1,
      "result": "xxxxxxxx"
    }
  }
}
```


#### 被控端列表 3、controlled_list

- **请求URL**
> [api/v1/controlled_list?token=<token>](#)

- **请求方式** 
>**GET**

- **请求参数**

| 请求参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| token|   String,不可为空|  用户token, 由api/v1/login 获得, 测试token master|
- **请求示例**
>    
```shell 
curl 'http(s)://<host>/api/v1/stat?token=master&script_id=1'
```

- **返回参数**

| 返回参数      |     参数类型 |   参数说明   | 
| :-------- | :--------| :------ | 
| code|   Integer|  执行结果code| 
| message|   String|  执行结果消息|
| data.controlledList |int| 脚本结果|

- **返回示例**
>    
```java 
{
  "code": 0,
  "message": "成功",
  "data": {
    "controlledList": [{
      "device_no": "12345",
      "others": "其他参数见[3. 数据库设计](https://github.com/slin1972/-1s/blob/master/doc/数据库设计.md) "
    }]
  }
