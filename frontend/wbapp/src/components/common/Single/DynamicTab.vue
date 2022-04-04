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
      :textareaHeight="4"
      :useTo="'edit'"
      :editId="dynamicId"
      :placeholder="'有什么新鲜事想分享给大家？'"
      @SetBar="isShowBar = !isShowBar"
      v-if="isShowBar"
    >
      <template #title>即时编辑</template>
      <template #editText>取消编辑</template>
    </set-bar>
    <!-- 下拉列表区 -->
    <div class="selectMenu" @click="isShowMenu = !isShowMenu" v-if="checkUser">
      字
      <div class="menuBox" v-show="isShowMenu">
        <ul>
          <li @click="showBar()">编辑微博</li>
          <li @click="isShowDialog = true">删除微博</li>
        </ul>
      </div>
    </div>

    <!-- 头部用户信息区 -->
    <div class="userInfo">
      <img :src="profile" alt="用户头像" @click="test1" />
      <h2>{{ getName }}</h2>
    </div>
    <!-- 头部信息区结束 -->

    <!-- 中间内容区 -->
    <div class="content">
      <div class="text">
        <div class="forwardT">
          <!-- 如果是不是转发 则显示正常文本 -->
          <div class="normal" v-if="isShowText">
            {{ this.dynamicInfo.text }}
          </div>
          <!-- 如果是 则遍历转发数据 -->
          <div class="forwardText" v-else>
            {{ this.dynamicInfo.text }}
            <template
              v-for="(item, index) of this.dynamicInfo.forwardTexts"
              :key="index"
            >
              <router-link
                :to="{
                  name: 'RandomInfo',
                  params: { path: `${item.profileUrl.match(/u\d+/)[0]}` },
                }"
                >@{{ item.name }}</router-link
              >:{{ item.text }}
            </template>
          </div>
        </div>
      </div>
      <div class="photos" v-if="hasPhotos">
        <!-- 图片区 -->
        <!-- <div class="photos" v-if="url.length != 0"> -->
        <ul class="imageBox">
          <li v-for="(u, index) of pics" :key="index">
            <img :src="u" alt="" />
          </li>
        </ul>
      </div>
      <!-- 中间内容区结束 -->

      <!-- 转发动态区 -->
      <forward-tab
        v-if="this.dynamicInfo.forwardDynamicId"
        :forwardDynamic="this.dynamicInfo.forwardDynamic"
        :forwardTexts="this.dynamicInfo.forwardTexts"
      ></forward-tab>
    </div>
    <!-- 转发动态区结束 -->
    <div class="tabs">
      <div class="forward"><button @click="showForward">转发</button></div>
      <div class="comments"><button @click="showComment">评论</button></div>
      <div class="likes">
        <button @click="test" :class="[isLike ? 'like' : '']">点赞</button>
      </div>
    </div>
    <!-- 转发编辑模块 -->
    <div class="functionBar" v-show="isForwardShow">
      <div class="avatar">
        <img src="" alt="" />
      </div>
      <div class="editArea">
        <!-- 转发区 -->
        <div class="forwardBar">
          <set-bar
            class="setBar"
            :showBack="false"
            :showTop="false"
            :textareaHeight="1"
            :hideAfterSent="false"
            :placeholder="'分享说说心得'"
            :useTo="'forward'"
            :editInfo="{}"
            :forwardId="dynamicId"
          >
            <template #btnName>转发</template>
          </set-bar>
        </div>
      </div>
    </div>

    <div class="functionBar" v-show="isCommentShow">
      <!-- 显示评论区时再渲染评论组件 -->
      <comment-bar
        :dynamicInfo="dynamicInfo"
        v-if="isCommentShow"
        :openAll="openAll"
      ></comment-bar>
    </div>
  </div>
</template>

