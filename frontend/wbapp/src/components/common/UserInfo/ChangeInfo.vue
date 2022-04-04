<template>
  <div class="changeInfo">
    <div class="rowInfo">
      <div class="photoFile">
        <div class="mask">
          点击更换头像
          <!-- <input
            type="file"
            @change="getUrl"
            accept="image/*"
            enctype="multipart/form-data"
          /> -->
          <div>
            <ImgCutter @cutDown="getUrl" :imgMove="false"></ImgCutter>
          </div>
        </div>
        <img :src="path" alt="头像" />
      </div>
      <div>
        <h2>{{ userInfo.email }}</h2>
      </div>
    </div>
    <!-- 展示信息 -->
    <div class="setInfo">
      <div class="infos" v-if="isShow">
        <template v-for="(value, name) of getUserInfo" :key="name">
          <div class="info">
            <div>{{ name }}:{{ value }}</div>
          </div>
        </template>
      </div>

      <div class="inputs" v-else>
        <div class="inputBox">
          <p>姓名</p>
          <input type="text" v-model="userInfo.name" ref="name" @input="ttt" />
        </div>

        <div class="inputBox">
          <p>地址</p>
          <input type="text" v-model="userInfo.address" ref="address" />
        </div>

        <div class="inputBox">
          <p>电话</p>
          <input type="text" v-model="userInfo.phone" ref="phone" />
        </div>

        <div class="inputBox">
          <p>职业</p>
          <input type="text" v-model="userInfo.job" ref="job" />
        </div>

        <div class="inputBox">
          <p>兴趣</p>
          <input type="text" v-model="userInfo.interests" ref="interest" />
        </div>
        <div class="inputBox">
          <p>性别</p>
          <label for="man">
            <i class="iconfont icon-nan">男</i>
          </label>
          <input
            type="radio"
            name="gender"
            class="gender"
            value="1"
            v-model="picked"
            :checked="this.$store.state.userInfo.gender == 1"
            id="man"
            ref="man"
          />
          <label for="woman">
            <i class="iconfont icon-nv">女</i>
          </label>
          <input
            type="radio"
            name="gender"
            class="gender"
            v-model="picked"
            :checked="this.$store.state.userInfo.gender == 0"
            value="0"
            id="woman"
            ref="woman"
          />

          {{ this.picked }}
        </div>
        <div class="inputBox">
          <p>特质</p>
          <input type="text" v-model="userInfo.trait" ref="trait" />
        </div>
        <div class="inputBox">
          <p>生日</p>
          <input type="date" name="" id="" />
          <select v-model="selected">
            <option value="" v-for="n of 100" :key="n">{{ n }}</option>
          </select>
          {{ selected }}
          <el-date-picker
            v-model="userInfo.birthday"
            type="date"
            editable="false"
            placeholder="Pick a day"
            ref="birthday"
            @change="get"
          />
        </div>
      </div>

      <div class="btn">
        <button @click="isShow = !isShow" v-show="isShow">编辑信息</button>
        <button
          @click="
            showPanel();
            cancel();
          "
          v-show="!isShow"
        >
          取消
        </button>
        <button @click="changeInfo" v-show="!isShow">修改信息</button>
      </div>
    </div>
  </div>
</template>

<script>
import ImgCutter from "vue-img-cutter";

