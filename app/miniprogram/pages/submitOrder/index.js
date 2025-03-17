import productApi from '../api/productApi'
import wxApi from '../api/wxApi'
import { compareSDKVersion, checkPaymentAPI } from '../utils/systemUtils' // 提取工具函数

Page({
    data: {
        isShowBut: false,
        UNPAID:'待支付',
        CHARGEING:'充值中',
        PAID:'已支付',
        DELIVERING:'发货中',
        COMPLETED:'已完成',
        CANCELLED:'已取消',
    },

    onLoad(options) {
        this.initSystemInfo()
        this.handlePageParams(options)
    },

    // 初始化系统信息
    initSystemInfo() {
        try {
            const { SDKVersion } = wx.getSystemInfoSync()
            this.sdkVersion = SDKVersion // 改为局部变量存储
        } catch (e) {
            console.error('获取系统信息失败', e)
            wx.showToast({ title: '系统信息获取失败', icon: 'none' })
        }
    },

    // 处理页面参数
    handlePageParams(options) {
        if (options.params) {
            try {
                const fromItem = JSON.parse(decodeURIComponent(options.params))
                this.setData({
                    fromItem: {
                        proid: fromItem.id,
                        ...fromItem,
                        openid: wx.getStorageSync('userInfo').openid
                    },
                    'paymentParams.goodsPrice': fromItem.selling_price, // 动态设置价格
                    isShowBut: true,
                })
                delete this.data.fromItem.id
                console.log(this.data)
            } catch (e) {
                console.error('参数解析失败', e)
                wx.showToast({ title: '参数错误', icon: 'none' })
            }
        }
    },

    async onClickButton() {
        // wx.sendSms({
        //     phoneNumber:"15102994475",
        //     content:'卡密 QADQWFQFQWEQRWEWEGWG',
        //     success:(res)=>{console.log(123)},
        //     fail:(err)=>{console.log(12313)}
        // })

        // wx.showLoading({ title: '保存中...' })
        // const items = await globeApi.productsUser({ ...this.data.fromItem, ordertime: new Date() ,countryid:this.data.fromItem.countryId,typename:this.data.fromItem.typeName})
        // wx.hideLoading()
        // if (items) {
        //     wx.navigateBack({ delta: 1 })
        // }
        console.log(this.data)

        // 获取当前时间
        const now = new Date()
        // 增加30分钟
        const expireTime = new Date(now.getTime() + 30 * 60000)
        // 格式化时间为 yyyyMMddHHmmss
        const formattedExpireTime = expireTime.toISOString().replace(/[-:T]/g, '').slice(0, 14)
        // 生成商户订单号
        const outTradeNo = this.generateOutTradeNo()
        // await this.handlePaymentSuccess({outTradeNo:outTradeNo})
        try {
            const prepayResult = await wxApi.prepayWithRequestPayment({
                description: this.data.fromItem.cname + '-' + this.data.fromItem.typeName + '-' + this.data.fromItem.ename + '-' + this.data.fromItem.title, // 商品描述
                outTradeNo: outTradeNo, // 商户订单号
                time_expire: formattedExpireTime,
                notifyUrl: 'https://wechatglow.online/api/wxApi/closeOrderTradeNo', // 支付成功回调地址
                // 金额信息
                amount: { total: 1, currency: 'CNY' },
                // 支付者信息
                payer: { openid: this.data.fromItem.openid },
            })
            if (!this.checkPaymentAvailability()) return
            // 调用微信支付
            const paymentResult = await this.handleWechatPayment(prepayResult)
            await this.handlePaymentSuccess(paymentResult)
        } catch (error) {
            await productApi.productUserSave({ ...this.data.fromItem,ordertime: new Date(),sernoberstate:'UNPAID', outTradeNo: outTradeNo })
            await wxApi.closeOrder({ outTradeNo: outTradeNo })
            this.handlePaymentError(error)
        }
    },
    // 生成商户订单号
    generateOutTradeNo() {
        const timestamp = Date.now()
        const randomNum = Math.floor(Math.random() * 10000)
        return `WXPAY${timestamp}${randomNum}`
    },
    // 检查支付能力
    checkPaymentAvailability() {
        const { sdkVersion } = this
        const isAvailable = checkPaymentAPI(sdkVersion)
        if (!isAvailable) {
            wx.showToast({
                title: '当前版本不支持支付功能',
                icon: 'none',
                duration: 2000,
            })
            return false
        }
        return true
    },
    // 处理微信支付
    handleWechatPayment(prepayResult) {
        console.log('prepayResult', prepayResult)
        return new Promise((resolve, reject) => {
            // 确保所有参数都是字符串
            const paymentParams = {
                timeStamp: prepayResult.timeStamp,
                nonceStr: prepayResult.nonceStr,
                package: prepayResult.packageVal,
                signType: prepayResult.signType,
                paySign: prepayResult.paySign,
            }

            // 检查必要参数是否为空
            if (!paymentParams.timeStamp || !paymentParams.nonceStr || !paymentParams.package || !paymentParams.paySign) {
                reject({ errMsg: '支付参数不完整' })
                return
            }
            wx.requestPayment({
                ...paymentParams,
                success: resolve,
                fail: reject,
            })
        })
    },

    // 支付成功处理
    async handlePaymentSuccess(res) {
        try {
          const items=  await productApi.productUserSave({ ...this.data.fromItem,ordertime: new Date(), outTradeNo: res.outTradeNo })
            wx.showToast({ title: '支付并保存成功' })
            this.setData({ isShowBut: false, cardpassword: items})
        } catch (e) {
            console.error('保存失败', e)
            wx.showToast({ title: '支付成功但保存失败', icon: 'none' })
        }
    },
    onClickOpend(){
         wx.sendSms({
            phoneNumber:"15102994475",
            content:this.data.cardpassword,
            success:(res)=>{},
            fail:(err)=>{}
        })
    },
    // 支付失败处理
    handlePaymentError(error) {
        console.error('支付失败', error)
        const errorMsg = this.getErrorMessage(error.errCode)
        wx.showToast({
            title: errorMsg || '支付失败',
            icon: 'none',
            duration: 2000,
        })
    },

    // 错误码映射
    getErrorMessage(code) {
        const errorMap = {
            1001: '用户取消支付',
            1002: '网络连接失败',
            1003: '支付系统繁忙',
        }
        return errorMap[code] || null
    },
})
