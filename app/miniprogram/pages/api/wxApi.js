import http from '../utils/http'

export default {
    /**
     * JSAPI支付下单，并返回JSAPI调起支付数据
     */
 prepayWithRequestPayment(datas) {
        return http.request({
            url: '/api/wxApi/prepayWithRequestPayment',
            method: 'POST',
            data: datas,
            showLoading: true,
            showError: true
        })
    },
  }


  