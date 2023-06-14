<!--管理员发工资-->
<template>
  <div>
    <!--    顶部搜索按钮-->
    <div>
      <el-input
          placeholder="请输入岗位名称" v-model="jobname" suffix-icon="el-icon-search" style="width: 200px; margin-left: 10px"
          size="small"
          @keyup.enter.native="loadGet"></el-input>
      <el-date-picker
          style="margin-left: 10px"
          v-model="value2"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
          size="small">
      </el-date-picker>
      <el-button type="primary" style="margin-left: 15px; margin-bottom: 5px" @click="loadGet">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: 'lightblue', color: '#555555'}"
              border>
      <el-table-column prop="saralyid" label="id" width="60" sortable>
      </el-table-column>
      <el-table-column prop="jobname" label="岗位名称" width="180" sortable>
      </el-table-column>
      <el-table-column prop="no" label="学号" width="140" sortable>
      </el-table-column>
      <el-table-column prop="name" label="学生姓名" width="120" sortable>
      </el-table-column>
      <el-table-column prop="clazz" label="学生班级" width="300" sortable>
      </el-table-column>
      <el-table-column prop="sdate" label="发放时间" width="300" sortable>
      </el-table-column>
      <el-table-column prop="money" label="工资发放情况" sortable>
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
  name: "SalaryUpdate",
  data() {

    return {
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      value2: '', //时间
      pageSize: 10,
      pageNum: 1,
      total: 0,
      no: "",
      jobname: "",
      centerDialogVisible: false,
      form: {
        saralyid: "",
        no: "",
        money: ""
      },
      payform: {
        salaryid: "",
        money: 0
      },
      tableData: [],
      formDataRules: {
        money: [
          {required: true, message: "请输入金额", trigger: "blur"},
        ],
      },
    }
  },
  methods: {
    //新增按钮关闭弹窗提示
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
      this.$axios.post(this.$HTTPURL + '/saraly/studentSalary', {
        // this.$axios.post(this.$HTTPURL + '/user/list', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          jobname: this.jobname,
          starttime:this.value2[0],
          endtime:this.value2[1],
          sid:JSON.parse(sessionStorage.getItem('CurUser')).sid
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
      this.value2 = ''
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