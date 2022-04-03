<template>
  <div class="blog" v-show="userDynamicNum" v-infinite-scroll="load">
    <template v-for="n of NowDisNum" :key="n">
      <dynamic-tab
        :userInfo="userInfo"
        :dynamicInfo="dynamics[n - 1]"
        v-if="userDynamicNum"
      ></dynamic-tab>
    </template>
    <p v-if="Loading" class="Tips">正在加载中</p>
    <p v-if="noMore">没有更多数据了</p>
  </div>
</template>

<script>
import dynamicTab from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/components/common/Single/DynamicTab.vue";
export default {
  components: {
    dynamicTab,
  },
  props: {
    dynamics: Array,
    userInfo: Object,
  },
  data() {
    return {
      Loading: false,
      NowDisNum: 1,
    };
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
    console.log(this.userInfo);
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