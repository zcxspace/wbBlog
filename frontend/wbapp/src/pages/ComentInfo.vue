<template>
  <div class="comment" v-infinite-scroll="load">
    <button @click="closeAll">返回</button>
    <dynamic-tab :dynamicInfo="info" ref="dynamic"></dynamic-tab>
    <button @click="open">点击展开评论区</button>
    <ul class="box">
      <li v-for="n of num" :key="n">{{ n }}</li>
    </ul>
    <button @click="getHi">点击</button>
  </div>
</template>

<script>
import DynamicTab from "../components/common/Single/DynamicTab.vue";
export default {
  components: { DynamicTab },
  data() {
    return {
      info: null,
      num: 0,
    };
  },
  methods: {
    closeAll() {
      this.$refs.dynamic.openAllCom();
      this.$router.go(-1);
    },
    load() {
      console.log("触发了");
      this.num++;
    },
    getHi() {
      this.$refs.dynamic.get();
    },
  },

  mounted() {
    this.$refs.dynamic.showComment();
    this.$refs.dynamic.openAllCom();
  },
  created() {
    this.info = JSON.parse(this.$route.params.info);
  },
};
</script>

<style scoped>
.box {
  width: 100%;
  background: seagreen;
  max-height: 400px;
}
.box li {
  width: 100%;
  height: 40px;
  background: royalblue;
  margin: 10px 0;
  list-style: none;
}
</style>