<template>
  <div style="margin-left: 100px;margin-right: 100px">
    <h1>搜索内容 : {{this.key}}</h1>
    <div v-if="this.blog.length>0">
      <el-card style="margin: 20px;width: auto" v-for="item,index in blog" :key="index">
        <div class="card-link">
          <div class="article-details">
            <h4 class="post-category">{{ item.createTime.substring(0,10) }}</h4>
            <h3 class="post-title">{{ item.title }}</h3>
            <p class="post-description">{{item.summary}}</p>
            <p class="post-author">
              <router-link :to="'/blog/detail/'+item.uniqueId">MORE</router-link>
            </p>
          </div>
        </div>
      </el-card>
    </div>
    <div v-else>
      <div id="background"></div>
      <div class="top">
        <h2>NOT FOUND</h2>
        <h3>未找到搜索内容</h3>
      </div>
      <div class="container2">
        <div class="ghost-copy">
          <div class="one"></div>
          <div class="two"></div>
          <div class="three"></div>
          <div class="four"></div>
        </div>
        <div class="ghost">
          <div class="face">
            <div class="eye"></div>
            <div class="eye-right"></div>
            <div class="mouth"></div>
          </div>
        </div>
        <div class="shadow"></div>
      </div>
    </div>

  </div>
</template>

<script>
    export default {
        name: "Search",
        data() {
            return {
                blog: [],
                key:''
            }
        },
        watch: {
            $route(val){
                let key = val.query.key;
                this.key=key;
                this.selectByKey(key);
            }
        },
        methods: {
            selectByKey(key) {
                let _this = this;
                this.$axios.get('/article/key', {
                    params: {
                        key: key
                    }
                })
                    .then(function (response) {
                        _this.blog = response.data.data;
                    })
            }
        },
        created() {
            let key = this.$route.query.key;
            this.key=key;
            this.selectByKey(key);
        }
    }
</script>

