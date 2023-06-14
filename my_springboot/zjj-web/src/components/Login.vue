<template>
  <div class="headers">
    <div class="login-header">
      <div class="login-logo">
      </div>
      <div class="login-header-title">
        勤工助学管理系统
      </div>
    </div>
    <div class="loginBody">
      <div class="loginDiv">
        <div class="login-content">
          <h2 class="login-title" style="color: #ffe3e3;  ">用户登录</h2>
          <el-form :model="loginForm" label-width="100px"
                   :rules="rules" ref="loginForm">

            <el-form-item label="" prop="no" class="inputBox">
              <el-input style="width: 300px" type="text" v-model="loginForm.no"
                        autocomplete="off">
                <template slot="prepend"><i style="font-size:20px" class="el-icon-user"></i></template>
              </el-input>
            </el-form-item>
            <el-form-item label="" prop="password" class="inputBox">
              <el-input style="width: 300px" type="password" v-model="loginForm.password"
                        show-password autocomplete="off" @keyup.enter.native="confirm">
                <template slot="prepend"><i style="font-size:20px" class="el-icon-key"></i></template>
              </el-input>
            </el-form-item>
            <el-form-item prop="roleId" style="margin-left: 7%; ">
              <el-radio-group v-model="loginForm.roleId">
                <el-radio class="rad" :label="0">管理员</el-radio>
                <el-radio class="rad" :label="1">教师</el-radio>
                <el-radio class="rad" :label="2">学生</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item style="margin-top: 10px">
              <el-button type="primary" id="successBtn" @click="confirm" :disabled="confirm_disabled">确 定</el-button>
            </el-form-item>

          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  name: "Login",
  data: function () {
    return {
      confirm_disabled: false,
      loginForm: {
        no: '',
        password: '',
        roleId: ''
      },
      rules: {
        no: [
          {required: true, message: '请输入账号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输密码', trigger: 'blur'}
        ],
        roleId: [
          {required: true, message: '请选择角色', trigger: 'blur'},
        ]
      }
    }
  },
  methods: {

    //重置
    resetForm() {
      this.loginForm.no = ''
      this.loginForm.password = ''
      this.loginForm.roleId = ''

    },

    //登录
    confirm() {
      this.confirm_disabled = true;
      this.$refs.loginForm.validate((valid) => {
        if (valid) { //valid成功为true，失败为false
          //去后台验证用户名密码
          this.$axios.post(this.$HTTPURL + '/user/login', this.loginForm).then(res => res.data).then(res => {
            if (res.code === 200) {
              //存储
              console.log('res.data.user')
              console.log(res.data)
              sessionStorage.setItem("CurUser", JSON.stringify(res.data.user[0]))
              this.$store.commit("setMenu", res.data.menu)
              // console.log(res.data.menu)
              //跳转到主页
              this.$router.replace('/Index');
            } else {
              this.confirm_disabled = false;
              alert('校验失败，用户名或密码错误！');
              this.resetForm()
              return false;
            }
          });
        } else {
          this.confirm_disabled = false;
          console.log('校验失败');
          this.resetForm()
          return false;
        }
      });

    }
  }
}
</script>

<style scoped>
.headers {
  width: 100%;
  height: 100%
}
.login-header-title{
  float: left;
  margin-top: 21px;
  color: #fff;
  font-size: 26px;
  font-weight: bold;
  letter-spacing: 2px;
  padding: 5px 10px;
  margin-left: 10px;
  border-left: 3px solid #fff;
}
.login-header {
  width: 100%;
  height: 90px;
  background: rgb(179 148 148 / 30%);
  position: absolute;
}
.login-logo {
  margin-top: 15px;
  margin-left: 40px;
  margin-right: 16px;
  width: 270px;
  height: 60px;
  background: url("../assets/logo.png");
  background-size: 100% 100%;
  float: left;
}
.loginBody {
  background: url("../assets/bg1.jpg");
  background-size: 100% 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  flex-direction: column;
}

.loginDiv {
  position: relative;
  width: 520px;
  height: 330px;
  background: #1c1c1c;
  border-radius: 8px;
  overflow: hidden;
  opacity: 0.77;
  /*position: absolute;*/
  /*top: 50%;*/
  /*left: 50%;*/
  /*margin-top: -200px;*/
  /*margin-left: -250px;*/
  /*width: 560px;*/
  /*height: 360px;*/
  /*background: #fff;*/
  /*border-radius: 5%;*/
}

.loginDiv::before {
  content: '';
  z-index: 1;
  position: absolute;
  top: -50%;
  left: -50%;
  width: 380px;
  height: 420px;
  transform-origin: bottom right;
  background: linear-gradient(0deg, transparent, #45f3ff, #45f3ff);
  animation: animate 6s linear infinite;
}

.loginDiv::after {
  content: '';
  z-index: 1;
  position: absolute;
  top: -50%;
  left: -50%;
  width: 380px;
  height: 420px;
  transform-origin: bottom right;
  background: linear-gradient(0deg, transparent, #45f3ff, #45f3ff);
  animation: animate 6s linear infinite;
  animation-delay: -3s;
}

@keyframes animate {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(540deg);
  }
}

.login-title {
  margin: 12px 0;
  text-align: center;

}

.login-content {
  position: absolute;
  inset: 2px;
  background: #28292d;
  padding: 10px 20px;
  border-radius: 8px;
  z-index: 2;
  display: flex;
  flex-direction: column;
}

.rad {
  color: #ffe3e3
}

#successBtn {
  width: 82%;
  font-size: 18px;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
  border-radius: 6px;
  border: none;
  font-weight: bold;
}
</style>