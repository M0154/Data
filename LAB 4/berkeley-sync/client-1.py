from timeit import default_timer as timer 
from dateutil import parser 
import threading 
import datetime 
import socket 
import time

# Client thread function to send local time to the server
def startSendingTime(slave_client):
    while True:
        try:
            # Send current time to the server
            current_time = str(datetime.datetime.now())
            slave_client.send(current_time.encode())
            print("Recent time sent successfully")
            time.sleep(5)
        except Exception as e:
            print("Error in sending time:", e)
            break

# Client thread function to receive synchronized time from server
def startReceivingTime(slave_client):
    while True:
        try:
            # Receive and parse the synchronized time from server
            data = slave_client.recv(1024).decode()
            synchronized_time = parser.parse(data)
            print("Synchronized time at the client is:", synchronized_time, "\n\n")
        except Exception as e:
            print("Error in receiving time:", e)
            break

# Function to initiate the slave client
def initiateSlaveClient(port=8080):
    slave_client = socket.socket()

    try:
        # Connect to the clock server (localhost)
        slave_client.connect(('127.0.0.1', port))
        print("Connected to Clock Server on port", port)

        # Start the sending thread
        print("Starting to send time to server...")
        send_time_thread = threading.Thread(target=startSendingTime, args=(slave_client,))
        send_time_thread.start()

        # Start the receiving thread
        print("Starting to receive synchronized time from server...")
        receive_time_thread = threading.Thread(target=startReceivingTime, args=(slave_client,))
        receive_time_thread.start()

    except Exception as e:
        print("Connection error:", e)

# Driver code
if __name__ == '__main__':
    initiateSlaveClient(port=8080)
