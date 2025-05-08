from flask import Flask, request, jsonify
from flask_cors import CORS

app = Flask(__name__)
CORS(app) # Enable CORS for all routes

@app.route('/add', methods=['GET'])
def add():
    a = int(request.args.get('a'))
    b = int(request.args.get('b'))
    return jsonify(result=a + b)

@app.route('/subtract', methods=['GET'])
def subtract():
    a = int(request.args.get('a'))
    b = int(request.args.get('b'))
    return jsonify(result=a - b)

@app.route('/multiply', methods=['GET'])
def multiply():
    a = int(request.args.get('a'))
    b = int(request.args.get('b'))
    return jsonify(result=a * b)

@app.route('/divide', methods=['GET'])
def divide():
    a = int(request.args.get('a'))
    b = int(request.args.get('b'))
    if b == 0:
        return jsonify(error="Division by zero not allowed"), 400
    return jsonify(result=a / b)

if __name__ == '__main__':
    app.run(debug=True)



#http://127.0.0.1:5000/subtract?a=2&b=3