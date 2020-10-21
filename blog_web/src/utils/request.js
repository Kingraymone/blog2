import service from 'axios';
import {Message} from 'element-ui';
import store from '@/store'

const axios = service.create({
  baseURL: 'http://localhost:7779/',
  timeout: 10000
});
function erroMessage(msg){
  Message.closeAll();
  Message.error(msg, {
    center: true,
    duration: 1000
  })
}

axios.interceptors.request.use(
  config => {
    // 如果存在token则在请求头添加
    let token = store.state.TOKEN;
    if(!!token) {
      config.headers.Authorization = token;
    }
    return config;
  },
  error => {
    console.log(error);
    return Promise.reject();
  }
);

axios.interceptors.response.use(
  response => {
    if (response.status === 200) {
      // 统一错误信息提示
      if (!!response.data && !response.data.result) {
        erroMessage(response.data.msg);
        return Promise.reject();
      }else {
        return response;
      }
    }
  },
  error => {
    erroMessage(error.message);
    return Promise.reject();
  }
);

export default axios;
