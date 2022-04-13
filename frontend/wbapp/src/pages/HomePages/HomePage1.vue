<template>
  <div class="HomePage1">
    <set-bar
      :showBack="false"
      :showTop="false"
      :hideAfterSent="false"
      :editInfo="{}"
      :textareaHeight="4"
      :useTo="'publish'"
      :placeholder="'有什么新鲜事想分享给大家？'"
      :showShadow="true"
    >
    </set-bar>
    <router-view></router-view>
    <!-- <component :is="nowCom"></component> -->
  </div>
</template>

<script>
import SetBar from "../../components/ShareComs/SetBar.vue";
// import HomePage from "./HomePage.vue";

export default {
  components: { SetBar },
  // props: {
  //   type: String,
  // },
  // data() {
  //   return {
  //     nowCom: this.type,
  //   };
  // },
  // watch: {
  //   nowCom(newCom, OldCom) {
  //     this.nowCom = newCom;
  //     console.log(OldCom);
  //   },
  // },
  created() {
    console.log("进入page1了");
    // console.log(this.nowCom);
  },
};
</script>

<style>
</style>