#!/usr/bin/python
# coding: utf-8
import urllib2
import json

url = "http://172.16.22.254:9090/api/v1/rules"

request = urllib2.Request(url)
result = urllib2.urlopen(request)
rs = eval(result.read())
result.close()
print rs
print rs['status']



url = "http://172.16.22.254:9090/api/v1/alerts"

request = urllib2.Request(url)
result = urllib2.urlopen(request)
rs = eval(result.read())
result.close()
print rs
print rs['status']


# print u'droppedTargets'
# print '-'*50 + ' droppedTargets ' + '-'*50
# for key in rs['data']['droppedTargets']:
#     print key
#
# print '-'*50 + ' activeTargets ' + '-'*50
# for key in rs['data']['activeTargets']:
#     print key
#     print '-'*10 + str(key['discoveredLabels']) + ' ' + str(key['labels']) + '-'*50
