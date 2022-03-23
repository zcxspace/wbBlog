<template>
  <div class="dynamic">
    <!-- 确认对话框 -->
    <dialogue-bar
      @yes="deleteDynamic"
      @hideDialog="hideShowDialog"
      v-if="isShowDialog"
    >
      <template #title>确认删除该条微博咩？</template>
      <template #content> 该内容将无法恢复</template>
    </dialogue-bar>
    <!-- 编辑动态区 -->
    <set-bar
      :showTop="true"
      :showBack="true"
      :hideAfterSent="true"
      :editInfo="editInfo"
      @SetBar="isShowBar = !isShowBar"
      v-if="isShowBar"
    >
      <template #title>即时编辑</template>
      <template #editText>取消编辑</template>
    </set-bar>
    <!-- 下拉列表区 -->
    <div class="selectMenu" @click="isShowMenu = !isShowMenu">
      字
      <div class="menuBox" v-show="isShowMenu">
        <ul>
          <li @click="showBar(), editDyn()">编辑微博</li>
          <li @click="isShowDialog = true">删除微博</li>
        </ul>
      </div>
    </div>

    <!-- 头部用户信息区 -->
    <div class="userInfo">
      <img src="" alt="" />
      <h2>微博用户001</h2>
    </div>
    <!-- 头部信息区结束 -->

    <!-- 中间内容区 -->
    <div class="content">
      <div class="text">{{ dynamicInfo.text }}</div>
      <div class="photos" v-if="false">
        <!-- <div class="photos" v-if="url.length != 0"> -->
        <ul class="imageBox">
          <li v-for="(u, index) of url" :key="index">
            <img :src="u" alt="" />
          </li>
        </ul>
      </div>
      <!-- 中间内容区结束 -->

      <!-- 转发动态区 -->
      <div class="forwardBar" v-if="false">
        <div class="forwardId">被转发者id</div>
        <div class="forwardText">转发信息</div>
        <div class="forwardPhotos">转发图片</div>
        <div class="forwardTabs">
          <div class="time">发布时间</div>
          <div class="bar">
            <button>转发</button>
            <button>评论</button>
            <button>点赞</button>
          </div>
        </div>
      </div>
    </div>
    <!-- 转发动态区结束 -->
    <div class="tabs">
      <div class="forward"><button>转发</button></div>
      <div class="comments"><button>评论</button></div>
      <div class="likes"><button @click="test">点赞</button></div>
    </div>
  </div>
</template>

<script>
import SetBar from "../../SetBar.vue";
import DialogueBar from "../DialogueBar.vue";
import {
  DeleteDynamic,
  editDynamic,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  components: { SetBar, DialogueBar },
  props: {
    dynamicInfo: Object,
  },
  data() {
    return {
      // 显示右侧功能菜单
      isShowMenu: false,
      isShowDialog: false,
      isShowBar: false,
      // 获取动态id
      dynamicId: this.dynamicInfo.id,
      //打包该动态所编辑的的信息
      editInfo: {
        text: this.dynamicInfo.text,
        // urls:this.dynamicInfo.file,
      },
    };
  },

  methods: {
    async deleteDynamic() {
      let result = await DeleteDynamic(this.dynamicId);
      console.log(result);
    },
    showBar() {
      this.isShowBar = !this.isShowBar;
    },
    hideShowDialog() {
      this.isShowDialog = !this.isShowDialog;
    },
    async editDyn() {
      let result = await editDynamic(this.dynamicId, this.text, this.file);
      console.log(result);
    },
  },
};
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}
li {
  list-style: none;
}
.dynamic {
  position: relative;
  width: 100%;
  height: auto;
  padding: 20px 20px 0 20px;
  background: rgb(219, 215, 215);
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin: 10px 0;
}
.userInfo {
  height: 80px;
  width: 100%;
  display: flex;
  justify-content: flex-start;
  /* background: chartreuse; */
}
.userInfo h2 {
  margin-top: 10px;
  display: inline-block;
  font-size: 20px;
}
.userInfo img {
  height: 100%;
  width: 80px;
  margin-right: 10px;
  background: royalblue;
  border-radius: 50%;
}
.tabs {
  width: 100%;
  height: 40px;
  display: flex;
  align-items: center;
}
.tabs div {
  flex: 1;
  width: 100%;
  height: 100%;
  transition: all ease 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
}
.tabs div:hover {
  color: chocolate;
}
.tabs div button {
  width: 100%;
  height: 100%;
  background: transparent;
  border: 0;
}
.content {
  height: auto;
  width: 100%;
  padding-left: 85px;
}
.text {
  min-height: 50px;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  /* background: yellow; */
}
.photos {
  flex: 1;
  width: 100%;
  min-height: 140px;

  /* background: chocolate; */
}
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
.selectMenu {
  width: 35px;
  height: 35px;
  border: 2px solid black;
  position: absolute;
  right: 10px;
  top: 10px;
}
.menuBox {
  position: absolute;
  top: 50px;
  right: 0;
  width: 120px;
  height: auto;
}
.menuBox ul {
  width: 100%;
  height: 100%;
  display: flex;
  background: rgb(252, 252, 252);
  padding: 10px;
  flex-direction: column;
  border-radius: 8px;
}
.menuBox ul li {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 5px 0;
  height: 30px;
  border-radius: 8px;
  transition: all ease 0.25s;
}
.menuBox ul li:hover {
  background: lightgrey;
}
</style>