<template>
  <div class="UserHot">
    <main-layout>
      <template #center-left>
        <left-bar :tabsArr="tabsArr" ref="HotLeftRef" @click="updateCom">
          <template #title>热门</template>
        </left-bar>
      </template>

      <template #center>
        {{ nowCom }}
      </template>

      <template #center-right>
        <hot-word></hot-word>
      </template>
    </main-layout>
  </div>
</template>

<script>
import MainLayout from "../layouts/MainLayout.vue";
import LeftBar from "../components/common/Single/LeftBar.vue";
import HotWord from "../components/common/HotWord.vue";
export default {
  components: {
    MainLayout,
    LeftBar,
    HotWord,
  },
  data() {
    return {
      tabsArr: [
        { title: "热门微博", comName: "热门微博" },
        { title: "热搜榜", comName: "热搜榜" },
      ],
      nowCom: "",
    };
  },
  methods: {
    updateCom() {
      this.nowCom = this.$refs.HotLeftRef.sentComName();
    },
  },
};
</script>

<style scoped>
</style>