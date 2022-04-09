<template>
  <div class="skeleton">
    <el-skeleton animated style="position: relative">
      <template #template>
        <el-skeleton
          style="
            width: auto;
            height: auto;
            --el-skeleton-circle-size: 100px;
            display: flex;
            justify-content: flex-start;
          "
        >
          <template #template>
            <!-- 圆 -->
            <el-skeleton-item variant="circle " />

            <el-skeleton
              style="
                width: 150px;
                margin-left: 10px;
                height: 100px;
                display: flex;
                justify-content: space-evenly;
                flex-direction: column;
                align-items: flex-start;
              "
            >
              <template #template>
                <el-skeleton-item style="width: 100px"></el-skeleton-item>
                <el-skeleton-item style="width: 100%"></el-skeleton-item>
              </template>
            </el-skeleton>
          </template>
        </el-skeleton>
        <el-skeleton>
          <template #template>
            <template v-for="n of 4" :key="n">
              <el-skeleton-item style="margin: 15px 0"></el-skeleton-item>
            </template>
          </template>
        </el-skeleton>
      </template>
    </el-skeleton>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.skeleton {
  width: 100%;
  height: auto;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
  margin: 10px 0;
}
</style>