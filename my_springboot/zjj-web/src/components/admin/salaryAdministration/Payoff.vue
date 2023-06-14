<!--管理员发工资-->
<template>
  <div>
  <!--    顶部搜索按钮-->
  <div>
    <el-input
        placeholder="请输入名字" v-model="name" suffix-icon="el-icon-search" style="width: 200px" size="small"
        @keyup.enter.native="loadGet"></el-input>
    <el-input
        placeholder="请输入学号"  v-model="no" suffix-icon="el-icon-search" style="width: 200px; margin-left: 10px" size="small"
        @keyup.enter.native="loadGet"></el-input>
    <el-input
        placeholder="请输入岗位名称"  v-model="jobname" suffix-icon="el-icon-search" style="width: 200px; margin-left: 10px" size="small"
        @keyup.enter.native="loadGet"></el-input>
    <el-button type="primary" style="margin-left: 15px; margin-bottom: 5px" @click="loadGet">查询</el-button>
    <el-button type="success" @click="resetParam">重置</el-button>
    </div>
  <el-table :data="tableData"
            :header-cell-style="{ background: 'lightblue', color: '#555555'}"
            border>
    <el-table-column prop="jobid" label="id" width="60" sortable>
    </el-table-column>
    <el-table-column prop="jobname" label="岗位名称" width="180" sortable>
    </el-table-column>
    <el-table-column prop="no" label="学号" width="140" sortable>
    </el-table-column>
    <el-table-column prop="name" label="学生姓名" width="120" sortable>
    </el-table-column>
    <el-table-column prop="clazz" label="学生班级" width="300" sortable>
    </el-table-column>
    <el-table-column prop="ss" label="操作">
      <template slot-scope="scope">
      <el-button type="success"  style="margin-left: 10px"  size="small" @click="modParam(scope.row)">发放工资</el-button>

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

    <!--    工资界面-->
    <el-dialog
        title="提示"
        :visible.sync="centerDialogVisible"
        :before-close="handleClose"
        width="30%"
        center>
      <el-form ref="payform" :rules="formDataRules" :model="payform" label-width="100px">
        <el-form-item label="工资金额" prop="money">
          <el-col :span="20">
            <el-input v-model="payform.money"></el-input>
          </el-col>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="closeView">取 消</el-button>
    <el-button type="primary" @click="pay">确 定</el-button>
      </span>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: "Payoff",
  data() {

    return {
      pageSize: 10,
      pageNum: 1,
      total: 0,
      no: "",
      name: "",
      jobname: "",
      centerDialogVisible: false,
      form: {
        saralyid: "",
        no: "",
        money: ""
      },
      payform:{
        jobid:"",
        money:0,
        sid:""
      },
      tableData: [],
      formDataRules: {
        money: [
          {required: true, message: "请输入金额", trigger: "blur"},
        ],
      },
    }
  },
  methods:{
    //新增按钮关闭弹窗提示
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {
          });
    },
    //不发
    closeView() {
      this.centerDialogVisible = false
    },
    //发工资
    modParam(row) {
      console.log(row)
      //赋值到表单
      this.payform.jobid = row.jobid
      this.payform.sid = row.sid
      this.centerDialogVisible = true
    },
    pay(){
      this.$axios.post(this.$HTTPURL + '/saraly/pay', this.payform).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '发放成功!',
            type: 'success',
          });
          this.payform.money = 0
          this.centerDialogVisible = false
          }
        else {
          this.$message({
            message: '发放失败!',
            type: 'error',
          })
          this.centerDialogVisible = false
        }
        // this.tableData = res
      })
    },
    //加载数据
    loadGet() {
      this.$axios.post(this.$HTTPURL + '/saraly/paySaralyList', {
        // this.$axios.post(this.$HTTPURL + '/user/list', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          no: this.no,
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
    },

    //重置按钮
    resetParam() {
      this.name = ""
      this.jobname = ""
      this.no = ""
      this.pageNum = 1
      this.pageSize = 10
      this.loadGet()
    },
  },
  beforeMount() {
    this.loadGet()
  }
}
</script>

<style scoped>

</style>