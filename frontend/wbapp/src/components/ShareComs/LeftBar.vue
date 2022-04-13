<template>
  <div class="leftBar">
    <dialogue-bar
      @hideDialog="isShowTips = false"
      @yes="goToSign('In')"
      v-if="isShowTips"
    >
      <template #title>还未登陆哦！</template>
      <template #content>请登录后再尝试叭</template>
      <template #yesBtn>去登录</template>
    </dialogue-bar>
    <div class="title" style="font-size: 20px">
      <h2>{{ ComArr[0].barTitle }}</h2>
    </div>
    <div class="tabs">
      <ul class="tabBox">
        <li
          v-for="com of ComArr"
          :key="com.comName"
          :class="[com.comName == nowTab ? 'isChose' : '']"
          @click="
            nowTab = com.comName;
            showD();
          "
        >
          <div class="icon">
            <i class="iconfont" :class="com.icon" :style="fontSize"></i>
          </div>
          <div v-if="com.params">
            <router-link
              :to="{
                name: `${com.comName}`,
                params: { path: `${com.params}` },
              }"
              :class="[com.comName == nowTab ? 'isChose' : '']"
              >{{ com.title }}</router-link
            >
          </div>
          <div v-if="!com.params">
            <router-link
              :to="{ name: `${com.comName}` }"
              :class="[com.comName == nowTab ? 'isChose' : '']"
              >{{ com.title }}</router-link
            >
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import DialogueBar from "../ShareComs/DialogueBar.vue";
export default {
  components: { DialogueBar },
  // icon size comName title ?params
  props: {
    ComArr: Array,
  },
  data() {
    return {
      nowTab: this.ComArr[0].comName,
      isShowTips: false,
    };
  },
  methods: {
    showD() {
      if (this.$store.state.isOnline) {
        return;
      } else {
        this.isShowTips = true;
      }
    },
    goToSign(page) {
      this.$router.push({ name: "Sign", params: { page: page } });
    },
  },
  computed: {
    fontSize() {
      return "font-size:" + this.ComArr.size + "px";
    },
  },
  created() {
    console.log(this.ComArr);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
a {
  text-decoration: none;
  color: black;
}

li:hover a,
li:hover i {
  color: royalblue;
  pointer-events: none;
}
li {
  list-style: none;
}
i {
  pointer-events: none;
  font-size: 35px;
}
.leftBar {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 0 3px;
}
.title {
  width: 100%;
  height: 50px;
  padding: 10px 16px;
  display: flex;
  justify-content: flex-start;
}
.tabs {
  width: 100%;
}
.tabBox {
  flex: 1;
  width: 100%;
  height: 100px;
}
.tabBox li {
  width: 100%;
  height: 45px;
  font-size: 20px;
  font-weight: 800;
  display: flex;
  border-radius: 5px;
  cursor: pointer;
  align-items: center;
  justify-content: flex-start;
  padding-left: 4px;
  transition: all ease 0.3s;
}
.tabBox li i {
  pointer-events: none;
}
.active {
  color: rgb(199, 132, 9);
}
.tabBox li:hover {
  background: rgb(243, 241, 241);
}
.icon {
  margin: 0 15px;
}
.isChose {
  background: rgb(228, 226, 222);
  color: royalblue;
}
</style>