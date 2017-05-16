<template>
	<section>
		<el-row>
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form >
				<el-form-item>
					<el-col :span="13">
					<el-input
							type="textarea"
							:autosize="{ minRows: 2, maxRows: 4}"
							placeholder="指令"
							v-model="script">
					</el-input>
					</el-col>
					<el-col :span="4">
					<el-button type="primary" class="send" v-on:click="sendScript">发送</el-button>
					</el-col>
				</el-form-item>
			</el-form>
		</el-col>
		</el-row>
		<el-row>
			<el-col :span="4">
				<el-table :data="controlleds" highlight-current-row v-loading="leftLoading" style="width: 100%;" @cell-click="changeDeviceNo">
					<el-table-column  type="expand">
						<template scope="props">
							<el-form label-position="left" inline class="demo-table-expand">
								<el-form-item label="设备号">
									<span>{{ props.row.device_no }}</span>
								</el-form-item>
								<el-form-item label="CPU">
									<span>{{ props.row.cpu }}</span>
								</el-form-item>
								<el-form-item label="IP">
									<span>{{ props.row.ip }}</span>
								</el-form-item>
								<el-form-item label="内存">
									<span>{{ props.row.ram }}</span>
								</el-form-item>
								<el-form-item label="系统">
									<span>{{ props.row.os }}</span>
								</el-form-item>
								<el-form-item label="用户名">
									<span>{{ props.row.name }}</span>
								</el-form-item>
								<el-form-item label="版本号">
									<span>{{ props.row.av }}</span>
								</el-form-item>
							</el-form>
						</template>
					</el-table-column>
					<el-table-column label="ALL" prop="device_no">
					</el-table-column>
				</el-table>
			</el-col>
			<el-col :span="20">
				<!--列表-->
				<el-table :data="scripts" highlight-current-row v-loading="loading" style="width: 100%;">
					<el-table-column label="Send">
						<template scope="props">
							{{props.row.send_time| unixtime_to_date}} : {{props.row.script}}
						</template>
					</el-table-column>
					<el-table-column label="Receive">
						<template scope="props">
							{{props.row.response_time| unixtime_to_date}} : {{props.row.result}}
						</template>
					</el-table-column>
				</el-table>
			</el-col>
		</el-row>

	</section>
</template>
<script>
	import { getScriptList, getControlledList, addScript} from '../../api/api';
    import ElCol from "element-ui/packages/col/src/col";
	//import NProgress from 'nprogress'
	export default {
        components: {ElCol},
        data() {
			return {
                filters: {
                    device_no: ''
                },
                script: "",
                scripts: [
                ],
                controlleds: [
                ],
				loading: false,
                leftLoading: false
			}
		},
		methods: {
            dateFormat: function (row, column) {
                var date = row[column.property];
                if (date === undefined) {
                    return "";
                }
                return new Date(date).toLocaleString();
            },
            sendScript: function () {
                var device_no = this.filters.device_no;
                var script = this.script ;
                if(device_no === null || device_no === undefined || device_no === ""){
                    this.$message({
                        message: '没有可选的被控端设备号',
                        type: 'error'
                    });
                    return ;
				}
                if(script === null || script === undefined || script === ""){
                    this.$message({
                        message: '脚本不能为空',
                        type: 'error'
                    });
                    return ;
                }
				addScript({"script": this.script, "device_no": this.filters.device_no}).then((res) => {
					//NProgress.done();
					this.$message({
						message: '发送成功',
						type: 'success'
					});
                    this.getScripts();
					//this.getUsers();
				});
            },
            changeDeviceNo: function (row, column, cell, event) {
                this.filters.device_no = row.device_no;
                this.getScripts();
            },
			//获取脚本列表
            getScripts: function () {
				let para = {
                    device_no: this.filters.device_no
				};
				this.loading = true;
				//NProgress.start();
				getScriptList(para).then((res) => {
					this.scripts = res.data.scripts;
					this.loading = false;
					//NProgress.done();
				});
			},
            getControlleds: function () {
                //NProgress.start();
                this.leftLoading = true;
                getControlledList({"device_no":""}).then((res) => {
                    this.controlleds = res.data.controlleds;
                    this.leftLoading = false;
                    if(this.controlleds.length > 0){
                        this.filters.device_no = this.controlleds[0].device_no;
                        this.getScripts();
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