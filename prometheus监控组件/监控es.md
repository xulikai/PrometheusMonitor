prometheus监控es

## 1.安装docker-compose
>pip install docker-compose

## 2.设置docker-compose文件
>[root@bogon ~]# cat docker-compose.yml 
elasticsearch_exporter:  
&emsp;&emsp;image: justwatch/elasticsearch_exporter:1.0.2  
&emsp;&emsp;command:  
&emsp;&emsp;- '-es.uri=https://search-sip-router-test-bddcgxcilsmacypukqm6qiim7u.cn-northwest-1.es.amazonaws.com.cn'  
&emsp;&emsp;restart: always  
&emsp;&emsp;ports:  
&emsp;&emsp;- "9108:9108"  

## 3.启动docker
> docker-compose up -d

## 4.验证
> 访问http://172.16.24.197:9108/metrics