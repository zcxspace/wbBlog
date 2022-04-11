<template>
  <div class="wbView">
    <main-layout>
      <template #topNavBar>导航栏区域</template>
      <template #center-left> <left-bar> </left-bar></template>
      <template #center>
        <router-view></router-view>
      </template>
      <template #center-right><hot-word></hot-word></template>
    </main-layout>
  </div>
</template>

<script>
import HotWord from "../components/common/HotWord.vue";
import LeftBar from "../components/common/Single/LeftBar.vue";
import MainLayout from "../layouts/MainLayout.vue";

export default {
  components: { MainLayout, LeftBar, HotWord },
};
</script>

<style>
</style>