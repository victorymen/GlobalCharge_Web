import productApi from '../api/productApi'
Page({
    data: {
        active: 0,
        loading: true,
        tabList: [
            { title: '全部', status: 'all' },
            { title: '待支付', status: 'pending' },
            { title: '待处理', status: 'processing' },
            { title: '已完成', status: 'completed' },
            { title: '已取消', status: 'canceled' },
        ],
        orders: [],
    },

    // 状态文本过滤器
    statusText: {
        completed: '已完成',
        pending: '待支付',
        canceled: '已取消',
    },

    onLoad(e) {
      e.active = parseInt(e.active, 10) || 0 // 页面加载时默认显示全部订单
      this.setData({ active: e.active,  ...wx.getStorageSync('userInfo') })
      this.loadOrders(e.active)
      console.log(this.data)
    },

    async loadOrders(tabIndex) {
      try {
        const { title } = this.data.tabList[tabIndex] // 使用预定义的status值代替中文判断
        const params = title === '全部' ? {} : { sernoberstate:title } // 使用接口标准参数名
        const { records } = await productApi.productUserGet({openid:this.data.openid,...params})
        this.setOrdersData(records)
    } catch (e) {
        wx.showToast({ title: '切换失败', icon: 'none' })
        this.setData({ loading: false })
    }
    },

    // 订单过滤方法
    filterOrders(status) {
        console.log(status)
        return status === 'all' ? this.data.orders : this.data.orders.filter((o) => o.status === status)
    },

    // 时间格式化
    // 时间格式化优化（补零处理）
    formatTime(timestamp) {
        const date = new Date(timestamp)
        const pad = (n) => n.toString().padStart(2, '0')
        return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}`
    },

    async onTabChange(e) {
      this.loadOrders(e.detail.index)
    },
    // 新增公共方法处理订单数据
    setOrdersData(records) {
        this.setData({
            orders: records.map((item) => ({
                ...item,
                ordertime: this.formatTime(item.ordertime),
            })),
            loading: false,
        })
    },

    filterOrders(status) {
      console.log(records)
      if (!this.data.orders || this.data.orders.length === 0) return [];
      return status === 'all' 
        ? this.data.orders 
        : this.data.orders.filter(o => o.status === status);
  }
})
