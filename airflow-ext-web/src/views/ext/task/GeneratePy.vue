<template>
  <el-dialog
    title="Py Generator"
    :visible.sync="visible"
    :close-on-click-modal="false"
    width="70%"
    @close="closeGenerator"
  >

    <div>
      <pre>
        {{ dagpy }}
      </pre>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="closeGenerator">Cancel</el-button>
      <el-button type="primary" @click="submitPy">submit py</el-button>
    </div>
  </el-dialog>
</template>

<script>

import DagTemplate from './template/dag.art'
import BashOperatorTemplate from './template/operator/BashOperator.js'
import HiveOperatorTemplate from './template/operator/HiveOperator.js'
import RDBMS2HiveOperatorTemplate from './template/operator/RDBMS2HiveOperator.js'
import Hive2RDBMSOperatorTemplate from './template/operator/Hive2RDBMSOperator.js'
import SimpleHttpOperatorTemplate from './template/operator/SimpleHttpOperator.js'
import ExternalTaskSensorTemplate from './template/sensor/ExternalTaskSensor.js'
import HttpSensorTemplate from './template/sensor/HttpSensor.js'
import CompassTaskSensorTemplate from './template/sensor/CompassTaskSensor.js'

import moment from 'moment'

import {
  getTaskList,
  pushPyToGit
} from '@/api/ext-dag'
import {
  getCategories
} from '@/api/ext-dag-category'

export default {
  props: {
    extDag: {
      type: Object,
      required: true
    },
    generatorVisible: {
      type: Boolean,
      required: true,
      default: false
    }
  },
  data() {
    return {
      visible: false,
      dagpy: '',
      categoryName: 'default',
      dagFileName: 'default',
      importStatement: new Set(),
      categories: new Map()
    }
  },
  watch: {
    generatorVisible(show) {
      this.visible = show
      if (show) {
        this.generate()
      }
    }
  },
  created() {
    this.getDagCategories()
  },
  methods: {
    closeGenerator() {
      this.visible = false
      this.$emit('update:generatorVisible', false)
    },
    getDagCategories() {
      getCategories().then(res => {
        const d = res.data
        for (let i = 0; i < d.length; i++) {
          const ele = d[i]
          this.categories.set(ele.id, ele.name)
        }
      })
    },
    generateDag() {
      const extDag = Object.assign({}, this.extDag)
      if (extDag.dependsOnPast) {
        extDag.dependsOnPast = 'True'
      } else {
        extDag.dependsOnPast = 'False'
      }
      const d = moment(extDag.startDate)
      extDag.startDate = '' + d.year() + ',' + (d.month() + 1) + ',' + d.date()
      const category = this.categories.get(extDag.categoryId)
      this.categoryName = category

      extDag.id = category + '_' + extDag.id + '_' + extDag.name
      if (extDag.cron !== 'None') {
        extDag.cron = '"' + extDag.cron + '"'
      }
      this.dagFileName = extDag.id + '.py'
      return new Promise(function(resolve) {
        resolve(DagTemplate({
          dag: extDag,
          fileName: category + '/' + extDag.id + '.py',
          date: moment().format('YYYY-MM-DD HH:mm:ss')
        }))
      })
    },
    generateTask() {
      // get tasks
      return getTaskList({
        dagId: this.extDag.id
      }).then(res => {
        const d = res.data
        let task_str = ''
        for (let i = 0; i < d.length; i++) {
          const dElement = d[i]
          let templator
          switch (dElement.taskType) {
            case 'BashOperator': {
              templator = BashOperatorTemplate
              break
            }
            case 'HiveOperator': {
              templator = HiveOperatorTemplate
              break
            }
            case 'RDBMS2HiveOperator': {
              templator = RDBMS2HiveOperatorTemplate
              break
            }
            case 'Hive2RDBMSOperator': {
              templator = Hive2RDBMSOperatorTemplate
              break
            }
            case 'SimpleHttpOperator': {
              templator = SimpleHttpOperatorTemplate
              break
            }
            case 'HttpSensor': {
              templator = HttpSensorTemplate
              break
            }
            case 'ExternalTaskSensor': {
              templator = ExternalTaskSensorTemplate
              break
            }
            case 'CompassTaskSensor': {
              templator = CompassTaskSensorTemplate
              break
            }
            default: break
          }

          templator.importStatement.forEach(item => this.importStatement.add(item))
          task_str += templator.template(dElement)
        }
        let r = '\n\n'
        for (const im of this.importStatement.values()) {
          r += im + '\n'
        }
        return r + task_str
      })
    },
    generateDependency() {
      if (this.extDag.taskDependency) {
        return '\n\n' + this.extDag.taskDependency
      } else {
        return ''
      }
    },
    generate() {
      this.dagpy = ''
      const that = this
      this.generateDag()
        .then(function(data) {
          that.dagpy += data
          return that.generateTask()
        }).then(function(data) {
          that.dagpy += data
          that.dagpy += that.generateDependency()
        })
    },
    submitPy() {
      const loading = this.$loading({
        lock: true,
        text: 'submitting',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })

      pushPyToGit({
        categoryName: this.categoryName,
        dagFileName: this.dagFileName,
        dagPyBody: this.dagpy
      }).then(res => {
        this.$notify({
          title: 'Success',
          message: '推送成功: ' + res.data,
          type: 'success',
          duration: 5000
        })
        loading.close()
        this.closeGenerator()
      }).catch(reason => {
        this.$notify({
          title: 'Failed',
          message: 'Failed: ' + reason,
          type: 'error',
          duration: 5000
        })
        this.$nextTick(() => {
          loading.close()
        })
      })
    }
  }
}
</script>
