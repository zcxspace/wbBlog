<template>
  <div class="leftBar">
    <div class="title">
      <h2><slot name="title">侧边栏标题</slot></h2>
    </div>
    <div class="tabs">
      <ul class="tabBox">
        <!-- <li v-for="tab of tabsArr" :key="tab.com"> -->
        <!-- <div class="icon"><i class="inconfont" :class="tab.icon"></i>字体图标</div> -->
        <li v-for="com of ComArr" :key="com.comName">
          <div class="icon">字体</div>
          <router-link
            :to="{ name: `${com.comName}`, params: { type: `${com.type}` } }"
            >{{ com.comName }}</router-link
          >
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    ComArr: Array,
  },
  data() {
    return {};
  },
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
.leftBar {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.title {
  width: 100%;
  height: 50px;
  padding: 10px 16px;
  display: flex;
  justify-content: flex-start;
}
.tabs {
  width: 100%;
}
.tabBox {
  flex: 1;
  width: 100%;
  height: 100px;
}
.tabBox li {
  width: 100%;
  height: 45px;
  font-size: 20px;
  font-weight: 800;
  display: flex;
  border-radius: 5px;
  align-items: center;
  transition: all ease 0.3s;
}
.active {
  color: rgb(199, 132, 9);
}
.tabBox li:hover {
  background: rgb(228, 226, 226);
}
.icon {
  margin: 0 15px;
}
</style>