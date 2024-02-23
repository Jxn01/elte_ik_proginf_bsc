import socket

with socket.socket(type = socket.SOCK_DGRAM) as server:
  server_address = ('localhost', 10000)
  server.bind(server_address)
  
  data, address = server.recvfrom(4096)
  
  print("Incoming message from " + str(address))
  print("Message: " + data.decode("utf-8"))
  
  server.sendto(b'Hello kliens', address)
