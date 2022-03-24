<template>
  <div class="blog">
    <template v-for="n of NowDisNum" :key="n">
      <dynamic-tab :dynamicInfo="dynamics[n]"></dynamic-tab>
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
    Loading: Boolean,
    NowDisNum: Number,
    noMore: Boolean,
  },
  data() {
    return {
      dynamics: this.$store.state.userDynamic,
    };
  },
  //   computed: {
  //     noMore() {
  //       return this.NowDisNum >= 13;
  //     },
  //   },
  created() {
    console.log("nihoa");
    console.log(this.NowDisNum);
    console.log(this.noMore);
    console.log(this.Loading);
  },
};
</script>

<style>
</style>