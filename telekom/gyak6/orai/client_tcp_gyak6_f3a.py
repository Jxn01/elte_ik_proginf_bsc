import socket
import struct
import time

def send_n_receive_values(values, packer, connection):
  packed_data = packer.pack(*values)
  print('%f %s %f' % values)
  connection.sendall(packed_data)
  result = connection.recv(16).decode()
  print(result)

packer = struct.Struct('f c f')
connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server_address = ('localhost', 10001)
connection.connect(server_address)

values = (7.0, b'-', 2.0)

send_n_receive_values(values, packer, connection)

time.sleep(2)

values = (9.0, b'+', 3.0)

send_n_receive_values(values, packer, connection)

time.sleep(2)

values = (2.0, b'*', 3.0)

send_n_receive_values(values, packer, connection)

time.sleep(2)

values = (9.0, b'/', 3.0)

send_n_receive_values(values, packer, connection)

time.sleep(2)

values = (1.0, b'+', 3.0)

send_n_receive_values(values, packer, connection)

connection.close()