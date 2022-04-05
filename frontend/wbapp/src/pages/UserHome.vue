<template>
  <div>
    <main-layout>
      <template #userPage> </template>
      <template #center>
        <!-- 防止组件复用而不刷新 -->
        <router-view :key="$route.fullPath"></router-view>
      </template>

      <template #center-left>
        <left-bar :ComArr="ComArr"></left-bar>
      </template>
      <template #center-right><hot-word></hot-word> </template>
    </main-layout>
  </div>
</template>

<script>
import HotWord from "../components/common/HotWord.vue";
import LeftBar from "../components/common/Single/LeftBar.vue";
import MainLayout from "../layouts/MainLayout.vue";
export default {
  components: { MainLayout, HotWord, LeftBar },
  data() {
    return {
      show: true,
      dynamics: this.$store.state.userDynamic,
      ComArr: [{ comName: "HomePage" }],
    };
  },
  computed: {
    infos() {
      return this.$store.state.post;
    },
  },
  async created() {
    console.log("重新请求动态数据");
    // let result = await GetPublic();
    // let publicDynamic = result.data.data;
    // console.log(publicDynamic);
    // this.updateHomePageDynamic(publicDynamic);
    // console.log(this.$store.state.HomePageDynamic);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  overflow: scroll;
  overflow-x: hidden;
}
.Tips {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40px;
}
.test1 {
  width: 100%;
  height: 500px;
  background: grey;
}
.test {
  background: orange;
  margin: 10px 0;
  height: 50px;
  width: 100%;
}
</style>