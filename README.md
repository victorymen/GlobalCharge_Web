类别	条目	优先级	责任人	进展
运营主体准备	微信小程序账号注册		庸文/门永亮	
	商户号注册（需要营业 资质）	⭐⭐⭐⭐⭐	庸文/门永亮	
	云服务器购买，环境搭建	⭐⭐⭐	门永亮	
关键阻塞点准备	获取国际银行卡在线支付能力资料	⭐⭐⭐⭐⭐	门永亮	
	获取运营商充值接口	⭐⭐⭐⭐⭐	门永亮	
开发流程	小程序功能页面搭建	⭐⭐⭐⭐	张晨阳	
	支付功能实现	⭐⭐⭐⭐	张晨阳	
	后端获取充值信息	⭐⭐⭐⭐	张晨阳	
	后端手机号校验	⭐⭐⭐⭐	张晨阳	
	调用国外银行卡账号付款	⭐⭐⭐⭐	张晨阳/门永亮	
	调用运营商接口充值确认	⭐⭐⭐⭐	张晨阳/门永亮	
	充值异常退款处理	⭐⭐⭐⭐	张晨阳	
	管理后台开发（前期不需要，直接数据库运维）	⭐	张晨阳	
	安全保护	⭐	门永亮	
运营推广	摩洛哥本地中企		庸文	
![image](https://github.com/user-attachments/assets/b5646189-b154-4403-b69f-c034ae44a100)

# 1. Chargelink
全球华人微信小程序充值平台

## 2. UI设计

**1. “充值”页**

**2. “订单”页**

## 2. 功能模块设计
**充值功能逻辑**
![image](https://github.com/user-attachments/assets/7777c4db-8d7c-41f3-a211-1f5e33547d92)

**历史订单查看逻辑**
充值成功后，个人id，充值金额，充值运营商，充值日期等数据入库，订单页查询数据库呈现个人信息。
### 2.1 国家地区选择
**描述**

**接口信息**
- **URL**: GET /api/v1/countrys/
- **请求头**:
  - Authorization: Bearer <token>
- **响应示例**:
  ```json
  [
   {
    "id": 1,
    "countryId": "+212",
    "countryName": "摩洛哥"
   }
  ]
### 2.2 运营商&充值套餐选择
**描述**

**接口信息**
- **URL**: GET /api/v1/operator/{id}
- **请求头**:
  - Authorization: Bearer <token>
- **响应示例**:
  ```json
  {
    "id": 1,
    "operatorName": "morocco telecom",
    "package": [20,30,50,100,200,300,500]
    "rate": 0.05
  }
### 2.2 充值接口
**描述**

**接口信息**
- **URL**: POST /api/v1/users/{id}
- **请求头**:
  - Authorization: Bearer <token>
- **响应示例**:
  ```json
  {
    "id": 123,
    "name": "John",
    "email": "john@example.com"
  }
