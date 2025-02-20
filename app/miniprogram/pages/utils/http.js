// 基础配置
import configJon from '../../config/env'

const config = {
  ...configJon,
  timeout: 5000,
  contentType: 'application/json',
  retryCount: 2
}

const pendingRequests = new Map()
const pendingUploads = new Map()

// 公共工具方法
const buildRequestURL = (url, params) => {
  if (!params) return config.baseURL + url;
  const queryString = Object.keys(params)
    .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
    .join('&');
  return config.baseURL + url + (queryString ? `?${queryString}` : '');
};

const handleLoading = (showLoading, timeout) => {
  let timer = null;
  if (showLoading) {
    wx.showLoading({ title: '加载中', mask: true });
    timer = setTimeout(() => wx.hideLoading(), timeout);
  }
  return {
    clear: () => {
      clearTimeout(timer);
      if (showLoading) wx.hideLoading();
    }
  };
};

// 普通请求
const request = (options = {}) => {
  if (typeof options !== 'object') throw new Error('请求配置必须为对象类型');
  if (!options.url || typeof options.url !== 'string') throw new Error('URL参数无效');

  const mergedOptions = {
    url: buildRequestURL(options.url, options.params),
    method: (options.method || 'GET').toUpperCase(),
    header: {
      'Content-Type': config.contentType,
      ...options.header
    },
    timeout: config.timeout,
    data: options.data || {},
  };

  // 请求拦截
  const token = wx.getStorageSync('token');
  if (token) mergedOptions.header.Authorization = `Bearer ${token}`;
  
  const requestKey = `${mergedOptions.method}-${mergedOptions.url}-${Date.now()}`;
  let retries = config.retryCount;

  const attempt = () => {
    return new Promise((resolve, reject) => {
      const loading = handleLoading(options.showLoading, config.timeout);
      const startTime = Date.now();

      const requestTask = wx.request({
        ...mergedOptions,
        success: (response) => {
          console.log(`请求耗时：${Date.now() - startTime}ms`);
          if (response.statusCode >= 200 && response.statusCode < 300) {
            resolve(response.data?.data || response.data);
          } else {
            handleError({ ...response, errMsg: response.data?.msg || '请求失败' }, options);
            reject(response);
          }
        },
        fail: (error) => {
          if (retries-- > 0) {
            setTimeout(attempt, 1000);
          } else {
            handleError(error, options);
            reject(error);
          }
        },
        complete: () => {
          pendingRequests.delete(requestKey);
          loading.clear();
        }
      });

      pendingRequests.set(requestKey, requestTask);
    });
  };

  return attempt();
}

// 文件上传
const uploadFile = (options = {}) => {
  if (typeof options !== 'object') throw new Error('上传配置必须为对象类型');
  if (!options.filePath || !options.url) throw new Error('缺少必要参数');

  const mergedOptions = {
    url: config.baseURL + options.url,
    filePath: options.filePath,
    name: options.name || 'file',
    formData: options.formData || {},
    header: {
      Authorization: `Bearer ${wx.getStorageSync('token')}`,
      'Content-Type': config.contentType,
      ...options.header
    },
    timeout: config.timeout
  };

  const uploadKey = `UPLOAD-${mergedOptions.url}-${Date.now()}`;
  let retries = config.retryCount;

  const attempt = () => {
    return new Promise((resolve, reject) => {
      const loading = handleLoading(options.showLoading, config.timeout);

      const uploadTask = wx.uploadFile({
        ...mergedOptions,
        success: (response) => {
          try {
            const data = JSON.parse(response.data);
            if (response.statusCode >= 200 && response.statusCode < 300) {
              resolve(data);
            } else {
              handleError({ ...response, errMsg: data?.msg || '上传失败' }, options);
              reject(response);
            }
          } catch (e) {
            handleError({ ...response, errMsg: '响应数据解析失败' }, options);
            reject(response);
          }
        },
        fail: (error) => {
          if (retries-- > 0) {
            setTimeout(attempt, 1000);
          } else {
            handleError(error, options);
            reject(error);
          }
        },
        complete: () => {
          pendingUploads.delete(uploadKey);
          loading.clear();
        }
      });

      pendingUploads.set(uploadKey, uploadTask);
    });
  };

  return attempt();
}

// 增强版错误处理
const handleError = (error, options) => {
  console.error('[网络错误]', error);

  if (error.errMsg.includes('network error')) {
    wx.showToast({ title: '网络连接异常', icon: 'none' });
    return;
  }

  if (options.showError) {
    wx.showToast({
      title: error.errMsg || '请求失败',
      icon: 'none',
      duration: 2000
    });
  }

  if (error.statusCode === 401) {
    wx.removeStorageSync('token');
    wx.reLaunch({ url: '/pages/login/login' });
  }
}

// 取消请求（保持不变）
const cancelRequest = (requestKey) => {
  if (pendingRequests.has(requestKey)) {
    pendingRequests.get(requestKey).abort();
    pendingRequests.delete(requestKey);
  }
}

// 取消上传（保持不变）
const cancelUpload = (uploadKey) => {
  if (pendingUploads.has(uploadKey)) {
    pendingUploads.get(uploadKey).abort();
    pendingUploads.delete(uploadKey);
  }
}

export default {
  request,
  uploadFile,
  cancelRequest,
  cancelUpload
}
