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
			</el-table>
		</template>

	</section>
</template>
<script>
	import { getControlledList } from '../../api/api';
	//import NProgress from 'nprogress'
	export default {
		data() {
			return {
				filters: {
					device_no: ''
				},
				loading: false,
				controlleds: [
				]
			}
		},
		methods: {
			//性别显示转换
            formatDateTime: function (row, column) {
				return new Date(row.last_heart_time*1000).toLocaleString();
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
			}
		},
		mounted() {
			this.getControlleds();
		}
	};

</script>

<style scoped>

</style>