<template>
  <div id="container">
    <h2>Коментари</h2>
    <div v-if="authorized" id="comments-container">
      <div id="add-comment">
        <textarea id="comment-id" rows="3" placeholder="Добави коментар..."/>
      </div>
      <button type="submit" class="submit-button" v-on:click="addComment()">Добави Коментар</button>
      <div id="comments">
        <div id="comment" v-for="comment in comments" v-bind:key="comment.id">
          <p class="owner">{{comment.owner}}</p>
          <span class="timestamp">{{comment.timestamp}}</span>
          <p class="msg"> {{ comment.message }}</p>
          <p v-if="isAdmin" class="del" v-on:click="delComment(comment)">Изтрий</p>
          <div id="reply-div">
            <a href="javascript:void(0)" @click="setID(comment)">
              <p class="home-text" id="reply"> отговор </p>
            </a>
          </div>
          <div id="reply-field-div" v-if="fire(comment)">
            <textarea rows="2" id="reply-field" placeholder="Отговор"></textarea>
            <button type="submit" class="submit-button" v-on:click="addSubComment(comment)">Добавяне</button>
          </div>
          <div id="replies">
            <div id="reply-comment" v-for="reply in comment.replies" v-bind:key="reply.id">
              <p class="reply-owner">{{reply.owner}}</p>
              <span id="reply-date">{{reply.timestamp}}</span>
              <p class="reply-msg">{{reply.message}}</p>
              <p v-if="isAdmin" class="del" v-on:click="delComment(reply)">Изтрий</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <p>Влезнете в акаунта си за да видите коментарите.</p>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Vue from "vue";
import VueAxios from "vue-axios";
Vue.use(VueAxios, axios)

export default {
  name: "CommentSection",
  data() {
    return {
      authorized: false,
      isAdmin: false,
      comments: null,
      projectID: null,
      replyFired: null
    }
  },
  mounted() {
    axios.get("/api/users/isAuthorized").then(resp => {
      if(resp.data.status === 200){
        this.authorized = true;
      }
    })
    axios.get("/api/users/isAdmin").then(resp => {
      if(resp.data.status === 200){
        this.isAdmin = resp.data.entity;
      }
    })


    let url_string = window.location.href;
    let url = new URL(url_string);
    this.projectID = url.searchParams.get("id")
    this.loadComments();
  },
  methods: {
    async loadComments() {
      await Vue.axios.get('/api/comment/all/' + this.projectID)
          .then( (resp) => {
            // this.item = resp.data;
            this.comments = resp.data.entity;
          })
    },
    async addComment() {
      if(!this.authorized){
        return;
      }
      let msg = document.getElementById("comment-id").value;
      if(msg) {
        await this.axios.post("/api/comment/add", {
          message: msg,
          projectID: this.projectID
        }).catch(e =>
            this.errors.push(e)
        )
        document.getElementById("comment-id").value = "";
        await this.loadComments()
        this.$forceUpdate()
      }
    },
    async addSubComment(comment){
      if(!this.authorized){
        return;
      }
      let msg = document.getElementById("reply-field").value;
      if(msg) {
        await this.axios.post("/api/comment/add/reply/" + comment.id, {
          message: msg
        }).catch(e =>
            this.errors.push(e)
        )
        document.getElementById("reply-field").value = "";
        this.replyFired = -1;

        await this.loadComments()
        this.$forceUpdate()
      }
    },
    setID(comment) {
      this.replyFired = comment.id
    },
    fire(comment) {
      return this.replyFired === comment.id;
    },
    async delComment(comment) {
      await axios.delete('api/comment/remove/' + comment.id);
      window.location.reload()
    }
  }
}
</script>
<style scoped>
#container{
  background-color: #e7e7e7;
  margin-top: 100px;
  height: 100%;
  padding: 10px;
  margin-left: 21%;
  margin-right: 21%;
}
#comments-container{
  background-color: white;
  /*margin-top: 100px;*/
  padding: 10px;
  margin-left: 10%;
  margin-right: 10%;
  display: block;
}
h2{
  position: relative;
  right: 35%
}
textarea{
  background-color: #bac7d6;
  border-radius: 10px;
  resize: none;
  position: inherit;
  margin-right: 50%;
  width: 70%;
}
textarea:focus, input:focus{
  outline: 0;
}
button{
  width: 30%;
  border-radius: 20px;
  border: 1px solid #009345;
  background-color: #009345;
  color: #fff;
  font-size: 10px;
  font-weight: bold;
  padding: 8px 10px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
}
.owner{
  position: relative;
  /*display: block;*/
  right: 40%;
  font-weight: bold;
}
#comments{
  display: block;
  margin-top: 10%;
}
.msg{
  margin-top: -1%;
  text-align: left;
  margin-left: 7%;
  margin-right: 20%
}
.timestamp{
  position: absolute;
  display: block;
  top: 5%;
  left: 80%;
  font-style: italic;
  color: #5e733f;
}
.reply-msg{
  margin-top: -1%;
  text-align: left;
  margin-left: 18%;
  margin-right: 20%
}
.reply-owner{
  position: relative;
  /*display: block;*/
  right: 30%;
  font-weight: bold;
}
#comment{
  position: relative;
  margin-top: 6%;
  border-radius: 8px;
  background-color: #e7e7e7;
  margin-left: 7%;
  margin-right: 7%;
  /*border: black;*/
}
div{
  word-wrap: break-word
}
#reply-date{
  display: table;
  margin-top: -15px;
  margin-left: 80%;
  top: 100px;
  font-style: italic;
  word-wrap:break-word;
  color: #5e733f;
}
#reply-div{
  position: relative;
  margin-right: 80%;
}
#reply-field{
  position: relative;
  margin-right: 20%;
  overflow: hidden;
}
p{
  white-space: pre-wrap;
}
.del{
  color: red;
  cursor: pointer;
}
</style>