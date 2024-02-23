import socket

server_address = ('localhost', 10000)

try:
  client1 = socket.socket()
  client1.connect(server_address)

  client2 = socket.socket()
  client2.connect(server_address)

  client3 = socket.socket()
  client3.connect(server_address)

  client1.send(b'Hello szerver')
  data = client1.recv(16)

  client2.send(b'Hello szerver')
  data = client2.recv(16)

  client3.send(b'Hello szerver')
  data = client3.recv(16)
except ConnectionRefusedError:
  print("Sok kapcsolat...")
finally:
  client1.close()
  client2.close()
  client3.close()