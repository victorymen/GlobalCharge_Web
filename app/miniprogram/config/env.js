// 环境配置
const env = {
	dev: {
	  baseURL: 'http://localhost:8282'
	},
	prod: {
	  baseURL: 'https://www.95cxmd.com'
	}
  }
  
  // 当前环境
  const currentEnv = 'dev'
  
  export default env[currentEnv]