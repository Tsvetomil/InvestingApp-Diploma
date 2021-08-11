<template>
  <div class="main">
    <NavBar/>
    <div class="container">
      <Logo/>
      <div class="text-overlay">
        <h2>Намери си инвеститор!</h2>
        <p>Попълнете формата за да започнете</p>
      </div>

      <div class="form-overlay">
        <div class="sign-in-form">
          <form id="add-project-form" class="sign-in">
            <div class="inputs-row">
              <div class="text-field">
                <input type="text" id="fname" required/>
                <label for="fname" class="floating-text">Собствено име*</label>
              </div>
              <div class="text-field">
                <input type="text" id="lname" required/>
                <label for="lname" class="floating-text">Фамилно име*</label>
              </div>
            </div>
            <div class="inputs-row">
              <div class="text-field">
                <input type="text" id="companyName" required/>
                <label for="companyName" class="floating-text">Име на компанията*</label>
              </div>
              <div class="text-field">
                <input type="text" id="email" required/>
                <label for="email" class="floating-text">Имейл*</label>
              </div>
            </div>
            <div class="inputs-row">
              <div class="text-field">
                <input type="text" id="phone" required/>
                <label for="phone" class="floating-text">Телефонен номер*</label>
              </div>
<!--              <input type="number" id="phone" placeholder="Телефонен номер*" required/>-->
              <div class="text-field">
                <input type="text" id="website" required/>
                <label for="website" class="floating-text">Уебсайт</label>
              </div>
            </div>
            <div class="text-field">
              <input type="text" id="city" required/>
              <label for="city" class="floating-text-2">Град</label>
            </div>
            <div class="text-field">
              <input type="text" id="address" required/>
              <label for="address" class="floating-text-2">Aдрес*</label>
            </div>
            <div class="text-field">
              <input type="text" id="caption" required/>
              <label for="caption" class="floating-text-2">Заглавие на обявата*</label>
            </div>
            <div class="text-field">
              <input type="text" id="toRaise" required/>
              <label for="toRaise" class="floating-text-2">Колко средства търсите да наберете?*(в лева)</label>
            </div>
            <div class="text-field">
              <input type="text" id="evaluationPrice" required/>
              <label for="evaluationPrice" class="floating-text-2">На колко се оценява компанията ви към този момент?*(в лева)</label>
            </div>
            <div class="text-field">
              <input type="text" id="equity" required/>
              <label for="equity" class="floating-text-2">Какъв % дял сте готови да дадете на инвеститора?*</label>
            </div>
            <label for="desc">Описание</label>
            <textarea id="desc" rows="10" cols="50">
            </textarea>
            <label for="reasonsToInvest">Защо някой би инвестирал във вашата компания?</label>
            <textarea id="reasonsToInvest" rows="4" cols="50">
            </textarea>
            <label for="file-input"> Снимка за миниатюра на обявата</label>
            <input type="file" accept="image/*" @change="onFileSelected" id="file-input" ref="uploadImage" required>
            <div id="loading"></div>
            <p class="error-msg" v-if="errors.length">
              {{this.errors[0].response.data.msg}}
            </p>
            <p class="error-msg" v-if="customError">
              {{this.customError.toString()}}
            </p>
          </form>
          <button type="submit" class="submit-button" v-on:click="submit()">Изпращане</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue';
import axios from 'axios'
import VueAxios from 'vue-axios'
import Logo from "@/components/Logo";
import NavBar from "@/components/NavBar";
Vue.use(VueAxios, axios)