import {
  changeProfile,
  changeUserInfo,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import { mapMutations } from "vuex";
export default {
  data() {
    return {
      userInfo: null,
      userBirthday: null,
      isShow: true,
      input: "",
      value1: "",
      fileUrl: "",
      picked: "",
      selected: 3,
      userFile: null,
      //用于回显
      path: null,
      // path: this.$store.state.userInfo.photo,
    };
  },
  name: "ChangeInfo",
  components: { ImgCutter },
  methods: {
    cutDown(result) {
      console.log(result);
      this.path = result.dataURL;
    },
    ...mapMutations(["updateUserPrivate", "updateUserAvatar"]),
    async changeInfo() {
      this.isShow = !this.isShow;
      let gender = this.getChangeGender;
      let { name, address, intro, phone, job, trait, interests, birthday } =
        this.userInfo;
      let res = await changeUserInfo(
        name,
        address,
        intro,
        birthday,
        phone,
        job,
        interests,
        trait,
        gender
      );
      console.log(res);
      this.updateUserPrivate(res.data.data);
    },

    cancel() {
      this.userInfo = Object.assign({}, this.$store.state.userInfo);
    },
    ttt() {
      console.log(this.userInfo.name);
      console.log(this.$store.state.userInfo.name);
    },
    get() {
      console.log(this.userBirthday);
      console.log(new Date(this.userInfo.birthday));
      console.log(new Date(this.$store.state.userInfo.birthday));
    },
    showPanel() {
      this.isShow = true;
    },
    async getUrl(e) {
      let file = e.file;
      let form = new FormData();
      form.append("file", file);
      let result = await changeProfile(form);
      console.log(result);
      if (result.data.message == "图片上传成功") {
        let filePath = result.data.data.file.filePath;
        this.updateUserAvatar(filePath);
      } else {
        console.log("图片上传失败");
      }
      //   // 获取当前实例
      //   let that = this;
      //   reader.onload = async function () {

      //    that.updateUserAvatar(this.result)
      //     // if (result.data.message == "图片上传成功") {
      //     //   let filePath = result.data.data.file.filePath;
      //     //   this.path = this.result;

      //     //   console.log(filePath);
      //     //   that.updateUserAvatar(filePath);

      //     //   console.log(result);
      //     // } else {
      //     //   console.log("图片上传失败");
      //     // }
      //     // console.log(result.data.data.filePath);
      //   };
      //    let form = new FormData();
      //     form.append("file", file);
      //     let result = await changeProfile(form);
      //     console.log(result);
    },
  },
  computed: {
    getGender() {
      return this.$store.state.userInfo.gender ? "男" : "女";
    },
    getBirth() {
      let time = new Date(this.userInfo.birthday);
      let year = time.getFullYear();
      let month = time.getMonth();
      let day = time.getDate();
      return `${year}年${month + 1}月${day}日`;
    },
    getUserInfo() {
      let { name, address, intro, phone, job, trait, interests } =
        this.$store.state.userInfo;
      let obj = {
        姓名: name,
        地址: address,
        简介: intro,
        电话: phone,
        工作: job,
        特质: trait,
        兴趣: interests,
        性别: this.getGender,
        生日: this.getBirth,
      };
      return obj;
    },

    //修改性别时 获取性别 若未选中则返回原数据
    getChangeGender() {
      return this.picked == "" ? this.userInfo.gender : this.picked;
    },
  },
  created() {
    this.userInfo = Object.assign({}, this.$store.state.userInfo);
    this.userBirthday = this.userInfo.birthday;
    this.path = this.$store.state.userInfo.photo;
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.changeInfo {
  width: 100%;
  min-height: 400px;
  display: flex;
  flex-direction: column;
  background: chocolate;
}
.rowInfo {
  width: 100%;
  height: 150px;
  display: flex;
  overflow: hidden;
  justify-content: space-between;
  padding: 5px;
}
.rowInfo div:nth-child(1) {
  width: 150px;
  height: 100%;
  overflow: hidden;
  border-radius: 50%;
}
.rowInfo div:nth-child(2) {
  flex: 1;
  display: flex;
  align-items: center;
  padding: 10px;
  background: cadetblue;
}
.setInfo {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  background: darkcyan;
}
.inputs {
  min-height: 200px;
  display: flex;
  padding: 10px;
  flex: 1;
  width: 100%;
  flex-direction: column;
}
.inputBox {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin: 5px 0;
  min-height: 60px;
  background: whitesmoke;
  border-radius: 8px;
  font-size: 25px;
  padding-left: 5px;
}
.inputBox input {
  font-size: 20px;
  outline: none;
  border: 0;
  background: transparent;
}
.inputBox p {
  min-width: 60px;
}
.inputBox i {
  font-size: 30px;
}
.chosen {
  color: royalblue;
}
.btn {
  width: 100%;
  height: 100px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
.btn button {
  height: 55%;
  width: 140px;
  border: 0;
  font-size: 20px;
  background: whitesmoke;
  transition: all ease 0.4s;
  border-radius: 70px;
}
.btn button:hover {
  transform: translateY(-4px);
}
.btn button:active {
  transform: translateY(4px);
}
.infos {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  padding: 10px;
}
.infos div {
  min-height: 50px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
  margin: 5px 0;
  font-size: 25px;
  border-radius: 8px;
  padding: 0 5px;
  background: whitesmoke;
}
.gender {
  opacity: 1;
}
.photoFile {
  position: relative;
  background: transparent;
  border: 2px solid whitesmoke;
}
.photoFile img,
.photoFile input {
  width: 100%;
  height: 100%;
}
.photoFile .mask {
  display: flex;
  opacity: 0;
  align-items: center;
  justify-content: center;
  position: absolute;
  left: 0;
  top: 0;
  transition: all ease 0.1s;
  width: 100%;
  height: 100%;
  font-size: 25px;
  color: white;
}
.photoFile:hover .mask {
  opacity: 1;
  background: rgba(0, 0, 0, 0.4);
}
.mask input {
  position: absolute;
  left: 0;
  top: 0;
  opacity: 0;
  width: 100%;
  height: 100%;
}
</style>