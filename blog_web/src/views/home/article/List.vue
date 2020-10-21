<template>
  <div>
    <!--首部-->
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item><i class="el-icon-s-custom"></i> 文章管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="myContainer">
      <!--表单搜索-->
      <el-row type="flex">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <div>
            <el-form :inline="true">
              <el-form-item label="文章名">
                <el-input
                  placeholder="请输入内容"
                  v-model="searchTab.title"
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
                  format="yyyy-MM-dd"
                  value-format="yyyyMMdd"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="发表状态">
                <el-select
                  v-model="searchTab.keyWord"
                  multiple
                  collapse-tags
                  clearable
                  size="small"
                  placeholder="请选择">
                  <el-option label="临时保存" value="0"></el-option>
                  <el-option label="已发表" value="1"></el-option>
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
            <el-button type="primary" icon="el-icon-plus" @click="handleAdd"></el-button>
          </el-tooltip>
          <el-tooltip class="item" content="删除">
            <el-button type="danger" icon="el-icon-delete" @click="handleDelete"></el-button>
          </el-tooltip>
        </el-button-group>
      </el-row>
      <!--表格-->
      <el-row >
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
            style="width: 100%;min-height: 300px"
            @selection-change="handleSelectionChange">
            <template v-for="(item,index) in tcolumn">
              <el-table-column
                :label="item.label"
                :width="item.width"
                :type="item.type"
                :prop="item.prop"
                :sortable="item.sort"
                :formatter="item.format"
              ></el-table-column>
            </template>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button size="mini"
                           type="info"
                           icon="el-icon-message"
                           circle
                           @click="handleDetail(scope.$index, scope.row)">
                  详情
                </el-button>
                <el-button size="mini" type="primary" icon="el-icon-edit" circle
                           @click="handleEdit(scope.$index, scope.row)">编辑
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <!--分页-->
      <el-row type="flex" style="height: 40px">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <div style="text-align: right">
            <el-pagination
              background
              :total="total"
              :page-size="pageSize"
              :current-page="currentPage"
              layout="total,prev, pager, next,jumper"
              @current-change="pageChange"
            >
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
            return {
                searchTab: {
                    title: '',
                    createTime: '',
                    keyWord: []
                },
                tableData: [],
                tcolumn: [
                    {
                        label: '',
                        width: '55',
                        type: 'selection',
                        prop: ''
                    }, {
                        label: '标题',
                        width: '120',
                        type: '',
                        prop: 'title',
                        sort: true
                    }, {
                        label: '摘要',
                        width: '120',
                        type: '',
                        prop: 'summary',
                        sort: true
                    }, {
                        label: '发布状态',
                        width: '180',
                        type: '',
                        sort: true,
                        prop: 'keyWord'
                    }, {
                        label: '内容',
                        width: '120',
                        type: '',
                        prop: 'content'
                    }, {
                        label: '点击量',
                        width: '120',
                        sort: true,
                        type: '',
                        prop: 'click'
                    }, {
                        label: '创建日期',
                        width: '',
                        type: '',
                        sort: true,
                        prop: 'createTime',
                        format: this.commons.dateFormat
                    }
                ],
                rowSelections: [],
                total: 0,
                pageSize: 10,
                currentPage: 1
            }
        },
        /*created() {
            this.selectData();
        },*/
        methods: {
            selectData() {
                let data = this.searchTab;
                let searchParam = {
                    "pageSize": this.pageSize,
                    "pageNum": this.currentPage,
                    "data": data
                };
                let _this = this;
                _this.$axios.post('/article/search', searchParam)
                    .then(function (response) {
                        _this.tableData = response.data.data;
                        _this.total = response.data.count
                    })
                    .catch(function (error) {
                    });
            },
            pageChange(val) {
                // 当前页改变，重新查询
                this.currentPage = val;
                this.selectData();
                console.info(val);
            },
            handleSelectionChange(val) {
                this.rowSelections = val;
            },
            handleAdd() {
              this.$router.push('/sys/home/article/publish/add');
            },
            handleEdit(index,row) {
                  this.$router.push('/sys/home/article/publish/'+row.uniqueId);
            },

            handleDelete() {
                if (this.rowSelections.length < 1) {
                    this.commons.kMessage("请选择至少一条数据！", 'info');
                } else {
                    let primaryKey = [];
                    this.rowSelections.forEach(function (item) {
                        primaryKey.push(item.uniqueId);
                    });
                    let _this = this;
                    this.$axios.post("/article/delete", {primaryKey})
                        .then(function (response) {
                            _this.commons.kMessage("删除文章成功！", 'success');
                            _this.currentPage = 1;
                            _this.selectData();
                        })
                        .catch(function (error) {
                        });
                }
            },

            handleDetail(index,row){
                this.$router.push('/sys/home/article/detail/'+row.uniqueId);
            },

            iconUpload() {
                if (this.rowSelections.length !== 1) {
                    this.commons.kMessage("请选择一条数据！", 'info');
                } else {

                }
            },
            resetForm() {
                this.searchTab = {
                    title: '',
                    createTime: '',
                    keyWord: []
                };
            },
            onQuery() {
                this.currentPage = 1;
                this.selectData();
            }
        }
    }
</script>

<style scoped>

</style>
