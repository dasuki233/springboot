<!--新闻-->
<template>

  <div>
    <!--  顶部搜索-->
    <div>
      <el-input
          placeholder="请输入标题" v-model="title" suffix-icon="el-icon-search" style="width: 200px" size="small"
          @keyup.enter.native="loadGet"></el-input>
      <el-button type="primary" style="margin-left: 15px; margin-bottom: 5px" @click="loadGet">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>


    <!--表格显示-->
    <el-table :data="tableData"
              :header-cell-style="{ background: 'lightblue', color: '#555555'}"
              border>
      <el-table-column prop="newsid" label="id" width="60" sortable>
      </el-table-column>
      <el-table-column prop="title" label="新闻公告标题" width="180" sortable>
      </el-table-column>
      <el-table-column prop="mains" label="内容" width="600" sortable>
      </el-table-column>
      <el-table-column prop="file" label="附件" width="180" sortable>
        <template slot-scope="scopes" >
          <a v-if="scopes.row.file == null">无</a>
          <el-button v-if="scopes.row.file != null" type="primary" size="small" style="padding: 10px 20px" @click="shout((scopes.row.file))">
            <a>下载</a>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column prop="newsdate" label="发布时间" sortable>
      </el-table-column>
    </el-table>

    <!--    分页栏-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>
    <!--    分页栏-->

  </div>
</template>

<script>
export default {
  name: "NewsManagement",
  data() {

    return {
      centerDialogVisible: false,
      tableData: [], //数据
      title:"", //标题搜索
      pageSize: 10,
      pageNum: 1,
      total: 0,
    }

  },
  methods:{
    //附件下载
    shout(filename){
      var a = document.createElement("a")
      a.href = 'http://localhost:8081/job/download?path=' + filename
      a.click()
      a.remove()
    },

    //加载数据
    loadGet() {
      this.$axios.post(this.$HTTPURL + '/news/newsList', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          title: this.title
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
    //保存
    doSava() {
      this.$axios.post(this.$HTTPURL + '/news/update', this.form).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '操作成功!',
            type: 'success',
          });
          this.centerDialogVisible = false
          this.loadGet()
        } else {
          this.$message({
            message: '失败!',
            type: 'error',
          })
        }
      })
    },
    //修改
    doMod() {
      this.$axios.post(this.$HTTPURL + '/news/update', this.form).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '操作成功!',
            type: 'success',
          });
          this.centerDialogVisible = false
          this.loadGet()
        } else {
          this.$message({
            message: '失败!',
            type: 'error',
          })
        }
      })
    },

    //重置按钮
    resetParam() {
      this.title = ""
      this.pageNum = 1
      this.pageSize = 10
      this.loadGet()
    },
//分页处理
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      //翻页bug 不会返回第一页
      this.pageNum = 1
      this.pageSize = val
      this.loadGet()
    },
    handleCurrentChange(val) {
      this.pageNum = val
      this.loadGet()
      console.log(`当前页: ${val}`);
    },
    //  methods over
  },
  beforeMount() {
    this.loadGet()
  }

}
</script>

<style scoped>

</style>