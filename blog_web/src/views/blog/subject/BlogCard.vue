<template>
  <div style="margin: 0 auto; padding-left: 80px;padding-right: 60px">
    <div style="min-height: 450px">
    <el-row style="margin-top: 20px" v-for="num in loop" :key="num">
      <el-col v-for="(item,index) in blog" :span="5" :offset="1" :key="index">
        <card v-if="index<num*pageSize/2&&index>=(num-1)*(pageSize/2)" :data-image="item.dataImg">
          <span slot="date">{{item.date}}</span>
          <a @click.once="addAppreciate(item)" slot="appreciate" class="fa fa-heart-o"><span>{{item.appreciate}}</span></a>
          <span slot="comment">{{item.comment}}</span>
          <h1 slot="header">{{item.header}}</h1>
          <p slot="content">{{item.content}}...<router-link :to="'/blog/detail/'+item.id">more</router-link></p>
        </card>
      </el-col>
    </el-row>
    </div>
    <el-row type="flex" style="margin-top:30px;margin-bottom: 10px;padding-top: 40px;text-align: center;height: 60px;line-height: 60px">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <div class="counter">{{currentPage}}<span> / </span>{{parseInt(total/pageSize+1)}}</div>
        <button @click="leftChange()" :data-state="leftState" class="paginate left"><i></i><i></i></button>
        <button @click="rightChange()" :data-state="rightState" class="paginate right"><i></i><i></i></button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    import card from '../../../views/blog/subject/Card.vue';
    export default {
        name: "BlogCard",
        data() {
            return {
                /*bgImgUrl: [
                    "https://images.unsplash.com/photo-1479660656269-197ebb83b540?dpr=2&auto=compress,format&fit=crop&w=1199&h=798&q=80&cs=tinysrgb&crop=",
                    "https://images.unsplash.com/photo-1479644025832-60dabb8be2a1?dpr=2&auto=compress,format&fit=crop&w=1199&h=799&q=80&cs=tinysrgb&crop=",
                    "https://images.unsplash.com/photo-1479621051492-5a6f9bd9e51a?dpr=2&auto=compress,format&fit=crop&w=1199&h=811&q=80&cs=tinysrgb&crop=",
                    "https://images.unsplash.com/photo-1479659929431-4342107adfc1?dpr=2&auto=compress,format&fit=crop&w=1199&h=799&q=80&cs=tinysrgb&crop="
                ],*/
                bgImgUrl: [
                    "https://uploadbeta.com/api/pictures/random/?key=BingEverydayWallpaperPicture",
                    "0",
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7"
                ],
                cardParams: {
                    date: '2020-01-01',
                    appreciate: 0,
                    comment: 0,
                    header: '',
                    content: '',
                    dataImg:''
                },
                blog:[],
                total: 0,
                pageSize: 8,
                currentPage: 1,
                loop:1,
                leftState:'',
                rightState:''
            }
        },
        created() {
            this.selectData();
        },
        methods:{
            selectData() {
                let searchParam = {
                    "pageSize": this.pageSize,
                    "pageNum": this.currentPage,
                    "data":{keyWord: [1]}
                };
                let _this = this;
                _this.$axios.post('/article/search', searchParam)
                    .then(function (response) {
                        let data = response.data.data;
                        _this.blog=[];
                        data.forEach((item)=>{
                            let curParams = {};
                            curParams.date=item.createTime.substring(0,10);
                            curParams.comment = item.comment == null ? 0 : item.comment;
                            curParams.appreciate = item.appreciate == null ? 0 : item.appreciate;
                            curParams.header=item.title;
                            curParams.content=item.summary;
                            curParams.id=item.uniqueId;
                            curParams.dataImg=_this.bgImgUrl[Math.floor(Math.random()*(_this.bgImgUrl.length))];
                            _this.blog.push(curParams);
                        });
                        _this.loop=parseInt((data.length*2-1)/_this.pageSize+1);
                        _this.total = response.data.count;
                        if(parseInt(_this.total/_this.pageSize+1)===_this.currentPage){
                            _this.rightState='disabled';
                        }else{
                            _this.rightState='';
                        }
                        if(_this.currentPage===1){
                            _this.leftState='disabled';
                        }else{
                            _this.leftState='';
                        }
                    })
                    .catch(function (error) {
                    });
            },
            pageChange(val) {
                // 当前页改变，重新查询
                this.currentPage = val;
                this.selectData();
            },
            leftChange(){
                if(this.currentPage===1){
                    this.leftState='disabled';
                }else {
                    this.currentPage--;
                    if(this.currentPage===1){
                        this.leftState='disabled';
                    }else{
                        this.leftState='';
                    }
                    this.pageChange(this.currentPage);
                }
            },
            rightChange(){
                let maxSize = parseInt(this.total/this.pageSize+1);
                if(maxSize<=this.currentPage){
                    this.rightState='disabled';
                }else {
                    this.currentPage++;
                    if(this.currentPage>=maxSize){
                        this.rightState='disabled';
                    }else{
                        this.rightState='';
                    }
                    this.pageChange(this.currentPage);
                }
            },
            addAppreciate(item){
                let _this=this;
                this.$axios.post('/article/appreciate',{'uniqueId':item.id}
                )
                    .then(function(response){
                        if(response.data.code!="-1") {
                            item.appreciate++;
                        }
                    })
                    .catch(function(error){

                    });
            }
        },
        components:{
            card
        }
    }
