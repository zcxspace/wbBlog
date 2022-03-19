<template>
  <div :class="[showBack ? 'back' : '']">
    <div class="setBar" v-if="true">
      <dialogue-box @yes="deleteAll" @hideDialog="confirm" v-show="dialogShow">
        <template #content>编辑内容将全部消失</template>
      </dialogue-box>

      <div class="top" v-if="showTop">
        <div><slot name="title">快捷发布</slot></div>
        <div>
          <button @click="confirm">
            <i class="iconfont icon-chahao"></i>
          </button>
        </div>
      </div>

      <div class="text">
        <el-input
          class="e-input"
          v-model="textarea"
          :autosize="{ minRows: 4, maxRows: 7 }"
          type="textarea"
          resize="none"
          placeholder="有什么新鲜事想分享给大家？ "
        />
      </div>
      <slot :textarea="textarea"></slot>
      <div class="img" v-if="urls.length != 0">
        <ul>
          <template v-for="(url, index) of urls" :key="index">
            <li class="imgBox">
              <img :src="url" alt="自定义" />
              <button @click="delImg(index)">
                <i class="iconfont icon-chahao"></i>
              </button>
            </li>
          </template>
          <li class="addBox" v-if="urls.length < 3">
            <label for="image"><i class="iconfont icon-jiahao"></i></label>
            <input type="file" name="image" id="image" @change="getUrl" />
          </li>
        </ul>
      </div>

      <div class="bottom">
        <div v-if="urls.length < 3">
          <label for="image"><i class="iconfont icon-tupiantianjia"></i></label>
          <input type="file" name="image" id="image" @change="getUrl" />
        </div>
        <div v-else>最多添加九张图片哦～</div>

        <div>
          <button class="postBtn" @click="postNew">发送</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DialogueBox from "./DialogueBar.vue";
export default {
  props: {
    showTop: Boolean,
    showBack: Boolean,
  },
  data() {
    return {
      urls: [],
      url: "",
      textarea: "",
      dialogShow: false,
    };
  },
  emits: ["SetBar"],
  components: { DialogueBox },
  methods: {
    //获取添加图片url
    getUrl(e) {
      console.log("我执行了");
      let file = e.target.files[0];
      let that = this;
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function () {
        console.log(that.urls.length);
        that.urls.push(this.result);
      };
    },
    delImg(index) {
      this.urls.splice(index, 1);
      console.log(this.urls.length);
    },
    confirm() {
      if (this.urls.length == 0 && this.textarea == "") {
        this.$emit("SetBar");
        console.log("隐藏");
      } else {
        this.dialogShow = !this.dialogShow;
      }
    },
    deleteAll() {
      this.urls = [];
      this.textarea = "";
      this.dialogShow = !this.dialogShow;
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
  height: 100%;
  width: 100%;
  background: rgba(0, 0, 0, 0.4);
}
.setBar {
  width: 100%;
  height: auto;
  background: whitesmoke;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 15px;
  border-radius: 8px;
}
.text {
  width: 100%;
  display: flex;
  min-height: 80px;
  flex-direction: column;
  margin-bottom: 10px;
}
.text textarea {
  resize: none;
  background: transparent;
  outline: none;
  border: 0;
  color: royalblue;
  font-size: 20px;
}
.bottom {
  width: 100%;
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.bottom .img {
  padding: 5px;
  width: 100%;
}

.img ul {
  width: 100%;
  height: 100%;
  display: flex;
  gap: 20px;
  padding: 20px 30px;
  flex-flow: row wrap;
}

.imgBox,
.addBox {
  position: relative;
  height: 120px;
  width: 120px;
  list-style: none;
  border-radius: 5px;
  overflow: hidden;
}
.bottom input,
.addBox input {
  opacity: 0;
}
.bottom label i {
  transition: all ease 0.3s;
  font-size: 40px;
}
.bottom label:hover i {
  color: royalblue;
  font-size: 45px;
}
.imgBox button {
  position: absolute;
  right: 0;
  top: 0;
  background: transparent;
  width: 30px;
  height: 30px;
  border: 0;
  color: whitesmoke;
}
.imgBox button i {
  transition: all ease 0.3s;
  font-size: 0;
}
.imgBox:hover button i {
  font-size: 18px;
  color: whitesmoke;
}
.addBox {
  position: relative;
  border: 2px solid black;
}
.addBox label i {
  transition: all ease 0.3s;
  font-size: 45px;
}
.addBox input {
  width: 100%;
  height: 100%;
}
.addBox label {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.addBox:hover {
  border: 2px solid royalblue;
}
.addBox:hover i {
  color: royalblue;
  font-size: 50px;
}
.imgBox img {
  width: 100%;
  height: 100%;
}
.postBtn {
  width: 65px;
  height: 40px;
  border: 0;
  transition: all ease-in 0.3s;
  background: steelblue;
  border-radius: 20px;
}

.postBtn:hover {
  background: royalblue;
}
.top {
  width: 100%;
  height: auto;
  padding: 10px;
}
.top i {
  color: black;
}
</style>