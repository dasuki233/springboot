<!--我发布的岗位-->

<template>
  <div>
    <!--    顶部搜索按钮-->
    <div>
      <el-input
          placeholder="请输入岗位名称" v-model="jobname" suffix-icon="el-icon-search" style="width: 200px" size="small"
          @keyup.enter.native="loadGet"></el-input>
      <el-button type="primary" style="margin-left: 15px; margin-bottom: 5px" @click="loadGet">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>
    <div>
      <el-table :data="tableData"
                :header-cell-style="{ background: 'lightblue', color: '#555555'}"
                border>
        <el-table-column prop="jobid" label="id" width="60" sortable>
        </el-table-column>
        <el-table-column prop="jobname" label="岗位名称" width="180" sortable>
        </el-table-column>
        <el-table-column prop="description" label="岗位介绍" width="359" sortable>
        </el-table-column>
        <el-table-column prop="counts" label="招聘人数" width="120" sortable>
        </el-table-column>
        <el-table-column prop="jdate" label="岗位发布时间" width="180" sortable>
        </el-table-column>
        <el-table-column prop="overTime" label="截止时间" width="180" sortable>
        </el-table-column>
        <el-table-column label="附件" width="140">
          <template slot-scope="scopes">
            <a v-if="scopes.row.files == null">无</a>
            <el-button v-if="scopes.row.files != null" type="primary" size="small" style="padding: 10px 20px"
                       @click="shout((scopes.row.files))">
              <a>下载</a>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column prop="ss" label="操作">
          <template slot-scope="scope">
            <el-button size="small" type="success" @click="modParam(scope.row)">申请</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--      分页----------->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>

      <!--      审核弹窗-->
    </div>

    <!--    新增页面-->
    <el-dialog
        title="提示"
        :before-close="handleClose"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="formDataRules" :model="form" label-width="100px">
        <el-form-item label="申请理由" prop="no">
          <el-input type="textarea" v-model="form.remark" :autosize="{ minRows: 20, maxRows: 30}"
                    style="width: 400px"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="closeView">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>
    <!--    编辑页面-->

  </div>

</template>

<script>
export default {
  name: "PostReview",
  data() {
    return {
      pageSize: 10,
      pageNum: 1,
      total: 0,
      tableData: [],
      jobname: '',
      centerDialogVisible: false,
      form: {
        sid: JSON.parse(sessionStorage.getItem('CurUser')).sid,
        jobid: "",
        remark:""
      },
      formDataRules: {
        remark: [
          {required: true, message: "请输入申请理由", trigger: "blur"}
        ],
      }
    }

  },
  methods: {
    //取消
    closeView() {
      this.centerDialogVisible = false
    },
    save(){
      this.$axios.post(this.$HTTPURL + '/joinjob/studentJob', this.form).then(res => res.data).then(res => {
        // console.log(res)
        if (res.code === 200) {
          this.$message({
            message: '成功!',
            type: 'success',
          })
          this.centerDialogVisible = false
          console.log(res)
        } else {
          this.$message({
            message: '失败!',
            type: 'error',
          })
        }
      })
    },

    //上传表单
    realSaveForm() {
      console.log('测速')
      this.$axios.post(this.$HTTPURL + '/job/updateForm', this.form).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '操作成功!',
            type: 'success',
          })
          this.resetForm() //清空表单
        } else {
          this.$message({
            message: '失败!',
            type: 'error',
          })
        }
      })
    },

    //重置表单
    resetForm() {
      let F5form;
      F5form = {
        uid: JSON.parse(sessionStorage.getItem('CurUser')).sid,
        jobid: '',
      }
      this.form = F5form
      this.filecheck = '-1' //filecheck在上传文件后被改为0了,所以每次上传改回来
      this.loadGet()
    },

    //保存前验证表单
    saveParam() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // if(this.form.files === '-1'){ //默认赋值-1,如果是-1,把file转为空值
          console.log('保存前的验证:')
          console.log(this.filecheck)
          if (this.filecheck == '-1') { //如果是-1说明没上传文件,如果是0说明上传了,就提交,0的话先sumbmit一下
            console.log('-1')
            this.openFullScreen2() //loading
            this.realSaveForm()
          } else {
            this.$refs.upload.submit() //提交 在handle-success里使用提交,避免文件还未命名,表单就提交了
            console.log('提交了')
            this.openFullScreen2() //loading
            // this.realSaveForm()
          }
        }
      })
    },


    //编辑信息
    modParam(row) {
      console.log(row)
      //赋值到表单
      this.form.jobid = row.jobid
      this.centerDialogVisible = true

    },

    //附件下载
    shout(filename) {
      var a = document.createElement("a")
      a.href = 'http://localhost:8081/job/download?path=' + filename
      a.click()
      a.remove()
    },

    //加载数据
    loadGet() {
      this.$axios.post(this.$HTTPURL + '/joinjob/StudentJobList', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          jobname: this.jobname,
          tid: JSON.parse(sessionStorage.getItem('CurUser')).id + ""
        }
      }).then(res => res.data).then(res => {
        // console.log(res)
        if (res.code === 200) {
          this.tableData = res.data
          this.total = res.total
          console.log(res)
        } else {
          console.log('获取数据失败')
        }
        // this.tableData = res
      })
    },
    formatter(row, column) {
      return row.address;
    },
    //分页处理
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      //翻页bug 不会返回第一页
      this.pageNum = 1
      this.pageSize = val
      this.loadGet()
    }
    ,
    handleCurrentChange(val) {
      this.pageNum = val
      this.loadGet()
      console.log(`当前页: ${val}`);
    },
    //重置按钮
    resetParam() {
      this.jobname = ""
      this.pageNum = 1
      this.pageSize = 10
      this.loadGet()
    },

    updateStates() {
      // console.log(this.modId)
      this.$axios.post(this.$HTTPURL + '/job/mod', {'jobid': this.modId}).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            showClose: true,
            message: '操作成功!',
            type: 'success',
          })
          this.modId = ''
          this.loadGet()
        } else {
          this.$message({
            showClose: true,
            message: '操作失败!',
            type: 'error',
          })
        }
      })
    },


    // 关闭弹窗提示
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },

    //完成上传触发的钩子
    handlePreview(file) {
      // console.log("handlePreview")
      // console.log(file);
    },
    //删除
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    //判断文件数量
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    //删除前确认
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    //前端点击上传按钮,选择本地文件后调用
    handleChange(file, fileList) {
      console.log("-------------handleChange")
      console.log(file)
      console.log(fileList)
      this.filecheck = '0'
      console.log("-------------handleChange")
    },
    //上传成功的回调
    handle_success(res) {
      console.log('-------------------success')
      // console.log(res.data)
      this.form.files = res.data
      this.$message.success("附件上传成功")
      console.log('-------------------success')
      this.realSaveForm()
      // this.$refs.upload.clearFiles()
    },
//loading
    openFullScreen2() {
      const loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      setTimeout(() => {
        loading.close();
      }, 2000);
    },

  },
  beforeMount() {
    this.loadGet()
  }
}


</script>

<style scoped>
.btns {
  width: 180px;
  margin: 0 auto;
}
</style>