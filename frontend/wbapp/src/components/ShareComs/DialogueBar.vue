<template>
  <div class="bg" v-if="dialogShow">
    <div class="box">
      <div class="title">
        <h2><slot name="title">确认取消吗？</slot></h2>
      </div>
      <div class="content"><slot name="content"></slot></div>
      <div class="btn">
        <button class="cancel" @click="cancel">取消</button>
        <button class="yes" @click="toDel">
          <slot name="yesBtn">确定</slot>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogShow: true,
    };
  },
  emits: ["hideDialog", "yes"],
  methods: {
    cancel() {
      //点击取消执行
      this.$emit("hideDialog");
    },
    toDel() {
      // 点击确认执行 并触发
      this.$emit("yes");

      this.$emit("hideDialog");
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.bg {
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2000;
  height: 100%;
  width: 100%;
  color: cornsilk;
  background: rgba(0, 0, 0, 0.4);
}
.box {
  width: 300px;
  height: 200px;
  z-index: 200;
  border-radius: 5px;
  color: black;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: space-between;
  background: whitesmoke;
  padding: 30px 0;
}
.box .btn {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-around;
}
.box .btn button {
  width: 90px;
  height: 40px;
  font-size: 20px;
  outline: none;
  border: 0;
  border-radius: 20px;
  transition: all ease 0.3s;
}
.box .btn button:hover {
  border: 2px solid black;
}
.box .btn button:active {
  transform: translateY(3px);
}
.box .btn button:nth-child(2) {
  background: royalblue;
}
</style>