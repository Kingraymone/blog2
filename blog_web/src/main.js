// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import axios from './utils/request'
import ElementUI from 'element-ui'
// 样式文件单独引入
import 'element-ui/lib/theme-chalk/index.css'
import 'font-awesome/css/font-awesome.css'
// 公共方法
import commons from './utils/common'

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.prototype.$axios = axios;
Vue.prototype.commons=commons;

new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
});
