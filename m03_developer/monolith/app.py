from flask import Flask, render_template
from datetime import datetime

app = Flask(__name__)

@app.route('/')
def home():
  return render_template('index.html', current_time=datetime.now())

if __name__ == "__main__":
  app.run(debug=True, host='0.0.0.0', port=5000)
