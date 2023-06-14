<!--留言-->
<!--新增岗位-->
<template>
  <div class="addBody">
    <div class="addDiv">
      <div class="add-content">
        <h1 class="add-title">留言</h1>
        <el-form ref="form" :rules="formDataRules" :model="form" label-width="80px">
          <el-form-item label="标题" prop="title">
            <el-input v-model="form.title" placeholder="请输入内容"></el-input>
          </el-form-item>
          <el-form-item label="留言内容" prop="text">
            <el-input type="textarea" v-model="form.text"
                      :autosize="{ minRows: this.textMinRows, maxRows: this.textMaxRows}"
                      style="width: 400px"></el-input>
          </el-form-item>
          <div class="btns">
            <el-button type="success" @click="realSaveForm">提交</el-button>
            <el-button type="info" style="margin-left: 25px; " @click="resetForm">取消</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AddPosition",
  data() {
    return {
      textMinRows: 25,
      textMaxRows: 25,
      form: {
        title: '',
        text: '',
        sid: JSON.parse(sessionStorage.getItem('CurUser')).sid,
      },
      formDataRules: {
        title: [
          {required: true, message: "请输入标题", trigger: "blur"}
        ],
        text: [
          {required: true, message: "请输入留言内容", trigger: "blur"}
        ],
      }
    }
  },
  methods: {

    //重置表单
    resetForm() {
      let F5form;
      F5form = {
        title: '',
        text: '',
        sid: JSON.parse(sessionStorage.getItem('CurUser')).sid,
      }
      this.form = F5form
    },
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
    realSaveForm() {
      this.$axios.post(this.$HTTPURL + '/message/studentMsg', this.form).then(res => res.data).then(res => {
        this.openFullScreen2()
        if (res.code === 200) {
          this.$message({
            message: '操作成功!',
            type: 'success',
          })
          this.resetForm() //清空表单
        } else {
          this.$message({
            message: '失败!',
            type: 'error',
          })
        }
      })
    },

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
  width: 520px;
  padding: 1px 35px 15px;
  margin: 50px auto;
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