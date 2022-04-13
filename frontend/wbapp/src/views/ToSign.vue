<template>
  <div class="toSign">
    <div class="goBackBar">
      <button class="goBackBtn" @click="goBack">
        <i class="iconfont icon-fanhui3"></i>
      </button>
    </div>
    <div class="mainBox">
      <div class="DisTab"></div>
      <div class="Form">
        <!-- 注册页 -->
        <div class="signUp" v-if="isSignUp">
          <ul>
            <li><h2>准备好开始了吗？</h2></li>
            <li>
              <input-com
                :reg="Ereg"
                v-model="UpEmail"
                :icon="'icon-sixin'"
                :type="'text'"
                :placeholder="'请输入您的邮箱'"
              ></input-com>
            </li>
            <li>
              <input-com
                v-model="UpPassword"
                :icon="'icon-mima'"
                :type="'password'"
                :reg="PassReg"
                :placeholder="'请填写八位有效字符'"
              ></input-com>
            </li>
            <li>
              <div class="gender">
                <div>
                  <input
                    type="radio"
                    name="gender"
                    id="man"
                    value="0"
                    v-model="UpGender"
                  />
                  <label for="man">
                    男<i class="iconfont icon-icon2"></i
                  ></label>
                </div>

                <div>
                  <input
                    type="radio"
                    name="gender"
                    value="1"
                    id="woman"
                    v-model="UpGender"
                  />
                  <label for="woman">
                    女<i class="iconfont icon-icon1"></i
                  ></label>
                </div>
              </div>
            </li>
            <!-- 登陆验证 -->
            <li>
              <input-com
                v-model="UpCap"
                :icon="'icon-yanzhengma'"
                :type="'text'"
              ></input-com>
              <button
                class="UpCap"
                @click="getUpCap"
                :class="[isObtain ? '' : 'disable']"
                :disabled="!isObtain"
              >
                <span v-if="isObtain">获取验证码</span>
                <span v-else>{{ count }}s后重新获取</span>
              </button>
            </li>
            <!-- <el-button :plain="true" @click="open2">success</el-button> -->
            <li><button @click="toSignUp" class="SignUpBtn">注册</button></li>

            <!-- <li><button @click="change" >去登录</button></li> -->
          </ul>
        </div>
        <!-- 登陆页 -->
        <div class="signIn" v-else>
          <ul>
            <li><h1>欢迎回来</h1></li>
            <li>
              <input-com
                :icon="'icon-sixin'"
                :type="'text'"
                :placeholder="'请输入您的邮箱'"
                v-model="SignInEmail"
              ></input-com>
            </li>
            <li>
              <input-com
                :icon="'icon-mima'"
                :type="'password'"
                :placeholder="'请输入您的密码'"
                v-model="SignInPass"
              ></input-com>
            </li>
            <li>
              <input-com
                :icon="'icon-yanzhengma'"
                :placeholder="'请输入验证码'"
                v-model="SignInCap"
              ></input-com>
              <div class="signCapBox" :class="[isDisable ? 'disable' : '']">
                <img :src="CapSrc" @click="getCaptcha" />
              </div>
            </li>
            <li>
              <button class="SignInBtn" @click="toSignIn">
                登录<i class="iconfont icon-fasong"></i>
              </button>
            </li>
            <li>
              <button @click="toUp" class="toSignUpBtn">
                还没有账号？去注册
              </button>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getUserInfo,
  getCaptcha,
  SignIn,
  getEmailCap,
  SignUp,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import { mapMutations } from "vuex";
import inputCom from "../components/SignComs/inputCom.vue";

