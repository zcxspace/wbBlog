<template>
  <div class="leftBar">
    <div class="title" style="font-size: 20px">
      <h2><slot name="title">侧边栏标题</slot></h2>
    </div>
    <div class="tabs">
      <ul class="tabBox">
        <li v-for="com of ComArr" :key="com.comName">
          <div class="icon">
            <i class="iconfont" :class="com.icon" :style="com.size"></i>
          </div>
          <router-link
            :to="{ name: `${com.comName}`, params: `${com.params}` }"
            v-if="com.params"
            >{{ com.title }}</router-link
          >
          <router-link :to="{ name: `${com.comName}` }" v-else>{{
            com.title
          }}</router-link>
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
  created() {
    console.log(this.ComArr);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
a {
  text-decoration: none;
  color: black;
}
a:hover {
}
li:hover a,
li:hover i {
  color: royalblue;
}
li {
  list-style: none;
}
i {
  font-size: 35px;
}
.leftBar {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 3px;
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
  justify-content: flex-start;
  padding-left: 4px;
  transition: all ease 0.3s;
}
.active {
  color: rgb(199, 132, 9);
}
.tabBox li:hover {
  background: rgb(243, 241, 241);
}
.icon {
  margin: 0 15px;
}
</style>