import globeApi from '../api/globeApi'

const throttle = (fn, delay) => {
    let lastCall = 0
    return function (...args) {
        const now = Date.now()
        if (now - lastCall < delay) return
        lastCall = now
        return fn.apply(this, args)
    }
}

Page({
    data: {
        showHistory: false,
        historyNumbers: ['13800138000', '13900139000', '13700137000'], // 假设这是历史充值手机号列表
        searchValue: '',
        filteredProductO: [],
        notice: '平台不会主动联系您，请不要给陌生人充值话费，充值失败请在订单记录申请退款。',
        show: false,
        selectedTabIndex: 0,
        selectedOperatorIndex: 0,
        selectedOperatorIndex2: 0,
        fromItem: {},
        loading: true,
        error: null,
    },

    // 手动更新 fromItem.phone 的值
    onChangePhone(event) {
        this.safeSetData({
            'fromItem.rechargeNo': event.detail,
        })
    },

    onTabChange(e) {
        const { index } = e.currentTarget.dataset
        this.safeSetData({
            reType: index,
            yysType: 0,
            proType: 0,
        })
    },

    // 选中列表项事件
    async onSelectItem(e) {
        const { item } = e.currentTarget.dataset
        this.safeSetData(
            {
                fromItem: { ...this.data.fromItem, ...item },
                show: !this.data.show,
            },
            () => this.fetchDataSelect(item)
        )
    },

    async fetchDataSelect(item) {
        try {
            const prodicts = await globeApi.products({ countryid: item.id })
            this.safeSetData({ ...prodicts })
        } catch (e) {
            wx.showToast({ title: '数据加载失败，请重试', icon: 'none' })
        }
    },

    onLoad: async function (options) {
   const openid= wx.getStorageSync('userInfo').openid
        try {
            const productO = await globeApi.countries({})
            const rechargeNo = await globeApi.rechargeNo({openid:openid})
            console.log('productO', productO)
            if (!productO?.length) throw new Error('无可用国家数据')
            this.safeSetData(
                {
                    productO,
                    filteredProductO: productO,
                    fromItem: { ...productO[0] },
                    loading: false,
                },
                () => this.fetchDataSelect(productO[0])
            )
        } catch (e) {
            this.safeSetData({
                error: e.message,
                loading: false,
            })
            wx.showToast({ title: '初始化失败', icon: 'none' })
        }
    },

    //运营商选定
    onSelectOperator(e) {
        const { index } = e.currentTarget.dataset
        this.safeSetData({
            yysType: index,
            proType: 0,
        })
    },

    //套餐选定
    onSelectOperator2: throttle(function (e) {
        const { index, item } = e.currentTarget.dataset
        if (!this.data.fromItem.rechargeNo?.trim()) {
            wx.vibrateShort()
            return wx.showToast({
                title: '请输入充值号码',
                icon: 'none',
                duration: 2000,
            })
        }

        this.safeSetData(
            {
                proType: index,
                fromItem: { ...this.data.fromItem, ...item },
            },
            () => {
                const params = encodeURIComponent(
                    JSON.stringify({
                        ...this.data.fromItem,
                        _t: Date.now(),
                    })
                )
                wx.navigateTo({
                    url: `/pages/submitOrder/index?params=${params}`,
                })
            }
        )
    }, 500),

    //打开/关闭 国家搜素
    popupClick() {
        this.safeSetData({ show: !this.data.show })
    },

    // 监听搜索框输入
    onSearchChange(e) {
        const keyword = e.detail.trim().toLowerCase()
        this.safeSetData({ searchValue: keyword }, () => {
            this.filterProductO(keyword)
        })
    },

    // 过滤数据方法
    filterProductO(keyword) {
        const filtered = keyword ? this.data.productO.filter((item) => `${item.cname}${item.ename}`.toLowerCase().includes(keyword)) : this.data.productO

        this.safeSetData({ filteredProductO: filtered })
    },

    async onShow() {
        if (this.data.productO?.[0]) {
            // await this.fetchDataSelect(this.data.productO[0])
            // this.safeSetData({  fromItem: { ...this.data.productO[0] } })
        }
    },

    // 新增安全数据更新方法
    safeSetData(update, callback) {
        this.setData(update, () => {
            callback?.()
        })
    },
})
