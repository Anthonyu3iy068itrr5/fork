import { extend, ResponseError } from 'umi-request';
import { message } from 'antd'; 

export type IErrorLevel = 'toast' | 'prompt' | 'critical' | false;
export interface IOptions{
  method?: "get" | 'post' | 'put' | 'delete';
  mock?: boolean;
  errorLevel?: 'toast' | 'prompt' | 'critical' | false;
}

console.log('location', location)

// TODO:
const codeMessage:{[errorCode:number]:string} = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};

const mockUrl = 'https://yapi.alibaba.com/mock/1000160';
const onLineServiceUrl = 'http://127.0.0.1:10824';
const localServiceUrl = 'http://127.0.0.1:7001';

const baseURL = location.href.indexOf('dist/index.html') > -1 ? onLineServiceUrl : localServiceUrl;
console.log(baseURL)
const errorHandler = (error: ResponseError,errorLevel:IErrorLevel) => {
  const { response } = error;
  if(!response) return
  const errortext = codeMessage[response.status] || response.statusText;
  const { status } = response;
  if(errorLevel === 'toast'){
    message.error(`${status}: ${errortext}`)
  }
};

const request = extend({
  // prefix: '/api',
  credentials: 'include', // 默认请求是否带上cookie
  headers:{
    'Content-Type': 'application/json' 
  }
});

request.interceptors.request.use((url, options) => {
  return {
    options: {
      ...options,
    },
  };
});

// request.interceptors.response.use(async response => {
//   const res = await response.clone().json();
//   const { code, message } = res;
//   if (code !== 0) {
//     message.error(`${code}: ${message}`)
//     return response;
//   }
//   return res;
// });

export default function createRequest<P = void, R = {}>(url:string, options:IOptions){
  const {method = 'get', mock = false,errorLevel = 'toast'} = options;
  const _baseURL = mock ? mockUrl : baseURL;
  return function(params: P){
    const paramsInUrl: string[] = [];
    const _url = url.replace(/:(.+?)\b/, (_, name:string) => {
      const value = params[name];
      paramsInUrl.push(name);
      return `${value}`;
    });

    if (paramsInUrl.length) {
      paramsInUrl.forEach((name) => {
        delete params[name];
      });
    }

    return new Promise<R>((resolve, reject) => {
      let dataName = ''
      switch (method) { 
        case 'get':
          dataName = 'params';
          break
        case 'delete':
          dataName = 'params';
          break
        case 'post':
          dataName = 'data';
          break;
        case 'put':
          dataName = 'data';
          break;
      }
      
      request[method](`${_baseURL}${_url}`,{[dataName]: params})
      .then(res=>{
        if(!res) return
        const {success, errorCode, errorMessage, data} = res
        if(!success && errorLevel === 'toast'){
          message.error(`${errorCode}: ${errorMessage}`)
          reject(`${errorCode}: ${errorMessage}`)
        }
        resolve(data)
      })
      .catch(error=>{
        errorHandler(error, errorLevel)
        reject(error)
      })
    }) 
  } 
}
