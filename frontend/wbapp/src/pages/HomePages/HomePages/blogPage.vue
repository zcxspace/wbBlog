<template>
  <div
    class="blog"
    v-show="userDynamicNum"
    v-infinite-scroll="load"
    infinite-scroll-distance="50"
  >
    <div v-if="isShowSk">
      <template v-for="n of 4" :key="n">
        <skeleton-com></skeleton-com>
      </template>
    </div>
    <div v-show="!isShowSk">
      <template v-for="n of NowDisNum" :key="n">
        <dynamic-tab
          :userInfo="userInfo"
          :dynamicInfo="dynamics[n - 1]"
          v-if="userDynamicNum"
        ></dynamic-tab>
      </template>
      <div v-if="!isShowSk">
        <loading-com :text="'疯狂加载中'" v-if="Loading"></loading-com>
      </div>

      <p v-if="noMore">没有更多数据了</p>
    </div>
  </div>
</template>

<script>
// import LoadingCom from "../../LoadingCom.vue";
// import SkeletonCom from "../../SkeletonCom.vue";
// import dynamicTab from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/components/common/Single/DynamicTab.vue";
import LoadingCom from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/components/LoadingComs/LoadingCom.vue";
import SkeletonCom from "../../../components/LoadingComs/SkeletonCom.vue";
import dynamicTab from "../../../components/DynamicComs/DynamicTab.vue";
export default {
  components: {
    dynamicTab,
    SkeletonCom,
    LoadingCom,
  },
  props: {
    dynamics: Array,
    userInfo: Object,
  },
  data() {
    return {
      Loading: false,
      NowDisNum: 0,
      isShowSk: true,
      dynamicNum: this.dynamics.length,
    };
  },
  watch: {
    NowDisNum(newValue) {
      if (newValue == this.dynamicNum) {
        this.Loading = false;
      }
    },
  },
  methods: {
    load() {
      console.log("触发了load函数");
      //根据当前动态数量来判断是否加载
      if (!this.noMore) {
        this.Loading = true;
        setTimeout(() => {
          //如果当前展示数量等于动态数量 则停止任务
          if (this.NowDisNum == this.dynamics.length) return;
          this.NowDisNum++;
          this.Loading = false;
          console.log(this.NowDisNum);
        }, 2000);
      } else console.log("没有动态了");
    },
  },
  computed: {
    userDynamicNum() {
      return this.dynamics.length ? true : false;
    },
    noMore() {
      return this.NowDisNum >= this.dynamics.length;
    },
  },
  created() {
    setTimeout(() => {
      this.isShowSk = false;
      if (this.dynamics.length > 4) {
        this.NowDisNum = 4;
      } else {
        this.NowDisNum = this.dynamics.length;
      }
    }, 1200);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
</style>