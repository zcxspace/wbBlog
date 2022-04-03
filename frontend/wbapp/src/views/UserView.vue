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
        <router-link to="/User/UserHome">微博主页</router-link>
      </template>

      <template #center2></template>

      <template #center3>
        <router-link to="/User/UserHot">热门</router-link>
      </template>

      <template #center4></template>

      <template #center5
        ><router-link to="/User/UserPage">用户页</router-link></template
      >

      <template #right1></template>
      <template #right2> </template>
      <template #right3
        ><button @click="isShow = !isShow">编辑</button>
      </template>
    </top-nav-bar>

    <router-view></router-view>
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
</style>