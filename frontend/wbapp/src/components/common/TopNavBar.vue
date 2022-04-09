<template>
  <div class="top-bar">
    <div class="tach-bar"></div>
    <div class="top-bar-center">
      <div class="left">
        <div class="search">
          <label for="input" class="searchLabel"
            ><i class="iconfont icon-sousuo" style="font-size: 37px"></i
          ></label>
          <input id="input" type="text" />
        </div>
      </div>
      <div class="center">
        <ul ref="ulBoxRef">
          <template v-for="n of 5" :key="`center${n}`">
            <li
              @click="change(n)"
              :class="[nowTab == `center${n}` ? 'chosen' : '']"
            >
              <slot :name="`center${n}`">center{{ n }}</slot>
            </li>
          </template>
        </ul>
      </div>
      <div class="right">
        <ul>
          <li><slot name="right1">right1</slot></li>
          <li><slot name="right2">right2</slot></li>
          <li><slot name="right3">right3</slot></li>
          <li><slot name="right4">right4</slot></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nowTab: null,
    };
  },
  methods: {
    change(n) {
      this.nowTab = `center${n}`;
      this.nowNum = n;
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
li {
  list-style: none;
}
.top-bar {
  width: 95%;
  height: 80px;
  z-index: 1000;
  position: fixed;
  left: 50%;
  transform: translateX(-50%);
  top: 0;
  display: flex;
  background: #fff;
  justify-content: space-between;
  border-radius: 10px;
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.2);
}

.top-bar-center {
  width: 80vw;
  height: 100%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
}
.left {
  width: 200px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.search {
  display: flex;
  align-items: center;
  justify-content: center;
  width: auto;
  height: 52px;
  padding: 7px;
  border-radius: 25px;
  color: whitesmoke;
  transition: all ease 0.4s;
  /* background: rgb(194, 190, 190); */
  background: royalblue;
}
.searchLabel {
  /* background: seagreen; */
}
.search input {
  width: 0;
  font-size: 20px;
  outline: none;
  border: 0;
  padding: 5px 0;
  transition: all ease 0.4s;
  background: transparent;
  text-indent: 3px;
}
.search:hover {
  color: black;
  background: rgb(192, 205, 243);
}
.search:hover input {
  width: 150px;
}
.right {
  width: 300px;
  height: 100%;
}

.center {
  position: relative;
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.tracker {
  height: 100%;
  width: 70px;
  position: absolute;
  left: 0;
  top: 0;
  background: turquoise;
}
.center ul {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.right ul {
  width: 100%;
  height: 100%;
  display: flex;

  align-items: center;
  justify-content: flex-end;
}
.center li,
.right li {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70px;
  height: 100%;
  font-size: 20px;
  font-weight: bolder;
  border-bottom: 4px solid transparent;

  transition: all ease 0.2s;
}
.center li:hover,
.right li:hover {
  color: royalblue;
}
.chosen {
  border-bottom: 4px solid royalblue;
  color: royalblue;
}

/* .center li:hover a,
.right li:hover a {
  color: royalblue;
} */
</style>