

角色

> 管理员：可以查看修改所有内容
>
> 项目经理：可以分配自己部门以下的内容，查看和修改
>
> 销售员：可以添加自己的交易，添加客户信息，查看自己创建的交易和客户需求
>
> 施工人员：项目经理为施工人员分配任务，然后施工人员可以看到自己的任务，创建对应的任务进度



添加客户：销售添加客户信息
添加用户需求：添加客户需求表——》项目经理查看需求表，上传方案——》销售创建交易表——》根据是否支付，修改支付状态，上传文件（pdf合同）。
销售业绩统计：统计每日已支付的金额，和项目数量等（可以随便发挥下）

项目经理添加任务分配：关联交易表——》选择分配施工人员

装修任务上报：关联任务分配表：施工人员在自己的任务列表查看项目，填写项目情况，上传图片



补充点细节，
1.要求是要多表联结查询
2.权限管理：
销售员对应客户管理模块
施工人员对应：装修任务管理
项目经理：项目劵模块
系统管理员全功能模块权限
3.文档里的数据库表设计只设计了员工的id，实际页面要对应人名的信息，有不合理的在讨论
4.去掉若依的redis，
5.更改项目ruoyi-的一系列前缀名，怕有点膈应，我有看到git上有改名的软件
6.完成了一个大模块期望能截图先看一下



### 客户信息表：tb_client_info

| 字段           | 类型     | 长度 | 键类型                   | null | 描述     |
| :------------- | -------- | ---- | ------------------------ | ---- | -------- |
| id             | bigint   |      | 主键                     | 否   | 编号     |
| client_name    | varchar  | 128  |                          | 否   | 客户名称 |
| phone          | varchar  | 128  |                          | 否   | 手机号码 |
| sex            | char     | 1    |                          | 否   | 性别     |
| client_address | varchar  | 128  |                          | 否   | 地址     |
| user_id        | bigint   |      | 外键（sys_user:user_id） | 否   | 创建人   |
| create_time    | datetime |      |                          | 否   | 创建时间 |
| update_by      | varchar  | 64   |                          | 是   | 更新人   |
| update_time    | datetime |      |                          | 是   | 更新时间 |
| remark         | varchar  | 1024 |                          | 是   | 备注     |

```sql
CREATE TABLE tb_client_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '编号',
    client_name VARCHAR(128) NOT NULL COMMENT '客户名称',
    phone VARCHAR(128) NOT NULL COMMENT '手机号码',
    sex CHAR(1) NOT NULL COMMENT '性别（0=男 1=女）',
    client_address VARCHAR(128) NOT NULL COMMENT '地址',
    user_id BIGINT NOT NULL COMMENT '创建人',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_by VARCHAR(64) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    remark VARCHAR(1024) COMMENT '备注',
    PRIMARY KEY (id),
    CONSTRAINT fk_tb_client_info_user_id 
        FOREIGN KEY (user_id) 
        REFERENCES sys_user (user_id) 
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户信息表';
```



### 客户需求表：tb_client_demand_info

| 字段              | 类型     | 长度   | 键类型 | null | 描述         |
| :---------------- | -------- | ------ | ------ | ---- | ------------ |
| id                | bigint   |        | 主键   | 否   | 编号         |
| client_id         | bigint   |        |        | 否   | 客户         |
| demand_name       | varchar  | 128    |        | 否   | 需求名称     |
| floor_space       | float    | (10,2) |        | 否   | 房屋面积     |
| address           | varchar  | 128    |        | 否   | 地址         |
| build_orientation | varchar  | 128    |        | 否   | 房屋朝向     |
| furnish_style     | varchar  | 128    |        | 否   | 装修风格     |
| total_budget      | float    | (10,2) |        | 否   | 总预算       |
| expect_start_time | datetime |        |        | 否   | 期望开始时间 |
| expect_end_time   | datetime |        |        | 否   | 期望结束时间 |
| demand_status     | char     | 1      |        | 否   | 需求状态     |
| user_id           | bigint   |        |        | 否   | 销售员       |
| dept_id           | bigint   |        |        | 否   | 部门         |
| create_time       | datetime |        |        | 否   | 创建时间     |
| update_by         | varchar  | 64     |        | 是   | 更新人       |
| update_time       | datetime |        |        | 是   | 更新时间     |
| remark            | varchar  | 1024   |        | 是   | 备注         |

