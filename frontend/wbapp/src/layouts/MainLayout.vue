<template>
  <div class="Viewer">
    <!-- 头部搜索区 -->
    <div class="nav-top" v-if="isShowTop">
      <div class="nav-top-search">
        <slot name="searchArea">默认搜索区域</slot>
      </div>
    </div>

    <slot name="top-bar">头部导航栏</slot>

    <!-- 板块中心区 -->

    <div class="nav-center" :class="[isShowTop ? '' : 'topPadding']">
      <!-- 板块中心内容区 -->

      <div class="nav-center-content">
        <!-- 中心左侧区 -->
        <el-affix :offset="80">
          <div class="nav-center-left">
            <slot name="center-left">左侧区域</slot>
          </div>
        </el-affix>

        <!-- 中心核心区 -->
        <div class="nav-center-center">
          <slot name="userPage"></slot>
          <slot name="center"></slot>
        </div>

        <el-affix :offset="80">
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
  props: {
    isShowTop: Boolean,
  },
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
.topPadding {
  margin-top: 80px;
}
.nav-top {
  width: 100vw;
  background: coral;
  height: 330px;
}
.nav-top-bar i {
  font-size: 40px;
  transition: color ease 0.2s;
}
.nav-top-bar i:hover {
  color: orange;
}
.nav-top {
  display: flex;
  align-items: center;
  justify-content: center;
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
.nav-top:hover .nav-top-search {
  /* display: block; */
  opacity: 1;
}
.nav-top-search {
  /* display: none; */
  opacity: 0;
  height: 60px;
  width: 600px;
  animation: show 1s ease 0s;
  /* display: flex;
  align-items: center;
  justify-content: center; */
  border-radius: 30px;
  background: #fff;
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.2);
}
.nav-center {
  width: 80vw;
  height: auto;
}
.nav-center-content {
  display: flex;
  justify-content: space-between;
}
.nav-center-left {
  width: 200px;
  height: 800px;
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
  height: 800px;
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