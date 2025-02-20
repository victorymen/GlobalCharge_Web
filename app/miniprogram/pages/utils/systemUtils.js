// 版本比较函数优化
export const compareSDKVersion = (v1, v2) => {
  if (!/^\d+(\.\d+)*$/.test(v1) || !/^\d+(\.\d+)*$/.test(v2)) return 0
  
  const normalize = version => 
    version.split('.')
      .map(v => v.padStart(6, '0'))
      .join('')

  return normalize(v1).localeCompare(normalize(v2), undefined, { numeric: true })
}

// 支付能力检测
export const checkPaymentAPI = (sdkVersion) => {
  const baseVersion = '2.19.2'
  return compareSDKVersion(sdkVersion, baseVersion) >= 0 || wx.canIUse('requestVirtualPayment')
}