<template>
  <div class="Viewer">
    <!-- 头部搜索区 -->
    <el-affix :offset="0">
      <div class="nav-bar">
        <slot name="topNavBar"></slot>
      </div>
    </el-affix>
    <!-- 顶部提供图片背景插槽和导航栏插槽 -->
    <div class="nav-top">
      <!-- 背景图片插槽 -->
      <div class="backPic">
        <slot name="topBackPic">默认背景</slot>
      </div>
      <!-- 搜索框插槽 -->
      <div class="nav-top-search">
        <slot name="searchArea">默认导航栏</slot>
      </div>
    </div>

    <!-- 板块中心区 -->

    <div class="nav-center">
      <!-- 板块中心内容区 -->

      <div class="nav-center-content">
        <!-- 中心左侧区 -->
        <el-affix :offset="92">
          <div class="nav-center-left">
            <slot name="center-left">左侧区域</slot>
          </div>
        </el-affix>

        <!-- 中心核心区 -->
        <div class="nav-center-center">
          <slot name="userPage"></slot>
          <slot name="center">sdf</slot>
        </div>

        <el-affix :offset="92">
          <div class="nav-center-right">
            <slot name="center-right">右侧区域</slot>
          </div>
        </el-affix>

        <!-- 中心右侧区-->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  created() {
    console.log("加载完了");
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
body {
  display: flex;
  width: 100vw;
  height: 100vh;
  overflow: scroll;
  overflow-x: hidden;
}
.Viewer {
  width: 100vw;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.nav-top {
  width: 100vw;
  background: coral;
  position: relative;
  display: flex;
  /* flex-direction: column; */
  align-items: center;
  justify-content: center;
}
.nav-top .backPic {
  width: 100%;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background: cornflowerblue;
  background: #7f7fd5; /* fallback for old browsers */
  background: -webkit-linear-gradient(
    to right,
    #91eae4,
    #86a8e7,
    #7f7fd5
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(
    to right,
    #91eae4,
    #86a8e7,
    #7f7fd5
  ); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}
.nav-bar {
  width: 100vw;
  padding: 25px;
}
@keyframes show {
  0% {
    transition: scale(0.7);
  }
  50% {
    transition: scale(0.8);
  }
  100% {
    transition: scale(1);
  }
}

.nav-top-search {
  /* display: none; */
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  width: 600px;
  border-radius: 35px;
  height: auto;
  backdrop-filter: blur(20px);
  background: rgba(216, 216, 216, 0.055);
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.2);
}
.nav-center {
  width: 80vw;
  height: auto;
  margin-top: 15px;
}
.nav-center-content {
  display: flex;
  justify-content: space-between;
}
.nav-center-left {
  width: 200px;
  height: 600px;
  background: #fff;
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.2);
}
.nav-center-center {
  margin: 0 10px;
  flex: 1;
  height: 1000px;
  padding: 0 5px;
}
.nav-center-right {
  width: 300px;
  background: #fff;
  border-radius: 8px;

  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.2);
}
.nav-center-left {
  background: #fff;

  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.3);
  border-radius: 8px;
}
nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>