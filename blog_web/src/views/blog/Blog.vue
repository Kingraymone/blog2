<template>
  <div>

    <div class="g-container" id="imgWave">

    </div>
    <div class="lightning flashit"></div>

    <div class="page">
      <header>
        <div class="avatar">
          <router-link to="/blog">
            <img src="../../assets/blog/mine.jpg"/>
          </router-link>
        </div>
      </header>
      <div class="content">
        <h1>Jsut hava a little faith</h1>
        <br/>
        <p>
        <span><a href="https://github.com/Kingraymone" target="_blank"><svg
          t="1602334796596" class="icon"
          viewBox="0 0 1024 1024" version="1.1"
          xmlns="http://www.w3.org/2000/svg"
          p-id="2180" width="32" height="32"><path
          d="M512 42.666667A464.64 464.64 0 0 0 42.666667 502.186667 460.373333 460.373333 0 0 0 363.52 938.666667c23.466667 4.266667 32-9.813333 32-22.186667v-78.08c-130.56 27.733333-158.293333-61.44-158.293333-61.44a122.026667 122.026667 0 0 0-52.053334-67.413333c-42.666667-28.16 3.413333-27.733333 3.413334-27.733334a98.56 98.56 0 0 1 71.68 47.36 101.12 101.12 0 0 0 136.533333 37.973334 99.413333 99.413333 0 0 1 29.866667-61.44c-104.106667-11.52-213.333333-50.773333-213.333334-226.986667a177.066667 177.066667 0 0 1 47.36-124.16 161.28 161.28 0 0 1 4.693334-121.173333s39.68-12.373333 128 46.933333a455.68 455.68 0 0 1 234.666666 0c89.6-59.306667 128-46.933333 128-46.933333a161.28 161.28 0 0 1 4.693334 121.173333A177.066667 177.066667 0 0 1 810.666667 477.866667c0 176.64-110.08 215.466667-213.333334 226.986666a106.666667 106.666667 0 0 1 32 85.333334v125.866666c0 14.933333 8.533333 26.88 32 22.186667A460.8 460.8 0 0 0 981.333333 502.186667 464.64 464.64 0 0 0 512 42.666667"
          p-id="2181" fill=svgColor></path></svg></a></span>
          <span><a href="https://blog.csdn.net/wq5350/" target="_blank"><svg t="1602334671461" class="icon"
                                                                             viewBox="0 0 1024 1024" version="1.1"
                                                                             xmlns="http://www.w3.org/2000/svg"
                                                                             p-id="1192" width="32" height="32"><path
            d="M512 0c282.784 0 512 229.216 512 512s-229.216 512-512 512S0 794.784 0 512 229.216 0 512 0z m189.952 752l11.2-108.224c-31.904 9.536-100.928 16.128-147.712 16.128-134.464 0-205.728-47.296-195.328-146.304 11.584-110.688 113.152-145.696 232.64-145.696 54.784 0 122.432 8.8 151.296 18.336L768 272.704C724.544 262.24 678.272 256 599.584 256c-203.2 0-388.704 94.88-406.4 263.488C178.336 660.96 303.584 768 535.616 768c80.672 0 138.464-6.432 166.336-16z"
            fill=svgColor p-id="1193"></path></svg></a></span>
        </p>
      </div>
      <!--<img src="http://www.supah.it/dribbble/008/008.jpg" />-->
    </div>
  </div>
