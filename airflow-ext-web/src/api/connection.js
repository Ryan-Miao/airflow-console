import request from '@/utils/request-api'

export function getConnList(connType, connId) {
  return request({
    url: '/v1/conns/',
    method: 'get',
    params: {
      connId: connId,
      type: connType
    }
  })
}
