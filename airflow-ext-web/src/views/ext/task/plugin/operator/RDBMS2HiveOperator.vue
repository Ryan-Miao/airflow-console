<template>
  <div>
    <el-form
      ref="taskPluginForm"
      :rules="taskPluginRules"
      :model="config"
      label-position="top"
      label-width="180px"
    >
      <el-form-item label="出库链接" prop="connId" class="task-plugin-field">
        <connection-selector :conn-id.sync="config.connId" />
      </el-form-item>
      <el-form-item prop="querySql" class="task-plugin-field">
        <template v-slot:label>
          <label>出库查询语句</label>
          <el-popover
            placement="right"
            title="querySql"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.querySql }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
        <template>
          <el-input v-model="config.querySql" type="textarea" />
        </template>

      </el-form-item>
      <el-form-item prop="hiveDb" class="task-plugin-field">
        <template v-slot:label>
          <label>Hive DB</label>
          <el-popover
            placement="right"
            title="hiveDb"
            width="500"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.hiveDb }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
        <el-input v-model="config.hiveDb" />
      </el-form-item>
      <el-form-item prop="hiveTable" class="task-plugin-field">
        <el-input v-model="config.hiveTable" />
        <template v-slot:label>
          <label>Hive Table</label>
          <el-popover
            placement="right"
            title="hiveTable"
            width="500"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.hiveTable }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="hiveTableColumn" class="task-plugin-field">
        <el-input v-model="config.hiveTableColumn" />
        <template v-slot:label>
          <label>Hive Table Column</label>
          <el-popover
            placement="right"
            title="hiveTableColumn"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.hiveTableColumn }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="hiveTablePartition" class="task-plugin-field">
        <el-input v-model="config.hiveTablePartition" />
        <template v-slot:label>
          <label>Hive Table Partition</label>
          <el-popover
            placement="right"
            title="hiveTablePartition"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.hiveTablePartition }}</pre>
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
      config: {
        connId: undefined,
        querySql: undefined,
        hiveDb: undefined,
        hiveTable: undefined,
        hiveTableColumn: undefined,
        hiveTablePartition: undefined
      },
      tip: {
        querySql: '出库sql语句，查询列.' + variableDesc,
        hiveDb: '要入库的hive database name',
        hiveTable: '要入库的hive table, 不要带db前缀',
        hiveTableColumn: '要入库的对应的hive表的字段，用英文逗号分割',
        hiveTablePartition: 'hive表分区， 可以使用变量或者固定字符。' + variableDesc
      },
      taskPluginRules: {
        connId: [
          {
            required: true,
            message: 'connId必须',
            trigger: 'blur'
          }
        ],
        querySql: [
          {
            required: true,
            message: 'querySql必须',
            trigger: 'blur'
          }
        ],
        hiveDb: [
          {
            required: true,
            message: 'hiveDb必须',
            trigger: 'blur'
          }
        ],
        hiveTable: [
          {
            required: true,
            message: 'hiveTable必须',
            trigger: 'blur'
          }
        ],
        hiveTableColumn: [
          {
            required: true,
            message: 'hiveTableColumn必须',
            trigger: 'blur'
          }
        ],
        hiveTablePartition: [
          {
            required: true,
            message: 'hiveTablePartition必须',
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
