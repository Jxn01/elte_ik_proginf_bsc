import socket

server_addr = ('localhost',9000)
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client:
  client.connect(server_addr)
  client.sendall(b'Hello server')
  data = client.recv(1024)
  print(data.decode())
  client.close()