<!--修改个人资料-->
<template>
  <div class="addBody">
    <div class="addDiv">
      <div class="add-content">
        <h1 class="add-title">修改个人资料</h1>
        <el-form ref="form" :rules="formDataRules" :model="form" label-width="80px">
          <el-form-item label="账号" prop="no">
            <el-input readonly v-model="form.no" style="width: 200px"></el-input>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input readonly v-model="form.name" style="width: 200px"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input type="password" v-model="form.password" style="width: 200px"></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="form.phone" style="width: 200px"></el-input>
          </el-form-item>
          <div class="btns">
            <el-button type="success" style="width: 80px" @click="saveParam">提 交</el-button>
            <el-button type="info" style="width: 80px; margin-left: 20px" @click="resetParam">重 置</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "EditInformation",
  data() {
    return {
      form: {
        id:"",
        no: '',
        phone: '',
        password: '',
        name: ''
      },
      formDataRules: {
        password: [
          {required: true, message: "限制人数禁止为空", trigger: "blur"}
        ],
        phone: [
          {required: true, message: "请输入岗位介绍", trigger: "blur"}
        ],
      }
    }
  },
  methods: {
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
    },
    //上传表单
    saveParam() {
      this.openFullScreen2()
      this.$axios.post(this.$HTTPURL + '/user/updateForSomeone', this.form).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '操作成功!',
            type: 'success',
          })
        } else {
          this.$message({
            message: '失败!',
            type: 'error',
          })
        }
      })
    },

    resetParam() {
      this.form.phone = ''
      this.form.password = ''
    },

    loadGet() {
      console.log('123123123123')
      this.$axios.post(this.$HTTPURL + '/user/findForSomeone', {id:JSON.parse(sessionStorage.getItem('CurUser')).id,}).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.form = res.data[0]
          // console.log(res)
        } else {
          console.log('获取数据失败')
        }
        // this.tableData = res
      })
    },
  },
  beforeMount() {
    this.loadGet()
  }
}
</script>

<style scoped>
.addBody {
  display: flex;
  justify-content: center;
}

.addDiv {
  left: 0;
  right: 0;
  width: 320px;
  padding: 1px 35px 15px;
  margin: 200px auto;
  border: 1px solid #ebeef5;
}

.add-title {
  margin: 20px 0;
  text-align: center;
}

.btns {
  width: 180px;
  margin: 0 auto;
}
</style>