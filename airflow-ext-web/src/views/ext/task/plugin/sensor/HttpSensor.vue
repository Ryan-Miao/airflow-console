<template>
  <div>
    <el-form
      ref="taskPluginForm"
      :rules="taskPluginRules"
      :model="config"
      label-position="top"
      label-width="130px"
    >

      <el-form-item label="Http Host Conn" prop="httpConnId" class="task-plugin-field">
        <connection-selector :conn-id.sync="config.httpConnId" />
      </el-form-item>
      <el-form-item prop="endpoint" class="task-plugin-field">
        <el-input v-model="config.endpoint" />

        <template v-slot:label>
          <label>endpoint</label>
          <el-popover
            placement="right"
            title="endpoint"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.endpoint }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="headers" class="task-plugin-field">
        <el-input v-model="config.headers" type="textarea" />

        <template v-slot:label>
          <label>headers</label>
          <el-popover
            placement="right"
            title="headers"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.headers }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="responseCheck" class="task-plugin-field">
        <el-input v-model="config.responseCheck" type="textarea" />

        <template v-slot:label>
          <label>responseCheck</label>
          <el-popover
            placement="right"
            title="responseCheck"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.responseCheck }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

import ConnectionSelector from '../../../component/ConnectionSelector'
import { variableDesc } from '../../../component/OperatorVariable'
export default {
  components: {
    ConnectionSelector
  },
  props: {
    param: {
      type: String,
      required: false,
      default: undefined
    }
  },
  data() {
    return {
      httpMethods: [
        'GET',
        'POST',
        'PUT',
        'DELETE'
      ],
      config: {
        httpConnId: 'http',
        endpoint: '',
        headers: '',
        responseCheck: undefined
      },
      tip: {
        endpoint: '请求url， 参数中可以携带变量。比如/api/v1/tasks/{{ds}}.' + variableDesc,
        headers: '请求header，json格式。 参数中可以携带变量。比如{ "bizdate": {{ds}} }.' + variableDesc,
        responseCheck: '结果校验，可以使用匿名函数labmda, ' +
          '比如\nlambda response: response.json()[\'retCode\'] == 0'
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
