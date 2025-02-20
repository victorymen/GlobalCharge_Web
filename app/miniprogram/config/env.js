// 环境配置
const env = {
	dev: {
	  baseURL: 'http://192.168.3.14:8080'
	},
	prod: {
	  baseURL: 'https://www.95cxmd.com'
	}
  }
  
  // 当前环境
  const currentEnv = 'dev'
  
  export default env[currentEnv]