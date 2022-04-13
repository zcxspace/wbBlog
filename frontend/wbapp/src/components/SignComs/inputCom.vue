<template>
  <div class="inputCom" :class="{ invalid: isValid, valid: isTrue }">
    <div class="inputIcon"><i class="iconfont" :class="icon"></i></div>
    <div class="tipsIcon" v-if="reg">
      <div v-show="isShowTip">
        <i
          class="iconfont icon-tongguo1"
          style="color: green; font-size: 32px"
          v-if="!isValid"
        ></i>
        <i
          class="iconfont icon-shibai"
          style="color: red; margin-top: 2px"
          v-else
        ></i>
      </div>
    </div>
    <input
      :type="type"
      :value="modelValue"
      :placeholder="placeholder"
      class="inputBtn"
      @input="updateInput($event)"
      @blur="check"
      @focus="remove"
    />
  </div>
</template>

<script>
export default {
  props: {
    modelValue: String,
    reg: RegExp,
    icon: String,
    type: String,
    placeholder: String,
  },
  data() {
    return {
      isValid: false,
      isTrue: false,
      isShowTip: false,
    };
  },
  emits: ["update:modelValue"],
  methods: {
    updateInput(e) {
      this.$emit("update:modelValue", e.target.value);
    },
    check() {
      //如果有值则进行判断
      if (this.modelValue) {
        //
        if (this.reg) {
          console.log(typeof this.reg.test(this.modelValue));
          if (this.reg.test(this.modelValue)) {
            console.log("合格");
            this.isValid = false;
            this.isTrue = true;
          } else {
            console.log("不合格");
            this.isValid = true;
            this.isTrue = false;
          }
          this.isShowTip = true;
        } else console.log("没有验证");
      } else return;
    },
    remove() {
      this.isValid = false;
      this.isShowTip = false;
      this.isTrue = false;
    },
  },

  created() {
    console.log(this.reg);
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
@keyframes shake {
  0% {
    transform: translateX(-7px);
  }
  25% {
    transform: translateX(14px);
  }
  50% {
    transform: translateX(-7px);
  }
  100% {
    transform: translateX(0px);
  }
}

.tipsIcon {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: -40px;
  width: 40px;
  height: 40px;
}
.tipsIcon div {
  display: flex;
  align-items: center;
  justify-content: center;
}
.inputIcon {
  width: auto;
  word-break: break-all;
  white-space: nowrap;
  display: flex;
  align-items: center;
  justify-content: center;
}
.inputIcon i {
  font-size: 40px;
}
.inputBtn {
  width: 100%;
  height: 100%;
  padding: 15px;
  border-radius: 15px;
  outline: none;
  border: 0;
  font-size: 25px;
  border: 3px solid transparent;
  background: transparent;
}
.inputCom {
  width: 100%;
  height: auto;
  display: flex;
  position: relative;
  border-radius: 10px;
  padding: 5px;
  border: 3px solid transparent;
  background: rgb(236, 236, 236);
}

.valid {
  border: 3px solid rgb(62, 189, 62);
}
.invalid {
  border: 3px solid red;
  animation: shake 0.6s ease 0s 1;
}
</style>