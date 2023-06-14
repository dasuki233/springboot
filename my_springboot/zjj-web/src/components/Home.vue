<template>
  <el-row>
    <el-col :span="8">
      <div class="grid-content bg-purple"></div>
    </el-col>
    <el-col :span="16">
      <div class="grid-content bg-purple-light"></div>
    </el-col>
  </el-row>
</template>

<script>

export default {
  name: "Home",
  components: {},
  data() {

    return {
      user: {}
    }
  },
  computed: {},
  methods: {
    init() {
      this.user = JSON.parse(sessionStorage.getItem('CurUser'))
    }
  },
  created() {
    this.init()
    console.log(this.user)
  }
}
</script>

<style scoped>
.el-descriptions {
  width: 90%;

  margin: 0 auto;
  text-align: center;
}
</style>