export default {
  props: {
    page: String,
  },
  components: { inputCom },
  data() {
    return {
      //注册数据
      count: 15,
      isObtain: true,
      UpCap: null,
      UpEmail: null,
      UpPassword: null,
      UpGender: null,
      TestValue: null,
      Ereg: /[-.\w]+@([\w-]+\.)+[\w-]+/i,
      PassReg: /^\d{8}$/i,
      //登陆数据
      SignInEmail: null,
      SignInPass: null,
      SignInCap: null,
      isSignUp: true,
      CapSrc: null,
      isDisable: false,
    };
  },
  methods: {
    toUp() {
      this.$router.push({ name: "Sign", params: { page: "Up" } });
    },
    goBack() {
      this.$router.go(-1);
    },
    ...mapMutations(["updateUserInfo", "updateStatus", "updateLeftBar"]),
    countDown() {
      this.count--;
      console.log(this.count);
    },
    async getECap() {
      let result = await getEmailCap(this.UpEmail);
      console.log(result);
    },
    getUpCap() {
      this.getECap();
      this.isObtain = false;
      let timer = setInterval(() => {
        this.countDown();
        if (this.count <= 0) {
          this.isObtain = true;
          this.count = 15;
          clearInterval(timer);
        }
      }, 1000);

      // console.log("niho");
    },
    change() {
      this.isChange = !this.isChange;
    },
    // 获取验证码
    async getCaptcha() {
      let result = await getCaptcha();
      this.CapSrc = result;
      this.isDisable = true;
      setTimeout(() => {
        this.isDisable = false;
      }, 2000);
    },
    async toSignUp() {
      let result = await SignUp(
        this.UpEmail,
        this.UpPassword,
        this.UpGender,
        this.UpCap
      );
      if (result.data.message.includes("成功")) {
        this.isSignUp = !this.isSignUp;
      }
      console.log(result);
    },
    //登陆方法

    async toSignIn() {
      let result = await SignIn(
        this.SignInEmail,
        this.SignInPass,
        this.SignInCap
      );
      console.log(result);
      if (result.data.message.includes("成功")) {
        let path = result.data.data.user.profileUrl;
        //登录成功获得用户信息

        let UserInfo = await getUserInfo(path);
        this.updateStatus(true);
        console.log(UserInfo);
        let Info = UserInfo.data.data;
        if (!Info.user.photo) {
          Info.user.photo =
            "https://images.unsplash.com/photo-1649537424344-ac798f2ba68b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw4MXx8fGVufDB8fHx8&auto=format&fit=crop&w=900&q=60";
        }
        console.log(Info);

        this.updateUserInfo(Info);
        this.updateLeftBar("homePage");
        this.leftName = "homePage";
        //切换页面后更新数据
        sessionStorage.setItem("store", JSON.stringify(this.$store.state));
        this.$router.push({
          name: "wb",
          params: {
            pageName: "homePage",
          },
        });
        console.log(this.$store.state.userInfo);
      } else console.log(result.data.message);
    },
  },

  created() {
    //更新验证码
    //为注册 切换注册
    this.getCaptcha();
    if (this.page.includes("In")) {
      this.isSignUp = false;
    }
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
@keyframes jump {
  0% {
    transform: translateY(0px);
  }
  25% {
    transform: translateY(4px);
  }
  50% {
    transform: translateY(-4px);
  }
  100% {
    transform: translateY(0px);
  }
}
@keyframes move {
  0% {
    transform: scale(0.7);
  }
  25% {
    transform: scale(0.95);
  }
  50% {
    transform: scale(1.01);
  }
  100% {
    transform: scale(1);
  }
}
.gender {
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
.gender div {
  display: flex;
  align-items: center;
  justify-content: center;
}
.gender div label i {
  font-size: 50px;
  font-weight: 900;
  transition: all ease 0.4s;
  margin: 5px;
}
.gender div input:checked + label {
  color: royalblue;
}
.gender div input {
  opacity: 0;
}
.gender div label i:hover {
  font-size: 65px;
}
.toSign {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
  position: relative;
}
.mainBox {
  position: relative;
  width: 75%;
  height: 65%;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.DisTab {
  background-image: linear-gradient(to right, #74ebd5 0%, #9face6 100%);
  border-radius: 15px;
  width: 100%;
  height: 100%;
  backdrop-filter: blur(50px);
  box-shadow: 0 1px 3px rgba(18, 18, 18, 0.2);
}
.carousel {
  width: 100%;
  height: 100%;
}
.Form {
  width: 55%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 10px;
}

.signIn,
.signUp {
  height: 90%;
  width: 100%;
  position: relative;
  margin: 0 50px;
  animation: move 1s linear 0s 1;
}

/* 登陆页样式 */
.signIn ul,
.signUp ul {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: space-between;
}
.toSignUpBtn {
  outline: none;
  background: transparent;
  border: 0;
  transition: all ease 0.4s;
}
.toSignUpBtn:hover {
  color: royalblue;
}
.signIn ul li,
.signUp ul li {
  width: 100%;
  min-height: 100px;
  list-style: none;
  text-align: start;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  margin: 10px;
}
.signIn ul li:nth-child(1),
.signUp ul li:nth-child(1) {
  justify-content: flex-start;
  font-size: 30px;
}
.mainForm {
  flex: 1;
  height: 100%;
}

.SignInBtn,
.SignUpBtn {
  background: royalblue;
  outline: none;
  border: 0;
  color: white;
  width: 100%;
  height: 100%;
  font-size: 30px;
  border-radius: 15px;
  transition: all ease 0.4s;
}
.SignInBtn:hover,
.SignUpBtn:hover {
  transform: translateY(-3px);
}
.SignInBtn:active,
.SignUpBtn:active {
  transform: translateY(3px);
}
.SignInBtn i {
  opacity: 0;
  transition: all linear 0.4s;
  display: inline-block;
}
.SignInBtn:hover i {
  opacity: 1;
}
.inputBox {
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: center;
  position: relative;
  background: rgb(235, 233, 233);
  border-radius: 15px;
}

.inputBox .label {
  position: absolute;
  left: 5px;
  top: 50%;
  transform: translateY(-50%);
}
.inputBox .label i {
  font-size: 40px;
}
.inputBox input {
  outline: none;
  border: 0;
  height: 100%;
  background: transparent;
  font-size: 30px;
  padding: 15px;
  text-indent: 40px;
  width: 100%;
  border-radius: 15px;
}
.inputBox input:focus {
  border: 3px solid royalblue;
}
.signCapBox {
  height: 100%;
  width: 70%;
  margin-left: 15px;
  border-radius: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  background: chocolate;
}
.getCap {
  height: 65px;
  width: 65%;
}
.signCapBox img {
  width: 100%;
  height: 100%;
}
.getCap button {
  width: 100%;
  height: 100%;
  border-radius: 15px;
  outline: none;
  border: 0;
  background: chocolate;
  margin-left: 5px;
}
.UpCap {
  width: 60%;
  height: 80%;
  background: rgb(158, 157, 157);
  outline: none;
  border-radius: 15px;
  border: 0;
  padding: 5px;
  margin-left: 10px;
  transition: all ease 0.3s;
  word-spacing: 2px;
  font-size: 26px;
  cursor: pointer;
}
.disable {
  /* pointer-events: none; */
  cursor: not-allowed;
}
.goBackBar {
  position: absolute;
  left: 20px;
  top: 20px;
}

.goBackBar button:hover {
  transform: translateX(5px);
}
.goBackBar button:hover i {
  color: royalblue;
}
.goBackBar button:active {
  transform: translateX(-5px);
  color: royalblue;
}
.goBackBar button i {
  font-size: 90px;
}
.goBackBar button {
  outline: none;
  transition: all ease 0.3s;
  border: 0;
}
</style>