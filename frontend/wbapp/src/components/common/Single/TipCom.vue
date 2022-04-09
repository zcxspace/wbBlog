<template>
  <div class="tip" v-if="isShowTip">
    <div class="tipTab" :class="style">
      <div class="icon"><i class="iconfont" :class="icon"></i></div>
      <div class="tipText">{{ tipText }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    //提示类型
    type: String,
    //提示文字
    tipText: String,
  },

  data() {
    return {
      isShowTip: true,
      icon: null,
    };
  },
  computed: {
    style() {
      if (this.type == "success") {
        return "success";
      } else if (this.type == "error") {
        return "error";
      } else return "";
    },
  },

  created() {
    if (this.type == "success") {
      this.icon = "icon-zhengque1";
    }
    setTimeout(() => {
      this.isShowTip = false;
    }, 2000);
    // console.log(this.type);
  },
};
</script>

<style scoped>
.tip {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
  z-index: 3000px;
}
.tipTab {
  /* z-index: 2000; */
  position: fixed;
  left: 50%;
  top: 11%;
  transform: translateX(-50%);
  width: auto;
  height: 75px;
  font-size: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 4px;
  border-radius: 8px;
  animation: show 0.5s linear 0s 1;
  /* transition: all ease 0.3s; */
}
.success {
  background: rgb(243, 247, 243);
  border: 3px solid rgb(79, 214, 79);
}
.error {
  background: rgb(245, 231, 234);
  border: 3px solid rgb(247, 61, 61);
}
.tipTab div {
  padding: 0 5px;
}
@keyframes show {
  0% {
    transform: translate(-50%, -20px);
  }
  50% {
    transform: translate(-50%, -15px);
  }
  75% {
    transform: translate(-50%, -5px);
  }
  100% {
    transform: translate(-50%, 0px);
  }
}
</style>