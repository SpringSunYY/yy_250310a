// 查询任务信息列表
import request from '@/utils/request'

export function getClientCount(query) {
  return request({
    url: '/manage/statics/getClientCount',
    method: 'get',
    params: query
  })
}

export function getDemandCount(query) {
  return request({
    url: '/manage/statics/getDemandCount',
    method: 'get',
    params: query
  })
}

export function getTaskCount(query) {
  return request({
    url: '/manage/statics/getTaskCount',
    method: 'get',
    params: query
  })
}

export function getDealCount(query) {
  return request({
    url: '/manage/statics/getDealCount',
    method: 'get',
    params: query
  })
}
