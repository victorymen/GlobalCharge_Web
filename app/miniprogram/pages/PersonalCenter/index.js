import api from '../api/userApi'
import http from '../utils/http'
Page({
    data: {
        username: '获取用户名称',
        userioc: wx.getStorageSync('userAvatar'),
        phone: '',
        ...wx.getStorageSync('userInfo'),
    },

    onLoad: async function (options) {
        this.setData({ ...wx.getStorageSync('userInfo') })
    },

    // 选择头像回调
    onChooseAvatar(e) {
        const { avatarUrl } = e.detail
        this.uploadImage(avatarUrl)
    },
    //保存修改
    async onClickButton() {
        wx.showLoading({ title: '保存中...' })
        await api.saveUpdate({ ...this.data, id: this.data.userId })
        wx.setStorageSync('userInfo',{...this.data});
        // 隐藏加载状态
        wx.hideLoading()
        wx.navigateBack({ delta: 1 })
    },

    complete: () => {
        // 获取页面栈实例
        const pages = getCurrentPages()
        const prevPage = pages[pages.length - 2]
        // 双重保障机制
        if (prevPage) {
            // 直接调用刷新方法（精确更新）
            prevPage.onManualRefresh?.()
            // 设置刷新标记（兜底策略）
            prevPage.setData?.({ __autoRefresh: true })
        }
        // 执行返回操作
        wx.navigateBack({ delta: 1 })
    },

    // 执行上传
    uploadImage(filePath) {
        http.uploadFile({
            url: '/api/userInfo/uploadFile',
            filePath: filePath,
            name: 'file',
            formData: {},
            showLoading: true,
            showError: true,
        })
            .then((res) => {
                console.log('上传成功', res)
                this.setData({ userioc: res.data })
                wx.setStorageSync('userInfo', this.data)
                wx.showToast({ title: '上传成功' })
            })
            .catch((err) => this.handleError('上传失败', err))
    },

    // 统一的错误处理机制
    handleError(title, err) {
        wx.showToast({ title: title, icon: 'none', duration: 2000 }) // 修改 icon 为 'none' 以便更灵活地显示错误信息
    },
})
