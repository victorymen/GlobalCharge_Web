import http from '../utils/http'

export default {
    // 修改订单信息  微信支付后进行回写修改支付状态 
    productUserSave(datas) {
        return http.request({
            url: '/api/productsUser',
            method: 'POST',
            data: datas,
        })
    },

    //获取订单支付信息
    productUserGet(datas) {
        return http.request({
            url: '/api/productsUser',
            method: 'GET',
            params: datas,
        })
    },

}
