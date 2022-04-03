<template>
  <div class="HomePage" v-infinite-scroll="load">
    <template v-for="n of NowDisNum" :key="n">
      <dynamic-tab :dynamicInfo="dynamics[n - 1]"></dynamic-tab>
    </template>
    <p v-if="Loading" class="Tips">正在加载中</p>
    <p v-if="noMore">没有更多数据了</p>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import { GetPublic } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import DynamicTab from "../../components/common/Single/DynamicTab.vue";

export default {
  components: { DynamicTab },
  data() {
    return {
      NowDisNum: 1,
      Loading: false,
      dynamics: this.$store.state.HomePageDynamic,
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
        }, 2000);
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
  async created() {
    console.log("进入HomePage");
    // console.log(this.$store.state.HomePageDynamic.length);
    // console.log(this.noMore);
    let result = await GetPublic();
    let publicDynamic = result.data.data;
    console.log(publicDynamic);
    this.updateHomePageDynamic(publicDynamic);
  },
};
</script>

<style>
</style>