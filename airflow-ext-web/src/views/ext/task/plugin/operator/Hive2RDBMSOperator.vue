<template>
  <div>
    <el-form
      ref="taskPluginForm"
      :rules="taskPluginRules"
      :model="config"
      label-position="top"
      label-width="180px"
    >
      <el-form-item prop="hiveQuerySql" class="task-plugin-field">
        <template v-slot:label>
          <label>Hive出库查询语句</label>
          <el-popover
            placement="right"
            title="querySql"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.hiveQuerySql }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
        <template>
          <el-input v-model="config.hiveQuerySql" type="textarea" />
        </template>
      </el-form-item>

      <el-form-item label="目标库链接" prop="rdbmsConnId" class="task-plugin-field">
        <connection-selector :conn-id.sync="config.rdbmsConnId" />
      </el-form-item>

      <el-form-item prop="rdbmsTable" class="task-plugin-field">
        <template v-slot:label>
          <label>RDBMS Table</label>
          <el-popover
            placement="right"
            title="rdbmsTable"
            width="500"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.rdbmsTable }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
        <el-input v-model="config.rdbmsTable" />
      </el-form-item>
      <el-form-item prop="rdbmsColumn" class="task-plugin-field">
        <el-input v-model="config.rdbmsColumn" />
        <template v-slot:label>
          <label>RDBMS Column</label>
          <el-popover
            placement="right"
            title="rdbmsColumn"
            width="500"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.rdbmsColumn }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="rdbmsPreSql" class="task-plugin-field">
        <template v-slot:label>
          <label>入库前操作语句</label>
          <el-popover
            placement="right"
            title="rdbmsPreSql"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.rdbmsPreSql }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
        <template>
          <el-input v-model="config.rdbmsPreSql" type="textarea" />
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
      config: {
        rdbmsConnId: undefined,
        hiveQuerySql: undefined,
        rdbmsTable: undefined,
        rdbmsColumn: undefined,
        rdbmsPreSql: undefined
      },
      tip: {
        hiveQuerySql: '来源库sql语句，查询hive列.' + variableDesc,
        rdbmsTable: '目标库的 table name',
        rdbmsColumn: '目标库的table column,用英文逗号分割',
        rdbmsPreSql: '目标库的表的操作，在插入数据前执行的sql，比如truncate, delete等' +
          '， 可以使用变量或者固定字符。' + variableDesc
      },
      taskPluginRules: {
        rdbmsConnId: [
          {
            required: true,
            message: 'connId必须',
            trigger: 'blur'
          }
        ],
        hiveQuerySql: [
          {
            required: true,
            message: 'hiveQuerySql必须',
            trigger: 'blur'
          }
        ],
        rdbmsTable: [
          {
            required: true,
            message: 'rdbmsTable必须',
            trigger: 'blur'
          }
        ],
        rdbmsColumn: [
          {
            required: true,
            message: 'rdbmsColumn必须',
            trigger: 'blur'
          }
        ],
        rdbmsPreSql: [
          {
            required: true,
            message: 'rdbmsPreSql必须',
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
