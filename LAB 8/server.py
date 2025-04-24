import socket
import threading

HOST = '0.0.0.0'
PORT = 12345

clients = []
board = [' '] * 9
current_turn = 0

def check_win():
    wins = [(0,1,2),(3,4,5),(6,7,8),(0,3,6),(1,4,7),(2,5,8),(0,4,8),(2,4,6)]
    for a,b,c in wins:
        if board[a] == board[b] == board[c] and board[a] != ' ':
            return board[a]
    return None

def handle_client(conn, addr, player_id):
    conn.sendall(f"You are Player {player_id + 1} ({'X' if player_id == 0 else 'O'})\\n".encode())
    global current_turn
    while True:
        if check_win():
            for c in clients:
                c.sendall(f"Player {check_win()} wins!\\n".encode())
            break
        if ' ' not in board:
            for c in clients:
                c.sendall("It's a draw!\\n".encode())
            break
        if current_turn == player_id:
            conn.sendall(f"Board: {board}\\nYour move (0-8): ".encode())
            try:
                move = int(conn.recv(1024).decode().strip())
                if board[move] == ' ':
                    board[move] = 'X' if player_id == 0 else 'O'
                    current_turn = 1 - current_turn
                else:
                    conn.sendall("Invalid move. Try again.\\n".encode())
            except:
                continue
        else:
            conn.sendall("Waiting for opponent...\\n".encode())

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((HOST, PORT))
    s.listen()
    print("Server started. Waiting for 2 players...")
    while len(clients) < 2:
        conn, addr = s.accept()
        print(f"Player {len(clients) + 1} connected from {addr}")
        clients.append(conn)
        threading.Thread(target=handle_client, args=(conn, addr, len(clients) - 1)).start()
