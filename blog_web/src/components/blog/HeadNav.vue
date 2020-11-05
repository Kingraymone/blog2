<template>
  <nav>
    <div style="padding-left: 80px">
      <router-link to='/'><span class="navbar-brand">Raymone</span></router-link>
    </div>
    <div style="padding-right: 100px">
      <ul class="nav navbar-nav navbar-right">
        <li class="active item"><router-link to="/blog">Home</router-link></li>
        <li class="item"><router-link to="/blog/archive">Archives</router-link></li>
        <!--<li class="item"><a href="#ser">Tags</a></li>-->
        <li class="item"><router-link to="/blog/about">About</router-link></li>
        <li style="line-height: 80px; height: 80px;margin-left: 5px">
          <input @keyup.enter="search()" v-model="title" id="search-box" type="text" class="search-box" name="q"/>
          <label title="enter" for="search-box"><span class="glyphicon glyphicon-search search-icon"></span></label>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
    export default {
        name: "HeadNavBlog",
        data(){
            return{
                title:''
            }
        },
        methods:{
            search(){
                if(!!this.title){
                    this.$router.push({
                        name: 'search',
                        query: {
                            key: this.title
                        }
                    });
                    this.title='';
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
  $tl: 0.6s;

  /*搜索框begin*/
  .search-box {
    transition: width $tl, border-radius $tl, background $tl, box-shadow $tl;
    width: 40px;height: 40px;
    border-radius: 20px;border: none;
    cursor: pointer;
    background: rgb(235, 235, 235);
    & + label .search-icon {    color: black    }
    &:hover {
      color: white;
      background: rgb(200, 200, 200);
      box-shadow: 0 0 0 5px rgb(61, 71, 82);
      & + label .search-icon {    color: white    }
    }
    &:focus {
      transition: width $tl cubic-bezier(0,1.22,.66,1.39), border-radius $tl, background $tl;
      border: none;outline: none;
      box-shadow: none;
      padding-left: 15px;
      cursor: text;
      width: 300px;
      border-radius: auto;
      background: rgb(235, 235, 235);
      color: black;
      & + label .search-icon {    color: black;    }
    }
    &:not(:focus) {    text-indent:-5000px;    } // for more-graceful falling back (:not browsers likely support indent)
  }


  .search-icon {
    position: relative;
    left: -30px;
    color: white;
    cursor: pointer;
  }
  /*搜索框end*/

  .navbar{
    background-color:white;
    padding-right:50px;
    padding-left:50px;
  }

  .navbar-brand {
    line-height: 80px;
    height: 80px;
    padding-top: 0;
    color: #323e46;
    font-family: lobster , Serif;
    font-size: 40px;
  }

  .navbar-nav li a {
    line-height: 80px;
    height: 80px;
    padding-top: 0;
    color:black;
  }


  .arrows span{
    color: #23b0ac;
    background-color:#fff;
    cursor:pointer;
    width:40px;
    height:40px;
    text-align:center;
    border-radius:100%;
  }

  .item :hover {
    color: #23b0ac;
    border-bottom: 3px solid #23b0ac;
  }



</style>
