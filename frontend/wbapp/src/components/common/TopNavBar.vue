<template>
  <div class="top-bar">
    <div class="tach-bar"></div>
    <div class="top-bar-center">
      <div class="left">
        <div class="left-search">
          <label for="input"><i class="iconfont icon-sousuo"></i></label>

          <input id="input" type="text" />
        </div>
      </div>
      <div class="center">
        <ul>
          <li><slot name="center1">center1</slot></li>
          <li><slot name="center2">center2</slot></li>
          <li><slot name="center3">center3</slot></li>
          <li><slot name="center4">center4</slot></li>
          <li><slot name="center5">center5</slot></li>
        </ul>
      </div>
      <div class="right">
        <ul>
          <li><slot name="right1">right1</slot></li>
          <li><slot name="right2">right2</slot></li>
          <li><slot name="right3">right3</slot></li>
          <li><slot name="right4">right4</slot></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {},
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
li {
  list-style: none;
}
.top-bar {
  z-index: 1000;
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  width: 100vw;
  height: 80px;
  justify-content: space-between;
  background: royalblue
    url("https://h5.sinaimg.cn/upload/100/1733/2022/03/02/3.3.png") no-repeat;
  background-size: contain;
  background-position: -75px;
}

.top-bar-center {
  width: 80vw;
  height: 100%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}
.left {
  width: 200px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.left-search {
  width: 100%;
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: gray;
  padding: 3px;
  border-radius: 20px;
}
.left-search input {
  text-indent: 4px;
  font-size: 20px;
  width: 150px;
  border: 0;
  background: transparent;
  outline: none;
}
.left-search i {
  font-size: 25px;
}
.right {
  width: 300px;
  height: 100%;
  background: red;
}

.center {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: gray;
}
.center ul,
.right ul {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.right ul {
  justify-content: flex-end;
}
.center li,
.right li {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70px;
  height: 100%;
  margin: 0 2px;
}

.tach-bar {
  position: absolute;
  left: 10px;
  top: 0;
  width: 50px;
  height: 100%;
  border: 2px solid red;
}
</style>