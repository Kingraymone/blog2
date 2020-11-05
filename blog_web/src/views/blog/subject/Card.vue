<template>
  <div class="card-wrap"
       @mousemove="handleMouseMove"
       @mouseenter="handleMouseEnter"
       @mouseleave="handleMouseLeave"
       ref="card">
    <div class="card"
         :style="cardStyle">
      <div class="card-bg" :style="[cardBgTransform, cardBgImage]"></div>
      <div class="header">
        <div class="date">
          <i class="fa fa-clock-o">
            <slot name="date"></slot>
          </i>
        </div>
        <ul class="menu-content">
          <li>
            <a class="fa fa-bookmark-o"></a>
          </li>
          <li><slot name="appreciate"></slot></li>
          <li><a class="fa fa-comment-o"><slot name="comment"></slot></a></li>
        </ul>
      </div>
      <div class="card-info">
        <slot name="header"></slot>
        <slot name="content"></slot>
      </div>
      <!--<div class="card-footer"><span></span></div>-->
    </div>
  </div>
</template>

<script>
    import card1 from '../../../assets/blog/cardBg/card1.jpg'
    import card2 from '../../../assets/blog/cardBg/card2.jpg'
    import card3 from '../../../assets/blog/cardBg/card3.jpg'
    import card4 from '../../../assets/blog/cardBg/card4.jpg'
    import card5 from '../../../assets/blog/cardBg/card5.jpg'
    import card6 from '../../../assets/blog/cardBg/card6.jpg'
    import card7 from '../../../assets/blog/cardBg/card7.jpg'
    import card8 from '../../../assets/blog/cardBg/card8.jpg'
    export default {
        name: "Card",
        mounted() {
            this.width = this.$refs.card.offsetWidth;
            this.height = this.$refs.card.offsetHeight;
        },
        props: ['dataImage'],
        data: () => ({
            width: 0,
            height: 0,
            mouseX: 0,
            mouseY: 0,
            mouseLeaveDelay: null,
            bgImg:[card1,card2,card3,card4,card5,card6,card7,card8]
        }),
        computed: {
            mousePX() {
                return this.mouseX / this.width;
            },
            mousePY() {
                return this.mouseY / this.height;
            },
            cardStyle() {
                const rX = this.mousePX * 3;
                const rY = this.mousePY * -3;
                return {
                    transform: `rotateY(${rX}deg) rotateX(${rY}deg)`
                };
            },
            cardBgTransform() {
                const tX = this.mousePX * -4;
                const tY = this.mousePY * -4;
                return {
                    transform: `translateX(${tX}px) translateY(${tY}px)`
                }
            },
            cardBgImage() {
                return {
                    backgroundImage: `url(${ isNaN(this.dataImage) ? this.dataImage : this.bgImg[Number(this.dataImage)]})`
                }
            }
        },
        methods: {
            handleMouseMove(e) {
                this.mouseX = e.pageX - this.$refs.card.offsetLeft - this.width/2;
                this.mouseY = e.pageY - this.$refs.card.offsetTop - this.height/2;
            },
            handleMouseEnter() {
                clearTimeout(this.mouseLeaveDelay);
            },
            handleMouseLeave() {
                this.mouseLeaveDelay = setTimeout(()=>{
                    this.mouseX = 0;
                    this.mouseY = 0;
                }, 1000);
            }
        }
    }
</script>

