<template>
  <div class="hotWord" @load="alert">
    <div class="top">
      <div>微博热搜</div>
      <div>
        <button @click="getNew">
          <i class="iconfont icon-shuaxin"></i>
          点击刷新
        </button>
      </div>
    </div>
    <ul class="ul" v-if="lists.length != 0">
      <li
        class="lis"
        v-for="(list, index) of lists"
        :key="list.index"
        @click="sayHi"
      >
        <div>{{ index + 1 }}</div>

        {{ list.title }}
      </li>
    </ul>
    <div class="bottom">
      <slot name="jumpBtn">跳转到热搜区</slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "HotWord",
  data() {
    return {
      lists: [
        { title: "延迟开学延迟开学延迟开学延迟开学" },
        { title: "延迟开学延迟开学延迟开学延迟开学" },
        { title: "延迟开学延迟开学延迟开学延迟开学" },
        { title: "延迟开学延迟开学延迟开学延迟开学" },
        { title: "延迟开学延迟开学延迟开学延迟开学" },
        { title: "延迟开学延迟开学延迟开学延迟开学" },
        { title: "延迟开学延迟开学延迟开学延迟开学" },
        { title: "延迟开学延迟开学延迟开学延迟开学" },
        { title: "延迟开学延迟开学延迟开学延迟开学" },
        { title: "延迟开学延迟开学延迟开学延迟开学" },
      ],
    };
  },
  methods: {
    alert() {
      console.log("测试");
    },
    getStr(str) {
      return str.length < 10 ? str : str.slice(0, 10) + "...";
    },
    getNew() {
      for (let i = 0; i < this.lists.length; i++) {
        this.lists[i].title = this.getStr(this.lists[i].title);
      }
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
  background: rgb(31, 29, 29);
  flex-direction: column;
  display: flex;
  width: 100%;
  color: whitesmoke;
  border-radius: 5px;
}
.top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 0.2px solid rgb(54, 53, 53);
  height: 40px;
}
.ul {
  flex: 1;
}
.lis {
  list-style: none;
  display: flex;
  align-items: center;
  min-height: 50px;
  font-size: 18px;
  justify-content: flex-start;
  border-bottom: 0.1px solid rgb(54, 53, 53);
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
</style>