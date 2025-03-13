<template>
  <div class="dashboard-editor-container home">

    <div style="margin-top: 10px"></div>
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="count" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              任务总数
            </div>
            <count-to :start-val="0" :end-val="Number(taskCount)" :duration="2600"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="peoples" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              客户总数
            </div>
            <count-to :start-val="0" :end-val="Number(clientCount)" :duration="3000"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="money" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              交易总额
            </div>
            <count-to :start-val="0" :end-val="Number(dealCount)" :duration="3200"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shopping">
            <svg-icon icon-class="shopping" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              需求总数
            </div>
            <count-to :start-val="0" :end-val="Number(demandCount)" :duration="3600"
                      class="card-panel-num"
            />
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart-component :chart-data="dealAndDemandInfo" :chart-name="'需求和交易'"/>
    </el-row>

    <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <pie-chart :chart-data="taskTodayData" chart-name="今日任务"/>
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="12">
        <div class="chart-wrapper">
          <my-only-bar-chart :bar-data="dealPriceByDay" chart-name="交易额"></my-only-bar-chart>
        </div>
      </el-col>
    </el-row>


  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'
import CountTo from 'vue-count-to'
import {
  getClientCount, getDealAndDemandByDay,
  getDealCount,
  getDealPriceByDay,
  getDemandCount,
  getTaskCount,
  getTaskToday
} from '@/api/manage/statics'
import LineChartComponent from '@/views/dashboard/LineChartComponent.vue'
import MyOnlyBarChart from '@/views/dashboard/MyOnlyBarChart.vue'

const lineChartData = {
  newVisitis: {
    expectedData: [100, 120, 161, 134, 105, 160, 165],
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  messages: {
    expectedData: [200, 192, 120, 144, 160, 130, 140],
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    expectedData: [80, 100, 121, 104, 105, 90, 100],
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  shoppings: {
    expectedData: [130, 140, 141, 142, 145, 150, 160],
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}

export default {
  name: 'Index',
  components: {
    MyOnlyBarChart,
    LineChartComponent,
    CountTo,
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      lineChartData: lineChartData.newVisitis,
      clientCount: 0,
      taskCount: 0,
      demandCount: 0,
      dealCount: 0,
      taskTodayData: [],
      dealPriceByDay: {},
      dealAndDemandInfo: {}
    }
  },
  created() {
    this.getTaskCountInfo()
    this.getClientCountInfo()
    this.getDemandCountInfo()
    this.getDealCountInfo()
    this.getTaskTodayDataInfo()
    this.getDealPriceByDayInfo()
    this.getDealAndDemandInfo()
  },
  methods: {
    getTaskCountInfo() {
      getTaskCount().then(response => {
        this.taskCount = response.data.count
      })
    },
    getClientCountInfo() {
      getClientCount().then(response => {
        this.clientCount = response.data.count
      })
    },
    getDemandCountInfo() {
      getDemandCount().then(response => {
        this.demandCount = response.data.count
      })
    },
    getDealCountInfo() {
      getDealCount().then(response => {
        this.dealCount = response.data.count
      })
    },
    getTaskTodayDataInfo() {
      getTaskToday().then(response => {
        this.taskTodayData = response.data
      })
    },
    getDealPriceByDayInfo() {
      getDealPriceByDay().then(response => {
        this.dealPriceByDay = response.data
      })
    },
    getDealAndDemandInfo() {
      getDealAndDemandByDay().then(res => {
        this.dealAndDemandInfo = res.data
      })
    },
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    }
  }
}
</script>

<style lang="scss" scoped>
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
