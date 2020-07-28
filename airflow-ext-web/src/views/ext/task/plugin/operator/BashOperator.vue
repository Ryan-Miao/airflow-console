<template>
  <div>
    <el-form
      ref="taskPluginForm"
      :rules="taskPluginRules"
      :model="config"
      label-position="top"
      label-width="130px"
    >
      <el-form-item prop="bashCommand">
        <el-input v-model="config.bashCommand" type="textarea" />

        <template v-slot:label>
          <label>Bash Command</label>
          <el-popover
            placement="right"
            title="bashCommand"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.bashCommand }}</pre>
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
        bashCommand: undefined
      },
      tip: {
        bashCommand: 'bash脚本内容。' + variableDesc
      },
      taskPluginRules: {
        bashCommand: [
          {
            required: true,
            message: 'bashCommand必须',
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
