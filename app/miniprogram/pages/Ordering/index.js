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
      { title: '已取消', status: 'canceled' }
    ],
    orders: []
  },

  // 状态文本过滤器
  statusText: {
    completed: '已完成',
    pending: '待支付',
    canceled: '已取消'
  },

  onLoad() {
    this.loadOrders();
  },

  async loadOrders() {
    try {
      const res = (await productApi.productUserGet()).records;
      console.log(res.records);
      this.setData({ 
        orders: res.map(item => ({
          ...item,
          ordertime: this.formatTime(item.ordertime)
        })),
        loading: false
      });
      console.log(this.data.orders);
    } catch (e) {
      wx.showToast({ title: '加载失败', icon: 'none' });
      this.setData({ loading: false });
    }
  },

  // 订单过滤方法
  filterOrders(status) {
    console.log(status);
    return status === 'all' 
      ? this.data.orders 
      : this.data.orders.filter(o => o.status === status);
  },

  // 时间格式化
  formatTime(timestamp) {
    const date = new Date(timestamp);
    return `${date.getFullYear()}-${date.getMonth()+1}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}`;
  },

  onTabChange(e) {
    this.setData({ active: e.detail.index });
  }
})
