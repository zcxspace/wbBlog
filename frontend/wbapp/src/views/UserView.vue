<template>
  <div class="User">
    <set-bar
      :showTop="true"
      :showBack="true"
      :hideAfterSent="true"
      :editInfo="{}"
      :textareaHeight="4"
      :useTo="'publish'"
      :placeholder="'有什么新鲜事想分享给大家？'"
      @SetBar="isShow = !isShow"
      v-if="isShow"
    ></set-bar>

    <top-nav-bar>
      <template #center1>
        <router-link to="/User/UserHome"
          ><i class="iconfont icon-home1" style="font-size: 45px"></i
        ></router-link>
      </template>

      <template #center2>
        <i class="iconfont icon-shipinbofang" style="font-size: 40px"></i
      ></template>

      <template #center3>
        <router-link to="/User/UserHot"
          ><i class="iconfont icon-resou"></i
        ></router-link>
      </template>

      <template #center4><i class="iconfont icon-youxiang"></i></template>

      <template #center5
        ><button @click="jumpPage('MuserInfo')" class="userBtn">
          <img :src="this.$store.state.userInfo.photo" alt="" /></button
      ></template>

      <template #right1><i class="iconfont icon-shezhi"></i></template>
      <template #right2> <i class="iconfont icon-taiyang"></i></template>
      <template #right3
        ><button @click="isShow = !isShow">
          <i class="iconfont icon-bianji"></i>
        </button>
      </template>
    </top-nav-bar>

    <router-view :key="$route.fullPath"></router-view>
  </div>
</template>

<script>
import TopNavBar from "../components/common/TopNavBar.vue";
import SetBar from "../components/SetBar.vue";
export default {
  components: { TopNavBar, SetBar },
  data() {
    return {
      isShow: false,
      inputName: "inputFile2",
    };
  },
  methods: {
    jumpPage(name) {
      this.$router.push({
        name: name,
        params: { path: `u${this.$store.state.userInfo.id}` },
      });
    },
  },
  async created() {
    //在页面加载时读取sessionStorage里的状态信息
    if (sessionStorage.getItem("store")) {
      console.log("重新加载了数据");
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state,
          JSON.parse(sessionStorage.getItem("store"))
        )
      );
    }
    //在页面刷新时将vuex里的信息保存到sessionStorage里
    window.addEventListener("beforeunload", () => {
      sessionStorage.setItem("store", JSON.stringify(this.$store.state));
    });
  },
};
</script>

<style scoped>
i {
  font-size: 35px;
}
a {
  text-decoration: none;
  color: black;
}
button {
  outline: none;
  border: 0;
}
.userBtn {
  width: 55px;
  height: 55px;
  border-radius: 50%;
  overflow: hidden;
  outline: none;
  border: 0;
}
.userBtn img {
  width: 100%;
  height: 100%;
}
</style>