<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.device_no" placeholder="设备号"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getControlleds">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="controlleds" highlight-current-row v-loading="loading" style="width: 100%;">
				<el-table-column type="index" width="60">
				</el-table-column>
				<el-table-column prop="device_no" label="设备号" width="120">
				</el-table-column>
				<el-table-column prop="cpu" label="CPU" width="100">
				</el-table-column>
				<el-table-column prop="ip" label="IP" width="150">
				</el-table-column>
				<el-table-column prop="ram" label="内存" width="100">
				</el-table-column>
				<el-table-column prop="os" label="系统" width="120">
				</el-table-column>
				<el-table-column prop="name" label="用户名" min-width="100">
				</el-table-column>
				<el-table-column prop="av" label="版本号" min-width="100">
				</el-table-column>
				<el-table-column prop="last_heart_time" label="最后上线时间" min-width="180" :formatter="formatDateTime"  sortable>
				</el-table-column>
				<el-table-column label="操作" width="150">
					<template scope="scope">
						<el-button size="small" @click="showScriptForm(scope.$index, scope.row)">发送指令</el-button>
					</template>
				</el-table-column>
			</el-table>


			<!--发送指令-->
			<el-dialog title="发送指令" v-model="scriptFormVisible" :close-on-click-modal="false">
				<el-form :model="scriptForm" label-width="80px" :rules="scriptFormRules" ref="scriptForm">
					<el-form-item label="指令">
						<el-input type="textarea" v-model="scriptForm.script"></el-input>
					</el-form-item>
				</el-form>
				<div slot="footer" class="dialog-footer">
					<el-button @click.native="scriptFormVisible = false">取消</el-button>
					<el-button type="primary" @click.native="scriptSubmit" :loading="scriptLoading">提交</el-button>
				</div>
			</el-dialog>
		</template>

	</section>
</template>
<script>
	import { getControlledList, addScript} from '../../api/api';
	//import NProgress from 'nprogress'
	export default {
		data() {
			return {
				filters: {
					device_no: ''
				},
				loading: false,
				controlleds: [
				],
                scriptFormVisible: false,
                scriptLoading: false,
                scriptFormRules: {
                    script: [
                        { required: true, message: '请输入指令', trigger: 'blur' }
                    ]
                },
                scriptForm: {
				    script: "",
					device_no: ""
				}
			}
		},
		methods: {
            formatDateTime: function (row, column) {
                var date = row[column.property];
                if (date === undefined) {
                    return "";
                }
				return new Date(date*1000).toLocaleString();
			},
			//获取用户列表
            getControlleds: function () {
				let para = {
                    device_no: this.filters.device_no
				};
				this.loading = true;
				//NProgress.start();
				getControlledList(para).then((res) => {
					this.controlleds = res.data.controlleds;
					this.loading = false;
					//NProgress.done();
				});
			},
            //显示指令表单
            showScriptForm: function (index, row) {
                this.scriptFormVisible = true;
                this.scriptForm.device_no = row.device_no;
            },
            //显示指令表单
            scriptSubmit: function () {
                this.$refs.scriptForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            this.addLoading = true;
                            //NProgress.start();
                            let para = Object.assign({}, this.scriptForm);
                            addScript(para).then((res) => {
                                this.addLoading = false;
                                //NProgress.done();
                                this.$message({
                                    message: '发送成功, 查看结果请跳转指令列表',
                                    type: 'success'
                                });
                                this.$refs['scriptForm'].resetFields();
                                this.scriptFormVisible = false;
                                //this.getUsers();
                            });
                        });
                    }
                });
            }
		},
		mounted() {
			this.getControlleds();
		}
	};

</script>

<style scoped>

</style>