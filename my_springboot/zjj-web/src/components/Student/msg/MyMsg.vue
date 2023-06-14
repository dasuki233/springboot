<!--我的留言-->
<!--留言管理-->
<template>
  <div>
    <!--    顶部搜索按钮-->
    <div>
      <el-input
      placeholder="请输入标题" v-model="title" suffix-icon="el-icon-search" style="width: 200px; margin-left: 10px"
      size="small"
      @keyup.enter.native="loadGet"></el-input>
      <el-button type="primary" style="margin-left: 15px; margin-bottom: 5px" @click="loadGet">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: 'lightblue', color: '#555555'}"
              border>
      <el-table-column prop="id" label="id" width="60" sortable>
      </el-table-column>
      <el-table-column prop="no" label="学号" width="180" sortable>
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="140" sortable>
      </el-table-column>
      <el-table-column prop="title" label="标题" width="180" sortable>
      </el-table-column>
      <el-table-column prop="text" label="留言内容" width="350">
      </el-table-column>
      <el-table-column prop="msgDate" label="留言时间" width="180" sortable>
      </el-table-column>
      <el-table-column prop="answer" label="回复内容" width="350">
      </el-table-column>
      <el-table-column prop="answerDate" label="回复时间"  sortable>
      </el-table-column>
    </el-table>

    <!--        分页-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

  </div>
</template>

<script>
export default {
  name: "Msg",
  data() {
    return {
      pageSize: 10,
      states: [],
      pageNum: 1,
      total: 0,
      title:'',
      centerDialogVisible: false,
      tableData: [],
      form: {
        id: "",
        answer: ""
      },
      formDataRules: {
        answer: [
          {required: true, message: "请输入回复内容", trigger: "blur"},
        ],
      },

    }
  },
  methods: {
//加载数据
    loadGet() {
      this.$axios.post(this.$HTTPURL + '/message/stuLookMsg', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          title:this.title,
          sid:JSON.parse(sessionStorage.getItem('CurUser')).sid,
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
    },
    //重置按钮
    resetParam() {
      this.title = ""
      this.pageNum = 1
      this.pageSize = 10
      this.loadGet()
    },
//取消
    closeView() {
      this.centerDialogVisible = false
    },


  },
  beforeMount() {
    this.loadGet()
  }
}
</script>

<style scoped>

</style>