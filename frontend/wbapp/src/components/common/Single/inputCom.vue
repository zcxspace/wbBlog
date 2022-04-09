<template>
  <div class="inputCom">
    <div class="icon">字体</div>
    <div class="tipIcon" v-show="reg">
      <div v-show="isShowTip">
        <i
          class="iconfont icon-tongguo1"
          style="color: green; font-size: 32px"
          v-if="pass"
        ></i>
        <i class="iconfont icon-shibai" style="color: red" v-else></i>
      </div>
    </div>
    <input
      type="text"
      :value="modelValue"
      class="inputBtn"
      :class="[isValid ? 'invalid' : '']"
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
  },
  data() {
    return {
      isValid: false,
      pass: true,
      isShowTip: false,
    };
  },
  emits: ["update:modelValue"],
  methods: {
    updateInput(e) {
      this.$emit("update:modelValue", e.target.value);
    },
    check() {
      //注意if判断语句块中 要给出判断语句或者转换的语句 不可直接给出true或false；
      if (this.reg.test(this.modelValue) === true) console.log("去你妈的");
      if (this.reg) {
        console.log(this.reg.test(this.modelValue));
        if (this.reg.test(this.modelValue) == true) {
          //   this.pass = true;
          //   this.isValid = false;
          console.log("合格");
        } else {
          //   this.pass = false;
          //   this.isValid = true;
          console.log("不格");
        }
        this.isShowTip = false;
      }
    },
    remove() {
      this.isValid = false;

      this.isShowTip = false;
      console.log(this.isValid);
    },
  },

  created() {
    // console.log(this.reg);
    // console.log("ni");
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
.invalid {
  border: 3px solid red;
  animation: shake 0.6s ease 0s 1;
}
.tipIcon {
  display: flex;
  align-items: center;
}
.inputCom {
  width: 100%;
  height: auto;
  position: relative;
  background: violet;
}
.icon {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  left: 10px;
}
.tipIcon {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  right: -40px;
}
.inputBtn {
  width: 100%;
  height: 100%;
  border-radius: 15px;
  outline: none;
  border: 0;
  padding: 10px 0;
  font-size: 25px;
  text-indent: 40px;
  background: salmon;
  border: 3px solid transparent;
}
.inputBtn:focus {
  border: 3px solid royalblue;
}
</style>