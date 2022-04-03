<template>
  <div class="SignUp">
    <div class="main">
      <div class="title">Sign Up</div>
      <div class="form">
        <div class="form-left">
          <form action="" @submit.prevent class="SignUpForm">
            <div class="email">
              <input
                type="text"
                id="email"
                name="email"
                v-model="userInfo.email"
                required
                @blur="checkForm('email', $event)"
                @focus="remove('email')"
              />
              <label for="email">邮箱</label>
              <div class="tip" v-show="emailShow">{{ EmailRight }}</div>
            </div>

            <div class="password">
              <input
                type="password"
                id="password"
                name="password"
                v-model="userInfo.password"
                required
                @blur="checkForm('password', $event)"
                @focus="remove('password')"
              />
              <label for="password">密码</label>
              <div class="tip" v-show="passwordShow">
                {{ PassRight }}
              </div>
            </div>

            <div class="password">
              <input
                type="password"
                id="Password"
                name="Password"
                v-model="userInfo.rePassword"
                @blur="confirm"
                @focus="remove('rePassword')"
                required
              />
              <label for="password">确认密码</label>
              <div class="tip" v-show="rePasswordShow">
                {{ PassTip }}
              </div>
            </div>

            <div class="cap">
              <div class="cap-left">
                <input
                  type="text"
                  name="captcha"
                  v-model="userInfo.captcha"
                  required
                />
                <label for="">验证码</label>
              </div>
              <div class="cap-right">
                <img id="captcha" :src="CapSrc" alt="验证码" @click="getCap" />
              </div>
              <div class="tip change"></div>
            </div>

            <div class="genderBox">
              <div>
                <input
                  type="radio"
                  id="man"
                  name="gender"
                  value="0"
                  v-model="userInfo.picked"
                  required
                />
                <label for="man"><i class="iconfont icon-nan"></i></label>
              </div>
              <div>
                <input
                  type="radio"
                  id="woman"
                  name="gender"
                  value="1"
                  v-model="userInfo.picked"
                />
                <label for="woman"><i class="iconfont icon-nv"></i></label>
              </div>
            </div>

            <div class="btn">
              <button @click="goToSignUp">SignUp</button>
            </div>
          </form>
        </div>

        <div class="form-right">
          已有账号 <span @click="goToSignIn">直接登录</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getCaptcha,
  SignUp,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";

export default {
  data() {
    return {
      userInfo: {
        email: "",
        password: "",
        captcha: null,
        picked: null,
        rePassword: "",
      },
      emailShow: null,
      passwordShow: null,
      rePasswordShow: null,
      PassTip: "请确认密码",
      EmailRight: null,
      PassRight: null,
      CapSrc: "",
    };
  },

  methods: {
    async getCap() {
      let CapSrc = await getCaptcha();
      this.CapSrc = CapSrc;
    },
    async goToSignUp() {
      await SignUp(
        this.userInfo.email,
        this.userInfo.password,
        this.userInfo.picked,
        this.userInfo.captcha
      );
    },
    checkForm(type, e) {
      if (type == "email") {
        if (/[-.\w]+@([\w-]+\.)+[\w-]+/gi.test(this.userInfo.email)) {
          this.emailShow = true;
          this.EmailRight = "格式正确";
          e.target.nextSibling.nextSibling.style.color = "green";
        } else {
          this.emailShow = true;
          this.EmailRight = "格式错误";
          e.target.nextSibling.nextSibling.style.color = "red";
        }
        this.userInfo.email == ""
          ? (this.emailShow = false)
          : (this.emailShow = true);
      }
      if (type == "password") {
        this.confirm();
        if (/[^\s]\w{7,}/gi.test(this.userInfo.password)) {
          this.passwordShow = true;
          this.PassRight = "格式正确";
          e.target.nextSibling.nextSibling.style.color = "green";
        } else {
          this.PassRight = "格式错误";
          e.target.nextSibling.nextSibling.style.color = "red";
          this.passwordShow = true;
        }
        this.userInfo.password == ""
          ? (this.passwordShow = false)
          : (this.passwordShow = true);
      }
    },
    remove(type) {
      if (type == "email") this.emailShow = false;
      if (type == "password") this.passwordShow = false;
      if (type == "rePassword") this.rePasswordShow = false;
    },
    confirm() {
      if (this.userInfo.password == this.userInfo.rePassword) {
        this.PassTip = "密码一致";
        this.rePasswordShow = true;
      } else {
        this.PassTip = "密码不一致";
        this.rePasswordShow = true;
      }
      if (this.userInfo.rePassword == "") this.rePasswordShow = false;
    },
    goToSignIn() {
      this.$router.push("/SignIn");
    },
  },
  created() {
    console.log("加载完");
    this.getCap();
  },

  components: {},
};
</script>

