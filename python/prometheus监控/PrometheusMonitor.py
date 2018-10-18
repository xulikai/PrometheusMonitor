# ecoding: utf-8
# import prometheus_client
# from prometheus_client import Counter
# from prometheus_client.core import CollectorRegistry
# from flask import Response, Flask
#
# app = Flask(__name__)
#
# requests_total = Counter("request_count", "Total request cout of the host")
#
# @app.route("/metrics")
# def requests_count():
#     requests_total.inc()
#     # requests_total.inc(2)
#     return Response(prometheus_client.generate_latest(requests_total),
#                     mimetype="text/plain")
#
# @app.route('/')
# def index():
#     requests_total.inc()
#     return "Hello World"
#
# if __name__ == "__main__":
#     app.run(host="0.0.0.0")


from prometheus_client import Gauge,start_http_server
import random
from prometheus_client import Gauge
import time
a = Gauge('A_test_monitor', 'Description of gauge')
a.set(random.random()) #value自己定义,但是一定要为 整数或者浮点数
g = Gauge('B_test_monitor', 'Description of gauge',['mylabelname'])
# g.labels(mylabelname='wmltest').set(123)
#此时一定要注意,定义Gague标签的时候是一个列表,列表可以存多个lablename,类型是字符串
#在给lable定义value的时候也要注意,mylablename 这里是一个方法,或者说是一个变量了,一定要注意.
start_http_server(8000)
while True:
    g.labels(mylabelname='wmltest').set(random.randint(1, 10))
    time.sleep(3)

