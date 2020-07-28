import request from '@/utils/request'
import requestApi from '@/utils/request-api'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

// 切换为统一认证api token
export function getInfo() {
  return requestApi({
    url: '/v1/users/current',
    method: 'get',
    params: { }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
