<template>
  <el-menu :default-openeds="['1', '3']" background-color="#304156"
           text-color="#fff"
           active-text-color="#0088ff"
           default-active="/Home"
           :collapse="isCollapse"
           :collapse-transition="false"
           style="height: 100%; width: 100%"
           router
  >
    <!--    <el-menu-item index="/Home">-->
    <!--      <i class="el-icon-s-home"></i>-->
    <!--      <span>首页</span>-->
    <!--    </el-menu-item>-->
    <!--    动态路由  level:index等级, menuIcon:图标, superMenuName:一级标题, menuClick:地址, menuName:二级菜单地址-->

    <el-menu-item index="/Home">
      <i class="el-icon-s-home"></i>
      <span>首页</span>
    </el-menu-item>

    <!--    多级菜单-->
    <template v-for="item in menu">
    <template v-if="item.childListsss.length > 1 || isCollapse">
    <el-submenu :index="item.menulevel">
      <template slot="title">
        <i :class="item.menuicon"></i>
        <span slot="title">{{item.menuname}}</span>
      </template>
      <el-menu-item-group>
        <el-menu-item :index="'/' + item1.menucomponent" v-for="item1 in item.childListsss">{{item1.menuname}}</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    </template>

<!--    一级菜单-->
    <template v-else>
    <el-menu-item :index="'/' + item.menucomponent">
      <i :class="item.menuicon"></i>
      <span slot="title">{{item.menuname}}</span>
    </el-menu-item>
    </template>
    </template>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      // isCollapse: false


    }
  },
  created() {
    console.log(this.$store.state.menu)

  }
  ,
  computed: {
    "menu": {
      get() {
        // this.menu11 = this.$store.state.menu
        console.log("this.$store.state.menu")
        console.log(this.$store.state.menu)
        return this.$store.state.menu
      }
    }
  },
  props: {
    isCollapse: Boolean
  },
  components: {},

}
</script>

<style scoped>

</style>