<template>
  <div class="Viewer">
    <main-layout :isShowTop="isShowTop">
      <template #searchArea>搜索区域</template>
      <template #center-left>左侧导航栏</template>
      <template #center-right>右侧热搜区</template>
      <template #center>中部内容区</template>
    </main-layout>
  </div>
</template>

<script>
import MainLayout from "../layouts/MainLayout.vue";
export default {
  data() {
    return {
      isShowTop: true, //显示头部背景区
    };
  },
  components: {
    MainLayout,
  },
};
</script>

<style scoped>
</style>