<template>
  <div class="changeInfo">
    <div class="rowInfo">
      <div class="photoFile">
        <div class="mask">
          点击更换头像
          <input type="file" @change="getUrl" accept="image/*" />
        </div>

        <img :src="fileUrl" alt="头像" />
      </div>
      <div>
        <h2>邮箱地址</h2>
      </div>
    </div>

    <div class="setInfo">
      <div class="infos" v-if="isShow">
        <div class="info" v-for="(info, index) of infos" :key="index">
          <div>{{ info.title }}</div>
        </div>
      </div>

      <div class="inputs" v-else>
        <div class="inputBox">
          <p>姓名</p>
          <input type="text" v-model="input" />
        </div>

        <div class="inputBox">
          <p>地址</p>
          <input type="text" v-model="input" />
        </div>

        <div class="inputBox">
          <p>电话</p>
          <input type="text" v-model="input" />
        </div>

        <div class="inputBox">
          <p>生日</p>
          <el-date-picker
            v-model="value1"
            type="date"
            placeholder="Pick a day"
          />
        </div>

        <div class="inputBox">
          <p>职业</p>
          <input type="text" v-model="input" />
        </div>

        <div class="inputBox">
          <p>兴趣</p>
          <input type="text" v-model="input" />
        </div>
        <div class="inputBox">
          <p>性别</p>
          <label for="man">
            <i class="iconfont icon-nan"></i>
          </label>
          <input
            type="radio"
            name="gender"
            value="man"
            class="gender"
            v-model="this.userInfo.picked"
            id="man"
          />
          <label for="woman">
            <i class="iconfont icon-nv"></i>
          </label>
          <input
            type="radio"
            name="gender"
            value="woman"
            class="gender"
            v-model="this.userInfo.picked"
            id="woman"
          />

          {{ this.userInfo.picked }}
        </div>
        <div class="inputBox">
          <p>特质</p>
          <input type="text" v-model="input" />
        </div>
      </div>

      <div class="btn">
        <button @click="isShow = true" v-show="!isShow">取消</button>
        <button @click="changeInfo">修改信息</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      infos: [
        { title: "姓名:" },
        { title: "地址:" },
        { title: "电话:" },
        { title: "生日:" },
        { title: "职业:" },
        { title: "兴趣:" },
        { title: "特质:" },
        { title: "性别:" },
      ],
      userInfo: {
        picked: "",
      },
      isShow: true,
      input: "",
      value1: "",
      fileUrl: "",
    };
  },
  methods: {
    changeInfo() {
      this.isShow = !this.isShow;
    },
    getUrl(e) {
      let file = e.target.files[0];
      let reader = new FileReader();
      reader.readAsDataURL(file);
      let that = this;
      reader.onload = function () {
        that.fileUrl = reader.result;
      };
    },
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
  opacity: 0;
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