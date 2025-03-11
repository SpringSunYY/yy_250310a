import request from '@/utils/request'

// 查询任务信息列表
export function listTaskInfo(query) {
  return request({
    url: '/manage/taskInfo/list',
    method: 'get',
    params: query
  })
}

// 查询任务信息详细
export function getTaskInfo(id) {
  return request({
    url: '/manage/taskInfo/' + id,
    method: 'get'
  })
}

// 新增任务信息
export function addTaskInfo(data) {
  return request({
    url: '/manage/taskInfo',
    method: 'post',
    data: data
  })
}

// 修改任务信息
export function updateTaskInfo(data) {
  return request({
    url: '/manage/taskInfo',
    method: 'put',
    data: data
  })
}

// 删除任务信息
export function delTaskInfo(id) {
  return request({
    url: '/manage/taskInfo/' + id,
    method: 'delete'
  })
}
