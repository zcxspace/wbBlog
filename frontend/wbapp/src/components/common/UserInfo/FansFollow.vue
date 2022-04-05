<template>
  <div class="fansFollow">
    <div class="top"><button @click="goBack">返回</button></div>
    <div class="bar">
      <button @click="goFans" :class="[carryInfo.type == 'fan' ? 'chose' : '']">
        粉丝</button
      ><button
        @click="goFollow"
        :class="[carryInfo.type == 'follower' ? 'chose' : '']"
      >
        关注
      </button>
    </div>
    <div class="fans" v-if="carryInfo.type == 'fan'">
      <template v-for="fan of fans" :key="fan.id">
        <fans-item :Info="fan"></fans-item>
      </template>
    </div>
    <div class="follow" v-else>
      <template v-for="follower of followers" :key="follower.id">
        <fans-item :Info="follower"></fans-item>
      </template>
    </div>
  </div>
</template>

<script>
import FansItem from "./FansItem.vue";
import {
  getFollower,
  getFans,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  components: { FansItem },
  name: "FansFollow",
  data() {
    return {
      fans: [],
      followers: [],
      carryInfo: {},
    };
  },
  methods: {
    goBack() {
      this.$router.push({
        name: "RandomInfo",
        params: { path: this.carryInfo.path },
      });
    },
    goFans() {
      this.$router.push({
        name: "FansFollow",
        params: {
          carryInfo: JSON.stringify({ type: "fan", path: this.carryInfo.path }),
        },
      });
    },
    goFollow() {
      this.$router.push({
        name: "FansFollow",
        params: {
          carryInfo: JSON.stringify({
            type: "follower",
            path: this.carryInfo.path,
          }),
        },
      });
    },
    async getFan() {
      let result = await getFans(this.$store.state.userInfo.id);
      console.log(result);
      if (result.data.message.includes("成功")) {
        this.fans = result.data.data;
      }

      console.log("fans");
    },
    async getFollow() {
      let result = await getFollower(this.$store.state.userInfo.id);
      console.log(result);
      if (result.data.message.includes("成功")) {
        this.followers = result.data.data;
      }

      console.log("follow");
    },
  },
  async created() {
    this.carryInfo = JSON.parse(this.$route.params.carryInfo);
    if (this.carryInfo.type == "fan") {
      this.getFan();
    }
    if (this.carryInfo.type == "follower") {
      this.getFollow();
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
.top {
  width: 100%;
  height: 50px;
  display: flex;
  background: #fff;
  box-shadow: 0 1px 3px rgba(20, 20, 20, 0.1);
  justify-content: flex-start;
  border-radius: 10px;
  padding: 10px 0;
}
.top button {
  width: 100px;
  height: 50px;

  border-radius: 8px;
  font-size: 25px;
  outline: none;
  color: black;
  transition: all ease 0.4s;
}
.top button:hover {
  color: royalblue;
}
.bar {
  margin: 10px 0;
  display: flex;
  padding: 10px 20px;
  background: #fff;
  box-shadow: 0 1px 3px rgba(20, 20, 20, 0.1);
  border-radius: 10px;
}
.bar button {
  width: 100px;
  height: 50px;
  border-radius: 8px;
  /* color: coral; */
  background: transparent;
  border: 0;
  margin: 0 10px;
  outline: none;
  font-size: 25px;
  transition: all ease 0.3s;
}
.bar button:hover,
.top button:hover {
  background: rgba(20, 20, 20, 0.1);
  color: royalblue;
}
.chose {
  color: royalblue;
}
</style>