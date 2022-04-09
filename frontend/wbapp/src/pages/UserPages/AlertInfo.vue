<template>
  <div class="alertInfo">
    <!-- 用户背景区 -->
    <div class="topBack" :style="bgStyle">
      <div class="addBtn">
        <ImgCutter @cutDown="getBg" :imgMove="false" :cutWidth="500">
          <template #open>
            <button class="BgBtn">点击上传背景</button>
          </template>
        </ImgCutter>
      </div>

      <!-- 用户头像 -->
      <div class="profile">
        <!-- 切换头像遮罩层 -->
        <div class="mask">
          <i class="iconfont icon-jia"></i>
          <ImgCutter @cutDown="getPhoto" :imgMove="false">
            <template #open>
              <button class="profileBtn">点击上传头像</button>
            </template>
          </ImgCutter>
        </div>
        <img :src="userPhoto" alt="用户头像" />
      </div>
    </div>
    <div class="mainInfo">
      <ul>
        <li>
          <span>名字:</span>
          <alert-box
            @changeInfo="changeName($event)"
            :trueValue="userName"
            :type="'text'"
          ></alert-box>
        </li>
        <li>
          <span>性别:</span>
          <alert-box
            @changeInfo="changeGender($event)"
            :trueValue="gender"
            :type="'genderBox'"
          ></alert-box>
        </li>
        <li>
          <span>地址:</span>
          <alert-box
            @changeInfo="changeAddress($event)"
            :trueValue="userAddress"
            :type="'text'"
          ></alert-box>
        </li>
        <li>
          <span>电话号:</span>
          <alert-box
            @changeInfo="changePhone($event)"
            :trueValue="userPhone"
            :type="'text'"
          ></alert-box>
        </li>
        <li>
          <span>工作:</span>
          <alert-box
            @changeInfo="changeJob($event)"
            :trueValue="userJob"
            :type="'text'"
          ></alert-box>
        </li>
        <li>
          <span>爱好:</span>
          <alert-box
            @changeInfo="changeInterest($event)"
            :trueValue="userInterest"
            :type="'text'"
          ></alert-box>
        </li>

        <li>
          <span>特质:</span>
          <alert-box
            @changeInfo="changeTrait($event)"
            :trueValue="userTrait"
            :type="'text'"
          ></alert-box>
        </li>
        <li>
          <span>生日:</span>
          <alert-box
            @changeInfo="changeBirth($event)"
            :trueValue="birth"
            :type="'birthBox'"
          ></alert-box>
        </li>
        <li>
          <span>个人介绍:</span>
          <alert-box
            @changeInfo="changeIntro($event)"
            :trueValue="userIntro"
            :type="'textArea'"
          ></alert-box>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import ImgCutter from "vue-img-cutter";
