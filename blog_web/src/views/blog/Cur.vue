<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">后台管理系统</div>
      <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input
            v-model="param.username"
            placeholder="username"
            prefix-icon="el-icon-user"
            size="large">
            <!--<el-button slot="prepend" icon="el-icon-user"></el-button>-->
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            :type="pwdtype"
            placeholder="password"
            prefix-icon="el-icon-lock"
            v-model="param.password"
            size="large"
            @keyup.enter.native="submitForm()"
          >
            <!--<el-button slot="prepend" icon="el-icon-lock"></el-button>-->
            <i slot="suffix" class="el-icon-view" @click="showPwd"></i>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>
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
  .login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/login_images/background.jpg);
    background-size: 100%;
  }

  .ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #000000;
    border-bottom: 1px solid #ddd;
  }

  .ms-login {
    position: absolute;
    left: 45%;
    top: 48%;
    width: 500px;
    height: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
  }

  .ms-content {
    padding: 40px 40px;
  }

  .login-btn {
    text-align: center;
  }

  .login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
  }

  .login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
  }
</style>
