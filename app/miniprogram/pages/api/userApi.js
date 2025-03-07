import http from '../utils/http'

export default {
 // 登录接口
  loginInfo(params) {
        return http.request({
            url: '/api/userInfo/loginInfo',
            method: 'POST',
            params: params ,
            showLoading: true,
            showError: true
        })
    },
    //修改用户信息
    saveUpdate(params) {
        return http.request({
            url: '/api/userInfo',
            method: 'PUT',
            data: params ,
            showLoading: true,
            showError: true
        })
    },
  
}