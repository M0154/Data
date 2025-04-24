import socket

HOST = input("Enter server IP: ")  # e.g., 192.168.1.10
PORT = 12345

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.connect((HOST, PORT))
    while True:
        data = s.recv(1024)
        if not data:
            break
        print(data.decode(), end='')
        if b"Your move" in data:
            s.sendall(input().encode())
