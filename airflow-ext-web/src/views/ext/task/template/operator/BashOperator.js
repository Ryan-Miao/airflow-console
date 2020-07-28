import BashOperatorTemplate from './BashOperator.art'

const plugin = {
  importStatement: ['from airflow.operators.bash_operator import BashOperator'],
  /**
   * 返回py模板
   * @param taskEntity task对象
   */
  template(taskEntity) {
    const task = JSON.parse(taskEntity.param)
    return BashOperatorTemplate({
      taskId: taskEntity.taskId,
      description: taskEntity.description,
      task: task
    })
  }
}

export default plugin
