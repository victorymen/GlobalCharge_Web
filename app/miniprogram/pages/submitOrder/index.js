import globeApi from '../api/globeApi'
import { compareSDKVersion, checkPaymentAPI } from '../utils/systemUtils' // 提取工具函数

Page({
    data: {
        paymentParams: {
            // 集中管理支付参数
            offerId: '123',
            buyQuantity: 1,
            env: 1,
            currencyType: 'CNY',
            productId: 'testproductId',
            goodsPrice: 10,
            outTradeNo: 'xxxxxx',
            attach: 'testdata',
        },
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
                        openid: wx.getStorageSync('userInfo').openid,
                        userpaystate: '未支付',
                        sernoberstate: '待付款',
                    },
                    'paymentParams.goodsPrice': fromItem.selling_price, // 动态设置价格
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
        wx.showLoading({ title: '保存中...' })
        const items = await globeApi.productsUser({ ...this.data.fromItem, ordertime: new Date() ,countryid:this.data.fromItem.countryId})
        wx.hideLoading()
        if (items) {
            wx.navigateBack({ delta: 1 })
        }

        // if (!this.checkPaymentAvailability()) return
        // try {
        //   const paymentResult = await this.handleVirtualPayment()
        //   await this.handlePaymentSuccess(paymentResult)
        // } catch (error) {
        //   this.handlePaymentError(error)
        // }
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

    // 处理虚拟支付
    handleVirtualPayment() {
        return new Promise((resolve, reject) => {
            wx.requestVirtualPayment({
                ...this.data.paymentParams,
                signData: JSON.stringify(this.data.paymentParams),
                paySig: this.generatePaySignature(),
                signature: this.generatePaySignature(),
                mode: 'short_series_goods',
                success: resolve,
                fail: reject,
            })
        })
    },

    // 支付成功处理
    async handlePaymentSuccess(res) {
        console.log('支付成功', res)
        try {
            await productApi.productUserSave({
                ...this.data.fromItem,
                prodType: 1,
            })
            wx.showToast({ title: '支付并保存成功' })
        } catch (e) {
            console.error('保存失败', e)
            wx.showToast({ title: '支付成功但保存失败', icon: 'none' })
        }
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

    // 生成支付签名（示例）
    generatePaySignature() {
        // 实际项目应调用安全接口生成签名
        return 'd0b8bbccbe109b11549bcfd6602b08711f46600965253a949cd6a2b895152f9d'
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
