<template>
	<section>
		<el-row>
			<el-col :span="3">
				<el-table :data="scripts" highlight-current-row v-loading="loading" style="width: 100%;">
					<el-table-column  :cell-click="changeDeviceNo" label="ALL">
						<template scope="scope">
							111123123
						</template>
					</el-table-column>
				</el-table>
			</el-col>
			<el-col :span="21">
				<!--列表-->
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
			</el-col>
		</el-row>

	</section>
</template>
<script>
	import { getScriptList} from '../../api/api';
    import ElCol from "element-ui/packages/col/src/col";
	//import NProgress from 'nprogress'
	export default {
        components: {ElCol},
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
            changeDeviceNo: function (row, column, cell, event) {
                alert('1111');
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