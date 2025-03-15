import productApi from '../api/productApi'
import globeApi from '../api/globeApi'
Page({
    data: {
        active: 0,
        loading: true,
        ItemKey:{
        UNPAID:'待支付',
        CHARGEING:'充值中',
        PAID:'已支付',
        DELIVERING:'发货中',
        COMPLETED:'已完成',
        CANCELLED:'已取消',
      },
        tabList: [
            { title: '全部', status: 'all' },
            { title: '待支付', status: 'UNPAID' },
            { title: '待处理', status: 'processing' },
            { title: '已完成', status: 'COMPLETED' },
            { title: '已取消', status: 'CANCELLED' },
        ],
        orders: [],
    },


    onLoad(e) {
      e.active = parseInt(e.active, 10) || 0 // 页面加载时默认显示全部订单
      this.setData({ active: e.active,  ...wx.getStorageSync('userInfo') })
      this.loadOrders(e.active)
    },

    async loadOrders(tabIndex) {
      try {
        const { status } = this.data.tabList[tabIndex] // 使用预定义的status值代替中文判断
        const params = status === 'all' ? {} : { sernoberstate:status } // 使用接口标准参数名
        const { records } = await productApi.productUserGet({openid:this.data.openid,...params})
        this.setOrdersData(records)
    } catch (e) {
        wx.showToast({ title: '切换失败', icon: 'none' })
        this.setData({ loading: false })
    }
    },

    // 订单过滤方法
    filterOrders(status) {
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
            orders: records.map((item) => ({  ...item,  ordertime: this.formatTime(item.ordertime),  })),
            loading: false,
        })
    },

    filterOrders(status) {
      console.log(records)
      if (!this.data.orders || this.data.orders.length === 0) return [];
      return status === 'all' 
        ? this.data.orders 
        : this.data.orders.filter(o => o.status === status);
  },
  async onTabChangeQX(e){
    const { item } = e.currentTarget.dataset
     await globeApi.productsUserUpdate({...item,ordertime:new Date(),sernoberstate:'已取消'})
     await this.loadOrders(this.data.active) 
  },
  onTabChangeFK(e){
    const { item } = e.currentTarget.dataset
      console.log(item)
  },
  onTabChangeXQ(e){
    const { item } = e.currentTarget.dataset
      console.log(item)
  }
})
