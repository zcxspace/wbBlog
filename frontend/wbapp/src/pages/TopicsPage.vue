<template>
  <div class="topic" v-infinite-scroll="load" infinite-scroll-distance="50">
    <div class="topicTab">
      <div class="top">
        <div class="title">#{{ title }}#</div>
        <button class="shareBtn">分享</button>
      </div>
      <div class="bottom"><span>今日阅读</span><span>今日讨论</span></div>
    </div>
    <div v-if="isShowSk">
      <template v-for="n of 3" :key="n">
        <skeleton-com></skeleton-com>
      </template>
    </div>
    <div class="setTab" v-show="!isShowSk">
      <div v-if="dynamicNum != 0">
        <template v-for="n of nowDisNum" :key="n">
          <dynamic-tab :dynamicInfo="dynamicInfo[n - 1]"></dynamic-tab>
        </template>
        <div v-show="!isShowSk">
          <loading-com v-if="isLoading"></loading-com>
        </div>

        <span v-if="noMore">没有更多数据了</span>
      </div>
    </div>
  </div>
</template>

<script>
import DynamicTab from "../components/DynamicComs/DynamicTab.vue";
import { getTopics } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  name: "TopicsPage",
  components: {
    DynamicTab,
  },
  props: {
    type: String,
    topic: String,
  },
  computed: {
    title() {
      return this.topic.slice(1, -1);
    },
  },
  data() {
    return {
      dynamicInfo: [],
      nowDisNum: 1,
      dynamicNum: 0,
      ComArr: [
        { comName: "TopicsPage", title: "综合" },
        { comName: "TopicsPage", title: "实时" },
      ],
      isShowSk: true,
      isLoading: false,
      noMore: false,
    };
  },
  methods: {
    load() {
      if (this.nowDisNum < this.dynamicNum) {
        this.isLoading = true;
        this.nowDisNum++;
      } else {
        setTimeout(() => {
          this.isLoading = false;
          this.noMore = true;
        }, 1000);

        console.log(this.nowDisNum);
        console.log(this.dynamicNum);
      }
    },
  },
  async created() {
    let result = await getTopics(this.topic);
    console.log(result);
    if (result.data.message.includes("成功")) {
      this.dynamicInfo = result.data.data;
      this.dynamicNum = result.data.data.length;
      console.log(this.dynamicNum);
    }
    setTimeout(() => {
      this.isShowSk = false;
      if (this.dynamicNum > 4) {
        this.nowDisNum = 4;
      } else this.nowDisNum = this.dynamicNum;
    }, 1000);
  },
};
</script>

<style scoped>
.topicTab {
  width: 100%;
  height: auto;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.3);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  align-items: center;
}
.top {
  width: 100%;
  position: relative;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-bottom: 3px solid black;
  justify-content: space-between;
  background: #83a4d4; /* fallback for old browsers */
  background: -webkit-linear-gradient(
    to right,
    #b6fbff,
    #83a4d4
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(
    to right,
    #b6fbff,
    #83a4d4
  ); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}
.shareBtn {
  position: absolute;
  right: 10px;
  bottom: 10px;
  outline: none;
  color: black;
}
.title {
  width: 100%;
  height: auto;

  font-size: 60px;
  letter-spacing: 3px;
  font-weight: bolder;
  padding: 20px 0;
}
.bottom {
  width: 50%;
  height: auto;
  padding: 15px 0;
  display: flex;
  justify-content: space-between;
}
.setTab {
  width: 100%;
  max-height: 500px;
}
</style>