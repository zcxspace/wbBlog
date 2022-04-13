<template>
  <div class="forwardBar">
    <div class="forwardId">{{ getRootName }}</div>
    <div class="forwardText">
      <template
        v-for="(item, index) of getStr(forwardDynamic.text)"
        :key="index"
      >
        <a v-if="item.match(/#[^#]+#/gi)" href="#">
          {{ item }}
        </a>
        <span v-else>{{ item }}</span>
      </template>
    </div>
    <div class="forwardPhotos" v-if="hasPhotos">
      <ul>
        <li v-for="(url, index) of picPaths" :key="index">
          <img :src="url" alt="转发的图片" />
        </li>
      </ul>
    </div>
    <div class="forwardTabs">
      <div class="time">{{ createdTime }}</div>
      <div class="bar">
        <button>转发</button>
        <button>评论</button>
        <button>点赞</button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getCreateTime,
  splitStr,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/PublicFun.js";
export default {
  props: {
    forwardDynamic: Object,
    forwardTexts: Object,
  },
  data() {
    return {
      picPaths: this.forwardDynamic.filePath,
    };
  },
  methods: {
    getStr(str) {
      return splitStr(str);
    },
  },
  computed: {
    hasPhotos() {
      return this.forwardDynamic.filePath ? true : false;
    },
    getRootName() {
      return this.forwardTexts.slice().pop().name;
    },
    createdTime() {
      return getCreateTime(this.forwardDynamic.createdTime);
    },
  },
};
</script>

<style scoped>
a {
  text-decoration: none;
  color: royalblue;
}
.forwardBar {
  width: 100%;
  height: auto;
  display: flex;
  align-items: center;
  color: rgb(66, 65, 65);
  flex-direction: column;
  /* background: rgb(218, 216, 216); */
  background: rgba(156, 155, 155, 0.2);

  padding: 6px;
  border-radius: 10px;
  /* background: darkgoldenrod; */
}
.forwardId {
  width: 100%;
  height: 30px;
  display: flex;
  align-items: center;
}
.forwardText {
  width: 100%;
  min-height: 60px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.forwardTabs {
  width: 100%;
  height: 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.forwardPhotos {
  width: 100%;
  min-height: 135px;
}
.forwardPhotos ul {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: flex-start;
}
.forwardPhotos ul li {
  width: 133px;
  height: 133px;
  border-radius: 5px;
  overflow: hidden;
  margin: 10px;
}
.forwardPhotos ul li img {
  width: 100%;
  height: 100%;
}
.photos .imageBox {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  gap: 30px;
}
.photos .imageBox li {
  width: 133px;
  height: 135px;
  list-style: none;
  border-radius: 10px;
  overflow: hidden;
}
.photos .imageBox li img {
  width: 100%;
  height: 100%;
}
.bar {
  height: 100%;
  width: auto;
  display: flex;
  align-items: center;
  justify-content: center;
}
.bar div {
  flex: 1;
}
.bar button {
  width: 50px;
  border: 0;
  outline: none;
  color: rgb(66, 65, 65);

  margin: 0 4px;
  transition: all ease 0.3s;
  background: transparent;
}
.bar button:hover {
  background: rgb(161, 154, 154);
}
.time {
  width: auto;
}
</style>