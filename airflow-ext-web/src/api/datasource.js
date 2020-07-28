import request from '@/utils/request-api'

export function getDatasourceList(data) {
  return request({
    url: '/v1/dag/list',
    method: 'post',
    data
  })
}

export function saveDatasource(data) {
  return request({
    url: '/v1/dag/save',
    method: 'post',
    data
  })
}

export function deleteDatasource(datasourceName) {
  return request({
    url: '/v1/dag/delete/' + datasourceName,
    method: 'delete'
  })
}

/**
 * 获取数据源下的所有表列表
 */
export function getTableNames(datasourceName) {
  return request({
    url: '/v1/api/tools/details/dag/' + datasourceName + '/tables',
    method: 'get'
  })
}

/**
 * 获取数据源下的表的表结构字段
 */
export function getTableColumns(datasourceName, tableName) {
  return request({
    url: '/v1/api/tools/details/dag/' + datasourceName + '/tables/' + tableName,
    method: 'get'
  })
}

/**
 * 获取数据源name列表
 * list<String>
 */
export function getDatasourceNameList() {
  return request({
    url: '/v1/api/tools/details/dag/',
    method: 'get'
  })
}

