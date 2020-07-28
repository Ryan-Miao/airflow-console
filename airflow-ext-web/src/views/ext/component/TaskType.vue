<template>
  <el-select
    v-model="selectVal"
    class="filter-item"
    placeholder="task type"
    filterable
    clearable
    @change="setVal"
  >
    <el-option-group
      v-for="group in plugins"
      :key="group.label"
      :label="group.label"
    >
      <el-option
        v-for="item in group.types"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      >
        <span>{{ item.value }}</span>
        <span style="color: #8492a6; font-size: 13px; padding-left: 10px;">
          {{ item.label }}
        </span>
      </el-option>
    </el-option-group>
  </el-select>

</template>

<script>

export default {
  props: {
    taskType: {
      type: String,
      required: false,
      default: ''
    }
  },
  data() {
    return {
      plugins: [
        {
          label: 'Operator任务',
          types: [
            {
              label: 'Bash脚本',
              value: 'BashOperator'
            },
            {
              label: 'Hive SQL',
              value: 'HiveOperator'
            },
            {
              label: '关系数据库入库hive',
              value: 'RDBMS2HiveOperator'
            },
            {
              label: 'hive出库到关系数据库',
              value: 'Hive2RDBMSOperator'
            },
            {
              label: 'Http请求',
              value: 'SimpleHttpOperator'
            }
          ]
        },
        {
          label: 'Sensor依赖',
          types: [
            {
              label: 'Http请求依赖',
              value: 'HttpSensor'
            },
            {
              label: '外部Dag Task依赖',
              value: 'ExternalTaskSensor'
            },
            {
              label: '罗盘任务依赖',
              value: 'CompassTaskSensor'
            }
          ]
        }
      ],
      taskTypes: [
        'BashOperator',
        'HiveOperator',
        'RDBMS2HiveOperator',
        'Hive2RDBMSOperator',
        'ExternalTaskSensor'
      ],
      selectVal: this.taskType
    }
  },
  watch: {
    taskType(val) {
      this.selectVal = val
    }
  },
  methods: {
    setVal() {
      this.$emit('update:taskType', this.selectVal)
    }
  }
}
</script>
