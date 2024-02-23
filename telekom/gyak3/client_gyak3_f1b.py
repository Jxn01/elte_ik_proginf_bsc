import socket
import sys

connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('localhost', int(sys.argv[1]))
connection.connect(server_address)

connection.sendall(b'Hello szerver') # or 'Hello szerver'.encode()

data = connection.recv(16)

print(data.decode('utf-8'))

connection.close()