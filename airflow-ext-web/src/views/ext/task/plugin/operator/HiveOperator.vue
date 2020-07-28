<template>
  <div>
    <el-form
      ref="taskPluginForm"
      :rules="taskPluginRules"
      :model="config"
      label-position="top"
      label-width="130px"
    >
      <el-form-item prop="owner" class="task-plugin-field">
        <el-input v-model="config.owner" />
        <template v-slot:label>
          <label>owner</label>
          <el-popover
            placement="right"
            title="owner"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.owner }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="hql" class="task-plugin-field">
        <el-input v-model="config.hql" type="textarea" />
        <template v-slot:label>
          <label>HQL</label>
          <el-popover
            placement="right"
            title="hql"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.hql }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="engine" class="task-plugin-field">
        <el-select
          v-model="config.engine"
          class="filter-item"
          filterable
          placeholder="选择计算引擎"
        >
          <el-option
            v-for="item in engines"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
        <template v-slot:label>
          <label>计算引擎</label>
          <el-popover
            placement="right"
            title="engine"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.engine }}</pre>
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
        owner: undefined,
        hql: undefined,
        engine: 'mr',
        hiveCliConnId: 'beeline'
      },
      tip: {
        owner: 'hive执行的用户名',
        hql: 'hive sql查询语句。多个语句用英文分号分割。' + variableDesc,
        engine: '计算引擎'
      },
      engines: [
        'mr',
        'spark',
        'tez'
      ],
      taskPluginRules: {
        owner: [
          {
            required: true,
            message: 'owner必须',
            trigger: 'blur'
          }
        ],
        hql: [
          {
            required: true,
            message: 'hql必须',
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
