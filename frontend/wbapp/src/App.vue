<template>
  <div>
    <router-view :key="$route.fullPath"></router-view>
  </div>
</template>



<style>
/* 隐藏页面滚动条 */
body {
  overflow: scroll;
  overflow-y: hidden;
  overflow-x: hidden;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  overflow: -Scroll;
  overflow-x: hidden;
  background: rgb(230, 230, 230);

  /* backdrop-filter: blur(50px); */
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
