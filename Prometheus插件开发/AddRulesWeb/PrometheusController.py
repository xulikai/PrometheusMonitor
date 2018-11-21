# -*- coding: utf-8 -*-
from flask import Flask, request, render_template, make_response, Response
import sys
import json
from PrometheusService import create_rules_model, get_rule, get_rule_detail, update_rules_model, delete_rules_model


reload(sys)
sys.setdefaultencoding('utf8')


app = Flask(__name__)


@app.route('/prometheus/add', methods=['POST'])
def add():
    content = json.dumps('ok')
    try:
        name = request.form['name']
        expr = request.form['expr']
        level = request.form['level']
        _for = request.form['_for']
        desc = request.form['desc']
        model = request.form['model']
        create_rules_model(name, expr, level, _for, desc, model)
    except Exception, e:
        print e
        content = json.dumps('error')

    resp = Response(content)
    return resp


@app.route('/prometheus/update', methods=['POST'])
def update():
    content = json.dumps('ok')
    try:
        _name = request.form['_name']
        name = request.form['name']
        expr = request.form['expr']
        level = request.form['level']
        _for = request.form['_for']
        desc = request.form['desc']
        model = request.form['model']
        update_rules_model(name, expr, level, _for, desc, model, _name)
    except Exception, e:
        print e
        content = json.dumps('error')

    resp = Response(content)
    return resp


@app.route('/prometheus/delete', methods=['POST'])
def delete():
    content = json.dumps('ok')
    try:
        _name = request.form['_name']
        delete_rules_model(_name)
    except Exception, e:
        print e
        content = json.dumps('error')

    resp = Response(content)
    return resp



@app.route('/prometheus/getRulesList', methods=['POST'])
def get_rules_list():
    try:
        content = get_rule()
    except Exception, e:
        print e
        content = []

    resp = Response(json.dumps(content))
    return resp


@app.route('/prometheus/getRulesDetail', methods=['POST'])
def get_rules_detail():
    try:
        name = request.form['name']
        content = get_rule_detail(name)
    except Exception, e:
        print e
        content = {'name': '', 'alert': '', 'expr': '', '_for': '', 'level': '', 'summary': '', 'description': ''}
    resp = Response(json.dumps(content))
    return resp


@app.route('/prometheus/AddRules', methods=['GET'])
def open_html():
    # return render_template('rulesWeb.html')
    return app.send_static_file('pages/rulesWeb.html')


@app.route('/prometheus/UpdateRules')
def update_html():
    # return render_template('rulesWeb.html')
    return app.send_static_file('pages/update.html')


if __name__ == '__main__':
    app.run("0.0.0.0", 8888)
