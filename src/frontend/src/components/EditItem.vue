<template>
  <div>
    <button type="submit" ref="item" class="submit-button" v-on:click="save">Запази</button>
    <Logo/>
    <div contenteditable class="editable-field" id="caption-container"><h1 id=caption-id>{{item.caption}}</h1></div>
    <div class="image-container">
      <img onclick="image()" class="img-file" :src="`${publicPath}images/${item.imgName}`">
      <label for="image" class="label-for-image">Сменете изображението от тук!</label>
      <input type="file" id="image" class="change-image" accept="image/*" @change="onFileSelected" ref="uploadImage"/>
    </div>
    <div class="container">
      <table>
        <tr>
          <th><div contenteditable class="editable-field" onKeypress="if(event.keyCode < 48 || event.keyCode > 57){return false;}"><p id="toRaise-id">{{item.toRaise}}</p></div> лв в замяна на <span style="color:#ca4949"><div contenteditable class="editable-field" onKeypress="if(event.keyCode < 48 || event.keyCode > 57){return false;}"><p id="equity-id">{{item.equity}}</p></div>% от компанията</span></th>
          <th><div contenteditable class="editable-field" onKeypress="if(event.keyCode < 48 || event.keyCode > 57){return false;}"><p id="phone-id">{{item.phone}}</p></div></th>
          <th><div contenteditable class="editable-field"><p id="companyName-id">{{item.companyName}}</p></div></th>
          <th><i><div contenteditable class="editable-field"><p id="website-id">{{item.website}}</p></div></i></th>
        </tr>
        <tr>
          <td><h4>Средства които се търсят</h4></td>
          <td><h4>Телефонен номер</h4></td>
          <td><h4>Име на компанията</h4></td>
          <td><h4>Уебсайт на компанията</h4></td>
          <!--          <td>country</td>-->
        </tr>
      </table>


    </div>
    <div class="container-desc">

      <div contenteditable class="editable-field" id="desc-id"><p>{{item.description}}</p></div>
      <br><br>
      <div contenteditable class="cr-reasons-to-invest" id="reasons-id">
        <p>{{item.reasonsToInvest}}</p>
      </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue';
import VueAxios from "vue-axios";
import axios from "axios";
import Logo from "@/components/Logo";
Vue.use(VueAxios, axios)
export default {
  components: {
    Logo
  },
  name:"ItemView",
  data()
  {
    return{item: undefined, publicPath: process.env.BASE_URL, selectedImage: null}
  },
  mounted()
  {
    let url_string = window.location.href;
    let url = new URL(url_string);
    let id = url.searchParams.get("id")

    Vue.axios.get('/api/project/' + id)
        .then( (resp) => {
          this.item = resp.data;
          console.warn(resp.data)
        })
  },
  methods: {
    onFileSelected() {
      this.selectedImage = this.$refs.uploadImage.files[0];
    },
    async save(){
      //save project first
      let caption = document.getElementById("caption-id").innerText;
      let toRaise = document.getElementById("toRaise-id").innerText;
      let equity = document.getElementById("equity-id").innerText;
      let phone = document.getElementById("phone-id").innerText;
      let companyName = document.getElementById("companyName-id").innerText;
      let website = document.getElementById("website-id").innerText;
      let description = document.getElementById("desc-id").innerText;
      let reasonsToInvest = document.getElementById("reasons-id").innerText;
      let imageName = document.getElementById("image");
      if(imageName.value) {
        imageName = document.getElementById("image").files[0].name;
      } else{
        imageName = null;
      }

      await this.axios.post("/api/project/edit/" + this.item.id, {
        toRaise: toRaise,
        equity: equity,
        companyName: companyName,
        phone: phone,
        website: website,
        description: description,
        caption: caption,
        imgName: imageName,
        reasonsToInvest: reasonsToInvest,
      }).catch(e =>
          this.errors.push(e)
      )

      if(imageName){
        let config = {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        };
        let fd = new FormData();
        fd.append("imageFile", this.selectedImage);
        await this.axios.post("/api/project/uploadImage", fd, config).catch(e =>
            this.errors.push(e)
        )
        this.$router.push('/');
      }
    }
  }
}
</script>
<style scoped>
.submit-button{
  position: absolute;
  top:40px;
  right:100px;
}
table{
  border-spacing: 10px 10px;
}
h4{
  color: #c3bcbc;
}
.container-desc{
  margin-top: 100px;
  background-color: white;
  height: 100%;
  border: 3px solid #15bf3e;
  padding: 10px;
  margin-left: 15%;
  margin-right: 15%;
}
.cr-reasons-to-invest{
  background-color: #d05858;
  border-style: dotted;
}
.editable-field{
  border-style: dotted;
}

.image-container {
  position: relative;
  /*width: 50%;*/
}

/*!* Make the image responsive *!*/
.image-container img {
  width: 50%;
  height: auto;
}

/*!* Style the button and place it in the middle of the container/image *!*/
.image-container .change-image {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  background-color: #555;
  color: white;
  font-size: 16px;
  padding: 12px 24px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}
.label-for-image{
  position: absolute;
  top: 35%;
  left: 50%;
  transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  background-color: #555;
  color: white;
  font-size: 16px;
  padding: 12px 24px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
}
.image-container .change-image:hover {
  background-color: black;
}
#caption-container{
  margin: auto;
  width: 60%;
  padding: 10px;
}
p{
  white-space: pre-wrap;
}
</style>
