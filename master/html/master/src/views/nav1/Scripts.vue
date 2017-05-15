<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.device_no" placeholder="设备号"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getScripts">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<template>
			<el-table :data="scripts" highlight-current-row v-loading="loading" style="width: 100%;">
				<el-table-column type="index" width="60">
				</el-table-column>
				<el-table-column prop="device_no" label="设备号" width="100">
				</el-table-column>
				<el-table-column prop="script" label="脚本">
				</el-table-column>
				<el-table-column prop="send_time" label="发送时间" width="180" :formatter="dateFormat">
				</el-table-column>
				<el-table-column prop="result" label="结果">
				</el-table-column>
				<el-table-column prop="response_time" label="接收时间" width="180" :formatter="dateFormat">
				</el-table-column>
			</el-table>
		</template>

	</section>
</template>
<script>
	import { getScriptList} from '../../api/api';
	//import NProgress from 'nprogress'
	export default {
		data() {
			return {
                filters: {
                    device_no: ''
                },
                scripts: [
                ],
				loading: false
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
			//获取用户列表
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
			}
		},
		mounted() {
			this.getScripts();
		}
	};

</script>

<style scoped>

</style>