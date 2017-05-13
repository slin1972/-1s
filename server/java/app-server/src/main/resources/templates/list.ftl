<!DOCTYPE html>

<html lang="en">

<body>
	<table>
		<thead>
			<tr>
				<td>deviceNo</td>
				<td>ip</td>
				<td>cpu</td>
				<td>ram</td>
				<td>os</td>
				<td>name</td>
				<td>最后时间</td>
			</tr>
		</thead>
		<tbody>
<#list list as obj>
			<tr>
				<td>${obj.deviceNo}</td>
				<td>${obj.ip}</td>
				<td>${obj.cpu}</td>
				<td>${obj.ram}</td>
				<td>${obj.os}</td>
				<td>${obj.name}</td>
				<td>${(obj.lastHeartTime*1000)?number_to_datetime}</td>
		    </tr>
</#list>
		</tbody>
	</table>
</body>
</html>