<style lang="scss" scoped>
  $hoverEasing: cubic-bezier(0.23, 1, 0.32, 1);
  $returnEasing: cubic-bezier(0.445, 0.05, 0.55, 0.95);

  @mixin cf {
    &::before,
    &::after {
      content: '';
      display: table;
    }
    &::after {
      clear: both;
    }
  }
  .header {
    @include cf;
    color: white;
    padding: 1em;
    .date {
      float: left;
      font-size: 12px;
      font-weight: 100;
    }
    .date i{
        margin: 0 2px;
        position: relative;
    }
  }
  .menu-content {
    float: right;
    li {
      margin: 0 2px;
      position: relative;
    }
    span {
      transition: all 0.3s;
      opacity: 0;
    }
  }


  p {
    line-height: 1.5em;
  }

  h1+p, p+p {
    margin-top: 10px;
  }

  .card-wrap {
    transform: perspective(1000px);
    transform-style: preserve-3d;
    cursor: pointer;
    // background-color: #fff;

    &:hover {
      .card-info {
        transform: translateY(0);
      }
      .card-info p {
        opacity: 1;
      }
      .card-info, .card-info p {
        transition: 0.6s $hoverEasing;
      }
      .card-info:after {
        transition: 5s $hoverEasing;
        opacity: 0;
        transform: translateY(0);
      }
      .card-bg {
        transition:
          0.6s $hoverEasing,
          opacity 5s $hoverEasing;
        opacity: 0.5;
      }
      .card {
        transition:
          0.6s $hoverEasing,
          box-shadow 2s $hoverEasing;
        box-shadow:
          rgba(white, 0.2) 0 0 40px 5px,
          rgba(white, 1) 0 0 0 1px,
          rgba(black, 0.66) 0 30px 60px 0,
          inset #333 0 0 0 5px,
          inset white 0 0 0 6px;
        .menu-content {
          span {
            transform: translate(-50%, -10px);
            opacity: 1;
          }
        }
      }
    }
  }

  .card {
    position: relative;
    flex: 0 0 210px;
    width: 210px;
    height: 280px;
    background-color: #333;
    overflow: hidden;
    border-radius: 10px;
    box-shadow:
      rgba(black, 0.66) 0 30px 60px 0,
      inset #333 0 0 0 5px,
      inset rgba(white, 0.5) 0 0 0 6px;
    transition: 1s $returnEasing;
    .menu-content {
      @include cf;
      margin: 0;
      padding: 0;
      list-style-type: none;
      li {
        display: inline-block;
      }
      a {
        color: white;
      }
      span {
        position: absolute;
        left: 50%;
        top: 0;
        font-size: 10px;
        font-weight: 700;
        font-family: 'Open Sans';
        transform: translate(-50%, 0);
      }
    }
  }

  .card-bg {
    opacity: 0.5;
    position: absolute;
    top: 0; left: 0;
    width: 100%;
    height: 100%;
    padding: 20px;
    background-repeat: no-repeat;
    background-position: center;
    background-size: cover;
    transition:
      1s $returnEasing,
      opacity 5s 1s $returnEasing;
    pointer-events: none;
  }
  .card-footer{

  }
  .card-head{
    position: absolute;
  }
  .card-info {
    padding: 20px;
    position: absolute;
    bottom: 20px;
    color: #fff;
    transform: translateY(40%);
    transition: 0.6s 1.6s cubic-bezier(0.215, 0.61, 0.355, 1);

    p {
      font-size: 12px;
      opacity: 0;
      text-shadow: rgba(black, 1) 0 2px 3px;
      transition: 0.6s 1.6s cubic-bezier(0.215, 0.61, 0.355, 1);
      word-break:normal;
      white-space:pre-warp;
      word-wrapL:break-word;
    }

    * {
      position: relative;
      z-index: 1;
    }

    &:after {
      content: '';
      position: absolute;
      top: 0; left: 0;
      z-index: 0;
      width: 100%;
      height: 100%;
      background-image: linear-gradient(to bottom, transparent 0%, rgba(#000, 0.6) 100%);
      background-blend-mode: overlay;
      opacity: 0;
      transform: translateY(100%);
      transition: 5s 1s $returnEasing;
    }
  }

  .card-info h1 {
    font-family: "Playfair Display";
    font-size: 16px;
    font-weight: 600;
    text-shadow: rgba(black, 0.5) 0 10px 10px;
  }
</style>
