<!--我发布的岗位-->
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
        <el-table-column prop="joinjobid" label="id" width="60" sortable>
        </el-table-column>
        <el-table-column prop="jobname" label="岗位名称" width="180" sortable>
        </el-table-column>
        <el-table-column prop="no" label="学生学号" width="150" sortable>
        </el-table-column>
        <el-table-column prop="name" label="学生姓名" width="120" sortable>
        </el-table-column>
        <el-table-column prop="remark" label="申请理由" width="500" sortable>
        </el-table-column>
        <el-table-column prop="jobdate" label="申请时间" width="180" sortable>
        </el-table-column>
        <el-table-column prop="states" label="申请状态" sortable>
          <template slot-scope="scope">
            <el-tag :type="scope.row.states === '未审核' ? 'danger' : (scope.row.states === '审核已通过' ? 'success' : 'info')">
              {{scope.row.states}}</el-tag>
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
  </div>

</template>

<script>
export default {
  name: "CheckPosition",
  data() {
    return {
      pageSize: 10,
      pageNum: 1,
      total: 0,
      tableData: [],
      jobname: '', //岗位搜索
      name: '', //姓名搜索
    }
  },
  methods: {

    //加载数据
    loadGet() {
      this.$axios.post(this.$HTTPURL + '/joinjob/joinjobStudent', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          sid: JSON.parse(sessionStorage.getItem('CurUser')).sid,
          jobname: this.jobname,
          name: this.name
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
    }
    ,
    //重置按钮
    resetParam() {
      this.jobname = ""
      this.name = ""
      this.pageNum = 1
      this.pageSize = 10
      this.loadGet()
    }
    ,

    updateStates() {
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
    }
    ,

    //删除岗位
    delJob(id) {
      this.$axios.post(this.$HTTPURL + '/job/delJob', {"jobid": this.modId}).then(res => res.data).then(res => {
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
    }
    ,

    // 关闭弹窗提示
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    }
    ,

    //完成上传触发的钩子
    handlePreview(file) {
      // console.log("handlePreview")
      // console.log(file);
    }
    ,
    //删除
    handleRemove(file, fileList) {
      console.log(file, fileList);
    }
    ,
    //判断文件数量
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    }
    ,
    //删除前确认
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    }
    ,
    //前端点击上传按钮,选择本地文件后调用
    handleChange(file, fileList) {
      console.log("-------------handleChange")
      console.log(file)
      console.log(fileList)
      this.filecheck = '0'
      console.log("-------------handleChange")
    }
    ,
    //上传成功的回调
    handle_success(res) {
      console.log('-------------------success')
      // console.log(res.data)
      this.form.files = res.data
      this.$message.success("附件上传成功")
      console.log('-------------------success')
      this.realSaveForm()
      // this.$refs.upload.clearFiles()
    }
    ,
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
    }
    ,

  }
  ,

  beforeMount() {
    this.loadGet()
  }
}


</script>

<style scoped>
</style>