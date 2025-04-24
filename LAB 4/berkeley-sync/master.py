from functools import reduce 
from dateutil import parser 
import threading 
import datetime 
import socket 
import time 

client_data = {}

# Function to receive clock time from a client
def startReceivingClockTime(connector, address): 
    while True: 
        # Receive clock time
        clock_time_string = connector.recv(1024).decode() 
        clock_time = parser.parse(clock_time_string) 
        clock_time_diff = datetime.datetime.now() - clock_time
        client_data[address] = {
            "clock_time" : clock_time,
            "time_difference" : clock_time_diff,
            "connector" : connector
        }
        print(f"Client Data updated with: {address}\n")
        time.sleep(5)

# Function to accept client connections
def startConnecting(master_server): 
    while True: 
        # Accept a new client
        master_slave_connector, addr = master_server.accept() 
        slave_address = f"{addr[0]}:{addr[1]}" 
        print(f"{slave_address} got connected successfully") 
        current_thread = threading.Thread(
            target = startReceivingClockTime,
            args = (master_slave_connector, slave_address, )
        )
        current_thread.start()

# Function to get the average clock difference
def getAverageClockDiff():
    current_client_data = client_data.copy()
    time_difference_list = [client['time_difference'] for client_addr, client in client_data.items()]
    sum_of_clock_difference = sum(time_difference_list, datetime.timedelta(0, 0))
    average_clock_difference = sum_of_clock_difference / len(client_data)
    return average_clock_difference

# Function to synchronize all clocks
def synchronizeAllClocks():
    while True: 
        print("New synchronization cycle started.") 
        print(f"Number of clients to be synchronized: {len(client_data)}") 
        if len(client_data) > 0: 
            average_clock_difference = getAverageClockDiff() 
            for client_addr, client in client_data.items(): 
                try: 
                    synchronized_time = datetime.datetime.now() + average_clock_difference
                    client['connector'].send(str(synchronized_time).encode()) 
                except Exception as e: 
                    print(f"Something went wrong while sending synchronized time through {client_addr}") 
        else: 
            print("No client data. Synchronization not applicable.") 
        print("\n\n") 
        time.sleep(5)

# Function to initiate the Clock Server (Master Node)
def initiateClockServer(port=8080): 
    master_server = socket.socket() 
    master_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1) 
    print("Socket at master node created successfully\n") 
    master_server.bind(('', port)) 
    master_server.listen(10) 
    print("Clock server started...\n") 
    print("Starting to make connections...\n") 
    
    # Start connection handling in a new thread
    master_thread = threading.Thread(target=startConnecting, args=(master_server,))
    master_thread.start()
    
    # Start synchronization in another thread
    print("Starting synchronization parallelly...\n")
    sync_thread = threading.Thread(target=synchronizeAllClocks)
    sync_thread.start()

# Driver function
if __name__ == '__main__': 
    initiateClockServer(port=8080)
