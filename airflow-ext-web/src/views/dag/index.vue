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
              <span>{{ props.row.dagId }}</span>
            </el-form-item>
            <el-form-item label="描述">
              <span>{{ props.row.description }}</span>
            </el-form-item>
            <el-form-item label="isSubdag">
              <span>{{ props.row.isSubdag }}</span>
            </el-form-item>
            <el-form-item label="isActive">
              <span>{{ props.row.isActive }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column align="left" label="DAG ID">
        <template slot-scope="scope">{{ scope.row.dagId }}</template>
      </el-table-column>
      <el-table-column label="owners" width="110">
        <template slot-scope="scope">{{ scope.row.owners }}</template>
      </el-table-column>
      <el-table-column label="Cron" width="120" align="left">
        <template slot-scope="scope">
          <span>{{ scope.row.scheduleInterval }}</span>
        </template>
      </el-table-column>
      <el-table-column label="isPaused" width="90" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.isPaused }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="lastSchedulerRun" label="lastSchedulerRun" width="250">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          <span>{{ scope.row.lastSchedulerRun }}</span>
        </template>
      </el-table-column>

      <el-table-column
        label="Actions"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleView(row)">查看</el-button>
          <el-button type="primary" size="mini" @click="handleBackFill(row)">回填</el-button>
          <el-button type="danger" size="mini" @click="handleDeleteGit(row)">删除Git</el-button>
          <el-button type="warning" size="mini" @click="handleDeleteRecord(row)">删除DAG记录</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      :total="total"
      :page.sync="listQuery.pageRq.pageNum"
      :limit.sync="listQuery.pageRq.pageSize"
      @pagination="fetchData"
    />

    <el-dialog title="回填" :visible.sync="backFillVisible" :close-on-click-modal="false" @close="closeBackFill">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="backFillTemp"
        label-position="left"
        label-width="180px"
        style="width: 700px; margin-left:50px;"
      >

        <el-form-item label="dagId" prop="dagId">
          <el-input v-model="backFillTemp.dagId" :disabled="true" />
        </el-form-item>
        <el-form-item label="taskId正则" prop="taskRegex">
          <el-input v-model="backFillTemp.taskRegex" placeholder="taskId正则，不填默认全部" />
        </el-form-item>
        <el-form-item label="markSuccess" prop="markSuccess">
          <el-checkbox v-model="backFillTemp.markSuccess">直接标记为成功，不执行</el-checkbox>
        </el-form-item>
        <el-form-item label="回填开始日期" prop="startDate">
          <div class="block">
            <el-date-picker
              v-model="backFillTemp.startDate"
              type="datetime"
              placeholder="开始日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            />
          </div>
        </el-form-item>
        <el-form-item label="回填结束日期" prop="endDate">
          <div class="block">
            <el-date-picker
              v-model="backFillTemp.endDate"
              type="datetime"
              placeholder="结束日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
            />
          </div>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeBackFill">Cancel</el-button>
        <el-button type="primary" @click="doBackFill()">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getDagList,
  deleteGit,
  deleteRecord,
  backfill
} from '@/api/dag'
import Pagination from '@/components/Pagination'

export default {
  filters: {

  },
  components: { Pagination },
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
        owners: undefined
      },
      backFillVisible: false,
      backFillTemp: {},
      rules: {
        startDate: [
          {
            required: true,
            trigger: 'blur'
          }
        ],
        endDate: [
          {
            required: true,
            trigger: 'blur'
          }
        ]
      }
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
        for (let i = 0; i < this.list.length; i++) {
          const si = this.list[i].scheduleInterval
          if (si.indexOf('type') !== -1) {
            const json = JSON.parse(si)
            this.list[i].scheduleInterval = json.attrs.days + ' days'
          } else {
            this.list[i].scheduleInterval = this.list[i].scheduleInterval.replace(/"/g, '')
          }
        }
        this.total = response.data.total
        this.listQuery.pageRq.pageNum = response.data.pageNum
        this.listQuery.pageRq.pageSize = response.data.pageSize
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.fetchData()
    },
    handleView(row) {
      const toRedirect = process.env.VUE_APP_AIRFLOW_HOST + '/admin/airflow/tree?dag_id=' + row.dagId
      window.open(toRedirect)
    },
    handleBackFill(row) {
      this.backFillVisible = true
      this.backFillTemp = row
    },
    closeBackFill() {
      this.backFillVisible = false
    },
    doBackFill() {
      const params = {
        dag_id: this.backFillTemp.dagId,
        start_date: this.backFillTemp.startDate,
        end_date: this.backFillTemp.endDate,
        task_regex: this.backFillTemp.taskRegex,
        mark_success: this.backFillTemp.markSuccess,
        ignore_first_depends_on_past: true
      }
      const loading = this.$loading({
        lock: true,
        text: '正在删除回填，超时请刷新确认',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      })
      backfill(params).then(res => {
        loading.close()
        this.$message({
          title: 'Success',
          message: '回填成功',
          type: 'success',
          duration: 2000
        })
      }).catch(err => {
        loading.close()
        console.log(err)
        this.$message({
          title: '失败',
          message: err,
          type: 'error',
          duration: 5000
        })
      })
    },
    handleDeleteGit(row) {
      this.$confirm('此操作将从gitlab永久删除该DAG[' + row.dagId + '], 删除2min后, 可以在airflow手动删除dag记录. 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading({
          lock: true,
          text: '正在删除git',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        deleteGit(row.dagId).then(res => {
          loading.close()
          this.$message({
            title: 'Success',
            message: 'git删除成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    },
    handleDeleteRecord(row) {
      this.$confirm('此操作将从airflow永久删除该DAG[' + row.dagId + ']记录, 前提是已经从git删除py文件,并同步到airflow(大约需要2min延迟). 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const loading = this.$loading({
          lock: true,
          text: '正在删除记录',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        deleteRecord(row.dagId).then(res => {
          loading.close()
          this.$message({
            title: 'Success',
            message: '删除记录成功',
            type: 'success',
            duration: 2000
          })
          this.fetchData()
        }).catch(err => {
          console.log(err)
          loading.close()
        })
      })
    }
  }
}
</script>
