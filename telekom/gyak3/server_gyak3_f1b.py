import socket
import sys

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('localhost', int(sys.argv[1]))
#server_address = ('', 0)        #any interface, any port

sock.bind(server_address)

sock.listen(1)

connection, client_address = sock.accept()

data = connection.recv(16)

print(data.decode('utf-8'))

connection.sendall(b'Hello kliens') # or 'Hello kliens'.encode()

connection.close()

sock.close()