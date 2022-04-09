<template>
  <div class="Comment" v-infinite-scroll="load" infinite-scroll-distance="50">
    <div class="editArea">
      <div class="avatar">
        <img :src="profile" alt="" />
      </div>
      <set-com
        :isShowTitle="false"
        :replyId="0"
        :floorId="0"
        class="setCom"
        @updateCom="updateAll"
        :dynamicInfo="dynamicInfo"
      ></set-com>
    </div>

    <div class="loadingBox" v-if="commentsNum != 0">
      <loading-com v-if="loading" :text="''"></loading-com>
    </div>

    <div v-show="!loading">
      <div>
        <template v-for="item of commentInfo" :key="item.id">
          <first-level
            :dynamicInfo="dynamicInfo"
            :commentInfo="item"
          ></first-level>
        </template>
      </div>
      <div v-show="isShowMore" @click="checkAll" style="cursor: pointer">
        点击查看更多
      </div>
    </div>
  </div>
</template>

<script>
import FirstLevel from "./common/FirstLevel.vue";
import LoadingCom from "./common/LoadingCom.vue";
import SetCom from "./common/SetCom.vue";
import { getComment } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  components: { FirstLevel, SetCom, LoadingCom },
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
      nowDisNum: 0,
      loading: true,
      profile: this.$store.state.userInfo.photo,
    };
  },
  computed: {
    isShowMore() {
      return this.commentsNum > 4 && this.commentsNum <= 5 ? true : false;
    },
  },
  methods: {
    load() {
      if (this.nowDisNum < this.commentsNum) {
        this.nowDisNum++;
      } else return;
    },
    async updateAll() {
      let result = await getComment(this.dynamicInfo.id, 0);
      console.log(result);
      if (result.data.data) {
        let commentInfo = result.data.data;
        if (this.openAll) {
          // this. commentInfo ?
          this.commentInfo = commentInfo;
          this.commentsNum = commentInfo.length;
        } else {
          this.commentInfo = commentInfo.slice(0, 5);
          this.commentsNum = this.commentInfo.length;
        }
      } else {
        this.commentsNum = 0;
        this.commentInfo = [];
      }
      console.log(this.commentsNum);
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
    console.log(this.commentsNum);

    console.log("进入评论页");
    setTimeout(() => {
      this.loading = false;
    }, 1500);
    console.log(this.openAll);

    console.log(this.isShowMore);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.loadingBox {
  width: 100%;
  height: auto;
}
.height {
  height: 400px;
}
.Comment {
  width: 100%;
  height: auto;
  padding: 10px;
  /* overflow: hidden; */
}
.editArea {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-start;
  margin-bottom: 10px;
}
.editArea .setCom {
  width: 100%;
}
.avatar {
  width: 65px;
  height: 65px;
  overflow: hidden;
  border-radius: 50%;
  margin: 5px;
}
.avatar img {
  width: 100%;
  height: 100%;
}
</style>