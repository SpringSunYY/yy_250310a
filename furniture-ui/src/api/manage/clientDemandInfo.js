import request from '@/utils/request'

// 查询客户需求列表
export function listClientDemandInfo(query) {
  return request({
    url: '/manage/clientDemandInfo/list',
    method: 'get',
    params: query
  })
}

// 查询客户需求详细
export function getClientDemandInfo(id) {
  return request({
    url: '/manage/clientDemandInfo/' + id,
    method: 'get'
  })
}

// 新增客户需求
export function addClientDemandInfo(data) {
  return request({
    url: '/manage/clientDemandInfo',
    method: 'post',
    data: data
  })
}

// 修改客户需求
export function updateClientDemandInfo(data) {
  return request({
    url: '/manage/clientDemandInfo',
    method: 'put',
    data: data
  })
}

// 删除客户需求
export function delClientDemandInfo(id) {
  return request({
    url: '/manage/clientDemandInfo/' + id,
    method: 'delete'
  })
}
