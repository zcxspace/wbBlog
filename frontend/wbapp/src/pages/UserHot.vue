<template>
  <div class="UserHot">
    <main-layout>
      <template #center-left>
        <!-- <left-bar :tabsArr="tabsArr">
          <template #title>热门</template>
        </left-bar> -->
      </template>

      <template #center>
        <router-view></router-view>
      </template>

      <template #center-right>
        <hot-word></hot-word>
      </template>
    </main-layout>
  </div>
</template>

<script>
import MainLayout from "../layouts/MainLayout.vue";
// import LeftBar from "../components/common/Single/LeftBar.vue";
import HotWord from "../components/common/HotWord.vue";

export default {
  components: {
    MainLayout,
    // LeftBar,
    HotWord,
  },
  data() {
    return {
      searchInfo: null,
      // tabsArr: [
      //   { title: "热门微博", icon: "a", size: "23px", comName: "热门微博" },
      //   { title: "热搜榜", icon: "b", size: "23px", comName: "热搜榜" },
      // ],
      nowCom: "",
    };
  },
  methods: {
    // async getSearchInfo() {
    //   let result = await getHotWord();
    //   this.searchInfo = result.data.data;
    //   console.log(result);
    // },
  },
  created() {
    // this.getSearchInfo();
  },
};
</script>

<style scoped>
</style>