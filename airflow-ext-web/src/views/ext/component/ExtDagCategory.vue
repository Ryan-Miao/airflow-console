<template>
  <el-select
    v-model="selectVal"
    class="filter-item"
    placeholder="category"
    filterable
    clearable
    @change="setVal"
  >
    <el-option
      v-for="item in categories"
      :key="'id_' + item.id"
      :label="item.name"
      :value="item.id"
    />
  </el-select>

</template>

<script>
import {
  getCategories
} from '@/api/ext-dag-category'

export default {
  props: {
    categoryId: {
      type: Number,
      required: false,
      default: undefined
    },
    status: {
      type: String,
      required: false,
      default: undefined
    }
  },
  data() {
    return {
      categories: [],
      selectVal: this.categoryId
    }
  },
  watch: {
    categoryId(val) {
      if (val) {
        this.selectVal = val
      }
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    setVal() {
      if (this.selectVal === undefined || this.selectVal === '') {
        this.selectVal = 0
      }
      this.$emit('update:categoryId', this.selectVal)
    },
    fetchList() {
      getCategories({ status: this.status }).then(res => {
        this.categories = res.data
      })
    }
  }
}
</script>
