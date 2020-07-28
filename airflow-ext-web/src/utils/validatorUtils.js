/**
 * 校验dagId或者taskId
 * @param dagOrTaskId
 * @returns {boolean}
 */
export function checkId(dagOrTaskId) {
  return /^[a-zA-Z][a-zA-Z0-9,_]*$/.test(dagOrTaskId)
}

export function validateId(rule, value, callback) {
  if (value === undefined || value === '') {
    callback(new Error('请输入唯一id'))
  } else {
    if (checkId(value)) {
      callback()
    } else {
      callback(new Error('Id由英文,数字,下划线组成, 不可以数字开头'))
    }
  }
}
