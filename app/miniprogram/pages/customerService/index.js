import http from '../utils/http'

Page({
    data: {
        active: 0,
        tabCount: 1,
        PageBase: {
            content: [
                {
                    title: '常见问题',
                    content: [
                      {
                        title: '老郭手机号如何查询话费、流量等',
                        value: '在线客服',
                    },
                    {
                      title: '流量为什么用的快？',
                      value: '在线客服',
                  },
                  {
                    title: '如何购买电话卡？办理电话卡？',
                    value: '在线客服',
                },
                    ],
                },
            ],
        },
    },

    onLoad() {},

    onChange(event) {
        console.log('当前激活的标签索引:', event.detail)
        this.setData({
            active: event.detail,
        })
    },
})
