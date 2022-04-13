<template>
  <div class="HomePage" v-infinite-scroll="load" infinite-scroll-distance="50">
    <div v-if="showSk" class="sk">
      <template v-for="n of 3" :key="n">
        <skeleton-com></skeleton-com>
      </template>
    </div>
    <div v-if="!showSk">
      <template v-for="n of NowDisNum" :key="n - 1">
        <dynamic-tab
          :dynamicInfo="dynamics[n - 1]"
          :userInfo="dynamics[n - 1].user"
        ></dynamic-tab>
      </template>
      <div v-if="!showSk">
        <loading-com v-if="Loading" :text="'疯狂加载中'"></loading-com>
      </div>

      <!-- <p v-if="Loading" class="Tips">正在加载中</p> -->
      <p v-if="noMore">没有更多数据了</p>
    </div>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import { GetPublic } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import DynamicTab from "../../components/DynamicComs/DynamicTab.vue";
import SkeletonCom from "../../components/LoadingComs/SkeletonCom.vue";

export default {
  components: { SkeletonCom, DynamicTab },
  data() {
    return {
      NowDisNum: null,
      Loading: false,
      dynamics: this.$store.state.HomePageDynamic,
      showSk: true,
    };
  },
  methods: {
    ...mapMutations(["updateHomePageDynamic"]),
    load() {
      console.log("触发了load函数");
      //根据当前动态数量来判断是否加载
      if (!this.noMore) {
        this.Loading = true;
        setTimeout(() => {
          //如果当前展示数量等于动态数量 则停止任务
          if (this.NowDisNum == this.$store.state.HomePageDynamic.length)
            return;
          this.NowDisNum++;
          this.Loading = false;
          console.log(this.NowDisNum);
        }, 1500);
      } else console.log("没有动态了");
    },
  },
  computed: {
    noMore() {
      return this.NowDisNum >= this.$store.state.HomePageDynamic.length;
    },
    isDisPlay() {
      return this.$store.state.HomePageDynamic ? true : false;
    },
  },
  mounted() {},
  async created() {
    console.log(Date.now());

    // console.log(this.$store.state.HomePageDynamic.length);
    // console.log(this.noMore);
    let result = await GetPublic();
    console.log(result);
    console.log("sd");
    let publicDynamic = result.data.data;

    this.updateHomePageDynamic(publicDynamic);
    //store中数据更新 但是如果该组件属性绑定此数据 数据不会进行更新
    console.log(publicDynamic);
    this.dynamics = this.$store.state.HomePageDynamic;
    console.log(this.$store.state.HomePageDynamic.length);
    let dynamicNum = this.$store.state.HomePageDynamic.length;

    console.log("执行完了");
    console.log(this.showSk);
    setTimeout(() => {
      this.showSk = false;
      if (dynamicNum >= 4) {
        this.NowDisNum = 4;
      } else {
        this.NowDisNum = dynamicNum;
      }
    }, 1500);
  },
};
</script>

<style scoped>
</style>