<template>
  <div class="Comment">
    <div class="editArea">
      <div class="avatar">头像</div>
      <set-com
        :isShowTitle="false"
        :replyId="0"
        :floorId="0"
        class="setCom"
        @updateCom="updateAll"
        :dynamicInfo="dynamicInfo"
      ></set-com>
    </div>
    <template v-for="item of commentInfo" :key="item.id">
      <first-level :dynamicInfo="dynamicInfo" :commentInfo="item"></first-level>
    </template>
    <div v-show="commentsNum > 5" @click="checkAll">点击查看更多</div>
  </div>
</template>

<script>
import FirstLevel from "./common/FirstLevel.vue";
import SetCom from "./common/SetCom.vue";
import { getComment } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  components: { FirstLevel, SetCom },
  props: {
    dynamicInfo: Object,
    openAll: Boolean,
  },
  data() {
    return {
      floorId: 0,
      text: null,
      UserComment: "",
      disAll: false,
      showFirst: true,
      commentInfo: null,
      commentsNum: null,
    };
  },
  methods: {
    async updateAll() {
      let result = await getComment(this.dynamicInfo.id, 0);
      let commentInfo = result.data.data;
      if (this.openAll) {
        this.commentInfo = commentInfo;
      } else {
        this.commentInfo = commentInfo.slice(0, 5);
      }
      this.commentsNum = commentInfo.length;
      return result;
    },
    checkAll() {
      this.$router.push({
        name: "comInfo",
        params: { info: `${JSON.stringify(this.dynamicInfo)}` },
      });
    },
    showOther(data) {
      this.showFirst = !this.showFirst;
      this.text = data.text;
    },
    updateNew() {
      this.testData.unshift({ name: "测试用户", text: this.UserComment });
      this.UserComment = "";
    },
    delCom() {
      this.testData.shift();
    },
  },
  async created() {
    this.updateAll();
    console.log("进入评论页");
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.height {
  height: 400px;
}
.Comment {
  width: 100%;
  height: auto;
  padding: 10px;
  overflow: auto;
  background: royalblue;
}
.editArea {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-start;
  background: sandybrown;
  margin-bottom: 10px;
}
.editArea .setCom {
  width: 100%;
}
.avatar {
  width: 65px;
  height: 65px;
  background: seagreen;
  border-radius: 50%;
}
</style>