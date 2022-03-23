<template>
  <div :class="[showBack ? 'back' : '']" v-if="isShow">
    <div class="setBar" v-if="true" :class="[showBack ? 'shotBarWidth' : '']">
      <dialogue-box @yes="deleteAll" @hideDialog="confirm" v-show="dialogShow">
        <template #content>编辑内容将全部消失</template>
      </dialogue-box>

      <div class="top" v-if="showTop">
        <div><slot name="title">快捷发布</slot></div>
        <div>
          <button @click="confirm">
            <slot name="editText">删除</slot>
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
            <i class="confont">字体</i>
            <input type="file" @change="getUrl" />
          </li>
        </ul>
      </div>

      <div class="bottom">
        <div class="addBtn" v-if="urls.length < 3">
          <i class="iconfont">add</i> <input type="file" @change="getUrl" />
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
import DialogueBox from "../components/common/DialogueBar.vue";
import { SentBlog } from "../assets/request/index.js";
import { mapMutations } from "vuex";
export default {
  props: {
    showTop: Boolean,
    showBack: Boolean,
    hideAfterSent: Boolean,
    editInfo: Object,
  },
  data() {
    return {
      urls: [],
      url: "",
      textarea: "",
      isShow: true,
      dialogShow: false,
      editFlag: null,
    };
  },
  components: { DialogueBox },
  methods: {
    //获取添加图片url
    getUrl(e) {
      console.log("我执行了");
      let file = e.target.files[0];
      console.log(file);
      let that = this;
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function () {
        console.log(that.urls.length);
        that.urls.push(this.result);
      };
      console.log(this.urls);
    },

    //删除已经添加的图片
    delImg(index) {
      this.urls.splice(index, 1);
      console.log(this.urls.length);
    },
    // 有数据后 删除提示
    confirm() {
      //若有数据则提示 否则直接删除
      if (this.urls.length == 0 && this.textarea == "") {
        this.$emit("SetBar");
        console.log("隐藏");
      } else {
        this.dialogShow = !this.dialogShow;
      }
    },

    // 清除发布框数据
    deleteAll() {
      this.urls = [];
      this.textarea = "";
      this.dialogShow = !this.dialogShow;
    },

    //异步发送数据
    // async postNew() {
    //   SentBlog(this.textarea, this.urls, 22);
    //   console.log("已经发送了" + this.inputName);
    // },

    ...mapMutations(["addData"]),
    //测试发布模块
    async postNew() {
      if (this.textarea) {
        let userId = this.$store.state.userInfo.id;
        console.log(userId);
        await SentBlog(this.textarea, userId);
        this.textarea = "";
        this.urls = [];
      } else {
        console.log("关键信息不能为空");
      }
      //点击发送后隐藏
      this.isShow = !this.hideAfterSent;
    },
  },
  created() {
    //编辑信息不为空 则自动填充编辑信息
    if (JSON.stringify(this.editInfo) !== "{}") {
      this.textarea = this.editInfo.text;
      // if (this.editInfo.picUrls.length) {
      //   for (let url of this.editInfo.picUrls) {
      //     this.urls.push(url);
      //   }
      // }
      this.editFlag = true;
    } else this.editFlag = false;
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
.setBar {
  width: 100%;
  height: auto;
  background: rgb(211, 210, 210);

  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 15px;
  border-radius: 8px;
}
.shotBarWidth {
  width: 800px;
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
.addBtn {
  position: relative;
  border: 2px solid black;
  width: 80px;
  height: 40px;
}
.addBtn i {
  pointer-events: none;
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
.addBox i,
.addBtn i {
  display: block;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
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
  font-size: 20px;
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
.top button {
  color: black;
}
</style>