<template>
  <div>
    <el-select
      v-model="selectTypeVal"
      class="filter-item"
      filterable
      placeholder="选择连接类型"
      clearable
    >
      <el-option
        v-for="item in connTypes"
        :key="item"
        :label="item"
        :value="item"
      />
    </el-select>
    <el-select
      v-model="selectVal"
      class="filter-item"
      filterable
      placeholder="选择conn"
      clearable
      @change="setVal"
    >
      <el-option
        v-for="item in conns"
        :key="item.id"
        :label="item.connId"
        :value="item.connId"
      >
        <span>{{ item.connId }}</span>
        <span style="color: #8492a6; font-size: 13px; padding-left: 10px;">{{ item.host }} -P {{ item.port }} -U {{ item.login }}</span>
      </el-option>
    </el-select>
  </div>
</template>

<script>
import {
  getConnList
} from '@/api/connection'

export default {
  props: {
    connId: {
      type: String,
      required: false,
      default: undefined
    }
  },
  data() {
    return {
      connTypes: [
        'postgres',
        'mysql',
        'hiveserver2',
        'http'
      ],
      conns: [],
      // 全量conn id map
      connIdMaps: new Map(),
      // type conn Map
      connTypeMaps: new Map(),
      // conn
      selectVal: this.connId,
      // conn type
      selectTypeVal: undefined
    }
  },
  watch: {
    connId: {
      handler(val) {
        if (val) {
          if (this.connIdMaps.size === 0) {
            this.fetchConn(() => {
              const conn = this.connIdMaps.get(val)
              this.selectTypeVal = conn.connType
              this.selectVal = val
            })
          }
        }
      },
      immediate: true,
      deep: true
    },
    selectTypeVal: {
      handler(val) {
        console.log(val)
        this.conns = this.connTypeMaps.get(val)
      }
    }
  },
  created() {
    this.fetchConn(() => {})
  },
  methods: {
    fetchConn(callback) {
      getConnList().then(response => {
        const data = response.data
        if (this.connIdMaps.size !== 0) {
          callback()
          return
        }

        for (let i = 0; i < data.length; i++) {
          const conn = data[i]
          this.connIdMaps.set(conn.connId, conn)
          this.addConnTypeMap(conn)
        }

        callback()
      })
    },
    addConnTypeMap(conn) {
      const list = this.connTypeMaps.get(conn.connType)
      if (list) {
        list.push(conn)
      } else {
        this.connTypeMaps.set(conn.connType, [conn])
      }
    },
    setVal() {
      this.$emit('update:connId', this.selectVal)
    }
  }
}

</script>
