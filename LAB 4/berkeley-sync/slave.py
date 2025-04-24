from timeit import default_timer as timer
from dateutil import parser
import threading
import datetime
import socket
import time

# Function to send current client time to server
def startSendingTime(slave_client):
    while True:
        slave_client.send(str(datetime.datetime.now()).encode())
        print("Recent time sent successfully\n")
        time.sleep(5)

# Function to receive synchronized time from server
def startReceivingTime(slave_client):
    while True:
        data = slave_client.recv(1024).decode()
        synchronized_time = parser.parse(data)
        print("Synchronized time at the client is: " + str(synchronized_time) + "\n")
        time.sleep(5)

# Main function to run the client
def initiateSlaveClient(port=8080):
    slave_client = socket.socket()
    slave_client.connect(('127.0.0.1', port))

    print("Connected to Clock Server\n")

    # Start sending time to server
    send_time_thread = threading.Thread(target=startSendingTime, args=(slave_client,))
    send_time_thread.start()

    # Start receiving synchronized time
    receive_time_thread = threading.Thread(target=startReceivingTime, args=(slave_client,))
    receive_time_thread.start()

# Run the client
if __name__ == '__main__':
    initiateSlaveClient(port=8080)
