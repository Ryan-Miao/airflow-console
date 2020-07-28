import ExternalTaskSensorTemplate from './ExternalTaskSensor.art'

const plugin = {
  importStatement: ['from airflow.sensors.external_task_sensor import ExternalTaskSensor'],
  /**
   * 返回py模板
   * @param taskEntity task对象
   */
  template(taskEntity) {
    const task = JSON.parse(taskEntity.param)
    return ExternalTaskSensorTemplate({
      taskId: taskEntity.taskId,
      sensor: task
    })
  }
}

export default plugin
