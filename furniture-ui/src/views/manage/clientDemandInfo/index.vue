<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="客户" prop="clientId">
        <el-select
          v-model="queryParams.clientId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入客户名称"
          :remote-method="selectClientInfoList"
          :loading="clientLoading"
        >
          <el-option
            v-for="item in clientInfoList"
            :key="item.id"
            :label="item.clientName"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="需求名称" prop="demandName">
        <el-input
          v-model="queryParams.demandName"
          placeholder="请输入需求名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker
          v-model="daterangeExpectStartTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
          v-model="daterangeExpectEndTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="需求状态" prop="demandStatus">
        <el-select v-model="queryParams.demandStatus" placeholder="请选择需求状态" clearable>
          <el-option
            v-for="dict in dict.type.demand_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="销售员" prop="userId">
        <el-select
          v-model="queryParams.userId"
          filterable
          remote
          reserve-keyword
          placeholder="请输入用户账号"
          :remote-method="selectSalesUserInfoList"
          :loading="salesUserLoading"
        >
          <el-option
            v-for="item in salesUserInfoList"
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
      <!--      <el-form-item label="创建时间">-->
      <!--        <el-date-picker-->
      <!--          v-model="daterangeCreateTime"-->
      <!--          style="width: 240px"-->
      <!--          value-format="yyyy-MM-dd"-->
      <!--          type="daterange"-->
      <!--          range-separator="-"-->
      <!--          start-placeholder="开始日期"-->
      <!--          end-placeholder="结束日期"-->
      <!--        ></el-date-picker>-->
      <!--      </el-form-item>-->
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
          v-hasPermi="['manage:clientDemandInfo:add']"
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
          v-hasPermi="['manage:clientDemandInfo:edit']"
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
          v-hasPermi="['manage:clientDemandInfo:remove']"
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
          v-hasPermi="['manage:clientDemandInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="clientDemandInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id"/>
      <el-table-column label="客户" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible"
                       prop="clientName"
      />
      <el-table-column label="需求名称" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible"
                       prop="demandName"
      />
      <el-table-column label="房屋面积" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible"
                       prop="floorSpace"
      />
      <el-table-column label="地址" :show-overflow-tooltip="true" align="center" v-if="columns[4].visible"
                       prop="address"
      />
      <el-table-column label="房屋朝向" :show-overflow-tooltip="true" align="center" v-if="columns[5].visible"
                       prop="buildOrientation"
      />
      <el-table-column label="装修风格" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible"
                       prop="furnishStyle"
      />
      <el-table-column label="总预算" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible"
                       prop="totalBudget"
      />
      <el-table-column label="期望开始时间" align="center" v-if="columns[8].visible" prop="expectStartTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expectStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="期望结束时间" align="center" v-if="columns[9].visible" prop="expectEndTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expectEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="需求状态" align="center" v-if="columns[10].visible" prop="demandStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.demand_status" :value="scope.row.demandStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="销售员" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible"
                       prop="userName"
      />
      <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[12].visible"
                       prop="deptName"
      />
      <el-table-column label="创建时间" align="center" v-if="columns[13].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[14].visible"
                       prop="updateBy"
      />
      <el-table-column label="更新时间" align="center" v-if="columns[15].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[16].visible"
                       prop="remark"
      />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:clientDemandInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:clientDemandInfo:remove']"
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

    <!-- 添加或修改客户需求对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="客户" prop="clientId">
          <el-select
            v-model="form.clientId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入客户名称"
            :remote-method="selectClientInfoList"
            :loading="clientLoading"
          >
            <el-option
              v-for="item in clientInfoList"
              :key="item.id"
              :label="item.clientName"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="需求名称" prop="demandName">
          <el-input v-model="form.demandName" placeholder="请输入需求名称"/>
        </el-form-item>
        <el-form-item label="房屋面积" prop="floorSpace">
          <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.floorSpace" placeholder="请输入房屋面积"/>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址"/>
        </el-form-item>
        <el-form-item label="房屋朝向" prop="buildOrientation">
          <el-input v-model="form.buildOrientation" placeholder="请输入房屋朝向"/>
        </el-form-item>
        <el-form-item label="装修风格" prop="furnishStyle">
          <el-input v-model="form.furnishStyle" placeholder="请输入装修风格"/>
        </el-form-item>
        <el-form-item label="总预算" prop="totalBudget">
          <el-input-number :precision="2" :step="0.1" :min="0" v-model="form.totalBudget" placeholder="请输入总预算"/>
        </el-form-item>
        <el-form-item label="期望开始时间" prop="expectStartTime">
          <el-date-picker clearable
                          v-model="form.expectStartTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择期望开始时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="期望结束时间" prop="expectEndTime">
          <el-date-picker clearable
                          v-model="form.expectEndTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择期望结束时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="需求状态" prop="demandStatus">
          <el-radio-group v-model="form.demandStatus">
            <el-radio
              v-for="dict in dict.type.demand_status"
              :key="dict.value"
              :label="dict.value"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="销售员" prop="userId">
          <el-select
            v-model="form.userId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入用户账号"
            :remote-method="selectSalesUserInfoList"
            :loading="salesUserLoading"
          >
            <el-option
              v-for="item in salesUserInfoList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            >
            </el-option>
          </el-select>
        </el-form-item>
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
  listClientDemandInfo,
  getClientDemandInfo,
  delClientDemandInfo,
  addClientDemandInfo,
  updateClientDemandInfo
} from '@/api/manage/clientDemandInfo'
import { allocatedUserList } from '@/api/system/role'
import { listClientInfo } from '@/api/manage/clientInfo'

