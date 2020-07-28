<template>
  <el-dialog :title="titleMap[dialogStatus]" :visible.sync="visible" :close-on-click-modal="false" @close="changeSwitch">
    <el-form
      ref="dataForm"
      :rules="rules"
      :model="temp"
      label-position="left"
      label-width="120px"
      style="width: 700px; margin-left:50px;"
    >

      <el-form-item v-if="dialogStatus === 'update'" label="id" prop="id">
        <el-input v-model="temp.id" :disabled="dialogStatus === 'update'" />
      </el-form-item>

      <el-form-item label="name" prop="name">
        <el-input v-model="temp.name" />
      </el-form-item>

      <el-form-item label="Category" prop="categoryId">
        <ext-dag-category :category-id.sync="temp.categoryId" />
      </el-form-item>
      <el-form-item label="description" prop="description">
        <el-input v-model="temp.description" />
      </el-form-item>
      <el-form-item label="cron" prop="cron">
        <el-input v-model="temp.cron" style="display: inline" />
        <template v-slot:label>
          <label>cron</label>
          <el-popover
            placement="right"
            title="dag执行时间"
            width="500"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.cron }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item label="owners" prop="owners">
        <el-input v-model="temp.owners" />
        <template v-slot:label>
          <label>owners</label>
          <el-popover
            placement="right"
            title="dag责任人"
            width="500"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.owner }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item prop="dependsOnPast">
        <el-checkbox v-model="temp.dependsOnPast">依赖</el-checkbox>
        <template v-slot:label>
          <label>dependsOnPast</label>
          <el-popover
            placement="right"
            title="dependsOnPast"
            width="500"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.dependsOnPast }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item label="任务开始日期" prop="startDate">
        <div class="block">
          <el-date-picker
            v-model="temp.startDate"
            type="datetime"
            placeholder="选择日期时间"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
            :picker-options="pickerOptions"
          />
        </div>
      </el-form-item>
      <el-form-item label="钉钉conn" prop="alertDing">
        <el-select
          v-model="temp.alertDing"
          class="filter-item"
          filterable
          placeholder="选择钉钉conn"
          value=""
        >
          <el-option
            v-for="item in dingConns"
            :key="item.connId"
            :label="item.connId+' : '+item.login"
            :value="item.connId"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="邮件接收人" prop="alertMailReceivers">
        <el-input v-model="temp.alertMailReceivers" />
        <template v-slot:label>
          <label>邮件接收人</label>
          <el-popover
            placement="right"
            title="alertMailReceivers"
            width="500"
            trigger="hover"
            popper-class="tip"
          >
            <pre>{{ tip.alertMailReceivers }}</pre>
            <i slot="reference" class="el-icon-info tip-flag" />
          </el-popover>
        </template>
      </el-form-item>
      <el-form-item label="成功后通知方式" prop="successAlertArr">
        <el-select
          v-model="temp.successAlertArr"
          class="filter-item"
          multiple
          placeholder="通知方式"
        >
          <el-option
            v-for="item in alertTypes"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="失败后告警" prop="failureAlertArr">
        <el-select
          v-model="temp.failureAlertArr"
          class="filter-item"
          multiple
          placeholder="通知方式"
        >
          <el-option
            v-for="item in alertTypes"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>

    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="changeSwitch">Cancel</el-button>
      <el-button type="primary" @click="submitForm()">Confirm</el-button>
    </div>
  </el-dialog>
</template>

<script>

import {
  getConnList
} from '@/api/connection'
import {
  addExtDag,
  updateExtDag
} from '@/api/ext-dag'
import { validateId } from '@/utils/validatorUtils'
import ExtDagCategory from './component/ExtDagCategory'

