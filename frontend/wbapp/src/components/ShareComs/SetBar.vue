<template>
  <div :class="[showBack ? 'back' : '']" v-if="isShow">
    <tip-com :type="status" :tipText="tipText" v-if="isShowTip"> </tip-com>
    <!-- 如果不显示黑色背景则为静态发布块 宽度100% -->
    <div
      class="setBar"
      v-if="true"
      :class="[showBack ? 'shotBarWidth' : '', showShadow ? 'shadow' : '']"
    >
      <!-- 对话框 -->
      <dialogue-box @yes="deleteAll" @hideDialog="confirm" v-show="dialogShow">
        <template #content>编辑内容将全部消失</template>
      </dialogue-box>
      <!-- 发布块标题 -->
      <div class="top" v-if="showTop" style="border: 0">
        <div><slot name="title">快捷发布</slot></div>
        <div class="closeBtn">
          <button @click="confirm">
            <slot name="editText"><i class="iconfont icon-close"></i></slot>
          </button>
        </div>
      </div>
      <!-- 输入文字区 -->
      <div class="text">
        <el-input
          v-model.trim="textarea"
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
          <template v-for="url of displayUrls" :key="url">
            <li class="imgBox">
              <img :src="url" alt="自定义" />
              <button @click="delImg(url)">
                <i class="iconfont icon-chahao"></i>
              </button>
            </li>
          </template>
          <li class="addBox" v-if="displayUrls.length < 3">
            <i class="iconfont icon-jia"></i>
            <input type="file" @change="getUrl" />
          </li>
        </ul>
      </div>

      <div class="bottom">
        <!-- 提示并确认添加话题 -->
        <div class="topicTip" v-if="isShowTopic">
          <div class="top">添加什么话题呢？</div>
          <div class="topics" v-if="topicText != ''">
            <p @click="setTopic">{{ topicText }}</p>
          </div>
        </div>
        <div class="bottomBar">
          <div class="funcBar" v-if="displayUrls.length < 3">
            <!-- 添加图片 -->
            <div class="addBtn">
              <button>
                <i class="iconfont icon-icon3"></i>
                <input type="file" @change="getUrl" multiple="multiple" />
              </button>
            </div>
            <!-- 添加话题 -->
            <div class="topic">
              <button @click="addTopic">
                <i class="iconfont icon-huati"></i>
              </button>
            </div>
            <div class="video">
              <button>
                <i class="iconfont icon-shipinbofang"></i>
              </button>
            </div>
            <!-- 发送动态 -->
          </div>
          <div class="post">
            <button
              class="postBtn"
              @click="postNew"
              :disabled="isDisabled"
              :class="[isDisabled ? 'disabled' : '']"
            >
              <slot name="btnName">发送</slot>
            </button>
          </div>
        </div>

        <!-- <div v-else>最多添加九张图片哦～</div> -->
      </div>
    </div>
  </div>
</template>

