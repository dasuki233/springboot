<!--留言管理-->
<template>
  <div>
    <!--    顶部搜索按钮-->
    <div>
      <el-input
          placeholder="请输入学生姓名" v-model="name" suffix-icon="el-icon-search" style="width: 200px" size="small"
          @keyup.enter.native="loadGet"></el-input>
      <el-input
          placeholder="请输入学号" v-model="no" suffix-icon="el-icon-search" style="width: 200px; margin-left: 10px"
          size="small"
          @keyup.enter.native="loadGet"></el-input>
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
      <el-table-column prop="text" label="留言内容" width="300">
      </el-table-column>
      <el-table-column prop="msgDate" label="留言时间" width="180" sortable>
      </el-table-column>
      <el-table-column prop="answer" label="回复" width="300">
      </el-table-column>
      <el-table-column prop="answerDate" label="回复时间" width="180" sortable>
      </el-table-column>
      <el-table-column prop="ss" label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="modParam(scope.row)">回 复</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="deleteParam(scope.row.id)" style="margin-left: 10px">
            <el-button slot="reference" size="small" type="danger">删 除</el-button>
          </el-popconfirm>
        </template>
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

    <!--    新增页面-->
    <el-dialog
        title="提示"
        :before-close="handleClose"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="formDataRules" :model="form" label-width="100px">
        <el-form-item label="学号" prop="no">
          <el-input type="textarea" v-model="form.answer" :autosize="{ minRows: 20, maxRows: 30}"
                    style="width: 400px"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="closeView">取 消</el-button>
    <el-button type="primary" @click="doMod">确 定</el-button>
  </span>
    </el-dialog>
  </div>
  <!--    编辑页面-->
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
      name: '',
      no: '',
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

    //打开表单赋值
    modParam(row) {
      this.form.answer = row.answer
      this.form.id = row.id
      this.centerDialogVisible = true
    },

    //删除留言
    deleteParam(id) {
      this.$axios.post(this.$HTTPURL + '/message/delete', {"id": id}).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '删除成功!',
            type: 'success',
          });
          this.loadGet()
        } else {
          this.$message({
            message: '删除失败!',
            type: 'error',
          })
        }
      })
    },

    //回复留言
    doMod() {
      this.$axios.post(this.$HTTPURL + '/message/answer', this.form).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '回复留言成功!',
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
    //解决新增后页面不重置问题
    resetForm() {
      let F5form;
      F5form = {
        id: "",
        answer: ""
      }
      this.form = F5form
    },
//加载数据
    loadGet() {
      this.$axios.post(this.$HTTPURL + '/message/msgList', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,

        param: {
          no: this.no,
          name: this.name,
          title:this.title
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

    //新增按钮关闭弹窗提示
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
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
      this.name = ""
      this.jobname = ""
      this.no = ""
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