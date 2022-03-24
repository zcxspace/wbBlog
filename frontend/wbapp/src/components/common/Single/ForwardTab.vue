<template>
  <div class="forwardBar">
    <div class="forwardId">被转发者id</div>
    <div class="forwardText">转发信息</div>
    <div class="forwardPhotos" v-if="photos">
      <ul>
        <li v-for="(url, index) of pics" :key="index">
          <img :src="url" alt="转发的图片" />
        </li>
      </ul>
    </div>
    <div class="forwardTabs">
      <div class="time">发布时间</div>
      <div class="bar">
        <button>转发</button>
        <button>评论</button>
        <button>点赞</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    forwardDynamic: Object,
  },
  data() {
    return {
      text: this.forwardDynamic.text,
      pics: this.forwardDynamic.filePath,
    };
  },
  computed: {
    forwardInfo() {
      return JSON.stringify(this.forwardDynamic);
    },
    photos() {
      return JSON.stringify(this.forwardDynamic) != {} ? true : false;
    },
  },
};
</script>

<style scoped>
.forwardBar {
  width: 100%;
  height: auto;
  display: flex;
  align-items: center;
  flex-direction: column;
  /* background: darkgoldenrod; */
}
.forwardId {
  width: 100%;
  height: 30px;
  display: flex;
  align-items: center;
  background: purple;
}
.forwardText {
  width: 100%;
  min-height: 60px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  background: pink;
}
.forwardTabs {
  width: 100%;
  height: 30px;
  display: flex;
  background: hotpink;
  justify-content: space-between;
  align-items: center;
}
.forwardPhotos {
  width: 100%;
  min-height: 135px;
  background: green;
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
  background: red;
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
  background: red;
}
.bar div button {
  width: 40px;
}
.time {
  width: auto;
  background: red;
}
</style>