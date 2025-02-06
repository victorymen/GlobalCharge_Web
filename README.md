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
