import Vue from 'vue'
import ElementUI from 'element-ui'



Vue.prototype.$message.success = function (msg) {
  return ElementUI.Message.success({
    message: msg,
    duration: 1000,
    center:true
  })
};
Vue.prototype.$message.warning = function (msg) {
  return ElementUI.Message.warning({
    message: msg,
    duration: 1000,
    center:true
  })
};
Vue.prototype.$message.info = function (msg) {
  return ElementUI.Message.info({
    message: msg,
    duration: 1000,
    center:true
  })
};
Vue.prototype.$message.error = function (msg) {
  return ElementUI.Message.error({
    message: msg,
    duration: 1000,
    center:true
  })
};


