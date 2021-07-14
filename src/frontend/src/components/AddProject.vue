<template>
  <div class="container">
    <Logo/>
    <div class="text-overlay">
      <h2>Find Your Investors!</h2>
      <p>Please fill out the form to get started</p>
    </div>
    <div class="form-overlay">
      <div class="sign-in-form">
        <form class="sign-in" action="#">
          <div class="inputs-row">
            <input type="text" id="fname" placeholder="First Name*" required/>
            <input type="text" id="lname" placeholder="Last Name*" required/>
          </div>
          <div class="inputs-row">
            <input type="text" id="companyName" placeholder="Company Name*" required/>
            <input type="text" id="email" placeholder="Email*" required/>
          </div>
          <input type="text" id="phone" placeholder="Phone Number*" required/>
          <label for="toRaise">How much are you looking to raise?*</label>
          <br>
          <select id="toRaise" name="toRaise">
            <option value="$0 - $100K"> $0 - $100K </option>
            <option value="$100K - $500K"> $100K - $500K </option>
            <option value="$500K - $1M"> $500K - $1M </option>
            <option value="$1M+"> $1M+  </option>
          </select>


<!--          <button v-on:click="register">Register</button>-->
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue';
import axios from 'axios'
import VueAxios from 'vue-axios'
import Logo from "@/components/Logo";
Vue.use(VueAxios, axios)

export default {
  components: {Logo},
  data() {
    return {
      msg: ""
    }
  },
  methods: {
  }
}
</script>
<style>
.inputs-row {
  /*float:left;*/
  display: flex;
  flex-direction: row;
}
.text-overlay {
  position: relative;
  background: #c6e7ae;
  margin-left: auto;
  margin-right: auto;
  width: 40%;
  padding: 10px;
  border-radius: 50px;
}
.container {
  padding: 10px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 8%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  background: #ffffff;
}
.sign-in-form {
  top: 0;
  padding: 50px 60px;
}
input {
  display: block;
  background-color: #c6e7ae;
  padding: 12px 26px;
  margin: 20px 0;
  border: none;
  border-radius: 15px;
  overflow: hidden;
  border-bottom: 1px solid #ddd;
  box-shadow: inset 0 1px 2px rgba(0, 0, 0, .4),
  0 -1px 1px #fff,
  0 1px 0 #fff;
  outline: none;
}
button {
  border-radius: 20px;
  border: 1px solid #009345;
  background-color: #009345;
  color: #fff;
  font-size: 1rem;
  font-weight: bold;
  padding: 10px 40px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
}
button:active {
  transform: scale(.9);
}

button:focus {
  outline: none;
}
.sign-up-overlay button {
  border: 1px solid #000000;
  background-color: #2a342f;
}
.form-overlay {
  display: inline-flex;
  position: relative;
  align-items: center;
  margin: auto;
}
input{
  margin-right: 10px;
}
select{
  background-color: #c6e7ae;
  width: 100%;
  height: 30px;
}
option{
  width: 100%;
}
</style>