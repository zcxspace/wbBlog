<template>
  <div class="myPage">
    <el-affix :offset="80">
      <div class="top">
        <div class="goBackTab"><button @click="goBack">返回</button></div>
      </div>
    </el-affix>

    <div class="infoBar">
      <div class="userFile">
        <img :src="profilePath" alt="用户头像" />
      </div>

      <div class="userInfo">
        <div class="name">
          <div class="userName">
            <h2>{{ userInfo.name }}</h2>
          </div>
          <div class="followInfo">
            <div class="fans" @click="goToFan(1)">
              <span>粉丝</span>
              <h4>{{ userInfo.followMe }}</h4>
            </div>

            <div class="follow" @click="goToFan(2)">
              <span>关注</span>
              <h4>{{ userInfo.following }}</h4>
            </div>
          </div>
        </div>

        <div class="follow">
          <slot name="followBar"
            ><button @click="changeStatus">
              <p v-if="follow">已关注</p>
              <p v-else>未关注</p>
            </button></slot
          >
        </div>
      </div>

      <div class="infoBox">
        <!-- 显示或隐藏用户信息 -->
        <div v-if="isShrink" @click="isShrink = !isShrink">个人信息</div>
        <div
          v-for="(info, index) of infos"
          :key="index"
          @click="isShrink = !isShrink"
          v-else
        >
          {{ info.title }}
        </div>
      </div>
      <div class="tab">
        <ul>
          <li
            v-for="tab of tabs"
            :key="tab.com"
            @click="currentTab = tab.com"
            :class="[currentTab == tab.com ? 'active' : '']"
          >
            {{ tab.title }}
          </li>
        </ul>
      </div>
      <component
        :is="currentTab"
        :dynamics="dynamics"
        :userInfo="userInfo"
      ></component>
    </div>
  </div>
</template>

<script>
import blog from "../UserInfo/MainPages/blogPage.vue";
import { getUserInfo } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  props: {
    path: String,
  },
  name: "RandomInfo",

  data() {
    return {
      userInfo: {},
      dynamics: {},
      uid: this.path,
      follow: false,
      isShrink: true,
      currentTab: "blog",
      infoStyle: "info-style",
      tabs: [
        { title: "精选", com: "select" },
        { title: "微博", com: "blog" },
        { title: "视频", com: "video" },
        { title: "相册", com: "album" },
      ],
      height: null,
      profilePath: this.$store.state.userInfo.photo,
    };
  },
  components: {
    blog,
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    changeStatus() {
      this.follow = !this.follow;
    },
    goToFan(flag) {
      if (flag == 1) {
        this.$router.push({ name: "FansFollow", params: { type: "fan" } });
      } else
        this.$router.push({ name: "FansFollow", params: { type: "follower" } });
    },
  },

  async created() {
    console.log(this.path);
    //如果为随机用户跳转则获取地址
    if (this.path) {
      let path = "http://120.25.125.57:8080/xhywblog/users/" + this.path;
      let result = await getUserInfo(path);
      console.log(result);
      this.userInfo = result.data.data.user;
      this.dynamics = result.data.data.dynamic;
      console.log(this.userInfo);
      console.log(this.dynamics);
    } else {
      this.userInfo = this.$store.state.userInfo;
      this.dynamics = this.$store.state.userDynamic;
    }
    // console.log(this.path);
    console.log("用户页生成了");
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.info-style {
  background: red;
}
.infoBox {
  flex: 1;
  padding-top: 30px;
  height: 100%;
}
.infoBox div {
  height: 50px;
  width: 100%;
  display: flex;
  align-items: center;
  padding-left: 30px;
}
.tab ul {
  width: 100%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  background: white;
  padding: 2px;
}
.tab ul li {
  height: 100%;
  flex: 1;
  border-radius: 8px;
  transition: all ease-in 0.3s;
  display: flex;
  align-items: center;
  font-size: 20px;
  justify-content: center;
}
.active {
  background: rgb(221, 220, 206);
  color: rgb(255, 166, 0);
}

.myPage {
  width: 100%;
  min-height: 500px;
  background: burlywood;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.top {
  width: 100%;
  height: 50px;
}
.top .goBackTab {
  width: 100%;
  height: 100%;
  display: flex;
  padding-left: 15px;
  justify-content: flex-start;
  background: white;
}
.goBackTab button {
  font-size: 25px;
  color: black;
  background: transparent;
  border: 0;
}
.infoBar {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 230px;
  width: 100%;
  background: white;
}
.userFile {
  width: 150px;
  height: 150px;
  position: absolute;
  left: 20px;
  top: -75px;
  overflow: hidden;
  background: whitesmoke;
  border-radius: 50%;
  /* padding: 10px; */
}
.userFile img {
  background: yellowgreen;
  width: 100%;
  height: 100%;
}
.userInfo {
  display: flex;
  justify-content: space-between;
  padding-left: 190px;
  width: 100%;
  min-height: 80px;
  padding-right: 50px;
  align-items: center;
}
.follow button {
  height: 45px;
  width: 100px;
  border: 0;
  background: transparent;
  border: 3px solid royalblue;
  border-radius: 22.5px;
}
.name {
  display: flex;
  align-items: flex-start;
  flex-direction: column;
}
.followInfo {
  display: flex;
  justify-content: flex-start;
}
.followInfo div {
  margin-right: 10px;
}
.followInfo h4 {
  display: inline-block;
}
.fans:hover,
.follow:hover {
  color: goldenrod;
  cursor: pointer;
}
</style>