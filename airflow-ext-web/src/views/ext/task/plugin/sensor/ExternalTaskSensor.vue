<template>
  <div>
    <el-form
      ref="taskPluginForm"
      :rules="taskPluginRules"
      :model="config"
      label-position="top"
      label-width="130px"
    >
      <el-form-item prop="externalDagId">
        <el-input v-model="config.externalDagId" />

        <template v-slot:label>
          <label>依赖的DAG Id</label>
          <el-popover
            placement="right"
            title="externalDagId"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.externalDagId }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="externalTaskId">
        <el-input v-model="config.externalTaskId" />

        <template v-slot:label>
          <label>依赖的DAG的Task Id</label>
          <el-popover
            placement="right"
            title="externalTaskId"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.externalTaskId }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="executionDelta">
        <el-input v-model="config.executionDelta" />

        <template v-slot:label>
          <label>依赖的执行日期延迟</label>
          <el-popover
            placement="right"
            title="executionDelta"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.executionDelta }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
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
        externalDagId: undefined,
        externalTaskId: undefined,
        executionDelta: undefined
      },
      tip: {
        externalDagId: 'dag id. 完整的dag id, 非ext-dag id.',
        externalTaskId: '依赖的dag内部的task id, 不填则依赖dag整体成功.',
        executionDelta: '任务延迟，不填则等于当前任务执行时间execution_date， ' +
          'datetime.timedelta(days=1)表示昨天'
      },
      taskPluginRules: {
        externalDagId: [
          {
            required: true,
            message: 'externalDagId必须',
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