</template>
<script>
    (function() {
        let x, y;
        let index = 0;
        let screenSizeWidth = document.body.clientWidth;
        let screenSizeHeight = document.body.clientHeight;
        let halfvmin = (screenSizeWidth > screenSizeHeight ? screenSizeHeight / 2 : screenSizeWidth / 2) * 0.8;

        console.log('halfvmin', halfvmin);
        document.onclick=function(e){
            x = e.pageX;
            y = e.pageY;
            waveMove(x, y, index++);
        };

        function waveMove(x, y, z) {
            let elementById = document.getElementById("imgWave");
            let gPosition = document.createElement("div");
            gPosition.setAttribute('class', 'g-position g-position' + z);
            gPosition.style.top = (y - halfvmin) + 'px';
            gPosition.style.left = (x - halfvmin) + 'px';
            gPosition.style.zIndex = z;
            let gCenter = document.createElement("div");
            gCenter.setAttribute('class', 'g-center');
            for (let i = 1; i < 5; i++) {
                let gWave = document.createElement("div");
                gWave.setAttribute('class', 'wave g-wave' + i);
                gCenter.appendChild(gWave);
            }
            gPosition.appendChild(gCenter);
            elementById.appendChild(gPosition);
            /*
            let innerWave = `
            <div class="g-position g-position${z}" style="top:${y - halfvmin}px; left:${x - halfvmin}px; z-index:${z}">
                <div class="g-center">
                    <div class="wave g-wave1"></div>
                    <div class="wave g-wave2"></div>
                    <div class="wave g-wave3"></div>
                    <div class="wave g-wave4"></div>
                </div>
            </div>
        `;
            for(let i = 0;i<elementById.length;i++){
                elementById[i].innerHTML+=innerWave;
            }*/

            setTimeout(function() {
                var curId = "g-position"+z;
                var element = document.getElementsByClassName(curId);
                for(let i = 0;i<element.length;i++){
                    element[i].remove();
                }
            }, 1000);
        }
    })();

    export default {

    }
