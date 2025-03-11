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
        <el-input
          v-model="queryParams.clientId"
          placeholder="请输入客户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求" prop="demandId">
        <el-input
          v-model="queryParams.demandId"
          placeholder="请输入需求"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付状态" prop="payStatus">
        <el-select v-model="queryParams.payStatus" placeholder="请选择支付状态" clearable>
          <el-option
            v-for="dict in dict.type.pay_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="销售员" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入销售员"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
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
      <el-form-item label="更新人" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间">
        <el-date-picker
          v-model="daterangeUpdateTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
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
          v-hasPermi="['manage:clientDealInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['manage:clientDealInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['manage:clientDealInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['manage:clientDealInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="clientDealInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" v-if="columns[0].visible" prop="id" />
        <el-table-column label="客户" :show-overflow-tooltip="true" align="center" v-if="columns[1].visible" prop="clientId" />
        <el-table-column label="需求" :show-overflow-tooltip="true" align="center" v-if="columns[2].visible" prop="demandId" />
        <el-table-column label="总金额" :show-overflow-tooltip="true" align="center" v-if="columns[3].visible" prop="totalPrice" />
        <el-table-column label="支付状态" align="center" v-if="columns[4].visible" prop="payStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_status" :value="scope.row.payStatus"/>
        </template>
      </el-table-column>
        <el-table-column label="合同附件" align="center" v-if="columns[5].visible" prop="contractFile" width="100">
        <template slot-scope="scope">
          <el-link v-show="scope.row.contractFile!=null"
                   :download="getFileName(scope.row.contractFile)"
                   :href="baseUrl+scope.row.contractFile"
                   :underline="false"
                   target="_blank"
          >
            <span style="cursor: pointer;"> 下载 </span>
          </el-link>
        </template>
      </el-table-column>
        <el-table-column label="销售员" :show-overflow-tooltip="true" align="center" v-if="columns[6].visible" prop="userId" />
        <el-table-column label="部门" :show-overflow-tooltip="true" align="center" v-if="columns[7].visible" prop="deptId" />
        <el-table-column label="创建时间" align="center" v-if="columns[8].visible" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="更新人" :show-overflow-tooltip="true" align="center" v-if="columns[9].visible" prop="updateBy" />
        <el-table-column label="更新时间" align="center" v-if="columns[10].visible" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
        <el-table-column label="备注" :show-overflow-tooltip="true" align="center" v-if="columns[11].visible" prop="remark" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['manage:clientDealInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['manage:clientDealInfo:remove']"
          >删除</el-button>
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

    <!-- 添加或修改客户交易对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户" prop="clientId">
          <el-input v-model="form.clientId" placeholder="请输入客户" />
        </el-form-item>
        <el-form-item label="需求" prop="demandId">
          <el-input v-model="form.demandId" placeholder="请输入需求" />
        </el-form-item>
        <el-form-item label="总金额" prop="totalPrice">
          <el-input v-model="form.totalPrice" placeholder="请输入总金额" />
        </el-form-item>
        <el-form-item label="支付状态" prop="payStatus">
          <el-radio-group v-model="form.payStatus">
            <el-radio
              v-for="dict in dict.type.pay_status"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="合同附件" prop="contractFile">
          <file-upload v-model="form.contractFile"/>
        </el-form-item>
        <el-form-item label="销售员" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入销售员" />
        </el-form-item>
        <el-form-item label="部门" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listClientDealInfo, getClientDealInfo, delClientDealInfo, addClientDealInfo, updateClientDealInfo } from "@/api/manage/clientDealInfo";

export default {
  name: "ClientDealInfo",
  dicts: ['pay_status'],
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,
      //表格展示列
      columns: [
        { key: 0, label: '编号', visible: true },
          { key: 1, label: '客户', visible: true },
          { key: 2, label: '需求', visible: true },
          { key: 3, label: '总金额', visible: true },
          { key: 4, label: '支付状态', visible: true },
          { key: 5, label: '合同附件', visible: true },
          { key: 6, label: '销售员', visible: true },
          { key: 7, label: '部门', visible: true },
          { key: 8, label: '创建时间', visible: true },
          { key: 9, label: '更新人', visible: true },
          { key: 10, label: '更新时间', visible: true },
          { key: 11, label: '备注', visible: true },
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
      // 客户交易表格数据
      clientDealInfoList: [],
      // 弹出层标题
      title: "",
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
        payStatus: null,
        userId: null,
        deptId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        clientId: [
          { required: true, message: "客户不能为空", trigger: "blur" }
        ],
        demandId: [
          { required: true, message: "需求不能为空", trigger: "blur" }
        ],
        totalPrice: [
          { required: true, message: "总金额不能为空", trigger: "blur" }
        ],
        payStatus: [
          { required: true, message: "支付状态不能为空", trigger: "change" }
        ],
        contractFile: [
          { required: true, message: "合同附件不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "销售员不能为空", trigger: "blur" }
        ],
        deptId: [
          { required: true, message: "部门不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询客户交易列表 */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeCreateTime && '' != this.daterangeCreateTime) {
        this.queryParams.params["beginCreateTime"] = this.daterangeCreateTime[0];
        this.queryParams.params["endCreateTime"] = this.daterangeCreateTime[1];
      }
      if (null != this.daterangeUpdateTime && '' != this.daterangeUpdateTime) {
        this.queryParams.params["beginUpdateTime"] = this.daterangeUpdateTime[0];
        this.queryParams.params["endUpdateTime"] = this.daterangeUpdateTime[1];
      }
      listClientDealInfo(this.queryParams).then(response => {
        this.clientDealInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
      //获取文件名 此功能只可以下载只有一个文件的
      getFileName(filePath) {
        if (filePath == null) {
          return
        }
        // 提取文件名或根据需求生成文件名
        const fileName = filePath.substring(filePath.lastIndexOf('/') + 1)
        return fileName
      },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        clientId: null,
        demandId: null,
        totalPrice: null,
        payStatus: null,
        contractFile: null,
        userId: null,
        deptId: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCreateTime = [];
      this.daterangeUpdateTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户交易";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getClientDealInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户交易";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateClientDealInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addClientDealInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除客户交易编号为"' + ids + '"的数据项？').then(function() {
        return delClientDealInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('manage/clientDealInfo/export', {
        ...this.queryParams
      }, `clientDealInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
