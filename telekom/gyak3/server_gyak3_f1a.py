import socket

server = socket.socket()

server_addr = ('localhost',10000)

server.bind(server_addr)

server.listen(10)

conn, client_addr = server.accept()

print(client_addr)

data = conn.recv(1024)

print(data.decode())

conn.sendall(b'Hello kliens')

conn.close()

server.close()