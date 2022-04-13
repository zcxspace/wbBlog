<template>
  <div class="hotPage" v-infinite-scroll="load" infinite-scroll-distance="50">
    <div v-if="isShowSk" class="sk">
      <template v-for="n of 3" :key="n">
        <skeleton-com></skeleton-com>
      </template>
    </div>
    <div v-if="!isShowSk">
      <template v-for="n of nowDisNum" :key="n">
        <dynamic-tab :dynamicInfo="hotDyInfo[n - 1]"></dynamic-tab>
      </template>
      <div v-if="!isShowSk">
        <loading-com v-if="isLoading"></loading-com>
      </div>
      <span v-if="nowDisNum == hotDyNum">没有更多数据了</span>
    </div>
  </div>
</template>

<script>
import DynamicTab from "../../components/DynamicComs/DynamicTab.vue";
import { getHotDy } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import LoadingCom from "../../components/LoadingComs/LoadingCom.vue";
export default {
  components: { LoadingCom, DynamicTab },
  data() {
    return {
      hotDyNum: null,
      nowDisNum: null,
      isShowSk: true,
      hotDyInfo: null,
      isLoading: false,
    };
  },
  computed: {
    noMore() {
      return this.nowDisNum < this.hotDyNum ? false : true;
    },
  },
  methods: {
    load() {
      console.log("触发了load函数");
      //根据当前动态数量来判断是否加载
      if (!this.noMore) {
        this.isLoading = true;
        setTimeout(() => {
          //如果当前展示数量等于动态数量 则停止任务
          if (this.NowDisNum == this.hotDyNum) return;
          this.NowDisNum++;
          this.isLoading = false;
          console.log(this.NowDisNum);
        }, 1500);
      } else console.log("没有动态了");
    },
    async getHotDynamic() {
      let result = await getHotDy();
      this.hotDyInfo = result.data.data;
      this.hotDyNum = result.data.data.length;
      console.log(result);
    },
  },
  created() {
    this.getHotDynamic();

    setTimeout(() => {
      this.isShowSk = false;
      if (this.hotDyNum <= 4) {
        this.nowDisNum = 4;
      } else {
        this.nowDisNum = this.hotDyNum;
      }
    }, 1200);
  },
};
</script>

<style scoped>
.hotPage {
  width: 100%;
}
</style>