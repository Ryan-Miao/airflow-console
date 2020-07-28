import CompassSensorTemplate from './CompassTaskSensor.art'

const plugin = {
  importStatement: [
    'from dag_utils import compass_utils'
  ],
  /**
   * 返回py模板
   * @param taskEntity task对象
   */
  template(taskEntity) {
    const task = JSON.parse(taskEntity.param)
    return CompassSensorTemplate({
      taskId: taskEntity.taskId,
      task: task
    })
  }
}

export default plugin