export default {
  components: {
    ExtDagCategory
  },
  props: {
    // form model
    extDagEntity: {
      type: Object,
      required: false,
      default: undefined
    },
    // 标题
    dialogStatus: {
      type: String,
      required: true
    },
    // 是否显示
    addExtDagVisible: {
      type: Boolean,
      required: true,
      default: false
    },
    // callback
    callback: {
      type: Function,
      required: false,
      default: () => {}
    }
  },
  data() {
    const that = this
    function validateAlert(rule, value, callback) {
      if (value.length > 0) {
        for (let i = 0; i < value.length; i++) {
          const alert_method = value[i]
          if (alert_method === 'dingding') {
            // check dingding conn
            if (!that.temp.alertDing) {
              callback(new Error('请选择钉钉群组'))
            }
          }
          if (alert_method === 'email') {
            // check mail
            if (!that.temp.alertMailReceivers) {
              callback(new Error('请选择邮件接收人'))
            }
          }
        }
      }
      return callback()
    }
    return {
      titleMap: {
        update: '编辑ExtDAG',
        create: '创建ExtDAG'
      },
      // 是否显示
      visible: this.addExtDagVisible,
      cronPopover: false,
      tip: {
        cron: 'cron表达式， 精度为分钟。\n即 min hour day month week\n0 3 * * *表示每天3点',
        owner: '关联查询使用，英文逗号分割',
        dependsOnPast: '是否依赖上一个周期的结果',
        alertMailReceivers: '统一登录邮件账号，邮箱前缀，用英文逗号分割'
      },
      rules: {
        name: [
          {
            required: true,
            validator: validateId,
            trigger: 'blur'
          }
        ],
        successAlertArr: [
          {
            validator: validateAlert,
            trigger: 'change'
          }
        ],
        failureAlertArr: [
          {
            validator: validateAlert,
            trigger: 'change'
          }
        ],
        cron: [
          {
            required: true,
            message: 'cron必填, 0 3 * * * 表示每天3点',
            trigger: 'blur'
          }
        ],
        owners: [
          {
            required: true,
            message: 'owners必填',
            trigger: 'blur'
          }
        ],
        startDate: [
          {
            required: true,
            message: '开始日期必填',
            trigger: 'blur'
          }
        ]

      },
      categories: [],
      // form model
      temp: {
        id: undefined,
        name: undefined,
        categoryId: undefined,
        description: undefined,
        cron: undefined,
        owners: undefined,
        dependsOnPast: undefined,
        startDate: undefined,
        alertDing: undefined,
        alertMailReceivers: undefined,
        successAlert: undefined,
        failureAlert: undefined,
        successAlertArr: [],
        failureAlertArr: [],
        taskDependency: undefined
      },
      // 日期选择
      pickerOptions: {
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date())
          }
        }, {
          text: '明天',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24)
            picker.$emit('pick', date)
          }
        }, {
          text: '7天后',
          onClick(picker) {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7)
            picker.$emit('pick', date)
          }
        }]
      },
      // dingding conn
      dingConns: [],
      // 通知方式
      alertTypes: ['dingding', 'email']
    }
  },
  watch: {
    addExtDagVisible(show) {
      this.visible = show
      if (show && this.extDagEntity) {
        Object.assign(this.temp, this.extDagEntity)
        if (this.temp.failureAlert) {
          this.temp.failureAlert.split(',').forEach(value => {
            this.temp.failureAlertArr.push(value)
          })
        }

        if (this.temp.successAlert) {
          this.temp.successAlert.split(',').forEach(value => {
            this.temp.successAlertArr.push(value)
          })
        }
      }
    }
  },
  created() {
    this.fetchDingConn()
  },
  methods: {
    changeSwitch() {
      this.visible = false
      this.$emit('update:addExtDagVisible', false)
      this.resetTemp()
    },
    fetchDingConn() {
      getConnList('http', 'dingding').then(response => {
        this.dingConns = response.data
      })
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        name: undefined,
        categoryId: undefined,
        description: undefined,
        cron: undefined,
        owners: undefined,
        dependsOnPast: undefined,
        startDate: undefined,
        alertDing: undefined,
        alertMailReceivers: undefined,
        successAlert: undefined,
        failureAlert: undefined,
        successAlertArr: [],
        failureAlertArr: [],
        taskDependency: undefined
      }
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
      })
    },
    changeCron(val) {
      this.temp.cron = val
    },
    createData() {
      const successAlertArr = this.temp.successAlertArr
      if (successAlertArr) {
        this.temp.successAlert = successAlertArr.join()
      }
      const failureAlertArr = this.temp.failureAlertArr
      if (failureAlertArr) {
        this.temp.successAlert = failureAlertArr.join()
      }
      this.temp.id=undefined
      addExtDag(this.temp).then(response => {
        this.changeSwitch()
        this.$notify({
          title: 'Success',
          message: '添加成功',
          type: 'success',
          duration: 2000
        })
        this.callback()
      })
    },
    updateData() {
      const successAlertArr = this.temp.successAlertArr
      if (successAlertArr) {
        this.temp.successAlert = successAlertArr.join()
      }
      const failureAlertArr = this.temp.failureAlertArr
      if (failureAlertArr) {
        this.temp.failureAlert = failureAlertArr.join()
      }
      updateExtDag(this.temp).then(response => {
        this.changeSwitch()
        this.$notify({
          title: 'Success',
          message: 'Update Successfully',
          type: 'success',
          duration: 2000
        })
        this.callback()
      })
    },
    submitForm() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          if (this.dialogStatus === 'create') {
            this.createData()
          } else if (this.dialogStatus === 'update') {
            this.updateData()
          } else {
            console.error('无法判断新增还是更新')
          }
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style>

  .el-popover__title {
    color: white;
  }
  .tip-flag {
    position: absolute;
  }

  .tip {
    background-color: #46556d;
    color: white;
  }

</style>
