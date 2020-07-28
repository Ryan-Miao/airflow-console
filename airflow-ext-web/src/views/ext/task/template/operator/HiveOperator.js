import HiveOperatorTemplate from './HiveOperator.art'

const plugin = {
  importStatement: ['from operators.hive_operator import HiveOperator'],
  /**
   * 返回py模板
   * @param taskEntity task对象
   */
  template(taskEntity) {
    const task = JSON.parse(taskEntity.param)
    let engineSql = ''
    switch (task.engine) {
      case 'mr': engineSql = 'set mapreduce.job.queuename=root.users.' +
        task.owner + ';\nset hive.execution.engine=mr;\n'
        break
      case 'spark': engineSql = 'set mapreduce.job.queuename=root.users.' +
        task.owner + ';\nset hive.execution.engine=spark;\n'
        break
      case 'tez': engineSql = 'set tez.queue.name=root.users.' +
        task.owner + ';\nset hive.execution.engine=tez;\n'
        break
    }
    task.hql = engineSql + task.hql
    return HiveOperatorTemplate({
      taskId: taskEntity.taskId,
      description: taskEntity.description,
      task: task
    })
  }
}

export default plugin
