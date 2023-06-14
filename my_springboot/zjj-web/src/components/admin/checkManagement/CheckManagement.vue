<!--管理员发工资-->
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
          placeholder="请输入岗位名称" v-model="jobname" suffix-icon="el-icon-search" style="width: 200px; margin-left: 10px"
          size="small"
          @keyup.enter.native="loadGet"></el-input>
      <el-date-picker
          style="margin-left: 10px"
          v-model="value1"
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
      <el-button type="primary" style="margin-left: 15px; margin-bottom: 5px" @click="addParam">新增</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: 'lightblue', color: '#555555'}"
              border>
      <el-table-column prop="checkid" label="id" width="60" sortable>
      </el-table-column>
      <el-table-column prop="jobname" label="岗位名称" width="180" sortable>
      </el-table-column>
      <el-table-column prop="no" label="学号" width="140" sortable>
      </el-table-column>
      <el-table-column prop="name" label="学生姓名" width="120" sortable>
      </el-table-column>
      <el-table-column prop="clazz" label="学生班级" width="150" sortable>
      </el-table-column>
      <el-table-column prop="jdate" label="考勤时间" width="200" sortable>
      </el-table-column>
      <el-table-column prop="checkes" label="上班时间" width="120" sortable>
      </el-table-column>
      <el-table-column prop="leavess" label="请假/迟到时间" width="140" sortable>
      </el-table-column>
      <el-table-column prop="ss" label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="modParam(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="deleteParam(scope.row.checkid)" style="margin-left: 10px">
            <el-button slot="reference" size="small" type="danger">删除</el-button>
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
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="岗位" prop="jobid">
          <el-col :span="22">
            <el-select v-model="form.jobid"  placeholder="请选择学院">
              <el-option
                  v-for="item in jobs"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="工作时间" prop="checkes">
          <el-col :span="20">
            <el-input v-model="form.checkes"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="请假/迟到时间" prop="leavess">
          <el-col :span="20">
            <el-input v-model="form.leavess"></el-input>
          </el-col>
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
  name: "CheckManagement",
  data: function(){
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.sid) {
        return callback();
      }
      this.$axios.post(this.$HTTPURL + "/check/checkNo", this.form).then(res => res.data).then(res => {
        // console.log(res.code)
        if (res.code == 200) {
          callback()
        } else {
          callback(new Error('学号不存在'))
        }
      })
    };
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
      pageSize: 10,
      states: [],
      pageNum: 1,
      total: 0,
      no: "",
      name: "",
      jobname: "",
      jobs: [], //工作岗位的list
      centerDialogVisible: false,
      form: {
        "checkid": "",
        "sid": "",
        "no": "",
        "jobid": "",
        "checkes": "",
        "leavess": "",

      },
      value1:"",
      jobid2: '',
      jobid1: '',
      tableData: [],
      formDataRules: {
        checkes: [
          {required: true, message: "请输入时间", trigger: "blur"},
        ],
        leavess: [
          {required: true, message: "请输入时间", trigger: "blur"},
        ],
        jobid: [
          {required: true, message: "请选择岗位", trigger: "blur"},
        ],
        no: [
          {required: true, message: "请输入学号", trigger: "blur"},
          {validator: checkDuplicate, trigger: 'blur'}
        ],
      },
    }
  },
  methods: {
    //获取group by 的院系信息
    loadAcademy() {
      this.$axios.get(this.$HTTPURL + '/check/findAllJob', {}).then(res => res.data).then(res => {
        if (res.code === 200) {
          for (let i = 0; i < res.data.length; i++) {
            // console.log(res.data)
            this.states.push(res.data[i])
          }
          console.log(res.data)
          this.mounted()
          // console.log(this.states)
        } else {
          console.log('获取数据失败')
        }
        // this.tableData = res
      })
    },
    mounted() {
      this.jobs = this.states.map(item => {
        return {value: `${item.jobid}`, label: `${item.jobname}`};
      });
    },
    //新增保存到数据库
    saveParam() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          //如果id存在说明是修改
          if (this.form.checkid) {
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
    //删除信息
    deleteParam(id) {
      this.$axios.post(this.$HTTPURL + '/check/delete', {"checkid": id}).then(res => res.data).then(res => {
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
    //保存
    doSava() {

      this.$axios.post(this.$HTTPURL + '/check/save', this.form).then(res => res.data).then(res => {
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
      if(this.jobid1 === this.form.jobid){
        this.form.jobid = this.jobid2
      }
      this.$axios.post(this.$HTTPURL + '/check/update', this.form).then(res => res.data).then(res => {
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
    //解决新增后页面不重置问题
    resetForm() {
      let F5form;
      F5form = {
        "checkid": "",
        "sid": "",
        "no": "",
        "jobid": "",
        "checkes": "",
        "leavess": ""
      }
      this.form = F5form
    },
    //新增按钮
    addParam() {
      this.centerDialogVisible = true
      this.resetForm()
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
    //取消
    closeView() {
      this.centerDialogVisible = false
    },
    //赋值
    modParam(row) {
      console.log(row)
      //赋值到表单
      this.form.checkid = row.checkid
      this.form.sid = row.sid
      this.form.no = row.no
      this.form.jobid = row.jobname
      this.form.checkes = row.checkes
      this.form.leavess = row.leavess
      this.jobid2 = row.jobid
      this.jobid1 = row.jobname
      this.centerDialogVisible = true
    },

    //加载数据
    loadGet() {
      this.$axios.post(this.$HTTPURL + '/check/checkList', {
        // this.$axios.post(this.$HTTPURL + '/user/list', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          no: this.no,
          jobname: this.jobname,
          name: this.name,
          starttime:this.value1[0],
          endtime:this.value1[1]
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
      this.value1 = ''
      this.loadGet()
    },
  },
  beforeMount() {
    this.loadGet()
    this.loadAcademy()
  }
}
</script>

<style scoped>

</style>