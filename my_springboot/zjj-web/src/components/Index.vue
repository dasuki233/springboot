<template>

  <el-container style="height: 100%;">
    <el-aside :width="asideWidth" style="background-color: rgb(238, 241, 246); margin-left: -1px">
      <!--      左边菜单-->

      <!--      左菜单栏-->
      <Aside :isCollapse="isCollapse"  v-if="" style="width: 100vm"></Aside>

    </el-aside>

    <el-container style="height: 100vh;">
      <el-header style="text-align: right; font-size: 12px; border-bottom: darkgray 1px solid">
        <!--        header-->
        <Header @doCollapse="doCollapse" :icon="icon"></Header>
      </el-header>

      <el-main style="height: 100%">
        <!--        内容-->
        <!--        <Main></Main>-->

        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: "Index",
  components: {Aside, Header},
  data() {
    return {
      isCollapse: false,
      asideWidth: '64',
      icon: 'el-icon-s-fold'
    }
  },
  methods: {
    doCollapse() {
      this.isCollapse = !this.isCollapse
      if (!this.isCollapse) {
        //展开菜单
        this.asideWidth = '200px'
        this.icon = 'el-icon-s-fold'
      } else {
        //收起菜单
        this.asideWidth = '65px'
        this.icon = 'el-icon-s-unfold'
      }
    }
  }
}
</script>

<style scoped>
.el-header {
  /*background-color: #B3C0D1;*/
  color: #333;
  line-height: 60px;
}
el-container{
 border-top: none;
}

.el-main {
  padding: 5px;
}

.el-aside {
  color: #333;
}
</style>