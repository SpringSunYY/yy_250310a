<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!--      <el-form-item label="编号" prop="id">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.id"-->
      <!--          placeholder="请输入编号"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="客户" prop="clientId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.clientId"-->
      <!--          placeholder="请输入客户"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="需求" prop="demandId">
        <el-select
          v-model="queryParams.demandId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入需求名称"
          :remote-method="selectDemandInfoList"
          :loading="demandLoading"
        >
          <el-option
            v-for="item in demandInfoList"
            :key="item.id"
            :label="item.demandName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务" prop="taskId">
        <el-select
          v-model="queryParams.taskId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入任务名称"
          :remote-method="selectTaskInfoList"
          :loading="taskLoading"
        >
          <el-option
            v-for="item in taskInfoList"
            :key="item.id"
            :label="item.taskName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务状态" prop="taskStatus">
        <el-select v-model="queryParams.taskStatus" placeholder="请选择任务状态" clearable>
          <el-option
            v-for="dict in dict.type.task_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="装修人员" prop="userId">
        <el-select
          v-model="queryParams.userId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户账号"
          :remote-method="selectFurnishUserInfoList"
          :loading="furnishUserLoading"
        >
          <el-option
            v-for="item in furnishUserInfoList"
            :key="item.userId"
            :label="item.userName"
            :value="item.userId"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <!--      <el-form-item label="部门" prop="deptId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.deptId"-->
      <!--          placeholder="请输入部门"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="创建时间">
        <el-date-picker
          v-model="daterangeCreateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <!--      <el-form-item label="更新人" prop="updateBy">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.updateBy"-->
      <!--          placeholder="请输入更新人"-->
      <!--          clearable-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="更新时间">-->
      <!--        <el-date-picker-->
      <!--          v-model="daterangeUpdateTime"-->
      <!--          style="width: 240px"-->
      <!--          value-format="yyyy-MM-dd"-->
      <!--          type="daterange"-->
      <!--          range-separator="-"-->
      <!--          start-placeholder="开始日期"-->
      <!--          end-placeholder="结束日期"-->
      <!--        ></el-date-picker>-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['manage:taskProgressInfo:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:taskProgressInfo:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:taskProgressInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:taskProgressInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="taskProgressInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="客户" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="clientName"
      />
      <el-table-column label="需求" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="demandName"
      />
      <el-table-column label="任务" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="taskName"
      />
      <el-table-column label="进度描述" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="progress"
      />
      <el-table-column label="任务状态" align="center" v-if="columns[5].visible" prop="taskStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.task_status" :value="scope.row.taskStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="凭证" align="center" v-if="columns[6].visible" prop="progressImage" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.progressImage" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="装修人员" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="userName"
      />
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[8].visible"
                       prop="deptName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[9].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[10].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[11].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:taskProgressInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:taskProgressInfo:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改任务进度对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="客户" prop="clientId">-->
<!--          <el-input v-model="form.clientId" placeholder="请输入客户"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="需求" prop="demandId">-->
<!--          <el-input v-model="form.demandId" placeholder="请输入需求"/>-->
<!--        </el-form-item>-->
        <el-form-item label="任务" prop="taskId">
          <el-select
            v-model="form.taskId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入任务名称"
            :remote-method="selectTaskInfoList"
            :loading="taskLoading"
          >
            <el-option
              v-for="item in taskInfoList"
              :key="item.id"
              :label="item.taskName"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="进度描述" prop="progress">
          <el-input v-model="form.progress" placeholder="请输入进度描述"/>
        </el-form-item>
        <el-form-item label="凭证" prop="progressImage">
          <image-upload v-model="form.progressImage"/>
        </el-form-item>
        <el-form-item label="任务状态" prop="taskStatus">
          <el-radio-group v-model="form.taskStatus">
            <el-radio
              v-for="dict in dict.type.task_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
<!--        <el-form-item label="装修人员" prop="userId">-->
<!--          <el-input v-model="form.userId" placeholder="请输入装修人员"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="部门" prop="deptId">-->
<!--          <el-input v-model="form.deptId" placeholder="请输入部门"/>-->
<!--        </el-form-item>-->
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listTaskProgressInfo,
  getTaskProgressInfo,
  delTaskProgressInfo,
  addTaskProgressInfo,
  updateTaskProgressInfo
} from '@/api/manage/taskProgressInfo'
import { allocatedUserList } from '@/api/system/role'
import { listClientDemandInfo } from '@/api/manage/clientDemandInfo'
import { listTaskInfo } from '@/api/manage/taskInfo'

