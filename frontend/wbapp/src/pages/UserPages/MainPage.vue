<template>
  <div class="myPage">
    <dialogue-bar v-if="isShowDialogue" @yes="cancelF" @hideDialog="changeDia">
      <template #title>确认取消关注么?</template>
    </dialogue-bar>
    <el-affix :offset="80">
      <div class="top" ref="backBar">
        <div class="goBackTab"><button @click="goBack">返回</button></div>
      </div>
    </el-affix>
    <div class="background" :style="backPic">
      <!-- <img :src="backUrl" alt="背景图片" /> -->
      <div class="editBtn" v-if="!isNotUser">
        <ImgCutter @cutDown="changB" :imgMove="false">
          <template #open
            ><button class="editBack">编辑背景图片</button></template
          >
        </ImgCutter>
      </div>
    </div>
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
              <h4>{{ userInfo.fansCount }}</h4>
            </div>

            <div class="follow" @click="goToFan(2)">
              <span>关注</span>
              <h4>{{ userInfo.following }}</h4>
            </div>
          </div>
        </div>

        <div class="follow" v-if="isNotUser">
          <slot name="followBar"
            ><button @click="toFollow" v-if="isUnFollow">关注</button>
            <button v-else @click="changeDia">已关注</button>
          </slot>
        </div>
      </div>

      <div class="infoBox">
        {{ testdata }}
        <select-com :userInfo="displayInfo"></select-com>
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
import blog from "./blogPage.vue";
import ImgCutter from "vue-img-cutter";
import DialogueBar from "../../components/ShareComs/DialogueBar.vue";
import SelectCom from "../../components/UserInfoComs/selectCom.vue";
import {
  getUserInfo,
  changeBack,
  follow,
  unFollow,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";

import { mapMutations } from "vuex";
export default {
  props: {
    path: String,
  },
  name: "userInfo",

  data() {
    return {
      userInfo: {},
      dynamics: {},
      uid: this.path,
      isNotUser: true,
      isUnFollow: true,
      isShrink: true,
      currentTab: "blog",
      displayInfo: [],
      isOpen: false,
      tabs: [
        { title: "精选", com: "great" },
        { title: "微博", com: "blog" },
        { title: "视频", com: "sd" },
        { title: "相册", com: "album" },
      ],
      height: null,
      profilePath: null,
      backUrl: null,
      isShowDialogue: false,
      backPic: {
        background: null,
      },
      testdata: null,
    };
  },
  components: {
    blog,
    ImgCutter,
    DialogueBar,
    SelectCom,
  },

  methods: {
    ...mapMutations(["updateUserBg"]),
    //关注用户
    async toFollow() {
      let result = await follow(
        this.$store.state.userInfo.id,
        Number(this.uid.slice(1))
      );
      if (result.data.message.includes("成功")) {
        this.isUnFollow = false;
      }
      console.log(result);
    },
    //取消关注
    async cancelF() {
      let result = await unFollow(
        this.$store.state.userInfo.id,
        Number(this.uid.slice(1))
      );
      if (result.data.message.includes("成功")) {
        this.isUnFollow = true;
      }
      console.log(result);
    },
    //更换背景
    async changB(e) {
      let form = new FormData();
      form.append("file", e.file);
      let result = await changeBack(form);
      if (result.data.message.includes("成功")) {
        this.updateUserBg(result.data.data.file.filePath);
        this.backPic.background = ` no-repeat center/100% url(${e.dataURL})  `;
      }
      console.log(result);
    },
    goBack() {
      this.$router.go(-1);
    },
    goToFan(flag) {
      if (flag == 1) {
        this.$router.push({
          name: "FansFollow",
          params: {
            carryInfo: JSON.stringify({ type: "fan", path: this.path }),
          },
        });
      } else
        this.$router.push({
          name: "FansFollow",
          params: {
            carryInfo: JSON.stringify({ type: "follower", path: this.path }),
          },
        });
    },
    changeDia() {
      this.isShowDialogue = !this.isShowDialogue;
    },
    // handleScroll() {
    //   console.log(0);
    //   console.log(this.$refs.backBar.getBoundingClientRect().top);
    // },
  },
  // mounted() {
  //   console.log("xiang");
  //   window.addEventListener("scroll", this.handleScroll);
  // },
  async created() {
    console.log(this.path);

    //如果为随机用户跳转则获取地址
    if (this.path) {
      if (this.path.slice(1) == this.$store.state.userInfo.id) {
        this.isNotUser = false;
      }
      let path = "http://120.25.125.57:8080/xhywblog/users/" + this.path;
      let result = await getUserInfo(path);
      this.userInfo = result.data.data.user;
      this.dynamics = result.data.data.dynamic;
      this.profilePath = result.data.data.user.photo;
      this.backPic.background = ` no-repeat center/100% url(${result.data.data.user.background})  `;
      let {
        intro = "这个人比较懒",
        interests = "没啥兴趣",
        trait = "没有，有就是摸鱼",
      } = result.data.data.user;
      this.displayInfo = [intro, interests, trait];
    } else {
      this.userInfo = this.$store.state.userInfo;
      this.dynamics = this.$store.state.userDynamic;
      this.profilePath = this.$store.state.userInfo.photo;
      this.backPic.background = ` no-repeat center/100% url(${this.$store.state.userInfo.background})  `;
    }
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.editBack {
  width: auto;
  height: auto;
  display: none;
  color: royalblue;
  background: transparent;
  border-radius: 10px;
  outline: none;
  padding: 10px;
  border: 3px solid royalblue;
  transition: all ease 0.4s;
}
.editBack:hover {
  background: royalblue;
  color: white;
}
.background {
  width: 100%;
  height: 300px;
  background-position: center;
  background-size: contain;
  position: relative;
}

.background:hover .editBack {
  display: block;
}
.background .editBtn {
  position: absolute;
  right: 10px;
  bottom: 10px;
}
.info-style {
  background: red;
}
.infoBox {
  flex: 1;
  height: auto;
}
.infoBox ul {
  width: 100%;
  height: 50px;
  display: flex;
  padding: 0 10px;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-evenly;
  background: orange;
  overflow: hidden;
}
.openStyle {
  height: auto;
}
.infoBox ul li {
  width: 100%;
  height: 50px;
}
.tab {
  margin-bottom: 20px;
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
  background: rgb(218, 217, 214);
  color: royalblue;
}

.myPage {
  width: 100%;
  height: auto;
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
  border-radius: 12px 12px 0 0;
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
  transition: all ease 0.4s;
  border-radius: 50%;
  border: 4px solid white;
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