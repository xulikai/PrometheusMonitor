import urllib, urllib2

url='http://172.16.22.254:9090/api/v1/targets/metadata?match_target={job="k8s-app"}'   #,metric="A_test_monitor"
# textmod ={'match_target':{'job':"local-172-16-22-234",'instance':"172.16.22.234:8000"}}
# textmod = urllib.urlencode(textmod)
# print(textmod)
req = urllib2.Request(url)
res = urllib2.urlopen(req)
res = res.read()
# print(res)

result = eval(res)
print result['data']
for item in result['data']:
    print item['metric']

# curl -G http://172.16.22.254:9090/api/v1/targets/metadata --data-urlencode 'match_target={job="local-172-16-22-234",instance="172.16.22.234:8000"}'
