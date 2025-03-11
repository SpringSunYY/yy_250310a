import request from '@/utils/request'

// 查询客户交易列表
export function listClientDealInfo(query) {
  return request({
    url: '/manage/clientDealInfo/list',
    method: 'get',
    params: query
  })
}

// 查询客户交易详细
export function getClientDealInfo(id) {
  return request({
    url: '/manage/clientDealInfo/' + id,
    method: 'get'
  })
}

// 新增客户交易
export function addClientDealInfo(data) {
  return request({
    url: '/manage/clientDealInfo',
    method: 'post',
    data: data
  })
}

// 修改客户交易
export function updateClientDealInfo(data) {
  return request({
    url: '/manage/clientDealInfo',
    method: 'put',
    data: data
  })
}

// 删除客户交易
export function delClientDealInfo(id) {
  return request({
    url: '/manage/clientDealInfo/' + id,
    method: 'delete'
  })
}
