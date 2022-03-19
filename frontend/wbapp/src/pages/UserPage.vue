<template>
  <div class="page">
    <main-layout>
      <template #center-left>
        <ul class="leftBar">
          <li
            v-for="(list, index) of leftLists"
            :key="list.com"
            @click="nowComActive = list.com"
          >
            {{ list.title }} {{ index }}
          </li>
        </ul>
      </template>

      <template #center>
        <keep-alive>
          <component :is="nowComActive"></component>
        </keep-alive>
      </template>

      <template #center-right>右侧</template>
    </main-layout>
  </div>
</template>

<script>
import MainLayout from "../layouts/MainLayout.vue";
import MainPage from "../components/common/UserInfo/MainPage.vue";
import ChangeInfo from "../components/common/UserInfo/ChangeInfo.vue";

export default {
  components: {
    MainLayout,
    MainPage,
    ChangeInfo,
  },
  data() {
    return {
      isShowTop: false,
      nowComActive: "MainPage",
      leftLists: [
        { title: "我的主页", com: "MainPage" },
        { title: "编辑信息", com: "ChangeInfo" },
      ],
    };
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.leftBar {
  width: 100%;
  height: auto;
}
.leftBar li {
  list-style: none;
  width: 100%;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: black;
  margin-bottom: 5px;
}
</style>
