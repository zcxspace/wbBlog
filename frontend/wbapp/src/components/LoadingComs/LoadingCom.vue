<template>
  <div
    class="loading"
    v-loading="true"
    :element-loading-text="text"
    element-loading-background="transparent"
  ></div>
</template>

<script>
export default {
  props: {
    text: String,
  },
};
</script>

<style scoped>
.loading {
  padding: 5px 0;
  width: 100%;
  height: 40px;
}
</style>