</script>
<style lang="scss" >
  $img2: '../../assets/blog/bg.jpg';
  $aniTime: 3s;

  /*悬浮头像begin*/
  @mixin center {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }

  header {
    padding: 25px;
  }

  .page {
    @include center;
    width: 100%;
    height: 100%;
    z-index: 1;
    font-family: Roboto, sans-serif;
    background: #0D0C1E;
    border-radius: 1px;
    overflow: hidden;
    box-shadow: 0 5px 15px rgba(0, 0, 0, .3);
  }


  /*--------------------
  Content
  --------------------*/
  .content {
    text-align: center;
    padding-top: 10px;
    color: #CDD4DE;

    h1 {
      color: #fff;
      font-family: 'Open Sans', sans-serif;
      font-weight: 800;
      font-size: 4em;
      letter-spacing: -2px;
      text-align: center;
      text-shadow: 1px 2px 1px rgba(0, 0, 0, .6);
    }

    h1:after {
      display: block;
      color: #fff;
      letter-spacing: 1px;
      font-family: 'Poiret One', sans-serif;
      content: 'Follow me on:';
      font-size: .4em;
      text-align: center;
    }

    h2 {
      font-family: 'Open Sans', Sans-serif,serif;
      color: #fff;
      text-align: center;
      text-shadow: 1px 2px 1px rgba(0, 0, 0, .6);
      font-size: 4em;
      letter-spacing: -2px;
      font-weight: 900;
      line-height: 1;
      margin-bottom: 10px;
      opacity: 0.6;
    }
    p {
      font-family: 'Poiret One', Sans-serif,serif;
      letter-spacing: 1px;
      font-size: 30px;
      font-weight: light;
      color: #333333;
      opacity: 0.7;
      margin-bottom: 15px;
    }

    p span a {
      font-size: 20px;
      color: #cccccc;
      text-decoration: none;
      margin: 0 10px;
      transition: all 0.4s ease-in-out;
    }

    svg:hover {
      fill: #ffffff;
    }

  }


  /*--------------------
  Image
  --------------------*/
  img {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: auto;
    z-index: -1;
    transform: scale(1.1);
  }

  @keyframes float {
    0% {
      box-shadow: 0 5px 15px 0px rgba(0, 0, 0, 0.6);
      transform: translatey(0px);
    }
    50% {
      box-shadow: 0 25px 15px 0px rgba(0, 0, 0, 0.2);
      transform: translatey(-20px);
    }
    100% {
      box-shadow: 0 5px 15px 0px rgba(0, 0, 0, 0.6);
      transform: translatey(0px);
    }
  }

  .avatar {
    width: 160px;
    height: 160px;
    margin: 100px auto 0;
    box-sizing: border-box;
    border: 5px white solid;
    border-radius: 50%;
    overflow: hidden;
    box-shadow: 0 5px 15px 0px rgba(0, 0, 0, 0.6);
    transform: translatey(0px);
    animation: float 6s ease-in-out infinite;

    img {
      width: 100%;
      height: auto;
    }
  }
  /*悬浮头像end*/
  /*高亮begin*/
  .bg{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: url($img2);
    background-position: center center;
    background-origin: content-box;
    background-size: cover;
    background-attachment: fixed;
    z-index: -2;
  }
  .lightning{
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: url($img2);
    background-position: center center;
    background-origin: content-box;
    background-size: cover;
    background-attachment: fixed;
    -webkit-filter: brightness(3);
    filter: brightness(3);
    -o-filter: brightness(3);
    -moz-filter: brightness(3);
    z-index: -1;
  }

  /*Now just a opacity animation*/
  .flashit{
    -webkit-animation: flash ease-out 7s infinite;
    -moz-animation: flash ease-out 7s infinite;
    animation: flash ease-out 7s infinite;
    animation-delay: 1s;
  }

  @-webkit-keyframes flash {
    from { opacity: 0; }
    92% { opacity: 0; }
    93% { opacity: 0.6; }
    94% { opacity: 0.2; }
    96% { opacity: 0.9; }
    to { opacity: 0; }
  }

  @keyframes flash {
    from { opacity: 0; }
    92% { opacity: 0; }
    93% { opacity: 0.6; }
    94% { opacity: 0.2; }
    96% { opacity: 1; }
    to { opacity: 0; }
  }
  /*高亮end*/

  .g-container{
    position: absolute;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    background-attachment: fixed;
    background-position: center center;
    // background-size: auto;
    background-size: auto 100%;
    overflow: hidden;
    cursor: pointer;

    background: url($img2);
    background-position: center center;
    background-origin: content-box;
    background-size: cover;
    background-attachment: fixed;
    z-index: -2;
  }

  .g-position {
    position:absolute;
    width: 80vmin;
    height: 80vmin;
  }

  .g-center {
    position: relative;
    width: 100%;
    height: 100%;
  }

  .wave {
    position: absolute;
    top: calc((100% - 20vmin)/2);
    left: calc((100% - 20vmin)/2);
    width: 20vmin;
    height: 20vmin;
    border-radius: 50%;
    background-image: url($img2);
    background-attachment: fixed;
    background-position: center center;
    transform: translate3d(0, 0, 0);
    opacity: 0;
    transition: all .2s;
  }

  .g-wave1 {
    background-size: auto 106%;
    animation: wave $aniTime ease-out .1s;
    animation-fill-mode: forwards;
    z-index: 10;
  }

  .g-wave2 {
    background-size: auto 102%;
    animation: wave $aniTime ease-out .15s;
    animation-fill-mode: forwards;
    z-index: 20;
  }

  .g-wave3 {
    background-size: auto 104%;
    animation: wave $aniTime ease-out .25s;
    animation-fill-mode: forwards;
    z-index: 30;
  }

  .g-wave4 {
    background-size: auto 100%;
    animation: wave $aniTime ease-out .4s;
    animation-fill-mode: forwards;
    z-index: 40;
  }

  @keyframes wave {
    0% {
      top: calc((100% - 20vmin)/2);
      left: calc((100% - 20vmin)/2);
      width: 20vmin;
      height: 20vmin;
      opacity: 1;
    }
    10% {
      // opacity: 1;
    }
    99% {
      opacity: 1;
    }
    100% {
      top: calc((100% - 80vmin)/2);
      left: calc((100% - 80vmin)/2);
      width: 80vmin;
      height: 80vmin;
      opacity: 0;
    }
  }
</style>