<style scoped>
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
.SignUp {
  width: 100vw;
  height: 100vh;
  display: flex;
  background: #74a7fe;
  justify-content: center;
  align-items: center;
  color: white;
}
.main {
  display: flex;
  flex-direction: column;
  padding: 5px;
  animation: move 1s linear 0s 1;
  width: 55%;
  height: 70%;
  border-radius: 24px;
  background: rgba(0, 0, 0, 0.4);
  box-shadow: 9px 9px 15px #5e87ce, -9px -9px 15px #8ac7ff;
}
.title {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  height: 100px;
  color: skyblue;
  border-radius: 30px;
  padding-left: 300px;
  font-size: 50px;
  background: rgba(0, 0, 0, 0.4);
}
.form {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  flex: 1;
}
.form-left {
  margin: 0 5px;
  padding-top: 2px;
  width: 75%;
  padding-top: 25px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  flex-direction: column;
  border-radius: 30px;
  background: rgba(0, 0, 0, 0.4);
  padding-bottom: 10px;
}

.form-right {
  /* width: 100%; */
  flex: 1;
  color: skyblue;
  margin: 0 5px;
  height: 100%;
  border-radius: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.4);
}
.form-right span {
  transition: all ease-in-out 0.3s;
}
.form-right:hover span {
  color: royalblue;
}

.SignUpForm {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}
.SignUpForm div {
  margin: 3px;
  width: 300px;
  height: 45px;
}

.genderBox,
.cap {
  position: relative;
  display: flex;
  align-items: center;

  justify-content: space-between;
}

.cap-right img {
  width: 100%;
  height: 100%;
  border: 0;
  display: block;
  position: relative;
}

.birth select {
  background: transparent;
  border: 0;
  outline: none;
  width: 80px;
  font-size: 20px;
  height: 40px;
}

.email,
.password,
.cap-left {
  position: relative;
}
.email .tip,
.password .tip,
.cap .tip {
  width: auto;
  height: auto;
  position: absolute;
  right: -100px;
  top: 50%;
  transform: translateY(-50%);
}
.email input,
.password input,
.cap-left input {
  height: 100%;
  text-indent: 5px;
  border: 0;
  outline: none;
  background: transparent;
  width: 100%;
  color: royalblue;
  font-size: 20px;
  border-bottom: 2px solid skyblue;
}
.email input:focus + label,
.email input:valid + label,
.password input:focus + label,
.password input:valid + label,
.cap-left input:focus + label,
.cap-left input:valid + label {
  transform: translateY(-50px);
  color: royalblue;
}
.email input:focus,
.email input:valid,
.password input:focus,
.password input:valid,
.cap-left input:focus,
.cap-left input:valid {
  border-bottom: 3px solid royalblue;
}
.email label,
.password label,
.cap-left label {
  position: absolute;
  left: 10px;
  top: 50%;
  color: skyblue;
  transition: all ease 0.4s;
  font-size: 25px;
  transform: translateY(-50%);
  pointer-events: none;
}
.change:active {
  color: red;
}

.genderBox input {
  opacity: 0;
}
.genderBox input:checked + label i {
  color: royalblue;
}
.genderBox i {
  font-size: 40px;
  transition: all ease-in 0.2s;
  color: skyblue;
}
.birth select,
.birth {
  color: skyblue;
}
.btn button {
  height: 100%;
  width: 100%;
  border-radius: 10px;
  color: skyblue;
  font-size: 20px;
  border: 2px solid skyblue;
  background: transparent;
  transition: all ease 0.4s;
}
.btn button:hover {
  color: royalblue;
  border: 2px solid royalblue;
}
.btn button:active {
  transform: translateY(5px);
}
</style>