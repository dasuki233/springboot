<template>

  <div style="display: flex; line-height: 60px">
    <div style="cursor: pointer">
      <i :class="icon" style="font-size: 28px; margin-top: 17px" @click="collapse"></i>
    </div>
    <div style="flex: 1; text-align: left; font-size: 24px; margin-left:20px">
      <span>财院勤工助学管理系统</span>
    </div>
    <el-dropdown>
      <i class="el-icon-arrow-down" style="margin-right: 10px"></i>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center;">
        <el-dropdown-item @click.native="toLogout">退出</el-dropdown-item>
      </el-dropdown-menu>
      <span>{{ user.name }}</span>
    </el-dropdown>

  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  props: {
    icon: String
  },
  methods: {
    toUser() {
      this.$router.push('/Home')
    },
    toLogout() {
      // alert('Logout')
      this.$confirm("确定退出登录吗? ", "提示", {
        confirmButtonText: "确定", //确认按钮的文字显示
        type: "warning",
        center: true,//文字居中显示
      })
          .then(() => {
            this.$message({
              type:"success",
              message:"退出登录成功"
            })
            sessionStorage.clear()
            this.$router.push("/")
          })
          .catch(() => {
            this.$message({
              type: "success",
              message: "已取消退出登录"
            })
          })
    },
    collapse() {
      this.$emit('doCollapse')
    },
  },
  created(){
    this.$router.push('/Home')
  }
}
</script>

<style scoped>

</style>