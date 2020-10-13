<!--头部导航栏-->
<template>
  <div id="HeadNav">
  <el-row type="flex" class="header">
    <el-col :xs="2" :sm="2" :md="2" :lg="2" :xl="2">
      <div class="collapse-btn" @click="collapseChage">
        <i v-if="!$store.state.isCollapse" class="el-icon-s-fold"></i>
        <i v-else class="el-icon-s-unfold"></i>
      </div>
    </el-col>
    <el-col :xs="4" :sm="4" :md="4" :lg="4" :xl="4">
      <div style="text-align: left;line-height: 70px">后台管理系统</div>
    </el-col>

    <el-col :offset="10" :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
      <div class="header-right">
        <div class="header-user-con">
          <!-- 全屏显示 -->
          <div class="btn-fullscreen">
            <i class="el-icon-rank"></i>
          </div>
          <!-- 消息中心 -->
          <div class="btn-bell">
            <i class="el-icon-bell"></i>
          </div>
          <!-- 用户头像 -->
          <div class="user-avator">
            <img src="../../assets/img.jpg"/>
          </div>
          <!-- 用户名下拉菜单 -->
          <el-dropdown class="user-name" trigger="hover" @command="handleCommand">
                    <span class="el-dropdown-link">
                         king
                        <i class="el-icon-caret-bottom  el-icon--right"></i>
                    </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-picture-outline-round" style="text-align: left">主题</el-dropdown-item>
              <el-dropdown-item icon="el-icon-lock" style="text-align: left">锁屏</el-dropdown-item>
              <el-dropdown-item icon="el-icon-edit-outline">密码修改</el-dropdown-item>
              <el-dropdown-item icon="el-icon-circle-check" command="logout">安全退出</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </el-col>
  </el-row>
  </div>


</template>

<script>
    export default {
        name: "HeadNav",
        data() {
            return {}
        },
        methods: {
            collapseChage() {
                this.$store.commit('collapseChage');
            },
            handleCommand(command){
                if(command==='logout'){
                    // 清除token、锁屏等
                    this.$router.push("/sys");
                }
            }
        }
    }
</script>

<style scoped>
  .header {
    width: 100%;
    height: 70px;
    font-size: 22px;
    color: #fff;
  }

  .collapse-btn {
    float: left;
    padding: 0 21px;
    cursor: pointer;
    line-height: 70px;
  }

  .header .logo {
    float: left;
    line-height: 70px;
  }

  .header-right {
    float: right;
    padding-right: 50px;
  }

  .header-user-con {
    display: flex;
    height: 70px;
    align-items: center;
  }

  .btn-fullscreen {
    transform: rotate(45deg);
    margin-right: 5px;
    font-size: 24px;
  }

  .btn-bell,
  .btn-fullscreen {
    position: relative;
    width: 30px;
    height: 30px;
    text-align: center;
    border-radius: 15px;
    cursor: pointer;
  }

  .btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
  }

  .btn-bell .el-icon-bell {
    color: #fff;
  }

  .user-name {
    margin-left: 10px;
  }

  .user-avator {
    margin-left: 20px;
  }

  .user-avator img {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }

  .el-dropdown-link {
    color: #fff;
    cursor: pointer;
  }

  .el-dropdown-menu__item {
    text-align: center;
  }
</style>
