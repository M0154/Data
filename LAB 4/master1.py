import socket
import time

def get_slave_time(host, port):
    with socket.socket() as s:
        s.connect((host, port))
        s.send("TIME_REQUEST".encode())
        slave_time = float(s.recv(1024).decode())
    return slave_time

def send_adjustment(host, port, adjustment):
    with socket.socket() as s:
        s.connect((host, port + 100)) # <-- use offset port for adjustment
        s.send("TIME_REQUEST".encode())
        s.recv(1024)  # receive time (ignored)
        s.send(str(adjustment).encode())

def main():
    slave_ports = [8001, 8002, 8003]
    host = 'localhost'
    times = []

    master_time = time.time()
    print(f"[MASTER] Local time: {master_time}")

    for port in slave_ports:
        t = get_slave_time(host, port)
        times.append(t)
        print(f"[MASTER] Received from slave {port}: {t}")

    times.append(master_time)
    avg = sum(times) / len(times)

    # Ignore outliers (difference > 2 sec)
    filtered = [t for t in times if abs(t - avg) <= 2]
    avg_filtered = sum(filtered) / len(filtered)

    print(f"[MASTER] Average time (ignoring outliers): {avg_filtered}")

    adjustments = [avg_filtered - t for t in times]

    # Send adjustments
    for i, port in enumerate(slave_ports):
        send_adjustment(host, port, adjustments[i])

    print("[MASTER] Synchronization complete.")

if __name__ == "__main__":
    main()
