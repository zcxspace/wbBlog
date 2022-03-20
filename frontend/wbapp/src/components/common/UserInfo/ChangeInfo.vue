<template>
  <div class="changeInfo">
    <div class="rowInfo">
      <div class="photoFile">
        <div class="mask">
          点击更换头像
          <input
            type="file"
            @change="getUrl"
            accept="image/*"
            enctype="multipart/form-data"
          />
        </div>

        <img :src="userInfo.profileUrl" alt="头像" />
      </div>
      <div>
        <h2>{{ userInfo.email }}</h2>
      </div>
    </div>

    <div class="setInfo">
      <div class="infos" v-if="isShow">
        <div class="info">
          <div>姓名:{{ userInfo.name }}{{ $store.state.userInfo.name }}</div>
        </div>
        <div class="info">
          <div>地址:{{ userInfo.address }}</div>
        </div>
        <div class="info">
          <div>电话:{{ userInfo.phone }}</div>
        </div>
        <div class="info">
          <div>职业:{{ userInfo.job }}</div>
        </div>
        <div class="info">
          <div>兴趣:{{ userInfo.interests }}</div>
        </div>
        <div class="info">
          <div>特质:{{ userInfo.trait }}</div>
        </div>
        <div class="info">
          <div>性别:{{ getGender }}</div>
        </div>
        <div class="info">
          <div>生日:{{ getBirth }}</div>
        </div>
      </div>

      <div class="inputs" v-else>
        <div class="inputBox">
          <p>姓名</p>
          <input type="text" :value="userInfo.name" ref="name" />
        </div>

        <div class="inputBox">
          <p>地址</p>
          <input type="text" :value="userInfo.address" ref="address" />
        </div>

        <div class="inputBox">
          <p>电话</p>
          <input type="text" :value="userInfo.phone" ref="phone" />
        </div>

        <div class="inputBox">
          <p>职业</p>
          <input type="text" :value="userInfo.job" ref="job" />
        </div>

        <div class="inputBox">
          <p>兴趣</p>
          <input type="text" :value="userInfo.interests" ref="interest" />
        </div>
        <div class="inputBox">
          <p>性别</p>
          <label for="man">
            <i class="iconfont icon-nan"></i>
          </label>
          <input
            type="radio"
            name="gender"
            class="gender"
            value="1"
            v-model="picked"
            :checked="this.userInfo.gender == 1"
            id="man"
            ref="man"
          />
          <label for="woman">
            <i class="iconfont icon-nv"></i>
          </label>
          <input
            type="radio"
            name="gender"
            class="gender"
            v-model="picked"
            :checked="this.userInfo.gender == 0"
            value="0"
            id="woman"
            ref="woman"
          />

          {{ this.userInfo.gender }}
        </div>
        <div class="inputBox">
          <p>特质</p>
          <input type="text" :value="userInfo.trait" ref="trait" />
        </div>
        <div class="inputBox">
          <p>生日</p>
          <el-date-picker
            :value="userInfo.birthday"
            type="date"
            placeholder="Pick a day"
            ref="birthday"
          />
        </div>
      </div>

      <div class="btn">
        <button
          @click="
            showPanel();
            cancel();
          "
          v-show="!isShow"
        >
          取消
        </button>
        <button @click="changeInfo">修改信息</button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  changeProfile,
  changeUserInfo,
} from "/Users/zhangchenxi/Desktop/git微博项目/Wblog/frontend/wbapp/src/assets/request/index.js";

export default {
  data() {
    return {
      userInfo: null,
      isShow: true,
      input: "",
      value1: "",
      fileUrl: "",
      picked: "",
    };
  },
  methods: {
    async changeInfo() {
      this.isShow = !this.isShow;
      let res = await changeUserInfo(
        this.$refs.name.value,
        this.$refs.address.value,
        this.$refs.intro.value,
        this.$refs.phone.value,
        this.$refs.job.value,
        this.$refs.trait.value,
        this.$refs.interest.value,
        this.$refs.gender.value
      );
      console.log(res);
    },
    showPanel() {
      this.isShow = true;
    },

    getUrl(e) {
      let file = e.target.files[0];
      let reader = new FileReader();
      reader.readAsDataURL(file);
      let that = this;
      reader.onload = async function () {
        let result = await changeProfile(reader.result);
        if (result == "图片上传成功") {
          that.userInfo.profileUrl = reader.result;
        } else {
          console.log("图片上传失败");
        }
      };
    },
  },
  computed: {
    getGender() {
      return this.userInfo.gender == 1 ? "男" : "女";
    },
    getBirth() {
      let time = new Date(this.userInfo.birthday);
      let year = time.getFullYear();
      let month = time.getMonth();
      let day = time.getDate();
      return `${year}年${month + 1}月${day}日`;
    },
    //修改性别时 获取性别 若未选中则返回原数据
    getChangeGender() {
      return this.picked == "" ? this.userInfo.gender : this.picked;
    },
  },
  created() {
    this.userInfo = this.$store.state.userInfo;
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