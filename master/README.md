## Here is controlled
```
Local Nginx Config
server {
	listen       80;
	server_name  1s.vernachen.com vernachen.com;

	location /api/ {
		proxy_pass http://localhost:8089;
	}
	location /master/ {
		root   J:\\-1s\\master\\html;
		index  index.html index.htm;
	}
}

Hosts

127.0.0.1 1s.vernachen.com
```