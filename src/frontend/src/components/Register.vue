<template>
  <div class="container">
    <Logo/>
    <div class="text-overlay">
      <h2>Добре дошли!</h2>
      <p>Моля попълнете формата за да се регистрирате</p>
    </div>
    <div class="form-overlay">
      <div class="sign-in-form">
        <form class="sign-in" action="javascript:void(0)">
          <div class="inputs-row">
            <div class="text-field">
              <input type="text" id="email" class="text-input" minlength="1" maxlength="30" required/>
              <label class="floating-text">Имейл</label>
            </div>
            <div class="text-field">
              <input type="text" id="fname" class="text-input" minlength="1" maxlength="20" required/>
              <label class="floating-text">Собствено име</label>
            </div>
          </div>
          <div class="inputs-row">
            <div class="text-field">
              <input type="text" id="lname" class="text-input" minlength="1" maxlength="20" required/>
              <label class="floating-text">Фамилно име</label>
            </div>
            <div class="text-field">
              <input type="password" id="pass" class="text-input" minlength="8" maxlength="20" required/>
              <label class="floating-text">Парола</label>
            </div>
          </div>
          <label for="date">Дата на раждане</label>
          <input type="date" id="date" required/>
          <div id="error-div">
            <p v-if="errors.length">
              {{this.errors[0].response.data.msg}}
            </p>
          </div>
          <button v-on:click="register">Регистриране</button>
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
      msg: "",
      errors: []
    }
  },
  methods: {
    register: async function () {
      this.errors = [];
      let email = document.getElementById("email").value;
      let fname = document.getElementById("fname").value;
      let lname = document.getElementById("lname").value;
      let pass = document.getElementById("pass").value;
      let date = document.getElementById("date").value;

      if(email && fname && lname && pass && date){
        let resp = await this.axios.post("/api/users/register", {
          firstName: fname,
          lastName: lname,
          email: email,
          password: pass,
          dateOfBirth: date
        }).catch(e => {
          this.errors.push(e)
          console.log(e)
        })
        console.log(resp)
        if(resp.data.status === 200) {
          this.$router.push('/login')
        }
      }

    }
  }
}
</script>
<style>
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
#error-div {
  background-color: #f38686;
  border-radius: 6px;
  margin-left: 60px;
  margin-right: 60px;
}
</style>
<style scoped>
label{
  margin-top: 50px;
  margin-left: 25px;
}
#dateOfBirth{
  margin-bottom: 20px;
}
</style>