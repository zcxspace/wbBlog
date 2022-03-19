<template>
  <div class="myPage">
    <div class="top">
      <button>返回</button>
    </div>
    <div class="infoBar">
      <div class="userFile"></div>

      <div class="userInfo">
        <div class="name">
          <div class="userName"><h2>微博用户001</h2></div>
          <div class="followInfo">
            <div>
              <span>粉丝</span>
              <h4>100万</h4>
            </div>

            <div>
              <span>关注</span>
              <h4>100万</h4>
            </div>
          </div>
        </div>

        <div class="follow">
          <slot name="followBar"
            ><button @click="changeStatus">
              <p v-if="follow">已关注</p>
              <p v-else>未关注</p>
            </button></slot
          >
        </div>
      </div>

      <div class="tab">
        <ul>
          <li
            v-for="tab of tabs"
            :key="tab.com"
            @click="currentTab = tab.com"
            :class="[currentTab == tab.com ? 'active' : '']"
          >
            {{ tab.title }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      follow: false,
      currentTab: "blog",
      tabs: [
        { title: "精选", com: "select" },
        { title: "微博", com: "blog" },
        { title: "视频", com: "video" },
        { title: "相册", com: "album" },
      ],
    };
  },
  methods: {
    changeStatus() {
      this.follow = !this.follow;
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.tab ul {
  width: 100%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  background: white;
  padding: 3px;
}
.tab ul li {
  height: 100%;
  flex: 1;
  border-radius: 8px;
  transition: all ease-in 0.3s;
  display: flex;
  align-items: center;
  font-size: 20px;
  justify-content: center;
}
.active {
  background: rgb(187, 186, 186);
  color: rgb(133, 93, 20);
}

.myPage {
  width: 100%;
  min-height: 500px;
  background: burlywood;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.top {
  padding: 15px;
}
.top button {
  font-size: 20px;
  color: whitesmoke;
}
.infoBar {
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 230px;
  width: 100%;
  background: rgb(41, 40, 40);
}
.userFile {
  width: 150px;
  height: 150px;
  position: absolute;
  left: 20px;
  top: -75px;
  background: chartreuse;
  border-radius: 50%;
}
.userInfo {
  display: flex;
  justify-content: space-between;
  padding-left: 190px;
  width: 100%;
  height: 80px;
  padding-right: 50px;
  align-items: center;
  background: chocolate;
}
.follow button {
  height: 45px;
  width: 100px;
  border: 0;
  background: transparent;
  border: 3px solid royalblue;
  border-radius: 22.5px;
}
.name {
  display: flex;
  align-items: flex-start;
  flex-direction: column;
}
.followInfo {
  display: flex;
  justify-content: flex-start;
}
.followInfo div {
  margin-right: 10px;
}
.followInfo h4 {
  display: inline-block;
}
</style>