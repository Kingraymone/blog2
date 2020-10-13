import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isCollapse:false,
    TOKEN:'',
    USER_INFO:''
  },
  mutations:{
    collapseChage(state){
      state.isCollapse=!state.isCollapse;
    },
    addToken(state,token){
      state.TOKEN=token;
      localStorage.setItem("token",token);
    },
    addUserInfo(state,userInfo){
      state.USER_INFO=userInfo;
      sessionStorage.setItem("userInfo",JSON.stringify(userInfo));
    },
    removeToken(state){
      state.TOKEN='';
      state.USER_INFO='';
    }
  }

});
