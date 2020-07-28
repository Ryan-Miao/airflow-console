import RDBMS2HiveOperatorTemplate from './RDBMS2HiveOperator.art'

const plugin = {
  importStatement: ['from operators.rdbms_to_hive_operator import RDBMS2HiveOperator'],
  /**
   * 返回py模板
   * @param taskEntity task对象
   */
  template(taskEntity) {
    const task = JSON.parse(taskEntity.param)
    return RDBMS2HiveOperatorTemplate({
      taskId: taskEntity.taskId,
      description: taskEntity.description,
      task: task
    })
  }
}

export default plugin