</script>

<style lang="scss" scoped>
  $size: 50px;
  $thickness: 5px;
  $angle: 40deg;
  $angleHover: 30deg;
  $angleActive: 25deg;

  @mixin arrowTransform( $angle, $x: 0, $y: 0 ) {
    i:first-child {
      transform: translate( $x, $y ) rotate( $angle );
    }

    i:last-child {
      transform: translate( $x, -$y ) rotate( -$angle );
    }
  }
  button {
    -webkit-appearance: none;
    background: transparent;
    border: 0;
    outline: 0;
  }
  .paginate {
    width: $size;
    height: $size;
    cursor: pointer;
    transform: translate3d(0,0,0); // fixes flicker in webkit

    position: absolute;
    top: 50%;
    margin: -20px 10px 10px;
    -webkit-filter: drop-shadow( 0 2px 0px rgba(0,0,0,0.2) );

    i {
      position: absolute;
      top: 40%;
      left: 0;
      width: $size;
      height: $thickness;
      border-radius: $thickness / 2;
      background: #115f37;

      transition: all 0.15s ease;
    }

    &.left {
      right: 58%;

      i {
        transform-origin: 0% 50%
      }

      @include arrowTransform( $angle, 0, -1px );

      &:hover {
        @include arrowTransform( $angleHover, 0, -1px );
      }

      &:active {
        @include arrowTransform( $angleActive, 1px, -1px );
      }

      &[data-state=disabled] {
        @include arrowTransform( 0deg, -5px, 0 );

        &:hover {
          @include arrowTransform( 0deg, -5px, 0 );
        }
      }
    }

    &.right {
      left: 58%;

      i {
        transform-origin: 100% 50%
      }

      @include arrowTransform( $angle, 0, 1px );

      &:hover {
        @include arrowTransform( $angleHover, 0, 1px );
      }

      &:active {
        @include arrowTransform( $angleActive, 1px, 1px );
      }

      &[data-state=disabled] {
        @include arrowTransform( 0deg, 5px, 0 );

        &:hover {
          @include arrowTransform( 0deg, 5px, 0 );
        }
      }
    }

    &[data-state=disabled] {
      opacity: 0.3;
      cursor: default;
    }
  }
  .counter {
    text-align: center;
    position: absolute;
    width: 100%;
    top: 50%;
    margin-top: -25px;
    font-size: 40px;
    font-family: Helvetica, sans-serif;
    text-shadow: 0px 2px 0px rgba( 0, 0, 0, 0.2 );
    color: #115f37;
  }

</style>
