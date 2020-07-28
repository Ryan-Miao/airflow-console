<template>
  <div>

    <div class="filter-container" style="padding: 5px">
      <el-input
        v-model="listQuery.taskId"
        placeholder="taskId"
        style="width: 400px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <task-type :task-type.sync="listQuery.taskType" />

      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >Search</el-button>
      <el-button
        class="filter-item"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >Add</el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="left" label="Task ID">
        <template slot-scope="scope">{{ scope.row.taskId }}</template>
      </el-table-column>
      <el-table-column align="left" label="description">
        <template slot-scope="scope">{{ scope.row.description }}</template>
      </el-table-column>
      <el-table-column align="left" label="taskType" width="180">
        <template slot-scope="scope">{{ scope.row.taskType }}</template>
      </el-table-column>
      <el-table-column align="center" prop="createTime" label="createTime" width="220">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.createTime }}</span>
          <br>
          <i class="el-icon-edit" />
          <span>{{ scope.row.updateTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Actions"
        align="center"
        width="250"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">Edit</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(row)"
          >Delete</el-button>
        </template>
      </el-table-column>

    </el-table>

    <add-ext-dag-task
      :dag-id="dagId"
      :callback="fetchTasks"
      :dialog-status="AddExtDagTaskStatus"
      :ext-dag-task-entity="toEditEntity"
      :add-ext-dag-task-visible.sync="AddExtDagTaskVisible"
    />

  </div>
</template>

<script>

import {
  getTaskList,
  deleteTask
} from '@/api/ext-dag'
import AddExtDagTask from './AddExtDagTask'
import TaskType from '../component/TaskType'

export default {
  components: {
    AddExtDagTask,
    TaskType
  },
  props: {
    dagId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      listQuery: {
        dagId: this.dagId,
        taskId: undefined,
        taskType: undefined
      },
      list: [],
      listLoading: true,
      // edit view
      AddExtDagTaskVisible: false,
      AddExtDagTaskStatus: 'create',
      // 更新的时候entity
      toEditEntity: undefined
    }
  },
  created() {
    this.fetchTasks()
  },
  methods: {
    fetchTasks() {
      if (this.listQuery.taskType === '') {
        this.listQuery.taskType = undefined
      }
      getTaskList(this.listQuery).then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listLoading = true
      this.fetchTasks()
    },
    handleCreate() {
      this.toEditEntity = undefined
      this.AddExtDagTaskVisible = true
      this.AddExtDagTaskStatus = 'create'
    },
    handleUpdate(row) {
      this.AddExtDagTaskVisible = true
      this.AddExtDagTaskStatus = 'update'
      this.toEditEntity = row
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除该任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteTask(row.dagId, row.taskId).then(response => {
          this.$message({
            title: 'Success',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          console.log('delete')
          this.fetchTasks()
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}

</script>