<script>
import DialogueBox from "../../components/ShareComs/DialogueBar.vue";
import { SentBlog, getUserInfo, GetPublic } from "../../assets/request/index";
import { mapMutations } from "vuex";
import TipCom from "../../components/ShareComs/TipCom.vue";
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
    showShadow: Boolean,
  },
  data() {
    return {
      displayUrls: [],
      postUrls: [],
      url: "",
      textarea: "",
      isShow: true,
      dialogShow: false,
      isEdit: null,
      editArr: null,
      copyArr: null,
      isShowTip: false,
      status: null,
      tipText: null,
      isDisabled: false,
    };
  },
  components: { DialogueBox, TipCom },
  computed: {
    markIndex() {
      //如果当前文本包含# 则返回#后的信息 如果添加新的#则匹配新的值
      if (this.textarea.includes("#")) {
        return this.textarea.lastIndexOf("#");
      }
      return -1;
    },
    isShowTopic() {
      //如果当前文本没有# 则隐藏话题
      if (!this.textarea.includes("#")) return false;
      //判断当前是否显示话题 从当前文本最后的#开始切割 如果中间包含space 则不显示话题
      return !this.textarea
        .slice(this.textarea.lastIndexOf("#") + 1)
        .includes(" ")
        ? true
        : false;
    },
    topicText() {
      return this.textarea.slice(this.textarea.lastIndexOf("#") + 1);
    },
  },
  methods: {
    setTopic() {
      this.textarea =
        this.textarea.slice(0, this.markIndex) +
        "#" +
        this.topicText +
        "#" +
        " ";
    },

    addTopic() {
      this.textarea = this.textarea + "#";
    },
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
    delImg(url) {
      // 获取对标数组;
      //判断是否为编辑 编辑操作复制数组 不是则操作原数组
      if (this.isEdit) {
        if (this.copyArr.includes(url)) {
          let index = this.copyArr.indexOf(url);
          this.editArr.splice(index, 1, 1);
        }
        let delDex = this.displayUrls.indexOf(url);
        this.displayUrls.splice(delDex, 1);
        this.editArr[delDex] = 1;
      } else {
        let delDex = this.displayUrls.indexOf(url);
        this.displayUrls.splice(delDex, 1);
      }

      let delDex = this.displayUrls.indexOf(url);
      this.displayUrls.splice(delDex, 1);
      this.editArr[delDex] = 1;
      console.log("test");
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

    ...mapMutations(["addData", "updateUserInfo", "updateHomePageDynamic"]),
    //测试发布模块
    async postNew() {
      this.isDisabled = true;
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
          if (result.data.message.includes("主页")) {
            this.status = "success";
            this.tipText = "发布成功";
            this.isShowTip = true;
          } else {
            this.status = "error";
            this.tipText = "发布失败";
            this.isShowTip = true;
          }

          console.log(result);
        } else if (this.useTo == "forward") {
          form.append("forwardDynamicId", this.forwardId);

          let result = await SentBlog(form);
          console.log(result);
          if (result.data.message.includes("成功")) {
            this.status = "success";
            this.tipText = "转发成功";
            this.isShowTip = true;
          } else {
            this.status = "error";
            this.tipText = "转发失败";
            this.isShowTip = true;
          }
        } else if (this.useTo == "edit") {
          form.append("id", this.editId);
          form.append("fileArray", this.editArr);
          await SentBlog(form);
        }

        let result = await GetPublic();
        let publicDynamic = result.data.data;
        this.updateHomePageDynamic(publicDynamic);
        this.textarea = "";
        this.displayUrls = [];
      } else {
        this.status = "error";
        this.tipText = "忘填文字辣";
        this.isShowTip = true;
      }
      setTimeout(() => {
        this.isShowTip = false;
        this.isDisabled = false;
      }, 2000);
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
        //返回编辑数组 传给后端进行判断
        this.copyArr = this.displayUrls.slice();
        let arr = [];
        for (let i = 0; i < this.displayUrls.length; i++) {
          arr.push(0);
        }
        console.log(this.displayUrls);
        this.editArr = arr;
      } else console.log("图片为空");
      this.isEdit = true;
    } else this.isEdit = false;
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.topicTip {
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
.topicTip div {
  margin: 5px 0;
}
.topicTip .top {
  width: auto;
  height: auto;
  border: 2px solid gray;
  padding: 5px;
  border-radius: 10px;
}
.topicTip .topics {
  width: auto;
  height: auto;
}
.topicTip .topics p {
  white-space: break-spaces;
  width: auto;
  background: rgb(207, 208, 206);
  padding: 5px 10px;
  border-radius: 10px;
}
.topicTip .topics p:hover {
  background: rgb(142, 143, 140);
}
.bottomBar {
  width: 100%;
  display: flex;
  justify-content: space-between;
  margin: 5px;
}
.funcBar {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.funcBar button {
  outline: none;
  background: transparent;
  border: 0;
  transition: all ease 0.35s;
  padding: 10px;

  border-radius: 50%;
}
.funcBar button:hover {
  color: royalblue;
  transform: scale(1.15);
  background: rgb(218, 221, 221);
}
.funcBar button:active {
  transform: scale(0.9);
}
.funcBar button i {
  font-size: 35px;
}
.funcBar div {
  margin: 0 10px;
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
  background: #fff;

  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 15px;
  border-radius: 8px;
}
.shadow {
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.2);
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
  height: auto;
  display: flex;
  flex-direction: column;

  justify-content: center;
  align-items: space-between;
}
.addBtn {
  position: relative;
  width: auto;
  height: auto;
}
.addBtn input {
  width: 30px;
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
  outline: none;
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
.addBox i {
  font-size: 30px;
  transition: all ease 0.3s;
}
.addBox:hover i {
  color: royalblue;
  font-size: 50px;
}
.addBox:active i {
  font-size: 30px;
}
.imgBox img {
  width: 100%;
  height: 100%;
}
.postBtn {
  width: 80px;
  height: 45px;
  background: royalblue;
  color: white;
  border-radius: 23px;
  border: 0;
  transition: all ease 0.3s;
  outline: none;
  cursor: pointer;
}
.disabled {
  cursor: not-allowed;
  background: grey;
}
.postBtn:hover {
  transform: translateY(-3px);
}
.postBtn:active {
  transform: translateY(3px);
}
.top {
  width: 100%;
  height: auto;
  padding: 10px;
  margin: 5px 0;
}
.top button {
  color: black;
  outline: none;
  border: 0;
  transition: all ease 0.3s;
}
.top button:hover {
  transform: scale(1.1);
}
.top button:active {
  transform: scale(0.9);
}
</style>