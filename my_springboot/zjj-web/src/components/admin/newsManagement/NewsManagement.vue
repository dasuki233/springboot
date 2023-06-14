<!--管理员公告管理-->
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
    <el-table-column prop="mains" label="内容" width="300" sortable>
    </el-table-column>
    <el-table-column prop="file" label="附件" width="180" sortable>
      <template slot-scope="scopes" >
        <a v-if="scopes.row.file == null">无</a>
        <el-button v-if="scopes.row.file != null" type="primary" size="small" style="padding: 10px 20px" @click="shout((scopes.row.file))">
          <a>下载</a>
        </el-button>
      </template>
    </el-table-column>
    <el-table-column prop="newsdate" label="发布时间" width="300" sortable>
    </el-table-column>
    <el-table-column prop="ss" label="操作">
      <template slot-scope="scope">
        <el-button size="small" type="success" @click="modParam(scope.row)">编辑</el-button>
        <el-popconfirm title="确定删除吗？" @confirm="deleteParam(scope.row.newsid)" style="margin-left: 10px">
          <el-button slot="reference" size="small" type="danger">删除</el-button>
        </el-popconfirm>
      </template>
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

    <!--    新增页面-->
    <el-dialog
        title="提示"
        :before-close="handleClose"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="formDataRules" :model="form" label-width="100px">
        <el-form-item label="标题" prop="title">
          <el-col :span="20">
            <el-input v-model="form.title"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="内容" prop="mains">
          <el-input type="textarea" v-model="form.mains" :autosize="{ minRows: 20, maxRows: 20}"
                    style="width: 310px"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="closeView">取 消</el-button>
    <el-button type="primary" @click="saveParam">确 定</el-button>
  </span>
    </el-dialog>

    <!--    编辑页面-->
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
      form: {
        newid:'',
        title: "",
        mains: "",
        file: "",
        newsdate: "",
      },
      formDataRules: {
        title: [
          {required: true, message: "请输入标题", trigger: "blur"}
        ],
        mains: [
          {required: true, message: "请输入内容", trigger: "blur"}
        ],
      }

      //return over
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
    //新增保存到数据库
    saveParam() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          //如果id存在说明是修改
          if (this.form.sid) {
            this.doMod();
          } else {
            this.doSava();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    },
    //新增页面点击取消关闭页面
    closeView() {
      this.centerDialogVisible = false
    },
    //编辑信息
    modParam(row) {
      console.log(row)
      //赋值到表单
      this.form.newsid = row.newsid
      this.form.title = row.title
      this.form.file = row.file
      this.form.mains = row.mains
      this.form.newsdate = row.newsdate
      // 编辑窗口弹窗
      this.centerDialogVisible = true
    },
    //新增编辑按钮关闭弹窗提示
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
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
    //删除信息
    deleteParam(id) {
      this.$axios.post(this.$HTTPURL + '/news/del', {"newsid":id}).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '删除成功!',
            type: 'success',
          });
          this.loadGet()
        } else {
          this.$message({
            message: '失败!',
            type: 'error',
          })
        }
      })
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