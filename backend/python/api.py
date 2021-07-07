from flask import Flask
import json

from prometheus_client import Counter, generate_latest

app = Flask(__name__)
data_path = "/etc/backend/data.json"

products_view_metrics = Counter('view', 'Product view', ['product'])
products_buy_metrics = Counter('buy', 'Product buy', ['product'])
products_view = {}
products_buy = {}

@app.route('/metrics')
def metrics():
    return generate_latest()

@app.route('/product/<pid>')
def view_product(pid):
    try:
        p = load_product(pid)
        # Update stats
        products_view_metrics.labels(product=pid).inc()
        if pid not in products_view:
            products_view[pid] = 1
        else:
            products_view[pid] = products_view[pid] + 1
        # Add stats
        p['view'] = products_view[pid]
        p['buy'] = products_buy[pid] if pid in products_buy else 0
        headers = {'Content-Type': 'application/json'}
        return json.dumps(p), 200, headers
    except ValueError:
        return "No such product", 404

@app.route('/buy/<pid>')
def buy_product(pid):
    try:
        load_product(pid)
        # Update stats
        products_buy_metrics.labels(product=pid).inc()
        if pid not in products_buy:
            products_buy[pid] = 1
        else:
            products_buy[pid] = products_buy[pid] + 1
        return 'Thanks for buying opensource software ;-)'
    except ValueError:
        return "No such product", 404


def load_product(product):
    with open(data_path, "r") as file:
        json_products = json.loads(file.read())
        for p in json_products['products']:
            if p['name'] == product:
                return p
        raise ValueError('Product %s not found' % product)


@app.route("/")
def products():
    with open(data_path, "r") as file:
        headers = {'Content-Type': 'application/json'}
        return file.read(), 200, headers


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)
