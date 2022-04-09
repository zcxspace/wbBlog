<template>
  <div :class="[isShowTitle ? 'back' : '']" v-show="isOpen">
    <div class="editBar" :class="[isShowTitle ? 'height' : '']">
      <dialogue-bar
        v-if="isShowD"
        @yes="closeSet"
        @hideDialog="isShowD = false"
      ></dialogue-bar>
      <div class="setTitle" v-if="isShowTitle">
        <div class="replyText">回复@{{ replyName }}的消息</div>
        <div class="close">
          <button @click="confirm">
            <i class="iconfont icon-close"></i>
          </button>
        </div>
      </div>
      <div class="textarea">
        <el-input
          v-model="comValue"
          :autosize="{ minRows: 2, maxRows: 4 }"
          type="textarea"
          placeholder="分享你的见解..."
        />
      </div>
      <div class="btn">
        <button @click="postCom"><slot name="btnTitle">评论</slot></button>
      </div>
    </div>
  </div>
</template>

<script>
import DialogueBar from "./DialogueBar.vue";
import { postComment } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  components: { DialogueBar },
  props: {
    isShowTitle: Boolean,
    dynamicInfo: Object,
    floorId: Number,
    replyName: String,
    replyId: Number,
  },

  emits: ["close", "updateCom"],
  data() {
    return {
      textarea2: "",
      isOpen: true,
      comValue: null,
      isShowD: false,
    };
  },
  mounted() {},
  methods: {
    confirm() {
      if (!Number(this.textarea2)) {
        this.closeSet();
      } else {
        this.isShowD = true;
      }
    },
    //   关闭评论弹窗
    closeSet() {
      this.$emit("close");

      this.isOpen = false;
    },
    async postCom() {
      let result = await postComment(
        this.comValue,
        this.replyId,
        this.dynamicInfo.id,
        this.$store.state.userInfo.id,
        this.floorId
      );

      if (result.data.message == "评论成功") {
        this.$emit("updateCom");
        this.comValue = "";
      }
      console.log(this.dynamicInfo.id);
      console.log(result);
      if (this.isShowTitle) {
        this.isOpen = false;
      }
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

.back {
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  height: 100%;
  width: 100%;
  background: rgba(0, 0, 0, 0.4);
}
.editBar {
  width: 100%;
  height: auto;
  padding: 10px;
  background: #fff;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
}
.height {
  width: 500px;
}
.textarea {
  margin: 10px 0;
}
.setTitle {
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.replyText {
  width: auto;
  border-radius: 5px;
  background: rgba(151, 151, 151, 0.4);
  display: flex;
  padding: 10px;
  align-items: center;
  height: auto;
}
.btn {
  display: flex;
  justify-content: flex-end;
}
.btn button {
  width: 80px;
  height: 45px;
  background: royalblue;
  color: white;
  border-radius: 23px;
  border: 0;
  transition: all ease 0.3s;
  outline: none;
}
.btn button:hover {
  transform: translateY(-3px);
}
.btn button:active {
  transform: translateY(3px);
}
.close button {
  border: 0;
  outline: none;
  background: transparent;
  transition: all ease 0.3s;
  margin-right: 5px;
}
.close button:hover {
  transform: scale(1.2);
}
.close button:active {
  transform: scale(0.9);
}
.close button i {
  font-size: 20px;
}
</style>