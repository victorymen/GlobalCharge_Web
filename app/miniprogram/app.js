import userApi from '/pages/api/userApi'
App({
    onLaunch: function () {
        if (!wx.cloud) {
            console.error('请使用 2.2.3 或以上的基础库以使用云能力')
        } else {
            wx.cloud.init({
                // env 参数说明：
                //   env 参数决定接下来小程序发起的云开发调用（wx.cloud.xxx）会默认请求到哪个云环境的资源
                //   此处请填入环境 ID, 环境 ID 可打开云控制台查看
                //   如不填则使用默认环境（第一个创建的环境）
                env: '',
                traceUser: true,
            })
        }
       this.onLoginClick()
  
    },
    towxml:require('/towxml/index'),
    onError(err) {
        console.error('全局错误捕获:', err)
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: async function (options) {
        this.onLoginClick()
    },
    //登录获取用户oppid
    onLoginClick() {
        wx.login({
            success: async (res) => {
                if (res.code) {
                    const items = await userApi.loginInfo({ code: res.code })
                    wx.setStorageSync('userInfo',items);
                    wx.setStorageSync('token',items.session_key);
                } else {
                    console.error('登录失败:', res.errMsg)
                }
            },
            fail: (err) => {
                console.error('调用 wx.login 失败:', err)
            },
        })
    },
    globalData: {
        userInfo: null // 用于存储用户信息
      }
})
