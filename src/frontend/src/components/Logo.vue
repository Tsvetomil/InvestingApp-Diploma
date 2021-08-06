<template>
  <div>
    <img src="../pic/logo.png" alt="Home Page" v-on:click="redirect">

  </div>
</template>

<script>
  export default {

    methods: {
      redirect: function (){
        this.$router.push('/')
      }
    }
  }
</script>

<style scoped>
img {
  position: absolute;
  top: 4px;
  left: 12px;
  width: 80px;
  padding: 3px;
  height: 60px;
  background-repeat: no-repeat;
  background-size: 30%;
  background-color: #aeb0b0;
  border-radius: 25px;
}
img:hover {
  opacity: 0.3;
  cursor: pointer;
  transform: scale(1.1);
  transition: .3s ease-in-out;
}

</style>