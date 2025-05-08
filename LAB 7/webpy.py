import time
import webbrowser
from flask import Flask, request, jsonify
from threading import Thread
import nest_asyncio
import aiohttp
import asyncio

# Apply asyncio compatibility for the Flask app
nest_asyncio.apply()

# Flask application
app = Flask(__name__)

@app.route('/square', methods=['GET'])
def square():
    """Endpoint that calculates the square of a given number"""
    try:
        number = float(request.args.get('number'))
        result = number ** 2
        return jsonify({'result': result})
    except Exception as e:
        return jsonify({'error': str(e)}), 400

def run_app():
    """Run Flask app on port 5000"""
    app.run(port=5000, debug=False, use_reloader=False)

def start_flask_app():
    """Run Flask app in a separate thread"""
    thread = Thread(target=run_app)
    thread.start()

async def get_square(number: float):
    """Async function to call the /square endpoint and get the result"""
    url = f'http://127.0.0.1:5000/square?number={number}'
    async with aiohttp.ClientSession() as session:
        async with session.get(url) as response:
            if response.status == 200:
                data = await response.json()
                return data.get('result')
            else:
                return f"Error: {response.status}"

async def main():
    """Main entry point to consume the Flask service asynchronously"""
    numbers = [1, 2, 3, 4, 5]
    tasks = []

    # Create async tasks to call the web service for each number
    for number in numbers:
        tasks.append(get_square(number))

    # Await all tasks concurrently
    results = await asyncio.gather(*tasks)

    # Print the results
    for num, result in zip(numbers, results):
        print(f"The square of {num} is {result}")

# Run Flask app in a separate thread
start_flask_app()

# Wait for Flask server to be ready before opening the browser
time.sleep(1)  # Adjust time based on the server load

# Automatically open the web browser to the /square endpoint
webbrowser.open("http://127.0.0.1:5000/square?number=5")

# Run the distributed application asynchronously
if __name__ == "__main__":
    asyncio.run(main())

