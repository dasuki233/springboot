<!--考勤情况-->
<template>
  <el-row>
    <!-- 左侧第一个盒子，占据 12 栅格 -->
    <el-col :span="12">
      <div class="box1">
<!--        <div ref="echarts1" style="height: 100%; width: 100%!important">-->
<!--        </div>-->
        <h3 style="text-align: center">本周考勤情况</h3>
        <el-table :data="tableData" :default-sort="{prop:'id', order:'ascending'}"
                  :header-cell-style="{ background: 'lightblue', color: '#555555'}"
                  border style="height: 400px; overflow: auto;" >
          <el-table-column prop="name" label="姓名" width="200" sortable>
          </el-table-column>
          <el-table-column prop="checkes" label="出勤时间(小时)" width="250" sortable>
          </el-table-column>
          <el-table-column prop="leavess" label="请假/迟到时间(小时)"  sortable>
          </el-table-column>
        </el-table>
      </div>
      <div class="box2">
        <div ref="echarts2" style="height: 100%; width: 100%!important"></div>
      </div>
    </el-col>
    <!-- 右侧盒子，占据 24 栅格 -->
    <el-col :span="12">
      <div class="box3">
        <div ref="echarts3" style="height: 100%; width: 100%!important"></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import * as echarts from '../../../assets/echarts.min'