```sql
CREATE TABLE tb_client_demand_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '编号',
    client_id BIGINT NOT NULL COMMENT '客户',
    demand_name VARCHAR(128) NOT NULL COMMENT '需求名称',
    floor_space DECIMAL(10,2) NOT NULL COMMENT '房屋面积', -- 精度优化为DECIMAL
    address VARCHAR(128) NOT NULL COMMENT '地址',
    build_orientation VARCHAR(128) NOT NULL COMMENT '房屋朝向',
    furnish_style VARCHAR(128) NOT NULL COMMENT '装修风格',
    total_budget DECIMAL(10,2) NOT NULL COMMENT '总预算', -- 精度优化为DECIMAL
    expect_start_time DATETIME NOT NULL COMMENT '期望开始时间',
    expect_end_time DATETIME NOT NULL COMMENT '期望结束时间', -- 修正重复字段
    user_id BIGINT NOT NULL COMMENT '销售员',
    dept_id BIGINT NOT NULL COMMENT '部门',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_by VARCHAR(64) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    remark VARCHAR(1024) COMMENT '备注',
    PRIMARY KEY (id),
    INDEX idx_tb_demand_client (client_id), -- 客户维度查询优化
    INDEX idx_tb_demand_dept (dept_id), -- 部门维度查询优化
    CONSTRAINT fk_tb_demand_info_client 
        FOREIGN KEY (client_id) 
        REFERENCES tb_client_info (id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_demand_info_sales 
        FOREIGN KEY (user_id) 
        REFERENCES sys_user (user_id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_demand_info_dept 
        FOREIGN KEY (dept_id) 
        REFERENCES sys_dept (dept_id) 
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户需求表';
```



### 客户交易表：tb_client_deal_info

| 字段          | 类型     | 长度   | 键类型 | null | 描述     |
| :------------ | -------- | ------ | ------ | ---- | -------- |
| id            | bigint   |        | 主键   | 否   | 编号     |
| client_id     | bigint   |        |        | 否   | 客户     |
| demand_id     | bigint   |        |        | 否   | 需求     |
| total_price   | float    | (10,2) |        | 否   | 总金额   |
| pay_status    | char     | 1      |        | 否   | 支付状态 |
| contract_file | varchar  | 256    |        | 否   | 合同附件 |
| user_id       | bigint   |        |        | 否   | 销售员   |
| dept_id       | bigint   |        |        | 否   | 部门     |
| create_time   | datetime |        |        | 否   | 创建时间 |
| update_by     | varchar  | 64     |        | 是   | 更新人   |
| update_time   | datetime |        |        | 是   | 更新时间 |
| remark        | varchar  | 1024   |        | 是   | 备注     |

注：根据需求创建交易

```sql
CREATE TABLE tb_client_deal_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '编号',
    client_id BIGINT NOT NULL COMMENT '客户',
    demand_id BIGINT NOT NULL COMMENT '需求',
    total_price DECIMAL(10,2) NOT NULL COMMENT '总金额', -- 精度优化为DECIMAL
    pay_status CHAR(1) NOT NULL COMMENT '支付状态（0未付 1已付）', -- 状态注释标准化
    contract_file TEXT NOT NULL COMMENT '合同附件', -- 优化为TEXT类型
    user_id BIGINT NOT NULL COMMENT '销售员',
    dept_id BIGINT NOT NULL COMMENT '部门',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_by VARCHAR(64) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    remark VARCHAR(1024) COMMENT '备注',
    PRIMARY KEY (id),
    INDEX idx_tb_deal_client (client_id), -- 客户维度查询优化
    INDEX idx_tb_deal_demand (demand_id), -- 需求关联优化
    INDEX idx_tb_deal_pay_status (pay_status), -- 支付状态筛选优化
    CONSTRAINT fk_tb_deal_info_client 
        FOREIGN KEY (client_id) 
        REFERENCES tb_client_info (id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_deal_info_demand 
        FOREIGN KEY (demand_id) 
        REFERENCES tb_client_demand_info (id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_deal_info_sales 
        FOREIGN KEY (user_id) 
        REFERENCES sys_user (user_id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_deal_info_dept 
        FOREIGN KEY (dept_id) 
        REFERENCES sys_dept (dept_id) 
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户交易表';
```



### 任务信息表：tb_task_info

| 字段          | 类型     | 长度   | 键类型 | null | 描述     |
| :------------ | -------- | ------ | ------ | ---- | -------- |
| id            | bigint   |        | 主键   | 否   | 编号     |
| client_id     | bigint   |        |        | 否   | 客户     |
| demand_id     | bigint   |        |        | 否   | 需求     |
| task_name     | bigint   |        |        | 否   | 任务名称 |
| furnish_space | float    | (10,2) |        | 否   | 装修面积 |
| task_status   | char     | 1      |        | 否   | 任务状态 |
| user_id       | bigint   |        |        | 否   | 装修人员 |
| dept_id       | bigint   |        |        | 否   | 部门     |
| create_time   | datetime |        |        | 否   | 创建时间 |
| update_by     | varchar  | 64     |        | 是   | 更新人   |
| update_time   | datetime |        |        | 是   | 更新时间 |
| remark        | varchar  | 1024   |        | 是   | 备注     |

