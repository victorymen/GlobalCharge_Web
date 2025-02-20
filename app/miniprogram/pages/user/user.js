import http from '../utils/http'

Page({
    /**
     * 页面的初始数据
     */
    data: {
        totalNavHeight: 44,
        hasUserInfo: false,
        canIUseGetUserProfile: false,
        userioc:''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad() {
        const { statusBarHeight } = wx.getWindowInfo() || { statusBarHeight: 0 }
        this.setData({
            totalNavHeight: statusBarHeight + 44,
            canIUseGetUserProfile: !!wx.getUserProfile,
            ...wx.getStorageSync('userInfo'),
        })
    },


    navigateToDetail: function () {
        wx.navigateTo({ url: '/pages/PersonalCenter/index' })
    },

    onShow() {
       
        this.setData({  ...wx.getStorageSync('userInfo'),});
        console.log(this.data)
    },
    



})