export default {
  name: 'TaskProgressInfo',
  dicts: ['task_status'],
  data() {
    return {
      //装修相关信息
      furnishUserInfoList: [],
      furnishUserLoading: false,
      furnishUserQueryParams: {
        userName: '',
        roleId: 101,
        pageNum: 1,
        pageSize: 100
      },
      //客户需求相关信息
      demandInfoList: [],
      demandLoading: false,
      demandQueryParams: {
        demandName: '',
        pageNum: 1,
        pageSize: 100
      },
      //任务相关信息
      taskInfoList: [],
      taskLoading: false,
      taskQueryParams: {
        taskName: '',
        pageNum: 1,
        pageSize: 100
      },
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: false },
        { key: 1, label: '客户', visible: true },
        { key: 2, label: '需求', visible: true },
        { key: 3, label: '任务', visible: true },
        { key: 4, label: '进度描述', visible: true },
        { key: 5, label: '任务状态', visible: true },
        { key: 6, label: '凭证', visible: true },
        { key: 7, label: '装修人员', visible: true },
        { key: 8, label: '部门', visible: false },
        { key: 9, label: '创建时间', visible: true },
        { key: 10, label: '更新人', visible: false },
        { key: 11, label: '更新时间', visible: false },
        { key: 12, label: '备注', visible: false }
      ],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 任务进度表格数据
      taskProgressInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeCreateTime: [],
      // 备注时间范围
      daterangeUpdateTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: null,
        clientId: null,
        demandId: null,
        taskId: null,
        taskStatus: null,
        userId: null,
        deptId: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        clientId: [
          { required: true, message: '客户不能为空', trigger: 'blur' }
        ],
        demandId: [
          { required: true, message: '需求不能为空', trigger: 'blur' }
        ],
        taskId: [
          { required: true, message: '任务不能为空', trigger: 'blur' }
        ],
        progress: [
          { required: true, message: '进度描述不能为空', trigger: 'blur' }
        ],
        taskStatus: [
          { required: true, message: '任务状态不能为空', trigger: 'change' }
        ],
        userId: [
          { required: true, message: '装修人员不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '部门不能为空', trigger: 'blur' }
        ],
        createTime: [
          { required: true, message: '创建时间不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getDemandInfoList()
    this.getTaskInfoList()
    this.getFurnishUserInfoList()
  },
  methods: {
    /**
     * 获取销售用户列表推荐
     * @param query
     */
    selectFurnishUserInfoList(query) {
      if (query !== '') {
        this.furnishUserLoading = true
        this.furnishUserQueryParams.userName = query
        setTimeout(() => {
          this.getFurnishUserInfoList()
        }, 200)
      } else {
        this.furnishUserInfoList = []
        this.furnishUserQueryParams.userName = null
      }
    },
    /**
     * 获取销售用户信息列表
     */
    getFurnishUserInfoList() {
      //添加查询参数
      if (this.form.userId != null) {
        this.furnishUserQueryParams.userId = this.form.userId
      } else {
        this.furnishUserQueryParams.userId = null
      }
      if (this.furnishUserQueryParams.userName !== '') {
        this.furnishUserQueryParams.userId = null
      }
      allocatedUserList(this.furnishUserQueryParams).then(res => {
        this.furnishUserInfoList = res?.rows
        this.furnishUserLoading = false
      })
    },
    /**
     * 获取客户需求列表推荐
     * @param query
     */
    selectDemandInfoList(query) {
      if (query !== '') {
        this.demandLoading = true
        this.demandQueryParams.demandName = query
        setTimeout(() => {
          this.getDemandInfoList()
        }, 200)
      } else {
        this.demandInfoList = []
        this.demandQueryParams.userName = null
      }
    },
    /**
     * 获取客户需求信息列表
     */
    getDemandInfoList() {
      //添加查询参数
      if (this.form.clientId != null) {
        this.demandQueryParams.id = this.form.userId
      } else {
        this.demandQueryParams.id = null
      }
      if (this.demandQueryParams.demandName !== '') {
        this.demandQueryParams.id = null
      }
      listClientDemandInfo(this.demandQueryParams).then(res => {
        this.demandLoading = false
        this.demandInfoList = res?.rows
      })
    },
    /**
     * 获取任务列表推荐
     * @param query
     */
    selectTaskInfoList(query) {
      if (query !== '') {
        this.taskLoading = true
        this.taskQueryParams.taskName = query
        setTimeout(() => {
          this.getTaskInfoList()
        }, 200)
      } else {
        this.taskInfoList = []
        this.taskQueryParams.taskName = null
      }
    },
    /**
     * 获取任务列表
     */
    getTaskInfoList() {
      //添加查询参数
      if (this.form.taskId != null) {
        this.taskQueryParams.id = this.form.userId
      } else {
        this.taskQueryParams.id = null
      }
      if (this.taskQueryParams.taskName !== '') {
        this.taskQueryParams.id = null
      }
      listTaskInfo(this.taskQueryParams).then(res => {
        this.taskLoading = false
        this.taskInfoList = res?.rows
      })
    },
    /** 查询任务进度列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listTaskProgressInfo(this.queryParams).then(response => {
        this.taskProgressInfoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        clientId: null,
        demandId: null,
        taskId: null,
        progress: null,
        taskStatus: null,
        userId: null,
        deptId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = []
      this.daterangeUpdateTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加任务进度'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getTaskProgressInfo(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改任务进度'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateTaskProgressInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addTaskProgressInfo(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除任务进度编号为"' + ids + '"的数据项？').then(function() {
        return delTaskProgressInfo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/taskProgressInfo/export', {
        ...this.queryParams
      }, `taskProgressInfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