<script>
import SetBar from "../../SetBar.vue";
import DialogueBar from "../DialogueBar.vue";
import ForwardTab from "./ForwardTab.vue";
import { mapMutations } from "vuex";
import {
  DeleteDynamic,
  isLike,
  GetPublic,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import CommentBar from "../../CommentBar.vue";
export default {
  components: { SetBar, DialogueBar, ForwardTab, CommentBar },

  props: {
    dynamicInfo: Object,
    userInfo: Object,
  },
  data() {
    return {
      openAll: false,
      // 显示右侧功能菜单
      isShowMenu: false,
      // 展示确认对话框
      isShowDialog: false,
      //展示编辑框
      isShowBar: false,
      // 获取动态id
      dynamicId: this.dynamicInfo.id,
      //打包该动态所编辑的的信息
      editInfo: {
        text: this.dynamicInfo.text,
        Urls: this.dynamicInfo.filePath,
      },
      //默认隐藏评论与转发
      isForwardShow: false,
      isCommentShow: false,
      //切换点赞效果
      isLike: false,
      //头像地址
    };
  },
  computed: {
    getName() {
      //若user为不为null 则返回user.name 否则返回提供的信息
      // 当user为null时 如果访问 user.属性 则会报错！！！！！
      return this.dynamicInfo.user
        ? this.dynamicInfo.user.name
        : this.userInfo.name;
    },
    //没有user则显示 功下拉框
    checkUser() {
      return this.dynamicInfo.userId == this.$store.state.userInfo.id
        ? true
        : false;
    },
    hasPhotos() {
      return this.dynamicInfo.filePath ? true : false;
    },
    pics() {
      return this.dynamicInfo.filePath ? this.dynamicInfo.filePath : [];
    },

    //处理转发信息
    isShowText() {
      //如果是转发
      if (this.dynamicInfo.forwardDynamicId) {
        return false;
      } else {
        return true;
      }
    },
    profile() {
      return this.dynamicInfo.user
        ? this.dynamicInfo.user.photo
        : this.$store.state.userInfo.photo;
    },
  },
  methods: {
    openAllCom() {
      // this.$refs.comment.sayHi();
      this.openAll = !this.openAll;
    },

    ...mapMutations(["updateHomePageDynamic"]),
    //删除动态接口
    async deleteDynamic() {
      let result = await DeleteDynamic(this.dynamicId);
      console.log(this.dynamicId);
      console.log(result);

      let publicD = await GetPublic();
      let publicDynamic = publicD.data.data;
      this.updateHomePageDynamic(publicDynamic);
    },
    //点击切换评论与转发模块
    showForward() {
      this.isForwardShow = !this.isForwardShow;
      this.isCommentShow = false;
    },
    showComment() {
      this.isCommentShow = !this.isCommentShow;
      this.isForwardShow = false;
    },
    getComment() {},
    showBar() {
      this.isShowBar = !this.isShowBar;
    },
    hideShowDialog() {
      this.isShowDialog = !this.isShowDialog;
    },

    //点赞接口
    async test() {
      this.isLike = !this.isLike;
      if (this.isLike) {
        let result = await isLike(this.dynamicId, "Like");
        console.log(result);
      } else {
        let result = await isLike(this.dynamicId, "Dislike");
        console.log(result);
      }
    },
    test1() {
      console.log(this.dynamicId);
      console.log(this.dynamicInfo);
      console.log(this.dynamicInfo.user);
    },
  },
  created() {
    console.log(this.dynamicInfo.userId);
    console.log(this.hasPhotos);
    console.log(this.userInfo);
    // console.log(this.$store.state.userInfo.id);
    // console.log(this.dynamicInfo.user.id);
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
  background: #fff;
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.1);
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
.tabs div:hover button {
  color: chocolate;
}
.tabs div button {
  width: 100%;
  height: 100%;
  outline: none;
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
}

.forwardT div {
  display: flex;
  justify-content: flex-start;
  flex-flow: row wrap;
}
.photos {
  flex: 1;
  width: 100%;
  min-height: 140px;

  /* background: chocolate; */
}
.photos .imageBox {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.photos .imageBox li img {
  width: 100%;
  height: 100%;
}
.photos .imageBox li {
  width: 135px;
  height: 135px;
  border-radius: 8px;
  overflow: hidden;
  margin: 10px;
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
.functionBar {
  width: 100%;
  height: auto;
  padding: 10px 0 0 0;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}
.avatar {
  width: 40px;
  height: 40px;
  margin: 0 20px;
}
.avatar img {
  display: block;
  width: 100%;
  height: 100%;
  background: chartreuse;
}
.editArea {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: flex-start;
  background: chocolate;
}

.forwardBar {
  flex: 1;
}

.editArea .setBar {
  width: 100%;
}

.like {
  color: rgb(206, 99, 0);
}
</style>