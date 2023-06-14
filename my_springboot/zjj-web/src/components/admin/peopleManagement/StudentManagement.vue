<template>
  <div>
    <!--    顶部搜索按钮-->
    <div>
      <el-input
          placeholder="请输入名字" v-model="name" suffix-icon="el-icon-search" style="width: 200px" size="small"
          @keyup.enter.native="loadGet"></el-input>
      <el-select v-model="collegesid" filterable placeholder="请选择学院" style="margin-left: 10px" size="small">
        <el-option
            v-for="item in collegesids"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-select v-model="clazzid" filterable placeholder="请选择班级" style="margin-left: 10px" size="small"
                 @click.native="loadClazzList">
        <el-option
            v-for="item in clazzs"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
      <el-button type="primary" style="margin-left: 15px; margin-bottom: 5px" @click="loadGet">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>
      <el-button type="primary" style="margin-left: 15px; margin-bottom: 5px" @click="addParam">新增</el-button>
    </div>
<!--    顶部搜索结束-->

<!--    页面展示-->
    <el-table :data="tableData" :default-sort="{prop:'id', order:'ascending'}"
              :header-cell-style="{ background: 'lightblue', color: '#555555'}"
              border>
      <el-table-column prop="sid" label="id" width="60" sortable>
      </el-table-column>
      <el-table-column prop="no" label="账号" width="180" sortable>
      </el-table-column>
      <el-table-column prop="password" label="密码" width="120" sortable>
      </el-table-column>
      <el-table-column prop="name" label="姓名" width="130" sortable>
      </el-table-column>
      <el-table-column prop="age" label="年龄 " width="80" sortable>
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="80" :formatter="formatter" sortable>
        <template slot-scope="scope">
          <el-tag :type="scope.row.sex === 1 ? 'primary' : 'success'"
                  disable-transitions>{{ scope.row.sex === 1 ? '男' : '女' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="120">
      </el-table-column>
      <el-table-column prop="cname" label="学院" width="180" sortable>
      </el-table-column>
      <el-table-column prop="clazz" label="班级" width="240" sortable>
      </el-table-column>
      <el-table-column prop="operate" label="操作">
        <template slot-scope="scope">
          <el-button size="small" type="success" @click="modParam(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="deleteParam(scope.row.sid)" style="margin-left: 10px">
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
        <el-form-item label="账号" prop="no">
          <el-col :span="20">
            <el-input v-model="form.no"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-col :span="20">
            <el-input v-model="form.password"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-col :span="20">
            <el-input v-model="form.age"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="form.sex">
            <el-radio label="1">男</el-radio>
            <el-radio label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-col :span="20">
            <el-input v-model="form.phone"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="班级" prop="cname">
          <el-select v-model="form.clazzid" placeholder="请选择班级" style="width: 260px">
            <el-option
                v-for="item in allClazz"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
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
  name: "StudentManagement",
  data: function () {
    let checkAge = (rule, value, callback) => {
      if (value > 150) {
        callback(new Error('年龄输⼊过⼤'));
      } else {
        callback();
      }
    };
    //检查账号唯一性
    let checkDuplicate = (rule, value, callback) => {
      if (this.form.sid) {
        return callback();
      }
      this.$axios.get(this.$HTTPURL + "/student/findByNo?no=" + this.form.no).then(res => res.data).then(res => {
        // console.log(res.code)
        if (res.code !== 200) {
          callback()
        } else {
          // console.log("账号已经存在")
          callback(new Error('账号已经存在'))
        }
      })
    };

    return {
      tableData: [],
      pageSize: 10,
      pageNum: 1,
      total: 0,
      name: "",
      value: [],
      collegesid: "",//院系
      collegesids: [],
      clazzid: '', //班级
      clazzs: [], //班级搜索框options
      loading: false,
      states: [], //院系
      states1: [], //班级根据院系
      states2: [], //所有班级
      allClazz: [], //所有班级用
      allclazz: "",
      centerDialogVisible: false,
      form: {
        sid: "",
        no: "",
        name: "",
        age: "",
        phone: "",
        password: "",
        sex: "",
        clazzid: "",
        clazzid1: "",
        clazzid2: ""
      },
      formDataRules: {
        no: [
          {required: true, message: "请输入学号", trigger: "blur"},
          {min: 6, max: 12, message: "长度在6-12个字符", trigger: "blur"},
          {validator: checkDuplicate, trigger: 'blur'}
        ],
        sex: [
          {required: true, message: "请选择性别", trigger: "blur"}
        ],
        name: [
          {required: true, message: "请输入姓名", trigger: "blur"},
        ],
        password: [
          {required: true, message: "密码不能为空", trigger: "blur"},
          {min: 8, max: 16, message: "长度在8-16个字符", trigger: "blur"}
        ],
        age: [
          {required: true, message: '请输⼊年龄', trigger: 'blur'},
          {min: 1, max: 3, message: '⻓度在 1 到 3 个位', trigger: 'blur'},
          {pattern: /^([1-9][0-9]*){1,3}$/, message: '年龄必须为正整数字', trigger: "blur"},
          {validator: checkAge, trigger: 'blur'}
        ],
        phone: [
          {required: true, message: "⼿机号不能为空", trigger: "blur"},
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输⼊正确的⼿机号码", trigger:
                "blur"
          }
        ],
        collegesid: [
          {required: true, message: "院系不能为空", trigger: "blur"}
        ]
      }
    }
  },
  methods: {
    //编辑信息
    modParam(row) {
      console.log(row)
      //赋值到表单
      this.form.sid = row.sid
      this.form.no = row.no
      this.form.name = row.name
      this.form.password = row.password
      this.form.age = row.age + ''
      this.form.phone = row.phone
      this.form.sex = row.sex + ''
      this.form.clazzid = row.cname
      this.form.clazzid1 = row.clazzid
      this.form.clazzid2 = row.cname
      // 编辑窗口弹窗
      this.centerDialogVisible = true
    },

    //删除信息
    deleteParam(id) {
      // console.log(this.$HTTPURL + '/user/del/' + id)
      // id = '' + id
      this.$axios.post(this.$HTTPURL + '/student/del/' + id).then(res => res.data).then(res => {
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

    //获取group by 的院系信息
    loadAcademy() {
      this.$axios.get(this.$HTTPURL + '/user/academyList', {}).then(res => res.data).then(res => {
        if (res.code === 200) {
          for (let i = 0; i < res.data.length; i++) {
            // console.log(res.data)
            this.states.push(res.data[i])
          }
          // console.log(this.states)
        } else {
          console.log('获取数据失败')
        }
        // this.tableData = res
      })
      //加载所有班级
      this.$axios.get(this.$HTTPURL + '/student/findAllClazz', {}).then(res => res.data).then(res => {
        if (res.code === 200) {
          for (let i = 0; i < res.data.length; i++) {
            // console.log(res.data)
            this.states2.push(res.data[i])
          }
          this.mounted()
          // console.log(this.states)
        } else {
          console.log('获取数据失败')
        }
        // this.tableData = res
      })
    },

    //班级搜索框点击事件
    loadClazzList() {
      this.loadClazz()
    },
    //班级groupby搜索框
    loadClazz() {
      if (this.collegesid.length >= 1) {
        this.$axios.get(this.$HTTPURL + '/student/findClazzList/' + this.collegesid).then(res => res.data).then(res => {
          if (res.code === 200) {
            this.states1 = []
            for (let i = 0; i < res.data.length; i++) {
              // console.log(res.data)
              this.states1.push(res.data[i])
            }
            console.log(res.data)
            console.log("res.data")
            this.mounted1()
            // console.log(this.states)
          } else {
            console.log('获取数据失败')
          }
          // this.tableData = res
        })
      }
    },
    mounted() {
      this.collegesids = this.states.map(item => {
        return {value: `${item.collegesid}`, label: `${item.cname}`};
      });
      this.allClazz = this.states2.map(item => {
        return {value: `${item.clzid}`, label: `${item.clazz}`};
      });
    },
    mounted1() {
      this.clazzs = []
      console.log("this.clazzs")
      console.log(this.clazzs)
      this.clazzs = this.states1.map(item => {
        return {value: `${item.clzid}`, label: `${item.clazz}`};
      });
    },
    //加载数据
    loadGet() {
      this.$axios.post(this.$HTTPURL + '/student/listPageC1', {
        // this.$axios.post(this.$HTTPURL + '/user/list', {
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        param: {
          name: this.name,
          cid: this.collegesid,
          clazzId: this.clazzid
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


    //院系搜索框
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.academys = this.academys.filter(item => {
            return item.label.toLowerCase()
                .indexOf(query.toLowerCase()) > -1;
          });
        }, 200);
      } else {
        this.academys = [];
      }
      console.log(this.academys)
    },
    //班级搜索框
    remoteMethod1(query) {
      if (query !== '') {
        this.loading = true;
        setTimeout(() => {
          this.loading = false;
          this.clazzs = this.clazzs.filter(item => {
            return item.label.toLowerCase()
                .indexOf(query.toLowerCase()) > -1;
          });
        }, 200);
      } else {
        this.clazzs = [];
      }
      console.log(this.clazzs)
    },
    //解决新增后页面不重置问题
    resetForm() {
      let F5form;
      F5form = {no: "", name: "", age: "", phone: "", password: "", sex: "", clazzid: ""}
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
    }
    ,
//新增页面点击取消关闭页面
    closeView() {
      this.centerDialogVisible = false
    }
    ,
    //保存
    doSava() {
      this.$axios.post(this.$HTTPURL + '/student/save', this.form).then(res => res.data).then(res => {
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
      if (this.form.clazzid === this.form.clazzid2)
        this.form.clazzid = this.form.clazzid1
      this.$axios.post(this.$HTTPURL + '/student/update', this.form).then(res => res.data).then(res => {
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


//重置按钮
    resetParam() {
      this.name = ""
      this.collegesid = ""
      this.clazzid = ""
      this.states1 = []
      this.states2 = []
      this.allClazz = []
      this.clazzs = []
      this.pageNum = 1
      this.pageSize = 10
      this.loadGet()
    }
    ,

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
  },
  beforeMount() {
    this.loadAcademy();
    // this.loadClazz();
    this.loadGet();
    this.mounted();
    // this.mounted1();
  }
  ,
  created() {

  }

}

</script>

<style scoped>

</style>