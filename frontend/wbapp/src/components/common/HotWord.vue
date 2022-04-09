<template>
  <div class="hotWord" @load="alert">
    <div class="top">
      <div>微博热搜</div>
      <div class="getNewBox" @click="getNew">
        <button class="getNewBtn">
          <i class="iconfont icon-shuaxin"></i>
        </button>
        点击刷新
      </div>
    </div>
    <ul class="ul" v-if="hotWordList.length != 0">
      <li
        class="lis"
        v-for="(list, index) of hotWordList"
        :key="list.theme"
        @click="goToTopic(list.theme)"
      >
        <div>{{ index + 1 }}</div>
        <!-- 首先获取文字 然后处理文字长度 -->
        {{ getStr(list.theme.slice(1, -1)) }}
      </li>
    </ul>
    <div class="bottom">
      <slot name="jumpBtn">跳转到热搜区</slot>
    </div>
  </div>
</template>

<script>
import { getHotWord } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  name: "HotWord",
  data() {
    return {
      hotWordList: [],
    };
  },
  methods: {
    async getNew() {
      let result = await getHotWord();
      this.hotWordList = result.data.data;

      console.log(result);
    },
    async goToTopic(topic) {
      this.$router.push({
        name: "TopicsPage",
        params: { type: "all", topic: topic },
      });
    },
    getStr(str) {
      return str.length < 10 ? str : str.slice(0, 10) + "...";
    },
  },
  created() {
    this.getNew();
  },
};
</script>

<style scope>
* {
  margin: 0;
  padding: 0;
}
.hotWord {
  background: #fff;
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.2);
  flex-direction: column;
  display: flex;
  width: 100%;
  color: black;
  border-radius: 5px;
}
.top {
  display: flex;
  padding: 0 5px;
  align-items: center;
  justify-content: space-between;
  border-bottom: 0.2px solid rgb(54, 53, 53);
  height: 40px;
}
.ul {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 5px 0;
}
.lis {
  list-style: none;
  display: flex;
  align-items: center;
  min-height: 50px;
  font-size: 18px;
  font-weight: 500;
  border-radius: 10px;
  width: 90%;
  justify-content: flex-start;
  border-bottom: 0.1px solid rgb(241, 241, 241);
}
.lis:hover {
  background: rgba(0, 0, 0, 0.05);
}
.lis div:nth-child(1) {
  width: 30px;
  height: 30px;
  color: red;
  display: flex;
  justify-content: center;
  align-items: center;
}
.bottom {
  width: 100%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.top button {
  border: 0;
  background: transparent;
  font-size: 15px;
  color: lightgray;
}
.getNewBox button i {
  color: black;
  font-weight: 800;
}
.getNewBox {
  display: flex;
  align-items: center;
  transition: all ease 0.4s;
}
.getNewBox:hover,
.getNewBox:hover button i {
  color: royalblue;
}
.getNewBox button {
  outline: none;
  background: transparent;
  border: 0;
  transition: all ease 0.3s;
}
/* .getNewBtn:hover {
  transform: rotate(45deg);
} */
.getNewBox:active button {
  transform: rotate(45deg);
}
</style>