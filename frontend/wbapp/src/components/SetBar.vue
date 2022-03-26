<template>
  <div :class="[showBack ? 'back' : '']" v-if="isShow">
    <!-- 如果不显示黑色背景则为静态发布块 宽度100% -->
    <div class="setBar" v-if="true" :class="[showBack ? 'shotBarWidth' : '']">
      <!-- 对话框 -->
      <dialogue-box @yes="deleteAll" @hideDialog="confirm" v-show="dialogShow">
        <template #content>编辑内容将全部消失</template>
      </dialogue-box>
      <!-- 发布块标题 -->
      <div class="top" v-if="showTop">
        <div><slot name="title">快捷发布</slot></div>
        <div>
          <button @click="confirm">
            <slot name="editText">删除</slot>
          </button>
        </div>
      </div>
      <!-- 输入文字区 -->
      <div class="text">
        <el-input
          v-model="textarea"
          :autosize="{ minRows: this.textareaHeight, maxRows: 7 }"
          type="textarea"
          resize="none"
          :placeholder="placeholder"
        />
      </div>
      <!-- <slot :textarea="textarea"></slot> -->

      <div class="img" v-if="displayUrls.length != 0">
        <ul>
          <!-- 图片展示区 -->
          <template v-for="(url, index) of displayUrls" :key="index">
            <li class="imgBox">
              <img :src="url" alt="自定义" />
              <button @click="delImg(index)">
                <i class="iconfont icon-chahao"></i>
              </button>
            </li>
          </template>
          <li class="addBox" v-if="displayUrls.length < 3">
            <i class="confont">字体</i>
            <input type="file" @change="getUrl" />
          </li>
        </ul>
      </div>

      <div class="bottom">
        <div class="addBtn" v-if="displayUrls.length < 3">
          <i class="iconfont">add</i>
          <input type="file" @change="getUrl" multiple="multiple" />
        </div>
        <div v-else>最多添加九张图片哦～</div>

        <div>
          <button class="postBtn" @click="postNew">
            <slot name="btnName">发送</slot>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DialogueBox from "../components/common/DialogueBar.vue";
import { SentBlog, getUserInfo, GetPublic } from "../assets/request/index.js";
import { mapMutations } from "vuex";
export default {
  props: {
    useTo: String,
    showTop: Boolean,
    showBack: Boolean,
    hideAfterSent: Boolean,
    editInfo: Object,
    textareaHeight: Number,
    placeholder: String,
    forwardId: Number,
    editId: Number,
  },
  data() {
    return {
      displayUrls: [],
      postUrls: [],
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
      this.postUrls.push(file);
      console.log(file);
      let that = this;
      //获取图片进行回显
      let reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function () {
        console.log(that.displayUrls.length);
        that.displayUrls.push(this.result);
        console.log(that.displayUrls);
        console.log(that.postUrls);
      };
      console.log(this.displayUrls);
      console.log(this.postUrls);
    },

    //删除已经添加的图片
    delImg(index) {
      this.displayUrls.splice(index, 1);
      console.log(this.displayUrls.length);
    },
    // 有数据后 删除提示
    confirm() {
      //若有数据则提示 否则直接删除
      if (this.displayUrls.length == 0 && this.textarea == "") {
        this.$emit("SetBar");
        console.log("隐藏");
      } else {
        this.dialogShow = !this.dialogShow;
      }
    },

    // 清除发布框数据
    deleteAll() {
      this.displayUrls = [];
      this.textarea = "";
      this.dialogShow = !this.dialogShow;
    },

    //异步发送数据
    // async postNew() {
    //   SentBlog(this.textarea, this.urls, 22);
    //   console.log("已经发送了" + this.inputName);
    // },

    ...mapMutations(["addData", "updateUserInfo", "updateHomePageDynamic"]),
    //测试发布模块
    async postNew() {
      if (this.textarea) {
        let form = new FormData();
        for (let url of Object.values(this.postUrls)) {
          form.append("file", url);
        }

        console.log(form.getAll("file"));
        let userId = this.$store.state.userInfo.id;

        form.append("text", this.textarea);
        form.append("visible", 0);
        form.append("userId", userId);
        //发布功能
        if (this.useTo == "publish") {
          form.append("forwardDynamicId", 0);
          await SentBlog(form);
          //发送动态后获取用户信息
          console.log(this.$store.state.userInfo.profileUrl);
          let result = await getUserInfo(this.$store.state.userInfo.profileUrl);
          this.updateUserInfo(result.data.data);
          console.log(result);
        } else if (this.useTo == "forward") {
          form.append("forwardDynamicId", this.forwardId);
          console.log("转发了");
          await SentBlog(form);
        } else if (this.useTo == "edit") {
          form.append("id", this.editId);
          await SentBlog(form);
        }

        let result = await GetPublic();
        let publicDynamic = result.data.data;
        this.updateHomePageDynamic(publicDynamic);
        // console.log(this.$store.state.HomePageDynamic);
        this.textarea = "";
        this.displayUrls = [];
      } else {
        console.log("关键信息不能为空");
      }
      //点击发送后隐藏
      this.isShow = !this.hideAfterSent;
    },
  },
  created() {
    console.log(this.textareaHeight);
    //编辑信息不为空 则自动填充编辑信息
    if (JSON.stringify(this.editInfo) !== "{}") {
      this.textarea = this.editInfo.text;
      if (this.editInfo.Urls) {
        this.displayUrls = this.editInfo.Urls;
      } else console.log("图片为空");
      console.log(this.editInfo.Urls);
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
.imgBox {
  margin: 0 15px;
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