export default {
  components: {NavBar, Logo},
  data() {
    return {
      selectedImage: null,
      msg: "",
      errors: [],
      customError: null
    }
  },
  methods: {
    onFileSelected() {
      this.selectedImage = this.$refs.uploadImage.files[0];
    },
    submit: async function() {
      if(!this.validateForm()){
        this.customError = "Моля попълнете всички задължителни полета"
        return;
      }
      const loader = document.querySelector("#loading");
      loader.classList.add("display");
      this.errors = [];
      let fname = document.getElementById("fname").value;
      let lname = document.getElementById("lname").value;
      let companyName = document.getElementById("companyName").value;
      let email = document.getElementById("email").value;
      let phone = document.getElementById("phone").value;
      let website = document.getElementById("website").value;
      let address = document.getElementById("address").value;
      let description = document.getElementById("desc").value;
      let toRaise = document.getElementById("toRaise").value;
      let evaluationPrice = document.getElementById("evaluationPrice").value;
      let equity = document.getElementById("equity").value;
      let imgName = document.getElementById("file-input").files[0].name;
      let caption = document.getElementById("caption").value;
      let reasonsToInvest = document.getElementById("reasonsToInvest").value;
      let city = document.getElementById("city").value;

      let resp = await this.axios.post("/api/project/add", {
        email: email,
        fname: fname,
        lname: lname,
        companyName: companyName,
        phone: phone,
        website: website,
        address: address,
        description: description,
        toRaise: toRaise,
        evaluationPrice: evaluationPrice,
        equity: equity,
        caption: caption,
        imgName: imgName,
        reasonsToInvest: reasonsToInvest,
        city: city
      }).catch(e =>
          this.errors.push(e)
      )


      if(resp.status === 200){
        let config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };
        let fd = new FormData();
        fd.append("imageFile", this.selectedImage);
        resp = await this.axios.post("/api/project/uploadImage", fd, config).catch(e =>
            this.errors.push(e)
        )
        if(resp.status === 200) {
          this.$router.push('/');
        }
      }
      loader.classList.remove("display")
    },
    validateForm: function(){
      let elements = document.getElementById("add-project-form").elements;

      for (let i = 0; i < elements.length; i++) {
        let element = elements[i];
        if(element.required){
          if(element.value === ""){
            return false;
          }
          // if(element.tagName === 'SELECT'){
          //   if(element.selectedOptions[0].disabled){
          //     // element.style.borderColor = "red"
          //     return false;
          //   }
          // }
        }
      }
      return true;
    }
  }
}
</script>
<style>
.inputs-row {
  /*float:left;*/
  display: flex;
  flex-direction: row;
  margin-bottom: -35px;
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
/*button {*/
/*  border-radius: 20px;*/
/*  border: 1px solid #009345;*/
/*  background-color: #009345;*/
/*  color: #fff;*/
/*  font-size: 1rem;*/
/*  font-weight: bold;*/
/*  padding: 10px 40px;*/
/*  letter-spacing: 1px;*/
/*  text-transform: uppercase;*/
/*  cursor: pointer;*/
/*}*/
/*button:active {*/
/*  transform: scale(.9);*/
/*}*/

/*button:focus {*/
/*  outline: none;*/
/*}*/
/*.sign-up-overlay button {*/
/*  border: 1px solid #000000;*/
/*  background-color: #2a342f;*/
/*}*/
.form-overlay {
  display: inline-flex;
  position: relative;
  align-items: center;
  margin: auto;
}
input{
  margin-right: 10px;
  margin-left: 20px;
}
select{
  background-color: #c6e7ae;
  width: 95%;
  height: 30px;
  position: inherit;
  /*margin-left: 20px;*/
}
label{
  display: flex;
  flex-direction: row;
  /*margin: auto;*/
  margin-top: 35px;
  margin-bottom: 6px;
}
#desc{
  width: 120%;
  display: block;
  resize: vertical;
  font-size: 16px;
}
textarea:focus{
  outline: 3px solid #9dd674;
  box-shadow: 5px 5px 5px 5px #c6e7ae;
}
textarea{
  padding: 5px;
  font-family: Tahoma, sans-serif;
  background-position: bottom right;
  background-repeat: no-repeat;
  border: 3px solid #acd9b6;
}
#reasonsToInvest{
  width: 95%;
  resize: vertical;
  font-size: 16px;
}
#loading {
  width: 2rem;
  height: 2rem;
  border: 5px solid #f3f3f3;
  border-top: 6px solid #9c41f2;
  border-radius: 100%;
  visibility: hidden;
  animation: spin 1s infinite linear;
  margin: auto;
}
#loading.display {
  visibility: visible;
}
.error-msg {
  color: red;
  font-weight: bold;
  font-style: italic;
}
#caption{
  width: 85%;
}
#toRaise{
  width: 85%;
}
#evaluationPrice{
  width: 85%;
}
#equity{
  width: 85%;
}
#address{
  width: 85%;
}
</style>
<style scoped>
.floating-text {
  color: #999;
  position: absolute;
  pointer-events: none;
  left: 28px;
  top: -4px;
  transition: 0.2s;
}
input:focus ~ .floating-text, input:valid ~ .floating-text {
  top: -30px;
  left: 15px;
  font-size: small;
  color: #32cd32;
  background-color: #ffffff;
  padding:0 5px 0 5px;
  pointer-events: none;
}

.floating-text-2{
  color: #999;
  position: absolute;
  pointer-events: none;
  left: 28px;
  top: -22px;
  transition: 0.2s;
}
input:focus ~ .floating-text-2, input:valid ~ .floating-text-2 {
  top: -51px;
  left: 15px;
  font-size: small;
  color: #32cd32;
  background-color: #ffffff;
  padding:0 5px 0 5px;
  pointer-events: none;
}
</style>