注：根据需求分配任务

```sql
CREATE TABLE tb_task_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '编号',
    client_id BIGINT NOT NULL COMMENT '客户',
    demand_id BIGINT NOT NULL COMMENT '需求',
    task_name VARCHAR(128) NOT NULL COMMENT '任务名称', -- 类型修正为VARCHAR
    furnish_space DECIMAL(10,2) NOT NULL COMMENT '装修面积', -- 精度优化为DECIMAL
    task_status CHAR(1) NOT NULL COMMENT '任务状态（0未开始 1进行中 2已完成）',
    user_id BIGINT NOT NULL COMMENT '装修人员',
    dept_id BIGINT NOT NULL COMMENT '部门',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_by VARCHAR(64) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    remark VARCHAR(1024) COMMENT '备注',
    PRIMARY KEY (id),
    INDEX idx_tb_task_client (client_id), -- 客户维度查询
    INDEX idx_tb_task_demand (demand_id), -- 需求关联优化
    INDEX idx_tb_task_status (task_status), -- 状态筛选优化
    CONSTRAINT fk_tb_task_info_client 
        FOREIGN KEY (client_id) 
        REFERENCES tb_client_info (id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_task_info_demand 
        FOREIGN KEY (demand_id) 
        REFERENCES tb_client_demand_info (id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_task_info_worker 
        FOREIGN KEY (user_id) 
        REFERENCES sys_user (user_id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_task_info_dept 
        FOREIGN KEY (dept_id) 
        REFERENCES sys_dept (dept_id) 
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务信息表';
```



### 任务进度表：tb_task_progress_info

| 字段        | 类型     | 长度 | 键类型 | null | 描述     |
| :---------- | -------- | ---- | ------ | ---- | -------- |
| id          | bigint   |      | 主键   | 否   | 编号     |
| client_id   | bigint   |      |        | 否   | 客户     |
| demand_id   | bigint   |      |        | 否   | 需求     |
| task_id     | bigint   |      |        | 否   | 任务     |
| progress    | varchar  | 128  |        | 否   | 任务描述 |
| task_status | char     | 1    |        | 否   | 任务状态 |
| user_id     | bigint   |      |        | 否   | 装修人员 |
| dept_id     | bigint   |      |        | 否   | 部门     |
| create_time | datetime |      |        | 否   | 创建时间 |
| update_by   | varchar  | 64   |        | 是   | 更新人   |
| update_time | datetime |      |        | 是   | 更新时间 |
| remark      | varchar  | 1024 |        | 是   | 备注     |

注：根据任务来描述状态

```sql
CREATE TABLE tb_task_progress_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '编号',
    client_id BIGINT NOT NULL COMMENT '客户',
    demand_id BIGINT NOT NULL COMMENT '需求',
    task_id BIGINT NOT NULL COMMENT '任务',
    progress VARCHAR(128) NOT NULL COMMENT '进度描述',
    task_status CHAR(1) NOT NULL COMMENT '任务状态（0=未开始 1=进行中 2=已完成）',
    user_id BIGINT NOT NULL COMMENT '装修人员',
    dept_id BIGINT NOT NULL COMMENT '部门',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_by VARCHAR(64) COMMENT '更新人',
    update_time DATETIME COMMENT '更新时间',
    remark VARCHAR(1024) COMMENT '备注',
    PRIMARY KEY (id),
    INDEX idx_tb_progress_task (task_id), -- 任务维度查询
    INDEX idx_tb_progress_status (task_status), -- 状态筛选
    CONSTRAINT fk_tb_progress_info_client 
        FOREIGN KEY (client_id) 
        REFERENCES tb_client_info (id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_progress_info_demand 
        FOREIGN KEY (demand_id) 
        REFERENCES tb_client_demand_info (id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_progress_info_task 
        FOREIGN KEY (task_id) 
        REFERENCES tb_task_info (id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_progress_info_worker 
        FOREIGN KEY (user_id) 
        REFERENCES sys_user (user_id) 
        ON UPDATE CASCADE,
    CONSTRAINT fk_tb_progress_info_dept 
        FOREIGN KEY (dept_id) 
        REFERENCES sys_dept (dept_id) 
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务进度表';
```

