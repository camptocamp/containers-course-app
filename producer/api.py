from flask import Flask

app = Flask(__name__)


@app.route("/")
def data():
    with open("/etc/producer/data.json", "r") as file:
        headers = {'Content-Type': 'application/json'}
        return file.read(), 200, headers


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080, debug=True)