<style lang="scss" scoped>
  h1 {
    position: relative;
    font-family: "Roboto Mono", monospace;
    font-size: 40px;
    color: #cfd8dc;
    cursor: pointer;
    transition: all 0.3s;
    margin-top: 15px;
  }

  h1:hover {
    border-bottom: 6px dotted #cfd8dc;
    padding-bottom: 10px;
    margin-top: 10px;
  }
  /*未找到begin*/
  $white: #EDEDED;
  $gray: #BFC0C0;
  $dark: #585959;
  $light: #D3DEEA;

  @import url('https://fonts.googleapis.com/css?family=Abril+Fatface|Lato');

  $big: 'Abril Fatface', serif;
  $body: 'Lato', sans-serif;


  .top {
    margin-top: 30px;
    margin-bottom: 20px;
  }

  .container2 {
    margin: 0 auto;
    position: relative;
    width: 250px;
    height: 250px;
    margin-top: -40px;
  }

  .ghost {
    width: 50%;
    height: 53%;
    left: 25%;
    top: 10%;
    position: absolute;
    border-radius: 50% 50% 0 0;
    background: $white;
    border: 1px solid $gray;
    border-bottom: none;
    animation: float 2s ease-out infinite;
  }

  .ghost-copy {
    width: 50%;
    height: 53%;
    left: 25%;
    top: 10%;
    position: absolute;
    border-radius: 50% 50% 0 0;
    background: $white;
    border: 1px solid $gray;
    border-bottom: none;
    animation: float 2s ease-out infinite;
    z-index: 0;
  }


  .face {
    position: absolute;
    width: 100%;
    height: 60%;
    top: 20%;
  }
  .eye, .eye-right {
    position: absolute;
    background: $dark;
    width: 13px;
    height: 13px;
    border-radius: 50%;
    top: 40%;
  }

  .eye {
    left: 25%;
  }
  .eye-right {
    right: 25%;
  }

  .mouth {
    position:absolute;
    top: 50%;
    left: 45%;
    width: 10px;
    height: 10px;
    border: 3px solid;
    border-radius: 50%;
    border-color: transparent $dark $dark transparent;
    transform: rotate(45deg);
  }

  .one, .two, .three, .four {
    position: absolute;
    background: $white;
    top: 85%;
    width: 25%;
    height: 23%;
    border: 1px solid $gray;
    z-index: 0;
  }

  .one {
    border-radius: 0 0 100% 30%;
    left: -1px;
  }

  .two {
    left: 23%;
    border-radius: 0 0 50% 50%;
  }

  .three {
    left: 50%;
    border-radius: 0 0 50% 50%;
  }

  .four {
    left: 74.5%;
    border-radius: 0 0 30% 100%;
  }

  .shadow {
    position: absolute;
    width: 30%;
    height: 7%;
    background: $gray;
    left: 35%;
    top: 80%;
    border-radius: 50%;
    animation: scale 2s infinite;
  }

  @keyframes scale {
    0% {
      transform: scale(1);
    }
    50% {
      transform: scale(1.1);
    }
    100% {
      transform: scale(1);
    }
  }

  @keyframes float {
    50% {
      transform: translateY(15px);
    }
  }

  .bottom {
    margin-top: 10px;
  }

  /*text styling*/
  .top h2 {
    margin: 5px;
    font-family: $big;
    color: $white;
    text-align: center;
    font-size: 6em;
    text-shadow: -1px 0 $gray, 0 1px $gray, 1px 0 $gray, 0 -1px $gray;
  }
  .top h3 {
    font-family: $body;
    font-size: 2em;
    text-transform: uppercase;
    text-align: center;
    color: $gray;
    margin-top: -10px;
    font-weight: 900;
  }
  .top p {
    text-align: center;
    font-family: $body;
    color: $dark;
    font-size: .6em;
    margin-top: -20px;
    text-transform: uppercase;
  }

  .search {
    text-align: center;
  }

  .buttons {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 10px;
  }

  /*search style*/

  .search-bar {
    border: 1px solid $gray;
    padding: 5px;
    height: 20px;
    margin-left: -30px;
    width: 200px;
    outline: none;
    &:focus {
      border: 1px solid $light;
    }
  }

  .search-btn {
    position: absolute;
    width: 30px;
    height: 32px;
    border: 1px solid $gray;
    background: $gray;
    text-align: center;
    color: $white;
    cursor: pointer;
    font-size: 1em;
    outline: none;
    &:hover {
      background: $white;
      border: 1px solid $white;
      color: $gray;
      transition: all .2s ease;
    }
  }

  .btn {
    background: $white;
    padding: 15px 20px;
    margin: 5px;
    color: $dark;
    font-family: $body;
    text-transform: uppercase;
    font-size: .6em;
    letter-spacing: 1px;
    border: 0;
    &:hover {
      background: $gray;
      transition: all .4s ease-out;
    }
  }

  footer {
    position: absolute;
    bottom: 0;
    right: 0;
    text-align: center;
    font-size: 0.8em;
    text-transform: uppercase;
    padding: 10px;
    color: #EA7996;
    letter-spacing: 3px;
    font-family: $body;
    a {
      color: #ffffff;
      text-decoration: none;
      &:hover {
        color: #7d7d7d;
      }
    }
  }
  /*未找到end*/
  $bg: #eedfcc;
  $text: #777;
  $black: #121212;
  $white: #fff;
  $red: #e04f62;
  $border: #ebebeb;
  $shadow: rgba(0, 0, 0, 0.2);

  @mixin transition($args...) {
    transition: $args;
  }

  * {
    box-sizing: border-box;
    &::before, &::after {
      box-sizing: border-box;
    }
  }



  .blog-card {
    display: flex;
    flex-direction: row;
    background: $white;
    box-shadow: 0 0.1875rem 1.5rem $shadow;
    border-radius: 0.375rem;
    overflow: hidden;
  }

  .card-link {
    position: relative;
    display: block;
    color: inherit;
    text-decoration: none;
    &:hover .post-title {
      @include transition(color 0.3s ease);
      color: $red;
    }
    &:hover .post-image {
      @include transition(opacity 0.3s ease);
      opacity: 0.9;
    }
  }

  .post-image {
    @include transition(opacity 0.3s ease);
    display: block;
    width: 100%;
    object-fit: cover;
  }

  .article-details {
    padding: 1.5rem;
  }

  .post-category {
    display: inline-block;
    text-transform: uppercase;
    font-size: 1rem;
    font-weight: 700;
    line-height: 1;
    letter-spacing: 0.0625rem;
    margin: 0 0 0.75rem 0;
    padding: 0 0 0.25rem 0;
    border-bottom: 0.125rem solid $border;
  }

  .post-title {
    @include transition(color 0.3s ease);
    font-size: 1.5rem;
    line-height: 1.4;
    color: $black;
    font-weight: 700;
    margin: 0 0 0.5rem 0;
  }
  .post-description{
    font-size: 1.5rem;
    line-height: 1;
    margin: 0.5rem 0 0 0;
    padding: 0.5rem 0 0 0;
  }
  .post-author {
    font-size: 0.875rem;
    line-height: 1;
    margin: 1.125rem 0 0 0;
    padding: 1.125rem 0 0 0;
    border-top: 0.0625rem solid $border;
  }

  @media (max-width: 40rem) {
    #container {
      width: 18rem;
      height: 27.25rem;
    }

    .blog-card {
      flex-wrap: wrap;
    }
  }

  @supports (display: grid) {
    body {
      display: grid;
      grid-template-columns: repeat(4, 1fr);
      grid-gap: 0.625rem;
      grid-template-areas: ". main main ." ". main main .";
    }

    #container {
      grid-area: main;
      align-self: center;
      justify-self: center;
    }

    .post-image {
      height: 100%;
    }

    .blog-card {
      display: grid;
      grid-template-columns: 1fr 2fr;
      grid-template-rows: 1fr;
    }

    @media (max-width: 40rem) {
      .blog-card {
        grid-template-columns: auto;
        grid-template-rows: 12rem 1fr;
      }
    }
  }
</style>
