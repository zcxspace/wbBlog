<template>
  <div class="fans">
    <dialogue-bar v-show="isShowD" @hideDialog="hideDialog" @yes="cancelFollow">
      <template #title>确认取消关注吗?</template>
    </dialogue-bar>
    <div class="profile">
      <img :src="this.Info.photo" alt="用户头像" />
    </div>
    <div class="info">
      <div class="userInfo">
        <div class="name">{{ this.Info.name }}</div>
        <div class="intro">{{ this.Info.intro }}</div>
        <div class="fansNum">{{ this.Info.fansCount }}</div>
        <!-- <div class="name">名字</div>
        <div class="intro">介绍</div>
        <div class="fansNum">粉丝数</div> -->
      </div>
      <div class="followBar">
        <button @click="toFollow" v-if="isUnFollow" class="unFollow">
          关注</button
        ><button @click="isShowD = !isShowD" v-else class="hasFollow">
          已关注
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import DialogueBar from "../DialogueBar.vue";
import {
  follow,
  unFollow,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  components: { DialogueBar },
  props: {
    Info: Object,
  },
  data() {
    return {
      isUnFollow: true,
      isShowD: false,
    };
  },
  methods: {
    async toFollow() {
      let result = follow(this.$store.state.userInfo.id, this.Info.id);
      console.log(result);
      this.isUnFollow = !this.isUnFollow;
    },
    async cancelFollow() {
      let result = unFollow(this.$store.state.userInfo.id, this.Info.id);
      console.log(result);
      this.isUnFollow = !this.isUnFollow;
      this.isShowD = false;
    },
    hideDialog() {
      this.isShowD = false;
      console.log("test");
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.fans {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-start;
  padding: 10px 15px;
  background: #fff;
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.1);
  border-radius: 10px;
  margin: 10px 0;
}
.profile {
  width: 100px;
  height: 100px;
  margin-right: 10px;
  overflow: hidden;
  border-radius: 50%;
}
.profile img {
  width: 100%;
  height: 100%;
}
.info {
  display: flex;
  justify-content: space-between;
  flex: 1;
  height: auto;
}
.userInfo {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-around;
}
.followBar {
  display: flex;
  align-items: center;
}
.unFollow {
  width: 100px;
  height: 50px;
  border-radius: 30px;
  background: transparent;
  outline: none;
  color: orange;
  border: 2px solid orange;
}
.hasFollow {
  width: 100px;
  height: 50px;
  border-radius: 30px;
  outline: none;

  background: transparent;
  color: gray;
  border: 2px solid gray;
}
</style>