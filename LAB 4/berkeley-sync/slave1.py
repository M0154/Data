import socket
import time
import threading

def handle_time_request(port, offset):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind(('localhost', port))
        s.listen(1)
        print(f"[SLAVE {port}] Waiting for master's time request...")
        conn, _ = s.accept()
        with conn:
            conn.recv(1024)  # Just to simulate "time_request"
            current_time = time.time() + offset
            print(f"[SLAVE {port}] Sending local time: {current_time}")
            conn.sendall(str(current_time).encode())

def handle_adjustment(port, offset):
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind(('localhost', port))
        s.listen(1)
        print(f"[SLAVE {port}] Waiting for time adjustment from master...")
        conn, _ = s.accept()
        with conn:
            adjustment = float(conn.recv(1024).decode())
            print(f"[SLAVE {port}] Received adjustment: {adjustment}")
            print(f"[SLAVE {port}] New time would be: {time.time() + offset + adjustment}")

if __name__ == "__main__":
    port = int(input("Enter slave port (e.g. 8001): "))
    offset = float(input("Enter time offset (e.g. 3 or -2): "))

    # Use threads to keep both sockets alive
    t1 = threading.Thread(target=handle_time_request, args=(port, offset))
    t2 = threading.Thread(target=handle_adjustment, args=(port, offset))

    t1.start()
    t1.join()

    time.sleep(1)  # Give master time to compute adjustments

    t2.start()
    t2.join()