import { mapMutations } from "vuex";
import AlertBox from "../../components/common/Single/alertBox.vue";
import { getDate } from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/PublicFun.js";
import {
  changeUserInfo,
  changeProfile,
  changeBack,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  components: { AlertBox, ImgCutter },
  data() {
    return {
      userBg: "",
      bgStyle: {
        background: null,
      },
      userPhoto: "",
      userBirth: getDate(new Date()),
      userGender: 1,
      userName: "zcx",
      userAge: 19,
      userIntro: "我比较懒",
      userJob: "学生",
      userAddress: "背景",
      userPhone: "123123",
      userTrait: "心态不好",
      userInterest: "滑板",
    };
  },
  computed: {
    gender() {
      return this.userGender == 0 ? "男" : "女";
    },
    birth() {
      return getDate(this.userBirth);
    },
  },
  methods: {
    ...mapMutations(["updateUserPrivate", "updateUserAvatar", "updateUserBg"]),
    // 修改用户头像
    async getPhoto(e) {
      let file = e.file;
      let form = new FormData();
      form.append("file", file);
      let result = await changeProfile(form);

      if (result.data.message.includes("成功")) {
        this.updateUserAvatar(result.data.data.file.filePath);
        this.userPhoto = e.dataURL;
      }
    },
    //更换背景
    async getBg(e) {
      let file = e.file;
      let form = new FormData();
      form.append("file", file);
      let result = await changeBack(form);
      if (result.data.message.includes("成功")) {
        this.updateUserBg(result.data.data.file.filePath);
        this.bgStyle.background = `no-repeat center/100% url(${e.dataURL})`;
      }
    },
    // 修改用户信息
    async change() {
      let result = await changeUserInfo(
        this.userName,
        this.userAddress,
        this.userIntro,
        this.userBirth,
        this.userPhone,
        this.userJob,
        this.userInterest,
        this.userTrait,
        this.userGender
      );
      console.log(result);
      if (result.data.message.includes("成功")) {
        this.updateUserPrivate(result.data.data);
      }
    },
    changeName(e) {
      console.log(e);
      // this.isTrans = !this.isTrans;
      this.userName = e;
      this.change();
      console.log("确认修改了");
    },
    changeAge(e) {
      this.userAge = e;
      this.change();
    },
    changeIntro(e) {
      this.userIntro = e;
      this.change();
    },
    changeGender(e) {
      this.userGender = e;
      this.change();
    },
    changeBirth(e) {
      this.userBirth = e;
      this.change();
    },
    changeAddress(e) {
      this.userAddress = e;
      this.change();
    },
    changePhone(e) {
      this.userPhone = e;
      this.change();
    },
    changeJob(e) {
      this.userJob = e;
      this.change();
    },
    changeInterest(e) {
      this.userInterest = e;
      this.change();
    },
    changeTrait(e) {
      this.userTrait = e;
      this.change();
    },
  },
  directives: {
    focus(el) {
      el.focus();
    },
  },
  created() {
    let userInfo = Object.assign({}, this.$store.state.userInfo);
    this.userName = userInfo.name;
    this.userAddress = userInfo.address;
    this.userPhone = userInfo.phone;
    this.userIntro = userInfo.intro;
    this.userJob = userInfo.job;
    this.userTrait = userInfo.trait;
    this.userInterest = userInfo.interests;
    this.userBirth = userInfo.birthday;
    this.userGender = userInfo.gender;
    this.userPhoto = userInfo.photo;
    this.bgStyle.background = `no-repeat center/100% url(${userInfo.background})`;
    console.log(userInfo);
  },
};
</script>

<style scoped >
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.alertInfo {
  width: 100%;
  height: auto;
  background: #fff;
  border-radius: 10px;
  overflow: hidden;
}
.topBack {
  position: relative;
  width: 100%;
  height: 350px;
  /* background: goldenrod; */
  display: flex;
  align-items: center;
  justify-content: center;
}
.topBack:hover .BgBtn {
  opacity: 1;
}
.addBtn {
  position: absolute;
  right: 10px;
  top: 10px;
}
.addBtn:hover .BgBtn {
  background: royalblue;
  color: white;
  transform: translateY(-3px);
}
.addBtn:active .BgBtn {
  transform: translateY(3px);
}
.BgBtn {
  opacity: 0;
  height: 40px;
  width: 120px;
  padding: 5px;
  outline: none;
  border-radius: 10px;
  border: 0;
  background: transparent;
  border: 3px solid royalblue;
  color: black;
  transition: all ease 0.4s;
}

.profile {
  position: relative;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  overflow: hidden;
  border: 4px solid white;
}
.profileBtn {
  position: absolute;
  left: 0;
  cursor: pointer;
  top: 0;
  width: 300px;
  height: 300px;
  opacity: 0;
}
.profile:hover .mask {
  opacity: 1;
}
.profile img {
  pointer-events: none;
  width: 100%;
  height: 100%;
}
.mask {
  position: absolute;
  left: 0;
  top: 0;
  opacity: 0;
  cursor: pointer;
  display: flex;
  background: rgba(0, 0, 0, 0.2);
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  transition: all ease 0.3s;
}
.mask i {
  font-size: 60px;
  color: #fff;
  transition: all ease 0.3s;
}
.mask:hover i {
  font-size: 70px;
}
.mask:active i {
  font-size: 40px;
}
.mainInfo {
  padding: 0 50px;
}
.mainInfo,
.mainInfo ul {
  height: auto;
  width: 100%;
}
.mainInfo ul li {
  list-style: none;
  width: 100%;
  min-height: 160px;
  margin: 5px 0;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  border-bottom: 3px solid rgb(236, 235, 235);
}
.mainInfo ul li span {
  display: inline-block;
  white-space: nowrap;
  height: 100%;
  font-size: 25px;
  font-weight: 900;
  margin: 0 5px;
}
</style>