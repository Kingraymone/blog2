<template>
  <div style="padding:10px 10px 10px 10px;background-color: #F7F7F7">
    <div style="padding:10px 10px 10px 10px;background-color: #EEEEEE">
      <!--表单搜索-->
      <el-row type="flex">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <div>
            <el-form :inline="true" class="demo-form-inline">
              <el-form-item label="审批人">
                <el-input placeholder="审批人"></el-input>
              </el-form-item>
              <el-form-item label="活动区域">
                <el-select placeholder="活动区域">
                  <el-option label="区域一" value="shanghai"></el-option>
                  <el-option label="区域二" value="beijing"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="onQuery">查询</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-col>
      </el-row>
      <!--基本按钮-->
      <el-row type="flex" style="height: 40px">
        <div>
          <el-button icon="el-icon-plus" type="primary" @click="handleAdd">
            添加
          </el-button>
          <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
            删除
          </el-button>
          <el-button type="primary" @click="testMessage">baseMessage</el-button>
          <el-button
            type="primary"
            icon="el-icon-delete"
            class="handle-del mr10"
          >批量删除
          </el-button>
        </div>
      </el-row>
      <!--表格-->
      <el-row type="flex" style="height: 350px">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-table
            ref="multipleTable"
            :data="tableData"
            stripe
            border
            height="100%"
            :default-sort = "{prop: 'date', order: 'descending'}"
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
  </div>
</template>

<script>
    export default {
        name: "user",
        data() {
            const item = {
                date: '2016-05-02',
                name: 'spring boot',
                address: '上海市普陀区金沙江路 1518 弄'
            };
            const item2 = {
                date: '2016-05-03',
                name: 'mybatis',
                address: '上海市普陀区金沙江路 1518 弄'
            };
            return {
                tableData: Array(10).fill(item).concat(Array.fill(item2)),
                tcolumn: [
                    {
                        label: '',
                        width: '55',
                        type: 'selection',
                        prop: ''
                    }, {
                        label: '日期',
                        width: '120',
                        type: '',
                        prop: 'date',
                        sort: true
                    }, {
                        label: '姓名',
                        width: '120',
                        type: '',
                        prop: 'name',
                        sort:true
                    }, {
                        label: '地址',
                        width: '',
                        type: '',
                        prop: 'address'
                    }
                ]
            }
        },
        methods: {
            onQuery() {
                console.log('submit!');
            }
        }
    }
</script>

<style scoped>
  .bg-purple {
    background: #d3dce6;
  }

  .bg-purple-light {
    background: #e5e9f2;
  }

  .grid-content {
    border-radius: 4px;
    min-height: 36px;
  }
</style>
