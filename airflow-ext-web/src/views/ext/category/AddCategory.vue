<template>
  <el-dialog :title="titleMap[dialogStatus]" :visible.sync="visible" :close-on-click-modal="false" @close="closeEditor">

    <el-form
      ref="categoryForm"
      :rules="rules"
      :model="temp"
      label-position="left"
      label-width="120px"
      style="width: 700px; margin-left:50px;"
    >
      <el-form-item v-if="dialogStatus === 'update'" label="Id" prop="id">
        <el-input v-model="temp.id" :disabled="dialogStatus === 'update'" />
      </el-form-item>
      <el-form-item label="Name" prop="name">
        <el-input v-model="temp.name" />
      </el-form-item>
      <el-form-item label="Description" prop="description">
        <el-input v-model="temp.description" />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select
          v-model="temp.status"
          class="filter-item"
          placeholder="status"
          filterable
          clearable
        >
          <el-option
            v-for="item in statusMap"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="closeEditor">Cancel</el-button>
      <el-button type="primary" @click="submitForm()">Confirm</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {
  addCategory,
  updateCategory
} from '@/api/ext-dag-category'
export default {
  props: {
    addCategoryVisible: {
      type: Boolean,
      required: false,
      default: false
    },
    categoryEntity: {
      type: Object,
      required: false,
      default: undefined
    },
    dialogStatus: {
      type: String,
      required: true
    },
    callBack: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      titleMap: {
        update: '编辑Category',
        create: '创建Category'
      },
      statusMap: [
        'active',
        'deleted'
      ],
      rules: {
        name: [
          {
            required: true,
            message: 'name is required'
          }
        ]
      },
      // 是否显示
      visible: this.addExtDagTaskVisible,
      temp: {

      }
    }
  },
  watch: {
    addCategoryVisible(show) {
      this.visible = show
      if (show && this.categoryEntity) {
        this.temp = Object.assign({}, this.categoryEntity)
      }
    }
  },
  methods: {
    resetTemp() {
      this.temp = {
        //
      }
    },
    closeEditor() {
      this.visible = false
      this.resetTemp()
      this.$emit('update:addCategoryVisible', false)
    },
    doCreate() {
      return addCategory(this.temp)
    },
    doUpdate() {
      return updateCategory(this.temp)
    },
    notifySuccess() {
      this.$notify({
        title: 'Success',
        message: '成功',
        type: 'success',
        duration: 2000
      })
      this.callBack()
      this.closeEditor()
    },
    submitForm() {
      this.$refs['categoryForm'].validate((valid) => {
        if (valid) {
          let promise
          if (this.dialogStatus === 'create') {
            promise = this.doCreate()
          } else {
            promise = this.doUpdate()
          }
          promise.then(this.notifySuccess)
        }
      })
    }
  }
}
</script>
