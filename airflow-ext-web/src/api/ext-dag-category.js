import request from '@/utils/request-api'

export function getCategories(params) {
  return request({
    url: '/v1/ext/categories',
    method: 'get',
    params
  })
}

/**
 * add
 * @param data name, description
 */
export function addCategory(data) {
  return request({
    url: '/v1/ext/categories',
    method: 'post',
    data
  })
}

/**
 * update
 * @param data name, description
 */
export function updateCategory(data) {
  return request({
    url: '/v1/ext/categories',
    method: 'put',
    data
  })
}
/**
 * delete
 * @param id id
 */
export function deleteCategory(id) {
  return request({
    url: '/v1/ext/categories/' + id,
    method: 'delete'
  })
}
