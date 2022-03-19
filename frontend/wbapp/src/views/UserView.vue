<template>
  <div class="User">
    <top-nav-bar>
      <template #center1>中1</template>

      <template #center2></template>

      <template #center3></template>

      <template #center4></template>

      <template #center5> </template>

      <template #right1></template>
      <template #right2> </template>
      <template #right3> </template>
    </top-nav-bar>
    <router-view></router-view>
  </div>
</template>

<script>
import TopNavBar from "../components/common/TopNavBar.vue";

export default {
  components: { TopNavBar },
  data() {
    return {};
  },
};
</script>

<style scoped>
</style>