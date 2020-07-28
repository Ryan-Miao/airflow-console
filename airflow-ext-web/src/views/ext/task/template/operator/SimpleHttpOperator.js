import SimpleHttpOperatorTemplate from './SimpleHttpOperator.art'

const plugin = {
  importStatement: [
    'from airflow.operators.http_operator import SimpleHttpOperator',
    'import json'
  ],
  /**
   * 返回py模板
   * @param taskEntity task对象
   */
  template(taskEntity) {
    const task = JSON.parse(taskEntity.param)
    return SimpleHttpOperatorTemplate({
      taskId: taskEntity.taskId,
      description: taskEntity.description,
      task: task
    })
  }
}

export default plugin
