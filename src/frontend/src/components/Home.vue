<template>
  <div class="main">
    <div class="navbar">
      <div class="login">
        <a href="/login"> Sign In </a>
        <router-view></router-view>
      </div>
      <div class="menu">
        <div class="menu-item menu-item-left">
          <div class="menu-text">
            Get Funding
          </div>
        </div>
        <div class="menu-item menu-item-right">
          <div class="menu-text">
            Invest
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  props: {
    msg: String
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.navbar {
  border-bottom: solid;
  border-width: 1px;
}
.login {
  float:right;
  font-size: 17px;
  margin-right: 50px;
  cursor: pointer;
  color: #436186;
}
.login:hover {
  color: #2775c8;
}
.menu {
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin-top: 10px;
  height: 60px;
}
.menu-item {
  flex: 1;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}
.menu-text{
  font-size: 20px;
  color: #818181;
  cursor: pointer;
  border-bottom: 2px solid #e5e0e0;
  width: fit-content;
}
.menu-text:hover {
  border-bottom: 2px solid #2e2437;
}
.menu-item-left {
  margin-left: 250px;
}
.menu-item-right {
  margin-right: 220px
}
.login a:link, a:visited {
  background-color: white;
  color: black;
  border: 2px solid green;
  padding: 10px 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
}

.login a:hover, a:active {
  background-color: green;
  color: white;
}
</style>