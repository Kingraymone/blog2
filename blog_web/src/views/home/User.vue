<template>
  <div>
    <!--首部-->
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-s-custom"></i> 用户管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <!--表单搜索-->
      <el-row type="flex">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <div>
            <el-form :inline="true">
              <el-form-item label="用户名">
                <el-input
                  placeholder="请输入内容"
                  v-model="searchTab.username"
                  clearable
                  size="small"
                  prefix-icon="el-icon-search">
                </el-input>
              </el-form-item>
              <el-form-item label="创建日期">
                <el-date-picker
                  v-model="searchTab.createTime"
                  type="date"
                  size="small"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="用户状态">
                <el-select
                  v-model="searchTab.status"
                  multiple
                  collapse-tags
                  clearable
                  size="small"
                  placeholder="请选择">
                  <el-option label="正常" value="0"></el-option>
                  <el-option label="冻结" value="1"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button size="small" type="primary" @click="onQuery">查询</el-button>
                <el-button size="small" @click="resetForm()">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
      <el-divider></el-divider>
      <!--基本按钮-->
      <el-row type="flex">
        <el-button-group>
          <el-tooltip class="item" content="新增">
            <el-button icon="el-icon-plus" @click="userAdd"></el-button>
          </el-tooltip>
          <el-tooltip class="item" content="修改">
            <el-button icon="el-icon-edit" @click="userEdit"></el-button>
          </el-tooltip>
          <el-tooltip class="item" content="删除">
            <el-button icon="el-icon-delete" @click="deleteUser"></el-button>
          </el-tooltip>
          <el-button @click="iconUpload">头像上传<i class="el-icon-upload el-icon--right"></i></el-button>
        </el-button-group>
      </el-row>
      <!--表格-->
      <el-row type="flex">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-table
            ref="multipleTable"
            :data="tableData"
            stripe
            border
            :header-cell-style="{textAlign: 'center'}"
            :cell-style="{ textAlign: 'center' }"
            :default-sort="{prop: 'createTime', order: 'descending'}"
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
            <template v-for="(item,index) in tcolumn">
              <el-table-column
                :label="item.label"
                :width="item.width"
                :type="item.type"
                :prop="item.prop"
                :sortable="item.sort"
              ></el-table-column>
            </template>

          </el-table>
        </el-col>
      </el-row>
      <!--页面导航-->
      <el-row type="flex" style="height: 40px">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <div style="text-align: right">
            <el-pagination
              background
              :page-size="20"
              :pager-count="10"
              layout="total, sizes,prev, pager, next,jumper"
              :total="2000">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
    </div>

    <!--新增表单-->
    <el-dialog ref="addDialog" title="新增"
               width="40%"
               :visible.sync="addVisible"
               :close-on-click-modal="false">
      <el-form ref="addForm"
               :model="addForm"
               :rules="rules"
               size="mini"
      >
        <el-row>
          <el-col :span="10">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="addForm.username" maxlength="10"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="4">
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="addForm.nickname"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="密码" prop="password">
              <el-input placeholder="请输入密码" v-model="addForm.password" show-password></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="4">
            <el-form-item label="座右铭" prop="motto">
              <el-input v-model="addForm.motto"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="邮箱" prop="mail">
              <el-input v-model="addForm.mail"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="4">
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="addVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="addSubmitForm('addForm')">提 交</el-button>
      </div>
    </el-dialog>
    <!--修改表单-->
    <el-dialog ref="editDialog" title="修改"
               width="40%"
               :visible.sync="editVisible"
               :close-on-click-modal="false">
      <el-form ref="editForm"
               :model="editForm"
               :rules="rules"
               size="mini"
      >
        <el-row>
          <el-col :span="10">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="editForm.username" maxlength="10" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="4">
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="editForm.nickname"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="密码" prop="password">
              <el-input placeholder="请输入密码" v-model="editForm.password" show-password></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="4">
            <el-form-item label="座右铭" prop="motto">
              <el-input v-model="editForm.motto"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="邮箱" prop="mail">
              <el-input v-model="editForm.mail"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="4">
              <el-form-item label="状态" prop="status">
                <el-select
                  v-model="editForm.status"
                  clearable
                  placeholder="请选择">
                  <el-option label="正常" value="0"></el-option>
                  <el-option label="冻结" value="1"></el-option>
                </el-select>
              </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="editVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="editSubmitForm('editForm')">提 交</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
    export default {
        name: "user",
        data() {
            const item = {
                createTime: '2016-05-02',
                username: '王小虎',
                nickname: '小虎',
                motto: '上海市普陀区金沙江路 1518 弄',
                mail: '5232323@qq.com',
                status: '1'
            };
            const item2 = {
                createTime: '2016-05-03',
                username: '范德萨',
                nickname: '分',
                motto: '上海市普陀区金沙江路 1518 弄',
                mail: '523232223@qq.com',
                status: '0'
            };
            return {
                addVisible: false,
                editVisible: false,
                addForm: {
                    username: '',
                    password: '',
                    nickname: '',
                    motto: '',
                    mail: ''
                },
                editForm: {
                    username: '',
                    password: '',
                    nickname: '',
                    motto: '',
                    mail: '',
                    status: ''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                    nickname: [
                        {required: true, message: '请输入昵称', trigger: 'blur'}
                    ]
                },
                formLabelWidth: 150,
                searchTab: {
                    username: '',
                    createTime: '',
                    status: ''
                },
                tableData: Array(1).fill(item).concat(Array(1).fill(item2)),
                tcolumn: [
                    {
                        label: '',
                        width: '55',
                        type: 'selection',
                        prop: ''
                    }, {
                        label: '用户名',
                        width: '120',
                        type: '',
                        prop: 'username',
                        sort: true
                    }, {
                        label: '昵称',
                        width: '120',
                        type: '',
                        prop: 'nickname',
                        sort: true
                    }, {
                        label: '状态',
                        width: '120',
                        type: '',
                        sort: true,
                        prop: 'status'
                    }, {
                        label: '座右铭',
                        width: '220',
                        type: '',
                        prop: 'motto'
                    }, {
                        label: '邮箱',
                        width: '200',
                        sort: true,
                        type: '',
                        prop: 'mail'
                    }, {
                        label: '创建日期',
                        width: '',
                        type: '',
                        sort: true,
                        prop: 'createTime'
                    }
                ],
                rowSelections: []
            }
        },
        methods: {
            kMessage(msg,type){
                this.$message.closeAll();
                this.$message({
                    message: msg,
                    center: true,
                    type:type,
                    duration:1000
                });
            },
            copyProperty(newP,oldP){
                for(let key in newP) {
                    newP[key] = oldP[key];
                }
            },
            handleSelectionChange(val) {
                this.rowSelections = val;
            },
            userAdd() {
                this.addVisible = true;
            },
            addSubmitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.addVisible = false;
                        return true;
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },

            userEdit() {
                if (this.rowSelections.length !== 1) {
                    this.kMessage("请选择一条数据！",'info');
                } else {
                    this.copyProperty(this.editForm,this.rowSelections[0]);
                    this.editVisible = true;
                }
            },
            editSubmitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.addVisible = false;
                        return true;
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },

            deleteUser(){
                if (this.rowSelections.length !== 1) {
                    this.kMessage("请选择一条数据！",'info');
                } else {

                }
            },

            iconUpload(){
                if (this.rowSelections.length !== 1) {
                    this.kMessage("请选择一条数据！",'info');
                } else {

                }
            },
            resetForm() {
                this.searchTab = {};
            },
            onQuery() {
                console.log('submit!');
            }
        }
    }
</script>

<style scoped>

</style>
