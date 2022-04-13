<template>
  <div class="wbView">
    <set-bar
      :showTop="true"
      :showBack="true"
      :hideAfterSent="true"
      :textareaHeight="4"
      :editInfo="{}"
      :useTo="'publish'"
      :placeholder="'有什么新鲜事想分享给大家？'"
      @SetBar="isShowTab = !isShowTab"
      v-if="isShowTab"
    >
    </set-bar>
    <dialogue-bar
      @yes="outApp"
      @hideDialog="isShowExit = false"
      v-if="isShowExit"
    >
      <template #title>真的要退出吗？</template>
      <template #yesBtn>退出</template>
    </dialogue-bar>
    <!-- 对话提示框 -->
    <dialogue-bar
      @yes="goToSign('In')"
      @hideDialog="isShowD = false"
      v-if="isShowD"
    >
      <template #title>还未登陆哦！</template>
      <template #content>请登录后再尝试叭</template>
      <template #yesBtn>去登录</template>
    </dialogue-bar>
    <main-layout>
      <!-- 导航栏区 -->
      <template #topNavBar>
        <!-- 登陆后导航栏 -->
        <top-nav-bar v-if="this.$store.state.isOnline">
          <template #center1
            ><button @click="jumpPage('homePage')" class="viewBtn">
              <i class="iconfont icon-home"></i></button
          ></template>
          <template #center2
            ><button class="viewBtn">
              <i class="iconfont icon-shipinbofang"></i></button
          ></template>
          <template #center3
            ><button class="viewBtn" @click="jumpPage('hotPage')">
              <i class="iconfont icon-resou"></i></button
          ></template>
          <template #center4
            ><button class="viewBtn">
              <i class="iconfont icon-youxiang"></i></button
          ></template>

          <template #center5>
            <button class="userBtn" @click="jumpPage('userPage')">
              <img :src="this.$store.state.userInfo.photo" alt="用户头像" />
              跳转
            </button>
          </template>
          <template #right1>
            <button class="rightBtn" @click="isShowTab = !isShowTab">
              <i class="iconfont icon-bianji"></i>
            </button>
          </template>
          <template #right2>
            <button class="rightBtn">
              <i class="iconfont icon-taiyang"></i>
            </button>
          </template>
          <template #right3>
            <button class="rightBtn">
              <i class="iconfont icon-shuaxin"></i>
            </button>
          </template>
          <template #right4>
            <button @click="isShowExit = true" class="rightBtn">
              <i class="iconfont icon-fanhui1"></i>
            </button>
          </template>
        </top-nav-bar>
        <!-- 未登陆导航栏 -->
        <top-nav-bar v-else
          >;
          <template #center1
            ><button @click="isShowD = true" class="viewBtn">
              <i class="iconfont icon-home"></i></button
          ></template>
          <template #center2
            ><button @click="isShowD = true" class="viewBtn">
              <i class="iconfont icon-shipinbofang"></i></button
          ></template>
          <template #center3
            ><button class="viewBtn"><i class="iconfont icon-resou"></i></button
          ></template>
          <template #center4
            ><button @click="isShowD = true" class="viewBtn">
              <i class="iconfont icon-youxiang"></i></button
          ></template>
          <template #center5
            ><button @click="isShowD = true" class="viewBtn">
              <i class="iconfont icon-yonghu-yuan"></i></button
          ></template>
          <template #right1>
            <button class="SignBtn SignIn" @click="goToSign('In')">登陆</button>
          </template>
          <template #right2>
            <button class="SignBtn" @click="goToSign('Up')">注册</button>
          </template>
          <template #right3>
            <button @click="isShowD = true" class="rightBtn">
              <i class="iconfont icon-taiyang"></i>
            </button>
          </template>
          <template #right4>
            <button @click="isShowD = true" class="rightBtn">
              <i class="iconfont icon-bianji"></i>
            </button>
          </template>
        </top-nav-bar>
      </template>

      <template #topBackPic>
        <div class="backColor" v-if="!this.$store.state.isOnline"></div>
      </template>
      <!-- 浏览页搜索框 -->

      <template #searchArea>
        <div class="inputBox" v-if="!this.$store.state.isOnline">
          <label for="VSearch"><i class="iconfont icon-sousuo"></i></label>
          <input type="text" id="VSearch" />
        </div>
      </template>
      <template #center-left>
        <left-bar :ComArr="leftBarList"> </left-bar
      ></template>
      <template #center>
        <router-view :key="$route.fullPath"></router-view>
      </template>
      <template #center-right><hot-word></hot-word></template>
    </main-layout>
  </div>
</template>

<script>
import HotWord from "../components/ShareComs/HotWord.vue";
import LeftBar from "../components/ShareComs/LeftBar.vue";
import TopNavBar from "../components/ShareComs/TopNavBar.vue";
import MainLayout from "../layouts/MainLayout.vue";
import DialogueBar from "../components/ShareComs/DialogueBar.vue";

