import socket

server_addr = ('localhost',10000)
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as conn:
  conn.connect(server_addr)
  conn.sendall(b'Hello server')
  data = conn.recv(1024)
  print(data.decode())

