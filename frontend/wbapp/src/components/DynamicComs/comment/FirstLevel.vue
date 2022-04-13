<template>
  <div :class="[showBack ? 'back' : '']">
    <dialogue-bar v-if="isShowD" @yes="delItem" @hideDialog="isShowD = false">
      <template #title> 真的要删除该条评论吗？</template>
      <template #content> 无法恢复哦！</template>
    </dialogue-bar>
    <div class="box">
      <div class="top" v-show="showBack">
        <div class="goBack">
          <button @click="goBack"><i class="iconfont icon-chahao"></i></button>
        </div>
      </div>
      <div
        class="comItem"
        :class="[showBack ? 'shotBar' : '']"
        v-infinite-scroll="load"
        infinite-scroll-distance="100px"
        style="overflow: auto"
      >
        <set-com
          v-show="isShowCom"
          :dynamicInfo="dynamicInfo"
          :floorId="commentInfo.id"
          :replyId="commentInfo.id"
          :isShowTitle="true"
          :replyName="userName"
          @updateCom="update"
          @close="closeSet"
        ></set-com>

        <div class="profile" @click="showInfo">
          <img :src="profile" alt="用户头像" />
        </div>
        <div class="comInfo">
          <!-- 用户文本区 -->

          <div class="content">
            <div class="userName">{{ userName }}:</div>
            <div class="text">
              <p>{{ text }}</p>
            </div>
          </div>

          <div class="funcBar">
            <div class="time">{{ createdTime }}</div>
            <div class="mulFun">
              <button @click="isShowD = true">
                <i
                  class="iconfont icon-shanchu"
                  style="font-size: 28px"
                ></i></button
              ><button @click="isShowCom = true">
                <i
                  class="iconfont icon-pinglunxiao"
                  style="font-size: 25px"
                ></i>
              </button>
            </div>
          </div>

          <!-- 二级评论展示区 -->
          <div class="secondLevelBar" v-if="isShowSecond">
            <template v-for="item of secComments" :key="item.id">
              <second-level
                :dynamicInfo="dynamicInfo"
                :secondItem="item"
                @getNew="update"
                :floorId="commentInfo.id"
              ></second-level>
            </template>

            <p @click="getAll" v-show="!showBack">点击查看完整评论</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getCreateTime } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/PublicFun.js";
import {
  delComment,
  getComment,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import SecondLevel from "../../DynamicComs/comment/SecondLevel.vue";
import SetCom from "../../ShareComs/SetCom.vue";
import DialogueBar from "../../ShareComs/DialogueBar.vue";
export default {
  components: { SecondLevel, SetCom, DialogueBar },
  props: {
    dynamicInfo: Object,
    commentInfo: Object,
  },
  data() {
    return {
      replyCount: this.commentInfo.replyCount,
      userName: this.commentInfo.user.name,
      text: this.commentInfo.text,
      isShowCom: false,
      secComments: null,
      allComments: null,
      comCount: 2,
      showBack: false,
      profile: this.commentInfo.user.photo,
      isShowD: false,
    };
  },
  methods: {
    showInfo() {
      console.log(this.commentInfo);
    },
    //更新 二级评论数据
    async update() {
      let result = await getComment(this.dynamicInfo.id, this.commentInfo.id);
      this.allComments = result.data.data;
      if (this.showBack) {
        this.secComments = result.data.data.slice(0, 4);
      } else {
        this.secComments = result.data.data.slice(0, 2);
      }
      this.replyCount = this.secComments.length;
      console.log(result);
      console.log("更新了二级楼评论信息");
      console.log(this.secComments);
      return result;
    },
    //删除评论
    async delItem() {
      let result = await delComment(this.commentInfo.id);
      console.log(result);
    },

    getAll() {
      this.showBack = !this.showBack;
      this.secComments = this.allComments;
    },
    closeSet() {
      this.isShowCom = false;
    },
    load() {
      if (this.showBack) {
        console.log("到底了");
        this.comCount++;
      } else return;
    },
    goBack() {
      this.showBack = !this.showBack;
      //默认展示两条
      this.comCount = 2;
    },
  },
  //监听当前展示数量 实现无线加载
  watch: {
    comCount(newCom) {
      this.secComments = this.allComments.slice(0, newCom);
    },
  },
  computed: {
    isShowSecond() {
      return this.replyCount ? true : false;
    },
    createdTime() {
      return getCreateTime(this.commentInfo.createdTime);
    },
  },

  async created() {
    console.log(this.replyCount);
    console.log("create");
    if (this.replyCount) {
      let result = await this.update();
      console.log(result);
      console.log(this.isShowSecond);
      console.log(this.allComments);
    } else console.log("还没有评论块");
    console.log(this.replyCount);
    console.log(this.isShowSecond);

    console.log(this.commentInfo);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.box {
  display: flex;
  flex-direction: column;
}
.back {
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  background: rgba(0, 0, 0, 0.4);
  align-items: center;
  justify-content: center;
  z-index: 1000;
  height: 100%;
  width: 100%;
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
  background: #fff;
  height: auto;
  padding: 5px;
  border-radius: 0 0 8px 8px;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  margin-bottom: 5px;
}

.comItem:hover .mulFun {
  display: block;
}
.shotBar {
  width: 700px;
  padding: 20px;
  overflow: auto;
  max-height: 500px;
}
.top {
  background: white;
  display: flex;
  justify-content: flex-end;
  border-radius: 8px 8px 0 0;
}
.goBack {
  padding: 10px;
}
.goBack button {
  color: black;
  background: transparent;
  outline: none;
  border: 0;
  transition: all ease-in-out 0.3s;
}
.goBack button:hover {
  transform: scale(1.2);
}
.goBack button:active {
  transform: scale(0.9);
}
.profile {
  width: 70px;
  height: 70px;
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
  padding: 5px;
}
.content {
  display: flex;
  justify-content: flex-start;
  height: auto;
  align-items: flex-start;
}
.text {
  height: auto;
  display: flex;
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
  min-height: 55px;
  padding: 5px 0;
  display: flex;
  align-items: center;
  transition: all ease 0.3s;
  justify-content: space-between;
}
.secondLevelBar {
  display: flex;
  flex-direction: column;
}
.secondLevelBar p {
  cursor: pointer;
  transition: all ease 0.3s;
}
.secondLevelBar p:hover {
  color: royalblue;
}
.mulFun {
  display: none;
}
.mulFun button {
  background: transparent;
  outline: none;
  border: 0;
  transition: all ease 0.3s;

  margin: 5px;
}
.mulFun button:hover i {
  color: royalblue;
}
.mulFun button:active {
  transform: scale(0.9);
}
.mulFun button:hover {
  transform: scale(1.2);
}
.mulFun button i {
  padding: 5px;
  transition: all ease 0.3s;
}
</style>