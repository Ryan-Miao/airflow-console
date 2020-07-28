<template>
  <div class="dag-info">
    <el-row>
      <el-col :span="6">
        <div class="dag-details">
          <span class="dag-field">DAG ID: </span>
          <span class="dag-field-value">{{ dagId + '_' + extDag.name }} </span>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="dag-details">
          <span class="dag-field">DAG owners: </span>
          <span class="dag-field-value">{{ extDag.owners }} </span>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="dag-details">
          <span class="dag-field">DAG cron: </span>
          <span class="dag-field-value">{{ extDag.cron }} </span>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24">
        <div class="dag-details">
          <span class="dag-field">DAG Description: </span>
          <span class="dag-field-value"> {{ extDag.description }} </span>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="8">
        <div class="dag-details">
          <el-popover
            placement="right"
            title="DAG内部依赖"
            width="500"
            trigger="hover"
            popper-class="tip"
          >
            <span slot="reference" class="dag-field">DAG Dependency<i class="el-icon-info" /> : </span>

            <pre>{{ tip.dependencies }}</pre>
          </el-popover>
          <el-input
            v-model="extDag.taskDependency"
            type="textarea"
            placeholder="t1>>t2;
[t3,t4]>>t5>>t6"
            :autosize="{ minRows: 3, maxRows: 5}"
            size="small"
          />
        </div>
      </el-col>

    </el-row>

    <el-row>
      <el-col :span="4">
        <div class="dag-details">
          <el-button
            type="primary"
            plain
            round
            icon="el-icon-edit"
            class="dag-action"
            @click="updateDependency"
          >Update Dependency</el-button>
        </div>
      </el-col>
      <el-col :span="18">
        <div class="dag-details">
          <el-button
            type="warning"
            plain
            round
            icon="el-icon-s-promotion"
            class="dag-action"
            @click="generatePy"
          >提交</el-button>
        </div>
      </el-col>
      <el-col :span="2">
        <div class="dag-details">
          <el-button
            type="info"
            plain
            round
            icon="el-icon-back"
            class="dag-action"
            @click="goBack"
          >Go Back</el-button>
        </div>
      </el-col>
    </el-row>

    <generate-py
      :generator-visible.sync="generatorVisible"
      :ext-dag="extDag"
    />

  </div>
</template>

<script>

import {
  getById,
  updateExtDag
} from '@/api/ext-dag'
import GeneratePy from './GeneratePy'

export default {
  components: {
    GeneratePy
  },
  props: {
    dagId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      extDag: {},
      generatorVisible: false,
      tip: {
        dependencies: '使用a>>b表示 b依赖a;使用[a,b] >> c表示c依赖a和b；\n不同语句可以换行来分割. e.g. \n' +
          'a>>b\nc>>d\n[b,d]>>m\n[a,c]>>n\n' +
          'a>>b>>c>>d>>e'
      }
    }
  },
  created() {
    getById(this.dagId).then(response => {
      this.extDag = response.data
    })
  },
  methods: {
    updateDependency() {
      updateExtDag({ id: this.dagId, taskDependency: this.extDag.taskDependency })
        .then(response => {
          this.$notify({
            title: 'Success',
            message: 'Update Success',
            type: 'success',
            duration: 2000
          })
        })
    },
    goBack(row) {
      this.$router.push({
        path: '/ext/dags/'
      })
    },
    generatePy() {
      this.generatorVisible = true
    }

  }
}

</script>
<style lang="scss" scoped>
  .dag-info {
    padding: 5px;
  }
  .dag-details {
    padding-top: 10px;
    padding-bottom: 5px;

    .dag-field-value {
      color: #5e636e;
      font-weight: 500;
    }
  }
</style>

<style>
  .tip {
    background-color: #46556d;
    color: white;
  }
  .el-popover__title {
    color: white;
  }
  .task-plugin-field {
    padding: 10px;
  }
</style>
