import request from '@/utils/request-api'
/**
 * list
 * @param data  {
        pageRq: {
          pageNum: 1,
          pageSize: 10
        },
        dagId: undefined,
        owners: undefined
      }
 */
export function getDagList(data) {
  return request({
    url: '/v1/ext/dags/list',
    method: 'post',
    data
  })
}

/**
 * add
 * @param data ext-dag
 */
export function addExtDag(data) {
  return request({
    url: '/v1/ext/dags/add',
    method: 'post',
    data
  })
}

/**
 * update
 * @param data ext-dag
 */
export function updateExtDag(data) {
  return request({
    url: '/v1/ext/dags/update',
    method: 'put',
    data
  })
}

export function getById(id) {
  return request({
    url: '/v1/ext/dags/get/' + id,
    method: 'get'
  })
}
export function deleteDagById(id) {
  return request({
    url: '/v1/ext/dags/delete/' + id,
    method: 'delete'
  })
}

/**
 *
 * @param data {dagId, taskId, operator}
 */
export function getTaskList(data) {
  return request({
    url: '/v1/ext/dags/tasks',
    method: 'post',
    data
  })
}

/**
 *
 * @param data {
  "dagId": "string",
  "description": "string",
  "param": "json",
  "taskId": "string",
  "taskType": "BashOperator",
}
 */
export function addTask(data) {
  return request({
    url: '/v1/ext/dags/tasks/add',
    method: 'post',
    data
  })
}

/**
 *
 * @param data {
 *     "dagId": "string",
 *      "description": "string",
 *     "param": "json",
 *     "taskId": "string",
 *     "taskType": "BashOperator",
 *  }
 */
export function updateTask(data) {
  return request({
    url: '/v1/ext/dags/tasks/update',
    method: 'post',
    data
  })
}

/**
 * 删除task
 * @param dagId dag Id
 * @param taskId task Id
 */
export function deleteTask(dagId, taskId) {
  return request({
    url: '/v1/ext/dags/tasks/delete',
    method: 'delete',
    params: {
      dagId: dagId,
      taskId: taskId
    }
  })
}

/**
 * push python to git
 * @param data  categoryName, dagPyFileName, dagPyBody
 */
export function pushPyToGit(data) {
  return request({
    url: '/v1/ext/dags/pushToGit',
    method: 'post',
    data
  })
}
