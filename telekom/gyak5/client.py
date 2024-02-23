import socket

with socket.socket(type = socket.SOCK_DGRAM) as client:
  server_addr = ('localhost', 10000)
  client.sendto(b'Hello szerver!', server_addr)
  data, addr = client.recvfrom(4096)
  print("Incoming message from: ", addr)
  print("Message: ", data.decode('utf-8'))