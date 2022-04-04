<template>
  <div :class="[showBack ? 'back' : '']">
    <div class="box">
      <div class="top" v-show="showBack">
        <div class="goBack"><button @click="goBack">关闭</button></div>
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

        <div class="profile" @click="showInfo"></div>
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
              {{ replyCount }} <button @click="delItem">删除</button
              ><button @click="isShowCom = true">评论</button>
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
import SecondLevel from "./SecondLevel.vue";
import SetCom from "./SetCom.vue";
export default {
  components: { SecondLevel, SetCom },
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
  align-items: center;
  justify-content: center;
  z-index: 1000;
  height: 100%;
  width: 100%;
  background: rgba(0, 0, 0, 0.8);
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
  background: #f2f6fc;
  padding: 5px;
  border-radius: 8px;
  display: flex;
  align-items: flex-start;
  justify-content: flex-start;
  margin-bottom: 5px;
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
}
.goBack button {
  color: black;
}
.profile {
  width: 70px;
  height: 70px;
  background: chocolate;
  margin-right: 5px;
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
  height: auto;
  padding: 5px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.secondLevelBar {
  display: flex;
  flex-direction: column;
}
</style>