import socket

with socket.socket(type=socket.SOCK_DGRAM) as server:
  server_addr = ('localhost', 10000)
  server.bind(server_addr)
  data, addr = server.recvfrom(4096)
  print("Incoming message from: ", addr)
  print("Message: ", data.decode('utf-8'))
  server.sendto(b'Hello kliens!', addr)