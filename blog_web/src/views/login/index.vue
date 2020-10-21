<template>
  <div class="loginBg">
    <div class="login-box">
      <h2>Login</h2>
      <el-form :model="param" :rules="rules" ref="login">
        <el-form-item prop="username" class="user-box">
          <el-input
            v-model="param.username"
            placeholder="username"
            >
            <!--<el-button slot="prepend" icon="el-icon-user"></el-button>-->
          </el-input>
        </el-form-item>
        <el-form-item prop="password" class="user-box">
          <el-input
            :type="pwdtype"
            placeholder="password"
            v-model="param.password"
            @keyup.enter.native="submitForm()"
          >
            <i slot="suffix" class="fa" :class="{'fa-eye':this.pwdtype=='','fa-eye-slash':this.pwdtype!=''}"  @click="showPwd"></i>
          </el-input>
        </el-form-item>
        <a @click="submitForm()">
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          Submit
        </a>
      </el-form>
    </div>
  </div>
</template>

<script>
    export default {
        data: function () {
            return {
                param: {
                    username: 'king',
                    password: '123456',
                },
                pwdtype: "password",
                rules: {
                    username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                },
            };
        },
        methods: {
            showPwd() {
                if (this.pwdtype === "password") {
                    this.pwdtype = "";
                } else {
                    this.pwdtype = "password";
                }
            },
            parseResponse(response){
              let token=response.headers.authorization;
              this.$store.commit('addToken',token);
              this.$store.commit('addUserInfo',response.data.data);
            },
            submitForm() {
                this.$refs.login.validate(valid => {
                    let _this = this;
                    if (valid) {
                        //_this.$router.push('/sys/home');
                        // 登陆后台校验  /login
                        // 存在token直接成功
                        this.$axios.post('/user/verify', {
                            username: this.param.username,
                            password: this.param.password
                        })
                            .then(function (response) {
                                _this.commons.kMessage('登录成功!', 'success');
                                // response解析
                                _this.parseResponse(response);
                                _this.$router.push('/sys/home');
                            }).catch(err => {

                            })
                    } else {
                        return false;
                    }
                });
            },
        },
    };
</script>

<style scoped>

  .loginBg {
    margin:0;
    padding:0;
    font-family: sans-serif;
    background: linear-gradient(#141e30, #243b55);
    height: 100%;
  }

  .login-box {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 400px;
    padding: 40px;
    transform: translate(-50%, -50%);
    background: rgba(0,0,0,.5);
    box-sizing: border-box;
    box-shadow: 0 15px 25px rgba(0,0,0,.6);
    border-radius: 10px;
  }

  .login-box h2 {
    margin: 0 0 30px;
    padding: 0;
    color: #fff;
    text-align: center;
  }

  .login-box .user-box {
    position: relative;
  }

  .login-box .user-box input {
    width: 100%;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    margin-bottom: 30px;
    border: none;
    border-bottom: 1px solid #fff;
    outline: none;
    background: transparent;
  }
  .login-box .user-box label {
    position: absolute;
    top:0;
    left: 0;
    padding: 10px 0;
    font-size: 16px;
    color: #fff;
    pointer-events: none;
    transition: .5s;
  }

  .login-box .user-box input:focus ~ label,
  .login-box .user-box input:valid ~ label {
    top: -20px;
    left: 0;
    color: #03e9f4;
    font-size: 12px;
  }

  .login-box form a {
    position: relative;
    display: inline-block;
    padding: 10px 20px;
    color: #03e9f4;
    font-size: 16px;
    text-decoration: none;
    text-transform: uppercase;
    overflow: hidden;
    transition: .5s;
    margin-top: 40px;
    letter-spacing: 4px
  }

  .login-box a:hover {
    background: #03e9f4;
    color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 5px #03e9f4,
    0 0 25px #03e9f4,
    0 0 50px #03e9f4,
    0 0 100px #03e9f4;
  }

  .login-box a span {
    position: absolute;
    display: block;
  }

  .login-box a span:nth-child(1) {
    top: 0;
    left: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(90deg, transparent, #03e9f4);
    animation: btn-anim1 1s linear infinite;
  }

  @keyframes btn-anim1 {
    0% {
      left: -100%;
    }
    50%,100% {
      left: 100%;
    }
  }

  .login-box a span:nth-child(2) {
    top: -100%;
    right: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(180deg, transparent, #03e9f4);
    animation: btn-anim2 1s linear infinite;
    animation-delay: .25s
  }

  @keyframes btn-anim2 {
    0% {
      top: -100%;
    }
    50%,100% {
      top: 100%;
    }
  }

  .login-box a span:nth-child(3) {
    bottom: 0;
    right: -100%;
    width: 100%;
    height: 2px;
    background: linear-gradient(270deg, transparent, #03e9f4);
    animation: btn-anim3 1s linear infinite;
    animation-delay: .5s
  }

  @keyframes btn-anim3 {
    0% {
      right: -100%;
    }
    50%,100% {
      right: 100%;
    }
  }

  .login-box a span:nth-child(4) {
    bottom: -100%;
    left: 0;
    width: 2px;
    height: 100%;
    background: linear-gradient(360deg, transparent, #03e9f4);
    animation: btn-anim4 1s linear infinite;
    animation-delay: .75s
  }

  @keyframes btn-anim4 {
    0% {
      bottom: -100%;
    }
    50%,100% {
      bottom: 100%;
    }
  }

</style>
