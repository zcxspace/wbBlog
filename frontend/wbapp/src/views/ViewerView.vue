<template>
  <div class="Viewer">
    <main-layout :isShowTop="isShowTop"> </main-layout>
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