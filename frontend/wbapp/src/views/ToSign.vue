<template>
  <div class="toSign">
    <div class="mainBox">
      <div class="DisTab"></div>
      <div class="Form">
        <!-- 注册页 -->
        <div class="signUp" v-if="isChange">
          <button @click="change">ann</button>
          <ul>
            <li>
              <!-- <div class="inputBox">
                <label for="SignUpEmail" class="label"
                  ><i class="iconfont icon-youxiang1"></i
                ></label>
                <input type="text" id="SignUpEmail" v-model="SignUpEmail" />
              </div> -->
            </li>
            <li>
              <!-- <div class="inputBox">
                <label for="SignUpPassword" class="label"
                  ><i class="iconfont icon-mima"></i
                ></label>
                <input
                  type="password"
                  id="SignUpPassword"
                  required
                  v-model="SignUpPassword"
                />
              </div> -->
            </li>
            <li>
              <!-- <div class="inputBox">
                <label for="SignUpRePassword" class="label"
                  ><i class="iconfont icon-querenmima"></i
                ></label>
                <input
                  type="password"
                  id="SignUpRePassword"
                  required
                  v-model="SignUpRePassword"
                />
              </div> -->
            </li>
            <li>
              <!-- {{ value }} -->
              <input-com v-model="value" :reg="reg"></input-com>
            </li>
            <li></li>
            <li></li>
          </ul>
        </div>
        <!-- 登陆页 -->
        <div class="signIn" v-if="!isChange">
          <ul>
            <li><h1>欢迎回来</h1></li>
            <li>
              <div class="inputBox">
                <label for="SignEmail" class="label"
                  ><i class="iconfont icon-youxiang1"></i
                ></label>
                <input type="text" id="SignEmail" v-model="SignInEmail" />
              </div>
            </li>
            <li>
              <!-- <div class="inputBox">
                <label for="SignInPassword" class="label"
                  ><i class="iconfont icon-mima"></i
                ></label>
                <input
                  type="password"
                  id="SignInPassword"
                  required
                  v-model="SignInPassword"
                />
              </div> -->
            </li>
            <li>
              <!-- <div class="inputBox">
                <label for="cap" class="label">
                  <i class="iconfont icon-yanzhengma"></i>
                </label>
                <input type="text" required v-model="SignInCap" />
              </div>
              <div class="signCapBox" :class="[isDisable ? 'disable' : '']">
                <img :src="CapSrc" @click="getCaptcha" />
              </div> -->
            </li>
            <li>
              <!-- <button class="SignInBtn" @click="toSignIn">
                登陆<i class="iconfont icon-fasong"></i>
              </button> -->
            </li>
            <li>
              <button @click="change" class="toSignUpBtn">
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
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
import { mapMutations } from "vuex";
import inputCom from "../components/common/Single/inputCom.vue";

export default {
  components: { inputCom },
  data() {
    return {
      reg: /^\d*$/gi,
      value: null,
      isChange: true,
      CapSrc: null,
      isDisable: false,
      //   登陆信息
      SignInEmail: null,
      SignInPassword: null,
      SignInCap: null,
    };
  },
  methods: {
    ...mapMutations(["updateUserInfo"]),
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
    //登陆方法

    async toSignIn() {
      let result = await SignIn(
        this.SignInEmail,
        this.SignInPassword,
        this.SignInCap
      );
      if (result.data.data.msg.includes("成功")) {
        let path = result.data.data.user.profileUrl;
        //登录成功获得用户信息
        let UserInfo = await getUserInfo(path);
        let Info = UserInfo.data.data;
        this.updateUserInfo(Info);
        this.$router.push({
          name: "UserView",
          params: { path: `${Info}` },
        });
      } else console.log(result.data.data.msg);
    },
  },

  created() {
    //更新验证码
    this.getCaptcha();
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
.invalid {
  border: 3px solid red;
  animation: shake 1s linear 0s 1 backwards;
}
.disable {
  cursor: not-allowed;
}
.toSign {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
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
  background: royalblue;
  border-radius: 15px;
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
  margin: 0 50px;
  animation: move 1s linear 0s 1;
}
/* 登陆页样式 */
.signIn ul {
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
.signIn ul li:nth-child(1) {
  justify-content: flex-start;
  font-size: 30px;
}
.mainForm {
  flex: 1;
  height: 100%;
}

.SignInBtn {
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
.SignInBtn:hover {
  transform: translateY(-3px);
}
.SignInBtn:active {
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
}
.signCapBox img {
  width: 100%;
  height: 100%;
}
</style>