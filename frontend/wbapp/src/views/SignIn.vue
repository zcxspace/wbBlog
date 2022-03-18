<template>
  <div class="SignIn">
    <div class="main">
      <div class="title">
        <div>Sign In</div>

        <div>
          back
          <button @click="goToViewer">
            <i class="iconfont icon-arrowRight"></i>
          </button>
        </div>
      </div>
      <div class="form">
        <div class="inputBox">
          <input id="email" type="text" v-model="email" required />
          <label for="email">邮箱</label>
        </div>

        <div class="inputBox">
          <input type="password" v-model="password" required />
          <label>密码</label>
        </div>

        <div class="inputBox">
          <input type="text" v-model="captcha" required />
          <label>验证码</label>
          <img :src="CapSrc" alt="验证码" @click="Captcha" />
        </div>
        <div class="btn">
          <button @click="test">SignIn</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getCaptcha,
  SignIn,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";
export default {
  data() {
    return {
      email: null,
      password: null,
      captcha: null,
      CapSrc: null,
    };
  },
  methods: {
    async Captcha() {
      let CapSrc = await getCaptcha();
      this.CapSrc = CapSrc;
    },
    async goToSignIn() {
      let result = await SignIn(this.email, this.password, this.captcha);
      console.log(result);
      if (result.data.data.msg == "登录成功") {
        this.$router.push({
          name: "userPage",
          params: { info: JSON.stringify(result) },
        });
      }
    },
    goToViewer() {
      this.$router.push("/ViewerPage");
    },

    test() {
      this.$router.push("/");
    },
  },
  created() {
    this.Captcha();
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
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
.SignIn {
  position: fixed;
  left: 0;
  top: 0;
  z-index: 500;
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
  padding: 10px;
  width: 55%;
  height: 70%;
  border-radius: 24px;
  background: rgba(0, 0, 0, 0.5);
  box-shadow: 9px 9px 15px #5e87ce, -9px -9px 15px #8ac7ff;
}
.title {
  width: 100%;
  height: 100px;
  background: rgba(0, 0, 0, 0.4);
  border-radius: 25px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
  font-size: 40px;
  color: skyblue;
}
.title div:nth-child(1) {
  color: skyblue;
}
.title button {
  background: transparent;
  border: 0;
  transition: all ease 0.4s;
  color: whitesmoke;
}

.title button i {
  color: skyblue;
  transition: all ease 0.4s;
  font-size: 45px;
}
.title button i:hover {
  color: royalblue;
}
.title button:hover {
  transform: translateX(-5px);
}
.title button:active {
  transform: translateX(5px);
}

.form {
  display: flex;
  flex-direction: column;
  align-items: center;
  color: black;
  padding: 20px;
  justify-content: space-around;
  width: 100%;
  flex: 1;
  background: rgba(0, 0, 0, 0.4);
  border-radius: 30px;
  margin-top: 10px;
}
.form .inputBox {
  position: relative;
  width: 300px;
  height: 50px;
}
.form .inputBox input {
  width: 100%;
  height: 100%;
  border: 0;
  border-bottom: 2px solid skyblue;
  color: royalblue;
  background: transparent;
  outline: none;
  transition: all ease 0.3s;
  font-size: 25px;
}
.inputBox input:focus,
.inputBox input:valid {
  border-bottom: 2px solid royalblue;
}
.inputBox input:focus + label,
.inputBox input:valid + label {
  transform: translateY(-60px);
  color: royalblue;
}
.inputBox input:focus {
  border-bottom: 2px solid royalblue;
}
.form .inputBox label {
  position: absolute;
  left: 0;
  top: 50%;
  font-size: 25px;
  color: skyblue;
  transition: all ease 0.3s;
  pointer-events: none;
  transform: translateY(-50%);
}

.form :nth-child(3) {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.form :nth-child(3) input {
  width: 150px;
}
.form :nth-child(3) img {
  height: 100%;
  flex: 1;
  margin-left: 3px;
}
.btn {
  width: 300px;
  height: 50px;
  border: 3px solid skyblue;
  transition: all ease 0.4s;
  border-radius: 10px;
}

.btn button {
  width: 300px;
  height: 50px;
  border: 0;
  outline: none;
  font-size: 25px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: skyblue;
  background: transparent;
  border-radius: 10px;
}
.btn button:hover {
  color: royalblue;
}
.btn:active {
  transform: translateY(7px);
}
.btn:hover {
  border: 2px solid royalblue;
}
</style>