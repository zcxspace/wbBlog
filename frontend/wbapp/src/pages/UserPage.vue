<template>
  <div
    class="page"
    v-infinite-scroll="load"
    infinite-scroll-distance="50"
    style="overflow: auto"
  >
    <main-layout>
      <template #center-left>
        <left-bar :ComArr="ComArr">
          <template #title>个人主页</template>
        </left-bar>
      </template>

      <template #center>
        <router-view v-slot="{ Component }" :key="$route.fullPath">
          <keep-alive>
            <component :is="Component" v-if="$route.meta.keepAlive"></component>
          </keep-alive>
        </router-view>
      </template>

      <template #center-right>右侧</template>
    </main-layout>
  </div>
</template>

<script>
import MainLayout from "../layouts/MainLayout.vue";
import LeftBar from "../components/common/Single/LeftBar.vue";

export default {
  components: {
    MainLayout,
    LeftBar,
  },
  data() {
    return {
      ComArr: [{ comName: "MuserInfo" }, { comName: "ChangeInfo" }],
    };
  },
  computed: {
    noMore() {
      return this.NowDisNum >= this.$store.state.userDynamic.length;
    },
  },
  methods: {
    //更新当前页面组件
    updateCom() {
      this.nowComActive = this.$refs.PageLeftRef.sentComName();
      console.log(this.nowComActive);
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
    console.log(this.$store.state.userInfo);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.leftBar {
  width: 100%;
  height: auto;
}
.leftBar li {
  list-style: none;
  width: 100%;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 5px;
}
.leftBar li:hover {
  background: rgb(145, 142, 142);
}
</style>
