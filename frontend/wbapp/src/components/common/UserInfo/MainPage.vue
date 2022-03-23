<template>
  <div class="myPage">
    <el-affix :offset="80">
      <div class="top">
        <div class="goBackTab"><button>返回</button></div>
      </div>
    </el-affix>

    <div class="infoBar">
      <div class="userFile">
        <img :src="userInfo.profileUrl" alt="用户头像" />
      </div>

      <div class="userInfo">
        <div class="name">
          <div class="userName">
            <h2>{{ userInfo.name }}</h2>
          </div>
          <div class="followInfo">
            <div>
              <span>粉丝</span>
              <h4>{{ userInfo.followMe }}</h4>
            </div>

            <div>
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
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
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
      infos: [{ title: "title" }, { title: "title" }, { title: "title" }],
      userInfo: null,
    };
  },
  methods: {
    changeStatus() {
      this.follow = !this.follow;
    },
  },
  created() {
    this.userInfo = this.$store.state.userInfo;
    console.log(this.userInfo);
    console.log("用户信息页");
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
  background: chartreuse;
  border-radius: 50%;
}
.userFile img {
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
  background: chocolate;
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
</style>