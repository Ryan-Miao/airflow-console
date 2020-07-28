import Hive2RDBMSOperatorTemplate from './Hive2RDBMSOperator.art'

const plugin = {
  importStatement: ['from operators.hive_to_rdbms_operator import Hive2RDBMSOperator'],
  /**
   * 返回py模板
   * @param taskEntity task对象
   */
  template(taskEntity) {
    const task = JSON.parse(taskEntity.param)
    return Hive2RDBMSOperatorTemplate({
      taskId: taskEntity.taskId,
      description: taskEntity.description,
      task: task
    })
  }
}

export default plugin
