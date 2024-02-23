import socket

conn = socket.socket()

server_addr = ('localhost',10000)

conn.connect(server_addr)

conn.sendall(b'Hello server')

data = conn.recv(1024)

print(data.decode())

conn.close()