Page({
    data: {
        active: 1,
        pageCache: {
            home: {
              show: false,
              active:0,
              selectedTabIndex:0,
              selectedOperatorIndex: 0,
              selectedOperatorIndex2: 0,
            }, // 保存首页状态
            user: {}, // 保存用户页状态
        },
        userInfo:{
          openid:''
        }
    },

    onLoad(options) {
      this.onLoginClick();
    },

    // Tab 切换事件
    onTabChange(e) {
        const oldActive = this.data.active
        const newActive = e.detail
        // 切换前保存旧页面数据
        this.saveCurrentCache(oldActive)
        // 切换到新页面
        this.setData({ active: newActive })
    },

    // 保存当前活动页面的数据
    saveCurrentCache(active) {
        const moduleId = active === 0 ? 'homeModule' : 'userModule' // 获取组件实例
        const component = this.selectComponent(`#${moduleId}`)
        if (component) {  // 触发组件自身的保存方法
            component.saveCacheData()
        }
    },

    // 接收子组件上报的缓存数据
    onSaveCacheData(e) {
        const { moduleKey, data } = e.detail
        this.setData({[`pageCache.${moduleKey}`]: data  })
    },



    //登录    
    onLoginClick() {
        wx.login({
            success: (res) => {
                if (res.code) {
                    console.log('获取到 code:', res.code)
                    this.sendCodeToServer(res.code) // 发送 code 到服务器
                } else {
                    console.error('登录失败:', res.errMsg)
                }
            },
            fail: (err) => {
                console.error('调用 wx.login 失败:', err)
            },
        })
    },
    // 发送 code 到服务器 获取openId
    sendCodeToServer(code) {
        wx.request({
            url: 'http://192.168.3.14:3000/api/get-openid', // 替换为你的服务器接口
            method: 'POST',
            data: { code },
            success: (res) => {
                const openid = res.data.openid
                this.setData({ userInfo: { openid: openid } })
                console.log('服务器返回的 openid:', openid)
            },
            fail: (err) => {
                console.error('请求服务器失败:', err)
            },
        })
    },
})