export default {
  name: "Check",
  data() {
    return {
      sid: "",
      tableData: [],
      tableData1: [],

    }
  },
  methods: {
    async loadEchartsData() {
      this.$axios.post(this.$HTTPURL + '/check/weekCheck', {
        // sid:this.sid
        sid: JSON.parse(sessionStorage.getItem('CurUser')).sid + ''
      }).then(res => res.data).then(res => {
        // console.log(res)
        if (res.code === 200) {

          this.tableData = res.data
          console.log("this.tableData:数据")
          console.log(this.tableData)
          // console.log(res.data)
          // console.log("tableData over")
          // this.loadEcharts1()
          // this.loadEcharts2()
        } else {
          console.log('获取数据失败')
        }
      })
    },

    async loadEchartsData1() {
      this.$axios.post(this.$HTTPURL + '/check/monthCheck', {
        // sid:this.sid
        sid: JSON.parse(sessionStorage.getItem('CurUser')).sid + ''
      }).then(res => res.data).then(res => {
        // console.log(res)
        if (res.code === 200) {
          this.tableData1 = res.data
          // console.log(res.data)
          // console.log("tableData over")
          // this.loadEcharts3()
          this.createEcharts()
        } else {
          console.log('获取数据失败')
        }
      })

    },

    // loadEcharts1() {
    //   // 准备echats dom
    //   const echarts1 = echarts.init(this.$refs.echarts1);
    //   // 配置数据
    //   const yData = this.tableData.map(item => item.checkes);
    //   const xData = this.tableData.map(item => {
    //     const date = new Date(item.jdate);
    //     const year = date.getFullYear();
    //     const month = ('0' + (date.getMonth() + 1)).slice(-2);
    //     const day = ('0' + date.getDate()).slice(-2);
    //     return `${year}-${month}-${day}`;
    //   });
    //   // console.log(xData)
    //   // console.log(yData)
    //   const Datas = {};
    //   for (let i = 0; i < xData.length; i++) {
    //     Datas[xData[i]] = yData[i];
    //   }
    //   // console.log(Datas)
    //
    //   const echart1Option = {
    //     xAxis: {
    //       type: 'category',
    //       data: xData
    //     },
    //     yAxis: {
    //       type: 'value',
    //       axisLabel: {
    //         formatter: '{value} 小时' // 添加单位“次”
    //       }
    //     },
    //     series: [{
    //       data: xData.map(key => Datas[key]),
    //       // data: yData,
    //       type: 'line',
    //       label: {
    //         show: true,
    //         position: 'top',
    //         formatter: '{c}小时'
    //       }
    //     }]
    //   };
    //   echart1Option.xAxis = {
    //     type: 'category',
    //     data: xData,
    //     axisLabel: {
    //       interval: 0, // 强制显示所有标签
    //       rotate: 30, // 设置标签旋转角度
    //     }
    //   };
    //   echart1Option.title = {
    //     text: '本周出勤情况',
    //     left: 'center'
    //   }
    //   echarts1.setOption(echart1Option);
    // },

    loadEcharts2() {
      // 准备echats dom
      echarts.registerTheme('myTheme', 'infographic');
      const echarts1 = echarts.init(this.$refs.echarts2, 'infographic');
      // 配置数据
      const yData = this.tableData.map(item => item.leavess);
      const yData1 = this.tableData.map(item => item.checkes);
      console.log("222")
      console.log(yData)
      console.log(yData1)
      const xData = this.tableData.map(item => {
        const date = new Date(item.jdate);
        const year = date.getFullYear();
        const month = ('0' + (date.getMonth() + 1)).slice(-2);
        const day = ('0' + date.getDate()).slice(-2);
        return `${year}-${month}-${day}`;
      });
      const Datas = {};
      for (let i = 0; i < xData.length; i++) {
        Datas[xData[i]] = yData[i];
      }
      const Datas1 = {};
      for (let i = 0; i < xData.length; i++) {
        Datas1[xData[i]] = yData1[i];
      }

      const echart1Option = {
        legend: {}, // 启用legend组件
        xAxis: {
          type: 'category',
          data: xData
        },
        yAxis: {
          type: 'value',
          position: 'left',
          axisLabel: {
            formatter: '{value} 小时', // 添加单位“次”

          }
        },
        grid: {
          top: 60, // 将表格向下移动60像素
        },
        series: [
          {
            name:"请假/迟到",
            data: xData.map(key => Datas[key]),
            // data: yData,
            type: 'line',
            position: 'left',
            label: {
              show: true,
              formatter: '{c}小时'
            }
          },
          {
            name:"出勤",
            data: xData.map(key => Datas1[key]),
            type: 'line',
            position: 'left',
            label: {
              show: true,
              formatter: '{c}小时'
            }
          }
        ]
      };
      echart1Option.xAxis = {
        type: 'category',
        data: xData,
        axisLabel: {
          interval: 0, // 强制显示所有标签
          rotate: 30, // 设置标签旋转角度
        }
      };
      echart1Option.title = {
        text: '本周考勤情况',
        right: 'center',
        padding: [30, 0, 0, 0]
      }
      echarts1.setOption(echart1Option);
    },

    loadEcharts3() {
      // 准备echats dom
      echarts.registerTheme('myTheme', 'infographic');
      const echarts1 = echarts.init(this.$refs.echarts3, 'infographic');
      // 配置数据
      const yData = this.tableData1.map(item => item.leavess);
      const yData1 = this.tableData1.map(item => item.checkes);
      const xData = this.tableData1.map(item => {
        const date = new Date(item.jdate);
        const year = date.getFullYear();
        const month = ('0' + (date.getMonth() + 1)).slice(-2);
        const day = ('0' + date.getDate()).slice(-2);
        return `${year}-${month}-${day}`;
      });
      console.log(xData)
      console.log(yData)
      console.log(yData1)
      const Datas = {};
      for (let i = 0; i < xData.length; i++) {
        Datas[xData[i]] = yData[i];
      }
      const Datas1 = {};
      for (let i = 0; i < xData.length; i++) {
        Datas1[xData[i]] = yData1[i];
      }
      // console.log(Datas)
      // console.log(Datas1)

      const echart1Option = {
        legend: {}, // 启用legend组件
        xAxis: {
          type: 'category',
          data: xData
        },
        yAxis: {
          type: 'value',
          position: 'left',
          axisLabel: {
            formatter: '{value} 小时', // 添加单位“次”

          }
        },
        grid: {
          top: 60, // 将表格向下移动60像素
        },
        series: [
          {
            name:"请假/迟到",
            data: xData.map(key => Datas[key]),
            // data: yData,
            type: 'line',
            position: 'left',
            label: {
              show: true,
              formatter: '{c}小时'
            }
          },
          {
            name:"出勤",
            data: xData.map(key => Datas1[key]),
            // data: yData,
            type: 'line',
            position: 'left',
            label: {
              show: true,
              formatter: '{c}小时'
            }
          }
        ]
      };
      echart1Option.xAxis = {
        type: 'category',
        data: xData,
        axisLabel: {
          interval: 0, // 强制显示所有标签
          rotate: 30, // 设置标签旋转角度
        }
      };
      echart1Option.title = {
        text: '本月考勤情况',
        right: 'center',
        padding: [30, 0, 0, 0]
      }
      echarts1.setOption(echart1Option);
    },

    createEcharts(){
      // this.loadEcharts1()
      this.loadEcharts2()
      this.loadEcharts3()
    }

  },
  beforeMount(){
    this.loadEchartsData(); // 等待数据加载完成
    console.log("等待数据加载完成")
    this.loadEchartsData1()
  },
  async mounted() {
    await this.loadEchartsData(); // 等待数据加载完成
    await this.loadEchartsData1(); // 等待数据加载完
    await this.createEcharts()
  },
  filters: {
    formatDate: function (value) {
      if (value) {
        const date = new Date(value)
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
      }
      return ''
    }
  },
}
</script>

<style scoped>
.box1 {
  margin-top: 20px;
  height: 400px;
  margin-left: 100px;
  margin-right: 30px;
}

/* 左侧第二个盒子 */
.box2 {
  height: 400px;
  margin-left: 100px;
  margin-top: 20px;
  margin-right: 30px;
}

/* 右侧盒子 */
.box3 {
  margin-top: 20px;
  height: 820px;
  margin-right: 30px;
}
</style>