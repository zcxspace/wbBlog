<template>
  <div class="Viewer">
    <top-nav-bar>
      <template #right1>
        <router-link to="/SignIn">登录</router-link>
      </template>
      <template #right2>
        <router-link to="/SignUp">注册</router-link>
      </template>
    </top-nav-bar>
    <main-layout :isShowTop="isShowTop">
      <template #searchArea>搜索区域</template>
      <template #center-left>左侧导航栏</template>
      <template #center-right><hot-word></hot-word></template>
      <template #center>中部内容区</template>
    </main-layout>
  </div>
</template>

<script>
import HotWord from "../components/common/HotWord.vue";
import TopNavBar from "../components/common/TopNavBar.vue";
import MainLayout from "../layouts/MainLayout.vue";
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
  },
};
</script>

<style scoped>
</style>