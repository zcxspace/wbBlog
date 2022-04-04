<template>
  <div class="userInfo">
    <router-view :key="$route.fullPath"></router-view>
  </div>
</template>

<script>
export default {
  name: "userInfo",
  created() {},
};
</script>

<style>
</style>