import socket

server_addr = ('localhost',10000)
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
  server.bind(server_addr)
  server.listen(1)
  conn, client_addr = server.accept()
  with conn:
    print('Connected by', client_addr)
    data = conn.recv(1024)
    print(data.decode())
    conn.sendall(b'Hello kliens')

