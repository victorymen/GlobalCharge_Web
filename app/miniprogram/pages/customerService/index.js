const app = getApp();
Page({
	data: {
		isLoading: true,					// 判断是否尚在加载中
		article: {}						// 内容数据
  },
	onLoad: function () {
    const _ts = this;

    //请求markdown文件，并转换为内容
    wx.request({
        url: 'https://github.com/jin-yufeng/mp-html/blob/master/README.md',
        header: {
            'content-type': 'application/x-www-form-urlencoded'
        },
        success: (res) => {
            //将markdown内容转换为towxml数据
            let data = app.towxml(res.data,'markdown',{
              base: 'https://example.com', // 重要！设置资源基础路径（如图片相对路径补全）
              theme: 'light',             // 主题（可选）
              events: {                   // 自定义事件（可选）
                tap: (e) => {
                  console.log('元素被点击', e);
                }
              }
            });
            //设置数据
            _ts.setData({
                article: data
            });
        }
    });
  }
})