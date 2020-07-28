<template>
  <el-dialog :title="titleMap[dialogStatus]" :visible.sync="visible" :close-on-click-modal="false" @close="closeEditor">
    <el-form
      ref="taskForm"
      :rules="rules"
      :model="temp"
      label-position="left"
      label-width="120px"
      style="width: 700px; margin-left:50px;"
    >

      <el-form-item label="taskId" prop="taskId">
        <el-input v-model="temp.taskId" :disabled="dialogStatus === 'update'" />
      </el-form-item>
      <el-form-item label="description" prop="description">
        <el-input v-model="temp.description" type="textarea" />
      </el-form-item>
      <el-form-item label="taskType" prop="taskType">
        <task-type :task-type.sync="temp.taskType" />
        <div class="task-plugin">
          <component :is="temp.taskType" ref="taskPlugin" :param.sync="temp.param" />
        </div>
      </el-form-item>

    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="closeEditor">Cancel</el-button>
      <el-button type="primary" @click="submitForm()">Confirm</el-button>
    </div>
  </el-dialog>
</template>

<script>
import TaskType from '../component/TaskType'
import { validateId } from '@/utils/validatorUtils'
import BashOperator from './plugin/operator/BashOperator'
import HiveOperator from './plugin/operator/HiveOperator'
import RDBMS2HiveOperator from './plugin/operator/RDBMS2HiveOperator'
import Hive2RDBMSOperator from './plugin/operator/Hive2RDBMSOperator'
import ExternalTaskSensor from './plugin/sensor/ExternalTaskSensor'
import SimpleHttpOperator from './plugin/operator/SimpleHttpOperator'
import HttpSensor from './plugin/sensor/HttpSensor'
import CompassTaskSensor from './plugin/sensor/CompassTaskSensor'
import {
  addTask,
  updateTask
} from '@/api/ext-dag'

export default {
  components: {
    TaskType,
    BashOperator,
    HiveOperator,
    RDBMS2HiveOperator,
    Hive2RDBMSOperator,
    SimpleHttpOperator,
    ExternalTaskSensor,
    HttpSensor,
    CompassTaskSensor
  },
  props: {
    // form model
    extDagTaskEntity: {
      type: Object,
      required: false,
      default: undefined
    },
    // dagId
    dagId: {
      type: Number,
      required: true
    },
    // 标题
    dialogStatus: {
      type: String,
      required: true
    },
    // 是否显示
    addExtDagTaskVisible: {
      type: Boolean,
      required: true,
      default: false
    },
    callback: {
      type: Function,
      required: false,
      default: () => {}
    }
  },
  data() {
    return {
      titleMap: {
        update: '编辑Ext DAG Task',
        create: '创建Ext DAG Task'
      },
      // 是否显示
      visible: this.addExtDagTaskVisible,
      rules: {
        taskId: [
          {
            required: true,
            validator: validateId,
            trigger: 'blur'
          }
        ],
        taskType: [
          {
            required: true,
            message: 'taskType必须',
            trigger: 'blur'
          }
        ]
      },
      temp: {
        dagId: this.dagId,
        taskId: undefined,
        description: undefined,
        taskType: undefined,
        param: undefined
      }
    }
  },
  watch: {
    addExtDagTaskVisible(show) {
      this.visible = show
      if (show && this.extDagTaskEntity) {
        this.temp = Object.assign({}, this.extDagTaskEntity)
        this.temp.taskType = this.extDagTaskEntity.taskType
      } else {
        //
      }
    }
  },
  methods: {
    resetTemp() {
      this.temp = {
        dagId: this.dagId,
        taskId: undefined,
        description: undefined,
        taskType: undefined,
        param: undefined
      }
      this.$refs['taskForm'].resetFields()
    },
    closeEditor() {
      this.visible = false
      this.resetTemp()
      this.$emit('update:addExtDagTaskVisible', false)
    },
    createTask() {
      addTask(this.temp).then(response => {
        this.closeEditor()
        this.$notify({
          title: 'Success',
          message: '添加成功',
          type: 'success',
          duration: 2000
        })
        this.callback()
      })
    },
    updateTask() {
      updateTask(this.temp).then(response => {
        this.closeEditor()
        this.$notify({
          title: 'Success',
          message: '修改成功',
          type: 'success',
          duration: 2000
        })
        this.callback()
      })
    },
    doSubmitForm() {
      if (this.dialogStatus === 'create') {
        this.createTask()
      } else if (this.dialogStatus === 'update') {
        this.updateTask()
      } else {
        console.error('无法判断新增还是更新')
      }
    },
    submitForm() {
      this.$refs['taskForm'].validate((valid) => {
        if (valid) {
          const taskPluginCheckPromise = this.$refs.taskPlugin.check()
          taskPluginCheckPromise.then(this.doSubmitForm).catch(function(err) {
            console.info(err)
          })
        } else {
          console.log('校验失败')
          return false
        }
      })
    }
  }
}

</script>

<style>
  .task-plugin {
    padding-top: 10px;
  }
</style>
