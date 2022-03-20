import { createStore } from 'vuex'

export default createStore({
  state: {
    userInfo: null,

  },
  getters: {
  },
  mutations: {
    //更新当前登录用户的数据
    updateUserInfo(state, info) {
      state.userInfo = info
    },
    //退出登录时清空所有数据
    resetInfo(state) {
      state.userInfo = null;
    }
  },
  actions: {
  },
  modules: {
  }
})
