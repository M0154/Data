import requests
from multiprocessing import Process
import time

URL = 'http://localhost:3000'

def call_service(node_id, data):
    print(f"🌐 Node {node_id} sending data: {data}")
    try:
        response = requests.post(f"{URL}/compute", json={"data": data})
        if response.status_code == 200:
            result = response.json()
            print(f"✅ Node {node_id} received: {result}")
        else:
            print(f"❌ Node {node_id} error: {response.status_code}")
    except Exception as e:
        print(f"🚨 Node {node_id} failed: {e}")

if __name__ == "__main__":
    print("🔁 Starting distributed nodes...\n")

    nodes = []
    for i in range(5):
        data = [i + 1, (i + 1) * 2, (i + 1) * 3]
        p = Process(target=call_service, args=(i + 1, data))
        nodes.append(p)
        p.start()
        time.sleep(0.5)

    for node in nodes:
        node.join()

    print("\n📡 All nodes completed.")
