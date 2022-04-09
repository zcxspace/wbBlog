<template>
  <div class="Viewer">
    <top-nav-bar>
      <template #right1>
        <router-link to="/SignIn">登录</router-link>
      </template>
      <template #right2>
        <router-link to="/SignIn">注册</router-link>
      </template>
      <template #right3>
        <router-link :to="{ name: 'AlertInfo' }">修改信息</router-link>
      </template>
    </top-nav-bar>
    <main-layout :isShowTop="isShowTop">
      <template #searchArea>
        <div class="searchBox">
          <label for="search" class="sLabel">搜索</label>
          <input type="text" class="searchInput" />
        </div>
      </template>
      <template #center-left>左侧导航栏</template>
      <template #center-right><hot-word></hot-word></template>
      <template #center><alert-info></alert-info></template>
    </main-layout>
  </div>
</template>

<script>
import HotWord from "../components/common/HotWord.vue";
import TopNavBar from "../components/common/TopNavBar.vue";
import MainLayout from "../layouts/MainLayout.vue";
import AlertInfo from "../pages/UserPages/AlertInfo.vue";
export default {
  data() {
    return {
      isShowTop: true, //显示头部背景区
    };
  },
  components: {
    MainLayout,
    HotWord,
    TopNavBar,
    AlertInfo,
  },
};
</script>

<style scoped>
.searchBox {
  display: flex;
  width: 100%;
  padding: 10px;
}
.searchBox .sLabel {
  width: auto;
}
.searchBox .searchInput {
  flex: 1;
  font-size: 25px;
  padding: 5px 0;
  text-indent: 3px;
  outline: none;
}
</style>