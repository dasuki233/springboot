<!--岗位管理-->
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
        <el-table-column prop="description" label="岗位介绍" width="420" sortable>
        </el-table-column>
        <el-table-column prop="counts" label="招聘人数" width="120" sortable>
        </el-table-column>
        <el-table-column prop="states" label="申请状态" width="120" sortable>
          <template slot-scope="scope">
            <el-tag :type="scope.row.states === '未审核' ? 'danger' : (scope.row.states === '进行中' ? 'success' : 'info')">
              {{scope.row.states}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="jdate" label="岗位发布时间" width="180" sortable>
        </el-table-column>
        <el-table-column prop="overTime" label="截止时间" width="180" sortable>
        </el-table-column>
        <el-table-column  label="附件" width="140" >
        <template slot-scope="scopes" >
          <a v-if="scopes.row.files == null">无</a>
<!--          <a v-if="scopes.row.files != null"  type="primary" href="http://localhost:8081/job/download?path=">下载文件</a>-->
          <el-button v-if="scopes.row.files != null" type="primary" size="small" style="padding: 10px 20px" @click="shout((scopes.row.files))">
            <a>下载</a>
          </el-button>
<!--          <el-link :href="require('@/assets/files/' + scopes.row.files)">{{scopes.row.files}}</el-link>-->
        </template>
        </el-table-column>
        <el-table-column prop="ss" label="操作">
          <template slot-scope="scope">
          <el-popconfirm
              confirm-button-text='通过'
              cancel-button-text='不通过'
              icon="el-icon-info"
              icon-color="red"
              @confirm="updateStates"
              @cancel="notSuccess"
              title="是否通过审核？"
          >
            <el-button slot="reference" size="small" type="success" @click="getModId(scope.row.jobid)">审 核</el-button>
          </el-popconfirm>
            <el-popconfirm
                confirm-button-text='好的'
                cancel-button-text='不用了'
                icon="el-icon-info"
                icon-color="red"
                @confirm="delJob"
                title="确定删除该岗位吗？"
            >
              <el-button style="margin-left: 10px" slot="reference" size="small" type="primary" @click="getModId(scope.row.jobid)">删 除</el-button>
            </el-popconfirm>
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
  name: "PostReview",
  data() {
    return {
      pageSize: 10,
      pageNum: 1,
      total: 0,
      tableData: [],
      jobname:'',
      modId: '',
    }

  },
  methods: {

    //附件下载
    shout(filename){
      var a = document.createElement("a")
      a.href = 'http://localhost:8081/job/download?path=' + filename
      a.click()
      a.remove()
    },

    //加载数据
    loadGet() {
      this.$axios.post(this.$HTTPURL + '/job/jobList', {
        // this.$axios.post(this.$HTTPURL + '/user/list', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          jobname: this.jobname
        }
        // name:"小明"
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
    //审核通过
    getModId(id) {
      this.modId = id
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
    //审核不通过
    notSuccess() {
      // console.log(this.modId)
      this.$axios.post(this.$HTTPURL + '/job/modNotSuccess', {'jobid': this.modId}).then(res => res.data).then(res => {
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
    //删除岗位
    delJob(id){
      this.$axios.post(this.$HTTPURL + '/job/delJob', {"jobid":this.modId}).then(res => res.data).then(res => {
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
  },
  beforeMount(){
    this.loadGet()
  }
}


</script>

<style scoped>

</style>