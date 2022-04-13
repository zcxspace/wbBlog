<template>
  <div class="selectCom">
    <ul>
      <template v-for="(item, index) of info" :key="index">
        <li @click="isOpen = !isOpen" v-if="item">{{ info[index] }}</li>
        <li @click="isOpen = !isOpen" v-else>这个人比较懒...</li>
      </template>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    userInfo: Array,
  },
  data() {
    return {
      isOpen: false,
    };
  },

  computed: {
    info() {
      if (!this.isOpen) {
        return this.userInfo.slice(0, 1);
      } else {
        return this.userInfo;
      }
    },
  },
  created() {
    console.log(this.userInfo);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.selectCom {
  width: 100%;
  height: auto;
}
.selectCom ul {
  width: 100%;
  height: auto;
  transition: all ease 0.4s;
}
.selectCom ul li {
  width: 100%;
  min-height: 40px;
  list-style: none;
  text-align: start;
  cursor: pointer;
  padding: 5px 20px;
}
</style>