<template>
  <div class="comment" v-infinite-scroll="load">
    <div class="backBar">
      <div class="btnBar">
        <button @click="closeAll">
          <i class="iconfont icon-fanhui"></i>
        </button>
        返回
      </div>
    </div>
    <dynamic-tab :dynamicInfo="info" ref="dynamic"></dynamic-tab>
  </div>
</template>

<script>
import DynamicTab from "../components/common/Single/DynamicTab.vue";
export default {
  components: { DynamicTab },
  data() {
    return {
      info: null,
    };
  },
  methods: {
    closeAll() {
      this.$refs.dynamic.openAllCom();
      this.$router.go(-1);
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
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
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
.backBar {
  width: 100%;
  height: auto;
  padding: 10px;
  font-size: 20px;
  display: flex;
  justify-content: flex-start;
  background: white;
  border-radius: 6px;
  font-size: 25px;
}
.backBar button {
  outline: none;
  border: 0;
  font-size: 20px;
  transition: all ease 0.3s;
}
.backBar button i {
  font-weight: bolder;
}
.btnBar:hover button {
  transform: translateX(5px);
  color: royalblue;
}
.btnBar:active button {
  transform: translateX(-4px);
}
</style>