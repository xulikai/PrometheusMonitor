监控redis
## 1.安装
>wget https://github.com/oliver006/redis_exporter/releases/download/v0.13/redis_exporter-v0.13.linux-amd64.tar.gz  
tar -xvf  redis_exporter-v0.13.linux-amd64.tar.gz -C /usr/local/sbin/

## 2.启动
> 无密码  
/usr/local/sbin/redis_exporter redis//127.0.0.1:6379 &  
有密码  
/usr/local/sbin/redis_exporter  -redis.addr 127.0.0.1:6379  -redis.password passwd 

## 3.验证
> 访问 http://172.16.24.197:9121/metrics