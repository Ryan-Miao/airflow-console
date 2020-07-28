<template>
  <div class="app-container">

    <dag-info :dag-id="dagId" />
    <div style="border-bottom-color: rgba(146,187,230,0.6); border-bottom-style: dashed; margin: 5px" />
    <task-list :dag-id="dagId" />

  </div>

</template>

<script>
import DagInfo from './DagInfo'
import TaskList from './TaskList'

export default {

  components: { DagInfo, TaskList },
  data() {
    return {
      dagId: parseInt(this.$route.params.id),
      extDag: {}

    }
  },
  methods: {

  }
}

</script>