export default {
  name: 'ClientDemandInfo',
  dicts: ['demand_status'],
  data() {
    return {
      //销售相关信息
      salesUserInfoList: [],
      salesUserLoading: false,
      salesUserQueryParams: {
        userName: '',
        roleId: 100,
        pageNum: 1,
        pageSize: 100
      },
      //客户相关信息
      clientInfoList: [],
      clientLoading: false,
      clientQueryParams: {
        clientName: '',
        pageNum: 1,
        pageSize: 100
      },
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: false },
        { key: 1, label: '客户', visible: true },
        { key: 2, label: '需求名称', visible: true },
        { key: 3, label: '房屋面积', visible: true },
        { key: 4, label: '地址', visible: true },
        { key: 5, label: '房屋朝向', visible: true },
        { key: 6, label: '装修风格', visible: true },
        { key: 7, label: '总预算', visible: true },
        { key: 8, label: '期望开始时间', visible: true },
        { key: 9, label: '期望结束时间', visible: true },
        { key: 10, label: '需求状态', visible: true },
        { key: 11, label: '销售员', visible: true },
        { key: 12, label: '部门', visible: false },
        { key: 13, label: '创建时间', visible: false },
        { key: 14, label: '更新人', visible: false },
        { key: 15, label: '更新时间', visible: false },
        { key: 16, label: '备注', visible: false }
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
      // 客户需求表格数据
      clientDemandInfoList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 备注时间范围
      daterangeExpectStartTime: [],
      // 备注时间范围
      daterangeExpectEndTime: [],
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
        demandName: null,
        expectStartTime: null,
        expectEndTime: null,
        demandStatus: null,
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
        demandName: [
          { required: true, message: '需求名称不能为空', trigger: 'blur' }
        ],
        floorSpace: [
          { required: true, message: '房屋面积不能为空', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '地址不能为空', trigger: 'blur' }
        ],
        buildOrientation: [
          { required: true, message: '房屋朝向不能为空', trigger: 'blur' }
        ],
        furnishStyle: [
          { required: true, message: '装修风格不能为空', trigger: 'blur' }
        ],
        totalBudget: [
          { required: true, message: '总预算不能为空', trigger: 'blur' }
        ],
        expectStartTime: [
          { required: true, message: '期望开始时间不能为空', trigger: 'blur' }
        ],
        expectEndTime: [
          { required: true, message: '期望结束时间不能为空', trigger: 'blur' }
        ],
        demandStatus: [
          { required: true, message: '需求状态不能为空', trigger: 'change' }
        ],
        userId: [
          { required: true, message: '销售员不能为空', trigger: 'blur' }
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
    this.getSalesUserInfoList()
    this.getClientInfoList()
  },
  methods: {
    /**
     * 获取客户列表推荐
     * @param query
     */
    selectClientInfoList(query) {
      if (query !== '') {
        this.clientLoading = true
        this.clientQueryParams.clientName = query
        setTimeout(() => {
          this.getClientInfoList()
        }, 200)
      } else {
        this.clientInfoList = []
        this.clientQueryParams.userName = null
      }
    },
    /**
     * 获取客户信息列表
     */
    getClientInfoList() {
      //添加查询参数
      if (this.form.clientId != null) {
        this.clientQueryParams.id = this.form.userId
      } else {
        this.clientQueryParams.id = null
      }
      if (this.clientQueryParams.clientName !== '') {
        this.clientQueryParams.id = null
      }
      listClientInfo(this.clientQueryParams).then(res => {
        this.clientInfoList = res?.rows
        this.clientLoading = false
      })
    },
    /**
     * 获取销售用户列表推荐
     * @param query
     */
    selectSalesUserInfoList(query) {
      if (query !== '') {
        this.salesUserLoading = true
        this.salesUserQueryParams.userName = query
        setTimeout(() => {
          this.getSalesUserInfoList()
        }, 200)
      } else {
        this.salesUserInfoList = []
        this.salesUserQueryParams.userName = null
      }
    },
    /**
     * 获取销售用户信息列表
     */
    getSalesUserInfoList() {
      //添加查询参数
      if (this.form.userId != null) {
        this.salesUserQueryParams.userId = this.form.userId
      } else {
        this.salesUserQueryParams.userId = null
      }
      if (this.salesUserQueryParams.userName !== '') {
        this.salesUserQueryParams.userId = null
      }
      allocatedUserList(this.salesUserQueryParams).then(res => {
        this.salesUserInfoList = res?.rows
        this.salesUserLoading = false
      })
    },
    /** 查询客户需求列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (null != this.daterangeExpectStartTime && '' != this.daterangeExpectStartTime) {
        this.queryParams.params['beginExpectStartTime'] = this.daterangeExpectStartTime[0]
        this.queryParams.params['endExpectStartTime'] = this.daterangeExpectStartTime[1]
      }
      if (null != this.daterangeExpectEndTime && '' != this.daterangeExpectEndTime) {
        this.queryParams.params['beginExpectEndTime'] = this.daterangeExpectEndTime[0]
        this.queryParams.params['endExpectEndTime'] = this.daterangeExpectEndTime[1]
      }
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params['beginCreateTime'] = this.daterangeCreateTime[0]
        this.queryParams.params['endCreateTime'] = this.daterangeCreateTime[1]
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params['beginUpdateTime'] = this.daterangeUpdateTime[0]
        this.queryParams.params['endUpdateTime'] = this.daterangeUpdateTime[1]
      }
      listClientDemandInfo(this.queryParams).then(response => {
        this.clientDemandInfoList = response.rows
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
        demandName: null,
        floorSpace: null,
        address: null,
        buildOrientation: null,
        furnishStyle: null,
        totalBudget: null,
        expectStartTime: null,
        expectEndTime: null,
        demandStatus: null,
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
      this.daterangeExpectStartTime = []
      this.daterangeExpectEndTime = []
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
      this.title = '添加客户需求'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getClientDemandInfo(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改客户需求'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateClientDemandInfo(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addClientDemandInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除客户需求编号为"' + ids + '"的数据项？').then(function() {
        return delClientDemandInfo(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/clientDemandInfo/export', {
        ...this.queryParams
      }, `clientDemandInfo_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
