import { createStore } from 'vuex'

export default createStore({
  state: {
    userInfo: null,
    userDynamic: null,
    HomePageDynamic: null,
  },
  getters: {
  },
  mutations: {
    //更新当前登录用户的数据
    updateUserInfo(state, AllUserInfo) {
      console.log('updated')
      let { user } = AllUserInfo;
      let { dynamic } = AllUserInfo;
      state.userDynamic = dynamic;
      state.userInfo = user;
      console.log(dynamic.length);
      console.log("用户数据已经重新更新")
    },
    //单独更新用户信息
    updateUserPrivate(state, userInfos) {
      state.userInfo = userInfos
      console.log('已经单独更新了用户的信息')
    },
    //单独更新用户页信息
    updateHomePageDynamic(state, HomePageDynamic) {
      state.HomePageDynamic = HomePageDynamic
      console.log(HomePageDynamic.length)
      console.log(state.HomePageDynamic.length)
      console.log("更新了HomePage的公共微博")
    },
    //单独更新用户头像
    updateUserAvatar(state, path) {
      state.userInfo.photo = path
    },
    //模拟动态发布
    addData(state, obj) {
      state.post.unshift(obj);
    }

  },
  actions: {
  },
  modules: {
  }
})
