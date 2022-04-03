<template>
  <div class="commentArea">
    <ul>
      <!-- 一级评论 -->
      <li class="level-1" v-for="(data, index) of testData" :key="index">
        <div class="topLevel">
          <div class="profile"></div>
          <div class="commentInfo">
            <div class="info">
              {{ data.name }}
              <el-tag class="ml-2" effect="dark" type="warning">博主</el-tag
              >：{{ data.text }}
            </div>
            <div class="funcTab">
              <div class="time">时间</div>
              <div class="tabs">评论</div>
            </div>
          </div>
        </div>
        <!-- <div class="topBar" v-show="false">
          <ul>
            <li class="level-2">
              <div class="topTitle">二级标题</div>
              <div class="bottom">
                <div class="time">时间</div>
                <div class="funcBar">评论</div>
              </div>
            </li>
          </ul>
        </div> -->
      </li>
      <!-- 一级评论结束 -->
    </ul>
    <div v-if="this.testData.length > 5" @click="gotoCheck">
      点击展示全部评论
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      testData: [],
    };
  },
  updateNew() {
    this.testData.unshift({ name: "测试用户", text: this.UserComment });
    this.UserComment = "";
  },
  delCom() {
    this.testData.shift();
  },
};
</script>

<style scoped>
.commentArea {
  background: orange;
  /* max-height: 400px; */
  height: auto;
  overflow: auto;
}
li {
  list-style: none;
}
.commentArea ul,
.topBar ul {
  height: auto;
  width: 100%;
  background: brown;
  display: flex;
  flex-direction: column;
}
</style>