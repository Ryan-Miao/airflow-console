<template>
  <div class="app-container">
    <div class="filter-container" style="padding: 5px">
      <el-input
        v-model="listQuery.name"
        placeholder="name"
        style="width: 400px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select
        v-model="listQuery.status"
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
      <el-table-column align="left" label="ID">
        <template slot-scope="scope">{{ scope.row.id }}</template>
      </el-table-column>
      <el-table-column align="left" label="Name">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column align="left" label="Desc">
        <template slot-scope="scope">{{ scope.row.description }}</template>
      </el-table-column>
      <el-table-column align="left" label="Status">
        <template slot-scope="scope">{{ scope.row.status }}</template>
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            {{ row.status }}
          </el-tag>
        </template>
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
            v-if="row.status === 'active'"
            size="mini"
            type="danger"
            @click="handleDelete(row)"
          >Delete</el-button>

          <el-button
            v-if="row.status === 'deleted'"
            size="mini"
            type="success"
            @click="handleActive(row)"
          >Active</el-button>

        </template>
      </el-table-column>
    </el-table>

    <add-category
      :dialog-status="AddCategoryStatus"
      :call-back="handleFilter"
      :add-category-visible.sync="addCategoryVisible"
      :category-entity="categoryEntity"
    />

  </div>
</template>

<script>
import {
  getCategories,
  updateCategory,
  deleteCategory
} from '@/api/ext-dag-category'

import AddCategory from './AddCategory'

export default {
  components: {
    AddCategory
  },
  filters: {
    statusFilter(status) {
      const statusMap = {
        active: 'success',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      listQuery: {
        status: undefined,
        name: undefined
      },
      list: [],
      listLoading: true,
      temp: {

      },
      statusMap: [
        'active',
        'deleted'
      ],
      AddCategoryStatus: 'create',
      addCategoryVisible: false,
      categoryEntity: undefined
    }
  },
  created() {
    this.handleFilter()
  },
  methods: {
    handleFilter() {
      getCategories(this.listQuery).then(res => {
        this.list = res.data
        this.listLoading = false
      })
    },
    handleDelete(row) {
      deleteCategory(row.id).then(res => {
        this.$notify({
          title: 'Success',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        this.handleFilter()
      })
    },
    handleUpdate(row) {
      this.addCategoryVisible = true
      this.AddCategoryStatus = 'update'
      this.categoryEntity = row
    },
    handleCreate() {
      this.addCategoryVisible = true
      this.AddCategoryStatus = 'create'
      this.categoryEntity = undefined
    },
    handleActive(row) {
      updateCategory({
        id: row.id,
        status: 'active'
      }).then(res => {
        this.$notify({
          title: 'Success',
          message: '修改成功',
          type: 'success',
          duration: 2000
        })
        this.handleFilter()
      })
    }
  }
}
</script>
