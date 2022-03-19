<template>
  <div class="Viewer">
    <main-lay-out :isShowTop="isShowTop"> </main-lay-out>
  </div>
</template>

<script>
import MainLayouts from "../layouts/MainLayout.vue";
export default {
  data() {
    return {
      isShowTop: true, //显示头部背景区
    };
  },
  components: {
    MainLayouts,
    MainLayOut,
  },
};
</script>

<style scoped>
</style>