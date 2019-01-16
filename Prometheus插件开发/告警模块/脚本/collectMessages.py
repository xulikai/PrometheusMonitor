# -*- coding: utf-8 -*-

import io, sys


from flask import Flask, Response
from flask import request
import logging
import locale
import threading
import json
import psycopg2
import random
import time

locale.setlocale(locale.LC_ALL, "en_US.UTF-8")


app = Flask(__name__)


console = logging.StreamHandler()
fmt = '%(asctime)s - %(filename)s:%(lineno)s - %(name)s - %(message)s'
formatter = logging.Formatter(fmt)
console.setFormatter(formatter)
# log = logging.getLogger("flask_webhook_dingtalk")
# log.addHandler(console)
# log.setLevel(logging.DEBUG)

_url = 'api.clink.cn'


class save_massages(threading.Thread):

    def __init__(self, application, describetions, alert_job, extend, keyword, monitortype, platform, startat, alert_status, count, level, instance):
        super(save_massages, self).__init__()
        self.application = application
        self.describetions = describetions
        self.alert_job = alert_job
        self.extend = extend
        self.keyword = keyword
        self.monitortype = monitortype
        self.platform = platform
        self.startat = startat
        if alert_status == 'firing':
            alert_status = 1
        else:
            alert_status = 0
        self.alert_status = alert_status
        self.count = count
        self.level = level
        self.instance = instance

    def run(self):
        conn = psycopg2.connect(database="promethues", user="postgres", password="postgres", host="172.16.24.196", port="5432")
        cur = conn.cursor()
        sql = "INSERT INTO public.pending_messages(id, application, content, job, extend, key_word, monitortype, platform, flag, create_time, status, value, level, instance)VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"

        if self.extend == {}:
            id = 'SR' + time.strftime('%Y%m%d%H%M%S',time.localtime(time.time())) + ''.join(str(random.choice(range(10))) for _ in range(5))
            cur.execute(sql, (id, self.application, self.describetions, self.alert_job, '', self.keyword, self.monitortype, self.platform, -1, self.startat, self.alert_status, self.count, self.level, self.instance))
        else:
            for key in self.extend.keys():
                if self.extend[key] == [] or key == 'other':
                    continue
                id = 'SR' + time.strftime('%Y%m%d%H%M%S',time.localtime(time.time())) + ''.join(str(random.choice(range(10))) for _ in range(5))
                cur.execute(sql, (id, self.application, self.describetions, self.alert_job, str(json.dumps(self.extend[key], ensure_ascii=False)).replace('"', "'"), self.keyword, self.monitortype, key, -1, self.startat, self.alert_status, self.count, self.level, self.instance))

        conn.commit()
        print "Records created successfully"
        conn.close()


@app.route('/', methods=['POST'])
def hander_session():
    post_data = request.get_data()
    post_data = json.loads(post_data.decode("utf-8"))['alerts']
    for i in post_data:
        try:
            try:
                alert_status = i['status']
            except:
                alert_status = "unkown!"
            try:
                alert_name = i['labels']['alertname']
            except:
                alert_name = "no alertname"
            try:
                alert_job = i['labels']['job']
            except:
                alert_job = "no job"
            try:
                startat = i['startsAt']
            except:
                startat = "no startsAt"
            try:
                summary123 = i['annotations']['summary']
            except:
                summary123 = i['annotations']['message']
            try:
                instances123 = i['labels']['instance']
            except:
                instances123 = i['annotations']['description']
            try:
                describetions = i['annotations']['description']
            except:
                describetions = i['labels']['instance']
            try:
                keyword = i['labels']['key_word']
            except:
                keyword = alert_name

            try:
                application = i['labels']['application']
            except:
                application = ''

            try:
                monitortype = i['labels']['monitortype']
            except:
                monitortype = ''

            try:
                platform = i['labels']['platform']
            except:
                platform = alert_job

            try:
                count = summary123.split()[1].replace(u'告警值：','')
            except:
                count = ''

            extend = {}
            try:
                extend = eval(i['labels']['extend'])
            except:
                extend = {}

            try:
                level = i['labels']['level']
            except:
                level = 'average'

            startat = startat.split('.')[0].replace('T', ' ')

            # print application, describetions, alert_job, str(extend), keyword, monitortype, platform, startat, alert_status, count

            sm = save_massages(application, describetions, alert_job, extend, keyword, monitortype, platform, startat, alert_status, count, level, instances123)
            sm.start()

        except Exception as e:
            # log.error(repr(e))
            return repr(e)
    return 'ok'


if __name__ == '__main__':
    app.debug = True
    app.run(host='0.0.0.0', port=5001)
