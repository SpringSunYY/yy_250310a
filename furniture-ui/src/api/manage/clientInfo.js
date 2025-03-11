import request from '@/utils/request'

// 查询客户信息列表
export function listClientInfo(query) {
  return request({
    url: '/manage/clientInfo/list',
    method: 'get',
    params: query
  })
}

// 查询客户信息详细
export function getClientInfo(id) {
  return request({
    url: '/manage/clientInfo/' + id,
    method: 'get'
  })
}

// 新增客户信息
export function addClientInfo(data) {
  return request({
    url: '/manage/clientInfo',
    method: 'post',
    data: data
  })
}

// 修改客户信息
export function updateClientInfo(data) {
  return request({
    url: '/manage/clientInfo',
    method: 'put',
    data: data
  })
}

// 删除客户信息
export function delClientInfo(id) {
  return request({
    url: '/manage/clientInfo/' + id,
    method: 'delete'
  })
}
