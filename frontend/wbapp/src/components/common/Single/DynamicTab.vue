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
    <div class="selectMenu" v-if="checkUser">
      <el-dropdown placement="bottom-end" trigger="click" size="large">
        <span class="el-dropdown-link">
          <i class="iconfont icon-xuanzeqizhankai"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="showBar()">编辑微博</el-dropdown-item>
            <el-dropdown-item @click="isShowDialog = true"
              >删除微博</el-dropdown-item
            >
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <div class="menuBox" v-show="isShowMenu">
        <ul></ul>
      </div>
    </div>

    <!-- 头部用户信息区 -->
    <div class="userInfo">
      <img :src="profile" alt="用户头像" @click="goToInfo" />
      <h2 @click="goToInfo">{{ getName }}</h2>
    </div>
    <!-- 头部信息区结束 -->

    <!-- 中间内容区 -->
    <div class="content">
      <div class="text">
        <div class="forwardT">
          <!-- 如果是不是转发 则显示正常文本 -->
          <!-- 无转发文本区 -->
          <div class="normal" v-if="isShowText">
            <template v-for="(item, index) of getStr(this.text)" :key="index">
              <a
                @click.prevent="toTopics(item)"
                v-if="item.match(/#[^#]+#/gi)"
                href="#"
              >
                {{ item }}
              </a>
              <span v-else>{{ item }}</span>
            </template>
          </div>

          <!-- 如果是 则遍历转发数据 -->
          <div class="forwardText" v-else>
            <template
              v-for="(item, index) of getStr(this.dynamicInfo.text)"
              :key="index"
            >
              <a
                @click.prevent="toTopics(item)"
                v-if="item.match(/#[^#]+#/gi)"
                href="#"
              >
                {{ item }}
              </a>
              <span v-else>{{ item }}</span>
            </template>

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
              >:
              <!-- 处理转发话题 -->
              <template v-for="(item, index) of getStr(item.text)" :key="index">
                <a
                  @click.prevent="toTopics(item)"
                  v-if="item.match(/#[^#]+#/gi)"
                  href="#"
                >
                  {{ item }}
                </a>
                <span v-else>{{ item }}</span>
              </template>
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
      <div class="forward">
        <button @click="showForward">
          <i class="iconfont icon-zhuanfa1"></i> 转发
        </button>
      </div>
      <div class="comments">
        <button @click="showComment">
          <i class="iconfont icon-pinglunxiao" style="font-size: 27px"></i>评论
        </button>
      </div>
      <div class="likes">
        <button @click="test" :class="[isLike ? 'like' : '']">
          <i class="iconfont icon-icon" style="font-size: 30px"></i>点赞
        </button>
      </div>
    </div>
    <!-- 转发编辑模块 -->
    <div class="functionBar" v-show="isForwardShow">
      <div class="avatar">
        <img :src="profile" alt="用户头像" />
      </div>
      <div class="editArea">
        <!-- 转发区 -->
        <div class="forwardBar">
          <set-bar
            class="forwardSet"
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
import { splitStr } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/PublicFun.js";
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
      text: "",
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
      if (this.dynamicInfo.user) {
        return this.dynamicInfo.user.photo;
      } else {
        return this.userInfo.id == this.$store.state.userInfo.id
          ? this.$store.state.userInfo.photo
          : this.userInfo.photo;
      }
    },
  },
  methods: {
    //通过profileUrl获取的没有user信息
    //点击用户头像与姓名跳转用户页
    goToInfo() {
      if (this.dynamicInfo.user) {
        this.$router.push({
          name: "RandomInfo",
          params: { path: `u${this.dynamicInfo.user.id}` },
        });
      } else {
        this.$router.push({
          name: "RandomInfo",
          params: { path: `u${this.userInfo.id}` },
        });
      }
    },
    //去往话题页
    toTopics(topic) {
      this.$router.push({
        name: "TopicsPage",
        params: { type: "all", topic: topic },
      });
    },
    //获得分割字符串数组
    getStr(str) {
      console.log(str);
      console.log(splitStr(str));
      return splitStr(str);
    },
    openAllCom() {
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
    this.text = this.dynamicInfo.text;
    console.log(this.dynamicInfo.userId);
    console.log(this.hasPhotos);
    console.log(this.userInfo);
    console.log(this.profile);
    console.log(this.dynamicInfo);
    console.log(Boolean(this.dynamicInfo.user));
    // console.log(this.dynamicInfo.user.photo);
    console.log(this.$store.state.userInfo.photo);
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
a {
  text-decoration: none;
  color: royalblue;
}
.dynamic {
  position: relative;
  width: 100%;
  height: auto;
  padding: 20px 20px 0 20px;
  background: #fff;
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.2);
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
  cursor: pointer;
  transition: all ease 0.4s;
}
.userInfo h2:hover {
  color: royalblue;
}
.userInfo img {
  height: 100%;
  width: 80px;
  margin-right: 10px;
  border-radius: 50%;
  cursor: pointer;
}
.tabs {
  width: 100%;
  height: auto;
  padding: 10px 5px;
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
.tabs div i {
  margin: 0 5px;
}
.tabs div button {
  transition: all ease 0.35s;
}
.tabs div:hover button {
  color: royalblue;
  transform: scale(1.1);
}
tabs div:active button {
  color: royalblue;
  transform: scale(0.9);
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
  /* padding: 10px 0 0 0; */
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}
.avatar {
  width: 70px;
  height: 70px;
  margin: 20px 20px;
  border-radius: 50%;
  overflow: hidden;
}
.avatar img {
  display: block;
  width: 100%;
  height: 100%;
}
.editArea {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: flex-start;
}

.forwardBar {
  flex: 1;
  padding: 10px;
}

.editArea .forwardSet {
  width: 100%;
}

.like {
  color: rgb(206, 99, 0);
}
</style>