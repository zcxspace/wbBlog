<template>
  <!-- 无限滚动组件 -->
  <div
    class="Home"
    v-infinite-scroll="load"
    infinite-scroll-distance="50"
    style="overflow: auto"
  >
    <main-layout>
      <template #center>
        <!-- 动态发布栏 -->
        <set-bar
          :showBack="false"
          :showTop="false"
          :inputName="inputName"
          :hideAfterSent="false"
          ref="homeSetRef"
          @click="getinfo"
        >
        </set-bar>
        {{ NowTab }}
        <!-- 测试动态发布 -->
        <template v-for="n of NowDisNum" :key="n">
          <dynamic-tab :dynamicInfo="dynamics[n]"></dynamic-tab>
        </template>

        <!-- <template v-for="n of NowDisNum" :key="n"> -->
        <!-- :dynamicInfo="dynamics[n]" -->
        <!-- <dynamic-tab></dynamic-tab> -->
        <!-- </template> -->

        <!-- 无限加载测试 -->

        <!-- <div v-for="i in count" :key="i" class="test">
          {{ i }}
        </div> -->
        <p v-if="Loading" class="Tips">正在加载中</p>
        <p v-if="noMore">没有更多数据了</p>
      </template>

      <template #center-left>
        <left-bar :tabsArr="tabsArr" @click="updateCom" ref="HomeLeftRef">
          <template #title>首页</template>
        </left-bar>
      </template>
      <template #center-right><hot-word></hot-word></template>
    </main-layout>
  </div>
</template>

<script>
import HotWord from "../components/common/HotWord.vue";
import DynamicTab from "../components/common/Single/DynamicTab.vue";
// import DynamicTab from "../components/common/Single/DynamicTab.vue";
import LeftBar from "../components/common/Single/LeftBar.vue";
import SetBar from "../components/SetBar.vue";
import MainLayout from "../layouts/MainLayout.vue";
export default {
  components: { MainLayout, SetBar, HotWord, LeftBar, DynamicTab },
  data() {
    return {
      count: 0,
      Loading: false,
      inputName: "inputFile1",
      NowTab: "",
      NowDisNum: 2,
      dynamics: this.$store.state.userDynamic,
      tabsArr: [
        { title: "全部微博", comName: "微博主页" },
        { title: "最新微博", comName: "最新微博" },
      ],
    };
  },
  computed: {
    infos() {
      return this.$store.state.post;
    },
    noMore() {
      return this.NowDisNum >= 13;
    },
  },
  methods: {
    getinfo() {
      let info = this.$refs.homeSetRef.deliverData();
      alert(info);
    },
    updateCom() {
      this.NowTab = this.$refs.HomeLeftRef.sentComName();
    },

    // 无限滚动测试
    load() {
      if (!this.noMore) {
        this.Loading = true;
        setTimeout(() => {
          this.NowDisNum += 1;
          this.Loading = false;
          console.log(this.NowDisNum);
        }, 1000);
      } else return;
    },
  },
  created() {
    console.log("重新请求动态数据");
    console.log(this.$store.state.userDynamic);
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