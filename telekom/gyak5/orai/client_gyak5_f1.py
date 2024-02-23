import socket

with socket.socket(type = socket.SOCK_DGRAM) as client:
  server_address = ('localhost', 10000)
  
  client.sendto(b'Hello szerver', server_address)
  
  data, address = client.recvfrom(4096)
  
  print("Incoming message from " + str(address))
  print("Message: " + data.decode("utf-8"))
