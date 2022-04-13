<template>
  <div class="comItem2">
    <dialogue-bar v-if="isShowD" @yes="delItem" @hideDialog="isShowD = false">
      <template #title>确认删除该条评论吗？</template>
      <template #content> 无法恢复哦！</template>
    </dialogue-bar>
    <set-com
      :isShowTitle="true"
      :dynamicInfo="dynamicInfo"
      :replyId="this.secondItem.id"
      :floorId="floorId"
      :replyName="secondItem.user.name"
      @updateCom="update"
      @close="closeSet"
      v-show="isShowSet"
    ></set-com>
    <div class="profile" @click="showInfo">
      <img :src="profile" alt="用户头像" />
    </div>
    <div class="comInfo">
      <!-- 用户文本区 -->
      <div class="content">
        <div class="userName">{{ secondItem.user.name }}</div>
        <div class="text">
          <p>{{ secondItem.text }}</p>
        </div>
        <div class="replyInfo" v-if="hasReplyText">
          <a href="#"> {{ replyText.name }}</a>
          :{{ replyText.text }}
        </div>
      </div>

      <div class="funcBar">
        <div class="time">{{ createdTime }}</div>
        <div class="mulFun">
          <button @click="isShowD = true">
            <i
              class="iconfont icon-shanchu"
              style="font-size: 30px"
            ></i></button
          ><button @click="isShowSet = true">
            <i class="iconfont icon-pinglunxiao"></i>
          </button>
        </div>
      </div>
      <!-- 内容区阿斯顿发生地方 -->
      <!-- 二级评论展示区 -->
    </div>
  </div>
</template>

<script>
import { getCreateTime } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/PublicFun.js";
import { delComment } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import SetCom from "../../ShareComs/SetCom.vue";
import DialogueBar from "../../ShareComs/DialogueBar.vue";
export default {
  props: {
    dynamicInfo: Object,
    secondItem: Object,
    floorId: Number,
  },
  emits: ["getNew"],
  components: { SetCom, DialogueBar },
  data() {
    return {
      isShowSet: false,
      replyText: this.secondItem.replyText,
      profile: this.$store.state.userInfo.photo,
      isShowD: false,
    };
  },
  computed: {
    hasReplyText() {
      return this.secondItem.replyText ? true : false;
    },
    createdTime() {
      return getCreateTime(this.secondItem.createdTime);
    },
  },
  methods: {
    showInfo() {
      console.log(this.secondItem);
    },
    update() {
      console.log("我要开始更新了");
      this.$emit("getNew");
      console.log("更新了二级楼的二级评论");
    },
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
.comItem2 {
  width: 100%;
  /* min-height: 100px; */
  border-left: 3px solid rgba(31, 31, 31, 0.4);
  height: auto;
  padding: 5px;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
}
.comItem2:hover .mulFun {
  opacity: 1;
}
.profile {
  width: 60px;
  height: 60px;
  overflow: hidden;
  border-radius: 50%;
  margin-right: 5px;
}
.profile img {
  width: 100%;
  height: 100%;
}
.comInfo {
  display: flex;
  flex-direction: column;

  flex: 1;
  height: 100%;
}

.replyInfo {
  width: auto;
  height: auto;
  padding: 5px;
  border-radius: 5px;
  background: rgb(233, 231, 231);
}
.content {
  display: flex;
  justify-content: flex-start;
  height: auto;
  flex-direction: column;
  align-items: flex-start;
}
.text,
.replyInfo {
  height: auto;
  display: flex;
  margin: 5px 0;
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
  white-space: nowrap;
}

.funcBar {
  height: auto;
  padding: 5px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.mulFun {
  opacity: 0;
}
.mulFun button {
  outline: none;
  border: 0;
  padding: 5px;
  margin: 5px;
  transition: all ease 0.3s;
}
.mulFun button:hover {
  color: royalblue;
  transform: scale(1.2);
}
.mulFun button:active {
  transform: scale(0.9);
}
</style>