import { mapMutations } from "vuex";
import SetBar from "../components/ShareComs/SetBar.vue";
export default {
  components: { MainLayout, LeftBar, HotWord, TopNavBar, DialogueBar, SetBar },
  //   props: {
  //     pageName: String,
  //   },
  data() {
    return {
      isShowD: null,
      leftName: this.$store.state.leftBarName,
      photo: null,
      isShowTab: false,
      isShowExit: false,
      leftList: {
        homePage: [
          {
            barTitle: "首页",
            barName: "homePage",
            title: "全部微博",
            size: 35,
            comName: "homeAll",
            icon: "icon-caidan",
          },
        ],
        hotPage: [
          {
            barTitle: "热门微博",
            barName: "hotPage",
            title: "热门动态",
            size: 35,
            comName: "hotPage",
            icon: "icon-quanbu",
          },
        ],
        userPage: [
          {
            barTitle: "个人",
            barName: "userPage",

            title: "个人主页",
            size: 80,
            comName: "userInfo",
            params: "testParams",
            icon: "icon-caidan",
          },
          {
            barName: "userPage",
            title: "编辑信息页",
            size: 35,
            comName: "edit",
            icon: "icon-bianji1",
          },
        ],
      },
    };
  },

  methods: {
    //退出
    outApp() {
      this.updateStatus(false);
      this.updateLeftBar("newLogin");
      this.leftName = "newLogin";
      //切换页面后更新数据
      sessionStorage.setItem("store", JSON.stringify(this.$store.state));
      this.$router.push({ name: "newLogin" });
    },
    //登陆注册
    goToSign(page) {
      this.$router.push({ name: "Sign", params: { page: page } });
    },
    //更新左侧导航栏数据
    ...mapMutations(["updateLeftBar", "updateStatus", "test"]),
    jumpPage(pageName) {
      this.updateLeftBar(pageName);
      this.leftName = pageName;
      if (pageName == "userPage") {
        this.$router.push({
          name: "userInfo",
          params: { path: "u" + this.$store.state.userInfo.id },
        });
      } else this.$router.push({ name: pageName });
    },
  },
  computed: {
    //未登录获取路径
    path() {
      if (this.$store.state.isOnline) {
        return this.$store.state.userInfo.id;
      } else {
        return "";
      }
    },
    //侧边栏
    leftBarList() {
      switch (this.leftName) {
        case "homePage": {
          return this.leftList.homePage;
        }

        case "hotPage":
        case "newLogin": {
          return this.leftList.hotPage;
        }
        case "userPage": {
          return this.leftList.userPage;
        }
        default:
          return [];
      }
    },
  },

  created() {
    if (this.$store.state.isOnline) {
      //今日大坑
      //切换页面更新数据
      sessionStorage.setItem("store", JSON.stringify(this.$store.state));
      this.leftList.userPage[0].params = "u" + this.$store.state.userInfo.id;
    } else {
      console.log("未登陆");
      this.leftName = "newLogin";
    }

    if (sessionStorage.getItem("store")) {
      console.log("从缓存中取数据");
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state,
          JSON.parse(sessionStorage.getItem("store"))
        )
      );
      this.leftName = this.$store.state.leftBarName;
    } else {
      this.leftName = this.$store.state.leftBarName;
    }
    // 路由跳转 执行生命周期函数但是不卸载页面
    window.addEventListener("beforeunload", () => {
      // this.updateLeftBar(this.leftName);
      // 这里储存的状态是刷新时的状态 对应的leftBarName也是当时刷新时所对应的页面
      sessionStorage.setItem("store", JSON.stringify(this.$store.state));
    });
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.viewBtn {
  width: 100%;
  height: 100%;
  outline: none;
  border: 0;
  background: transparent;
}
.viewBtn i {
  font-size: 40px;
  font-weight: 700;
  color: rgb(34, 33, 33);
}
.rightBtn {
  outline: none;
  background: transparent;
  border: 0;
  padding: 6px;

  border-radius: 50%;
  transition: all ease 0.6s;
}
.rightBtn:hover {
  background: royalblue;
}
.rightBtn:hover i {
  color: white;
}
.rightBtn i {
  font-size: 30px;
}
.SignBtn {
  outline: none;
  background: transparent;
  border: 0;
  font-size: 20px;
  padding: 5px 10px;
  border-radius: 20px;
  transition: all ease 0.3s;
}
.SignBtn:hover,
.SignIn {
  background: royalblue;
  color: whitesmoke;
}
.inputBox {
  width: 100%;
  position: relative;
  padding: 10px;
}
.inputBox input {
  width: 100%;
  height: 100%;
  font-size: 25px;
  padding: 5px;
  background: transparent;
  border: 0;
  outline: none;
  text-indent: 30px;
}
.inputBox label {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
}
.inputBox label i {
  font-size: 35px;
  color: black;
}
.backPic {
  width: 100%;
  height: 100%;
}
.backColor {
  min-height: 450px;

  height: 100%;
  background: cornflowerblue;
  background: #7f7fd5; /* fallback for old browsers */
  background: -webkit-linear-gradient(
    to right,
    #91eae4,
    #86a8e7,
    #7f7fd5
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(to right, #91eae4, #86a8e7, #7f7fd5);
}
.userBtn {
  width: 55px;
  height: 55px;
  border-radius: 50%;
  overflow: hidden;
}
.userBtn img {
  width: 100%;
  height: 100%;
}
</style>