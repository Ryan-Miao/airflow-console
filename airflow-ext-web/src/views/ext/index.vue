<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.dagId"
        placeholder="DagId"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="listQuery.dagName"
        placeholder="dag name"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="listQuery.owners"
        placeholder="owners"
        style="width: 400px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />

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
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left">
            <el-form-item label="DagId">
              <span>{{ props.row.id }}</span>
            </el-form-item>
            <el-form-item label="DagName">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="描述">
              <span>{{ props.row.description }}</span>
            </el-form-item>
            <el-form-item label="dependsOnPast">
              <span>{{ props.row.dependsOnPast }}</span>
            </el-form-item>
            <el-form-item label="startDate">
              <span>{{ props.row.startDate }}</span>
            </el-form-item>
            <el-form-item label="alertDing">
              <span>{{ props.row.alertDing }}</span>
            </el-form-item>
            <el-form-item label="alertMailReceivers">
              <span>{{ props.row.alertMailReceivers }}</span>
            </el-form-item>
            <el-form-item label="successAlert">
              <span>{{ props.row.successAlert }}</span>
            </el-form-item>
            <el-form-item label="failureAlert">
              <span>{{ props.row.failureAlert }}</span>
            </el-form-item>
            <el-form-item label="taskDependency">
              <span>{{ props.row.taskDependency }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column align="left" label="DAG ID">
        <template slot-scope="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column align="left" label="DAG Name">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="Owners" width="110">
        <template slot-scope="scope">{{ scope.row.owners }}</template>
      </el-table-column>
      <el-table-column label="Cron" width="120" align="left">
        <template slot-scope="scope">
          <span>{{ scope.row.cron }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Tasks" align="left">
        <template slot-scope="scope">
          <span>{{ scope.row.taskDependency }}</span>
        </template>
      </el-table-column>
      <el-table-column label="createTime" align="left">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="updateTime" align="left">
        <template slot-scope="scope">
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
          <el-button type="primary" size="mini" @click="handleEdit(row)">Edit</el-button>
          <el-button type="info" size="mini" @click="showTasks(row)">Tasks</el-button>
          <el-button type="danger" size="mini" @click="deleteTask(row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      :total="total"
      :page.sync="listQuery.pageRq.pageNum"
      :limit.sync="listQuery.pageRq.pageSize"
      @pagination="fetchData"
    />

    <add-ext-dag
      :callback="fetchData"
      :dialog-status="AddExtDagStatus"
      :add-ext-dag-visible.sync="AddExtDagVisible"
      :ext-dag-entity="toEditEntity"
    />
  </div>
</template>

<script>
import {
  getDagList,
  deleteDagById
} from '@/api/ext-dag'
import Pagination from '@/components/Pagination'
import AddExtDag from './AddExtDag'

export default {
  components: { Pagination, AddExtDag },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        pageRq: {
          pageNum: 1,
          pageSize: 10
        },
        dagId: undefined,
        dagName: undefined,
        owners: undefined
      },
      // edit view
      AddExtDagVisible: false,
      AddExtDagStatus: 'create',
      // 更新的时候entity
      toEditEntity: undefined
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      getDagList(this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.total
        this.listQuery.pageRq.pageNum = response.data.pageNum
        this.listQuery.pageRq.pageSize = response.data.pageSize
        this.listLoading = false
      })
    },
    handleFilter() {
      this.fetchData()
    },
    handleCreate() {
      this.AddExtDagVisible = true
      this.AddExtDagStatus = 'create'
      this.toEditEntity = undefined
    },
    handleEdit(row) {
      this.AddExtDagStatus = 'update'
      this.AddExtDagVisible = true
      this.toEditEntity = row
    },
    showTasks(row) {
      this.$router.push({
        path: '/ext/dags/' + row.id + '/tasks'
      })
    },
    deleteTask(row) {
      this.$confirm('此操作将永久删除该ExtDAG[' + row.id + '], 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$confirm('请再次确认删除该ExtDAG[' + row.id + ']?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteDagById(row.id).then(response => {
            this.$message({
              title: 'Success',
              message: '删除成功',
              type: 'success',
              duration: 2000
            })
            this.fetchData()
          })
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
