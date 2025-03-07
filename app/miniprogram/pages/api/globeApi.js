import http from '../utils/http'

export default {
    //国家
    countries(datas) {
        return http.request({
            url: '/api/countries',
            method: 'POST',
            data: datas,
        })
    },
    //产品
    products(datas) {
        return http.request({
            url: '/api/products',
            method: 'POST',
            data: datas,
        })
    },

    //订单提交
    productsUser(datas) {
        return http.request({
            url: '/api/productsUser',
            method: 'POST',
            data: datas,
        })
    },

    //修改订单信息 一般为支付成功后 修改为待处理  取消为已取消 充值完成为完成
    productsUserUpdate(datas) {
        return http.request({
            url: '/api/productsUser',
            method: 'PUT',
            data: datas,
        })
    },
}
