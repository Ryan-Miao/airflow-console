<template>
  <div>
    <el-form
      ref="taskPluginForm"
      :rules="taskPluginRules"
      :model="config"
      label-position="top"
      label-width="130px"
    >
      <el-form-item prop="compassTaskId" class="task-plugin-field">
        <el-input v-model="config.compassTaskId" />

        <template v-slot:label>
          <label>compassTaskId</label>
          <el-popover
            placement="right"
            title="compassTaskId"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.compassTaskId }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="bizdate" class="task-plugin-field">
        <el-input v-model="config.bizdate" />

        <template v-slot:label>
          <label>bizdate</label>
          <el-popover
            placement="right"
            title="bizdate"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.bizdate }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import { variableDesc } from '../../../component/OperatorVariable'
export default {
  props: {
    param: {
      type: String,
      required: false,
      default: undefined
    }
  },
  data() {
    return {
      config: {
        compassTaskId: '',
        bizdate: ''
      },
      tip: {
        compassTaskId: '罗盘调度系统的任务id.',
        bizdate: '依赖罗盘调度系统的任务的bizdate。格式为yyyy-MM-dd或者yyyy-MM-dd HH:mm:ss. 可以使用变量，' + variableDesc
      },
      taskPluginRules: {
        httpConnId: [
          {
            required: true,
            trigger: 'blur'
          }
        ],
        endpoint: [
          {
            required: true,
            trigger: 'blur'
          }
        ],
        responseCheck: [
          {
            required: true,
            trigger: 'blur'
          }
        ]
      }
    }
  },
  watch: {
    config: {
      handler(val, oldVal) {
        const json = JSON.stringify(val)
        this.$emit('update:param', json)
      },
      immediate: true,
      deep: true
    },
    param: {
      handler(val) {
        if (val) {
          this.config = JSON.parse(this.param)
        }
      },
      immediate: true
    }
  },
  methods: {
    check() {
      const f = this.$refs['taskPluginForm']
      return new Promise(function(resolve, reject) {
        f.validate((valid) => {
          if (valid) {
            resolve()
          } else {
            reject('check failed!')
          }
        })
      })
    }
  }
}
</script>
