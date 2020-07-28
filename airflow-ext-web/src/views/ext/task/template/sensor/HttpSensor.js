import HttpSensorTemplate from './HttpSensor.art'

const plugin = {
  importStatement: [
    'from airflow.sensors.http_sensor import HttpSensor',
    'import json'
  ],
  /**
   * 返回py模板
   * @param taskEntity task对象
   */
  template(taskEntity) {
    const task = JSON.parse(taskEntity.param)
    return HttpSensorTemplate({
      taskId: taskEntity.taskId,
      task: task
    })
  }
}

export default plugin
