import socket
import struct
import time

def send_n_receive_values(server_address, values, packer, client):
  packed_data = packer.pack(*values)
  print('%f %s %f' % values)
  client.sendto(packed_data, server_address)
  result, server_addr = client.recvfrom(16)
  print(result)

packer = struct.Struct('f c f')
server_address = ('localhost', 10000)
with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as client:
  values = (7.0, b'-', 2.0)
  
  send_n_receive_values(server_address, values, packer, client)
  
  time.sleep(2)
  
  values = (9.0, b'+', 3.0)
  
  send_n_receive_values(server_address, values, packer, client)
  
  time.sleep(2)
  
  values = (2.0, b'*', 3.0)
  
  send_n_receive_values(server_address, values, packer, client)
  
  time.sleep(2)
  
  values = (9.0, b'/', 3.0)
  
  send_n_receive_values(server_address, values, packer, client)
  
  time.sleep(2)
  
  values = (1.0, b'+', 3.0)
  
  send_n_receive_values(server_address, values, packer, client)
