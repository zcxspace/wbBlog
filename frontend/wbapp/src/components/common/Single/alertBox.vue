<template>
  <div class="changeInfo">
    <!-- 上层信息 -->
    <div class="topBar">
      <span v-if="!isShowInput" class="textBox"
        >{{ trueValue }}

        <button @click="showInput" v-if="!isShowInput" class="addBtn">
          <p v-if="trueValue" :class="[trueValue ? 'showAlert' : '']">
            <i class="iconfont icon-bianji1"></i>修改
          </p>
          <p v-else><i class="iconfont icon-jiajianzujianjiahao"></i>添加</p>
        </button></span
      >
      <!-- input开始 -->
      <div v-if="type == 'text'" class="input">
        <input
          type="text"
          v-model.lazy="tempValue"
          v-if="isShowInput"
          v-focus
        />
      </div>
      <!-- input结束 -->
      <!-- 文本域开始 -->
      <div v-if="type == 'textArea'" class="textArea">
        <!-- <textarea
          v-model.lazy="tempValue"
          v-if="isShowInput"
          v-focus
        ></textarea> -->

        <el-input
          v-model.lazy="tempValue"
          v-if="isShowInput"
          :autosize="{ minRows: 3, maxRows: 5 }"
          type="textarea"
          placeholder="Please input"
          v-focus
        />
      </div>
      <!-- 文本域结束 -->
      <!-- 性别框开始 -->
      <div v-if="type == 'genderBox'" class="genderBox">
        <div v-if="isShowInput">
          男<input
            type="radio"
            name="gender"
            value="0"
            id="man"
            :checked="tempValue == '男'"
            v-model="tempValue"
          />
          <label for="man"><i class="iconfont icon-icon2"></i></label>

          女<input
            type="radio"
            name="gender"
            id="woman"
            value="1"
            :checked="tempValue == '女'"
            v-model="tempValue"
          />
          <label for="woman"><i class="iconfont icon-icon1"></i></label>
        </div>
      </div>
      <!-- 性别框结束 -->
      <!-- 生日框开始 -->
      <div v-if="type == 'birthBox'">
        <el-date-picker
          v-model="tempValue"
          v-if="isShowInput"
          type="date"
          placeholder="从新选择吧!"
        />
      </div>
      <!-- 生日框结束 -->
      <div class="showBtn">
        <!-- 添加修改按钮 -->
      </div>
    </div>
    <div class="confirm">
      <!-- 取消 -->
      <button v-if="isShowInput" @click="cancel" class="cancelBtn">取消</button>
      <!-- 确认 -->
      <button v-if="isShowInput" @click="change" class="yesBtn">确认</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    trueValue: String,
    type: String,
  },
  emits: ["changeInfo"],
  directives: {
    focus(el) {
      el.focus();
    },
  },
  data() {
    return {
      isShowInput: false,
      tempValue: this.trueValue,
    };
  },
  methods: {
    change() {
      this.$emit("changeInfo", this.tempValue);
      this.isShowInput = false;
    },
    cancel() {
      this.isShowInput = false;
      this.tempValue = this.trueValue;
    },
    showInput() {
      this.isShowInput = true;
    },
  },

  created() {
    console.log(this.tempValue);
    console.log(this.trueValue);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.input,
.textArea,
.genderBox {
  width: 100%;
}
.addBtn {
  background: transparent;
  border: 0;
  outline: none;
  font-size: 25px;
  color: rgb(66, 132, 218);
}
.genderBox input {
  margin: 5px 10px;
  font-size: 25px;
  opacity: 0;
}
.genderBox input:checked + label {
  color: royalblue;
}
.genderBox label i {
  font-size: 50px;
  font-weight: 900;
}
.textArea textarea {
  width: 100%;
}
.input input {
  width: 100%;
  font-size: 25px;
  padding: 4px;
  text-indent: 5px;
  border-radius: 10px;
  outline: none;
  border: 0;
  border: 3px solid transparent;
}
.input input:focus {
  border: 3px solid royalblue;
}
.showAlert {
  opacity: 0;
}
.topBar:hover .showAlert {
  opacity: 1;
}
.changeInfo {
  width: 100%;
  height: auto;
  padding: 30px 20px;
  margin: 20px 0;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: flex-start;
  animation: move 1s linear 0s 1;
}
.showBtn {
  margin: 0 5px;
}
.topBar {
  height: auto;
  width: 100%;
}
.confirm {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: center;
}
.confirm button {
  font-size: 25px;
  outline: none;
  border: 0;
  padding: 10px;
  margin: 20px;
  border-radius: 5px;
  background: white;
  border: 2px solid royalblue;
  transition: all ease 0.3s;
}
.confirm button:hover {
  background: royalblue;
  color: white;
  transform: translateY(-3px);
}
.confirm button:active {
  transform: translateY(3px);
}
.confirm .yesBtn {
  background: royalblue;
  color: white;
}
.textBox {
  display: inline-block;
  width: 100%;
  font-size: 30px;
  white-space: normal;
  word-wrap: break-word;

  text-align: start;
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
</style>