import request from '@/utils/request'

// 查询任务进度列表
export function listTaskProgressInfo(query) {
  return request({
    url: '/manage/taskProgressInfo/list',
    method: 'get',
    params: query
  })
}

// 查询任务进度详细
export function getTaskProgressInfo(id) {
  return request({
    url: '/manage/taskProgressInfo/' + id,
    method: 'get'
  })
}

// 新增任务进度
export function addTaskProgressInfo(data) {
  return request({
    url: '/manage/taskProgressInfo',
    method: 'post',
    data: data
  })
}

// 修改任务进度
export function updateTaskProgressInfo(data) {
  return request({
    url: '/manage/taskProgressInfo',
    method: 'put',
    data: data
  })
}

// 删除任务进度
export function delTaskProgressInfo(id) {
  return request({
    url: '/manage/taskProgressInfo/' + id,
    method: 'delete'
  })
}
