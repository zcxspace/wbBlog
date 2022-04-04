<template>
  <div class="fansFollow">
    <div class="top"><button @click="goBack">返回</button></div>
    <div class="bar">
      <button @click="goFans" :class="[type == 'fan' ? 'chose' : '']">
        粉丝</button
      ><button @click="goFollow" :class="[type == 'follower' ? 'chose' : '']">
        关注
      </button>
    </div>
    <div class="fans" v-if="type == 'fan'">
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
  props: { type: String },
  components: { FansItem },
  name: "FansFollow",
  data() {
    return {
      fans: [],
      followers: [],
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    goFans() {
      this.$router.push({ name: "FansFollow", params: { type: "fan" } });
    },
    goFollow() {
      this.$router.push({ name: "FansFollow", params: { type: "follower" } });
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
    if (this.type == "fan") {
      this.getFan();
    }
    if (this.type == "follower") {
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
  background: rgba(0, 0, 0, 0.3);
  justify-content: flex-start;
  border-radius: 10px;
  padding: 0 10px;
}
.top button {
  color: white;
  outline: none;
}
.bar {
  margin: 10px 0;
  display: flex;
  padding: 10px 20px;
  background: rgba(0, 0, 0, 0.3);
  border-radius: 10px;
}
.bar button {
  width: 100px;
  height: 50px;
  border-radius: 25px;
  /* color: coral; */
  border: 0;
  margin: 0 10px;
  outline: none;
  transition: all ease 0.3s;
}
.bar button:hover {
  background: coral;
  color: white;
}
.chose {
  background: coral;
  color: white;
}
</style>