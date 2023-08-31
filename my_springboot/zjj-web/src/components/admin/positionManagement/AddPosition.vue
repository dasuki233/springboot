<!--新增岗位-->
<template>
  <div class="addBody">
    <div class="addDiv">
      <div class="add-content">
        <h1 class="add-title">新增岗位</h1>
        <el-form ref="form" :rules="formDataRules" :model="form" label-width="80px">
          <el-form-item label="岗位名称" prop="jobname">
            <el-input style="width: 400px;" v-model="form.jobname"></el-input>
          </el-form-item>
          <el-form-item label="限制人数" prop="counts">
            <el-input-number v-model="form.counts"></el-input-number>
          </el-form-item>
          <el-form-item label="截止时间" prop="overTime">
            <el-date-picker
                v-model="form.overTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <!--            action= "http://localhost:8081/job/upload"-->
          <el-form-item label="附件">
            <el-upload
                v-model="form.files"
                ref="upload"
                class="upload-demo"
                action="http://localhost:8081/job/upload"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :before-remove="beforeRemove"
                :auto-upload="false"
                :on-change="handleChange"
                :on-success="handle_success"
                multiple
                :limit="1"
                :on-exceed="handleExceed">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">文件不得超过30MB</div>
            </el-upload>
          </el-form-item>
          <el-form-item label="岗位介绍" prop="description">
            <el-input type="textarea" v-model="form.description" :autosize="{ minRows: this.textMinRows, maxRows: this.textMaxRows}"
                      style="width: 400px"></el-input>
          </el-form-item>
          <div class="btns">
            <el-button type="success" @click="saveParam">提交</el-button>
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
      filecheck:'-1', //检查是否上传文件
      fileList: [],
      textMinRows: 5,
      textMaxRows: 10,
      form: {
        files: '',
        jobname: '',
        counts: 1,
        description: '',
        tid: '',
        adminid: JSON.parse(sessionStorage.getItem('CurUser')).id,
        overTime: '',
      },
      formDataRules: {
        jobname: [
          {required: true, message: "请输入岗位名称", trigger: "blur"}
        ],
        counts: [
          {required: true, message: "限制人数禁止为空", trigger: "blur"}
        ],
        description: [
          {required: true, message: "请输入岗位介绍", trigger: "blur"}
        ],
        overTime: [
          {required: true, message: "截止时间禁止为空", trigger: "blur"}
        ],
      }
    }
  },
  methods: {
    //保存前验证表单
    saveParam() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          // if(this.form.files === '-1'){ //默认赋值-1,如果是-1,把file转为空值
          console.log('保存前的验证:')
          console.log(this.filecheck)
          if (this.filecheck == '-1'){ //如果是-1说明没上传文件,如果是0说明上传了,就提交,0的话先sumbmit一下
            console.log('-1')
            this.openFullScreen2() //loading
            this.realSaveForm()
          }else {
            this.$refs.upload.submit() //提交 在handle-success里使用提交,避免文件还未命名,表单就提交了
            console.log('提交了')
            this.openFullScreen2() //loading
            // this.realSaveForm()
          }
        }
      })
    },

    //重置表单
    resetForm() {
      let F5form;
      F5form = {files: '',
        jobname: '',
        counts: 1,
        description: '',
        tid: '',
        adminid: JSON.parse(sessionStorage.getItem('CurUser')).id,
        overTime: '',}
      this.form = F5form
      this.filecheck = '-1' //filecheck在上传文件后被改为0了,所以每次上传改回来
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

      this.$axios.post(this.$HTTPURL + '/job/saveForm', this.form).then(res => res.data).then(res => {
        if (res.code === 200) {
          this.$message({
            message: '操作成功!',
            type: 'success',
          })
          this.resetForm() //清空表单
          this.$refs.upload.clearFiles() //清空上传文件
        } else {
          this.$message({
            message: '失败!',
            type: 'error',
          })
        }
      })
    },
    // //保存按钮的点击事件
    // saveForm() {
    //   // this.openFullScreen2() //加载loading
    //   this.$refs.upload.submit() //图片上传
    //   this.openFullScreen2() //加载loading
    //   this.saveParam()
    //   //
    // },
    //上传成功的回调
    handle_success(res) {
      console.log('-------------------success')
      // console.log(res.data)
      this.form.files = res.data
      this.$message.success("附件上传成功")
      console.log('-------------------success')
      this.realSaveForm()
      // this.$refs.upload.clearFiles()
    },
    //前端点击上传按钮,选择本地文件后调用
    handleChange(file, fileList) {
      console.log("-------------handleChange")
      console.log(file)
      console.log(fileList)
      this.filecheck = '0'
      console.log("-------------handleChange")
    },
    //上传前文件大小验证
    beforeUpload(file) {
      if (file.type != "" || file.type != null || file.type != undefined) {
        //计算文件的大小
        const isLt5M = file.size / 1024 / 1024 < 30; //这里做文件大小限制
        //如果大于30M
        if (!isLt5M) {
          this.$showMessage('上传文件大小不能超过 30MB!');
          return false;
        }
      }
    },
    //完成上传触发的钩子
    handlePreview(file) {
      // console.log("handlePreview")
      // console.log(file);
    },
    //删除
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    //判断文件数量
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    //删除前确认
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
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