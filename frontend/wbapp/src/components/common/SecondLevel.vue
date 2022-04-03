<template>
  <div class="comItem">
    <set-com
      :isShowTitle="true"
      :dynamicInfo="dynamicInfo"
      :replyId="this.secondItem.id"
      :floorId="floorId"
      :replyName="secondItem.user.name"
      @close="closeSet"
      v-show="isShowSet"
    ></set-com>
    <div class="profile"></div>
    <div class="comInfo">
      <!-- 用户文本区 -->
      <div class="content">
        <div class="userName">{{ secondItem.user.name }}</div>
        <div class="text">
          <p>{{ secondItem.text }}</p>
        </div>
        <div class="replyInfo" v-if="hasReplyText">
          <a href="#"> {{ replyText.name }}</a>
          >{{ replyText.text }}
        </div>
      </div>

      <div class="funcBar">
        <div class="time">时间</div>
        <div class="mulFun">
          <button @click="delItem">删除</button
          ><button @click="isShowSet = true">评论</button>
        </div>
      </div>
      <!-- 内容区阿斯顿发生地方 -->
      <!-- 二级评论展示区 -->
    </div>
  </div>
</template>

<script>
import { delComment } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import SetCom from "./SetCom.vue";
export default {
  props: {
    dynamicInfo: Object,
    secondItem: Object,
    floorId: Number,
  },
  components: { SetCom },
  data() {
    return {
      isShowSet: false,
      replyText: this.secondItem.replyText,
    };
  },
  computed: {
    hasReplyText() {
      return this.secondItem.replyText ? true : false;
    },
  },
  methods: {
    closeSet() {
      this.isShowSet = false;
    },
    async delItem() {
      let result = await delComment(this.secondItem.id);
      console.log(result);
    },
  },
  created() {
    console.log(this.secondItem.replyText);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.secondLevel {
  width: 100%;
  height: auto;
  background: ivory;
  padding-left: 50px;
  display: flex;
  justify-content: flex-start;
}
.comItem {
  width: 100%;
  /* min-height: 100px; */
  height: auto;
  padding: 5px;
  background: whitesmoke;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
}
.profile {
  width: 100px;
  height: 100px;
  background: chocolate;
  margin-right: 5px;
}
.comInfo {
  display: flex;
  flex-direction: column;

  flex: 1;
  height: 100%;
  background: cornflowerblue;
}
.replyInfo {
  width: 100%;
  background: khaki;
}
.content {
  display: flex;
  justify-content: flex-start;
  min-height: 65px;
  flex-direction: column;
  align-items: flex-start;
  background: crimson;
}
.text,
.replyInfo {
  height: auto;
  display: flex;
  margin: 5px 0;
  background: gold;
}
.text p {
  width: 100%;
  display: flex;
  text-align: start;
  /* word-wrap: break-word; */
  word-break: break-all;
}

.userName {
  width: auto;
  background: chartreuse;
  white-space: nowrap;
}

.funcBar {
  height: auto;
  background: cyan;
  padding: 5px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>