import socket
import struct
import time

def send_n_receive_values(values, tcp_packer, connection):
  packed_data = tcp_packer.pack(*values)
  print('%f %s %f' % values)
  connection.sendall(packed_data)
  result = connection.recv(16).decode()
  print(result)

udp_packer = struct.Struct('15s i')
udp_client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
udp_server_address = ('localhost', 10000)
udp_client.sendto(b'GET', udp_server_address)
data, _ = udp_client.recvfrom(1024)
tcp_host, tcp_port = udp_packer.unpack(data)

# '\x00'-k eltűntetése a végéről, mert egyébként 'TypeError: host name must not contain null character' hiba jön:
tcp_host = tcp_host.replace(b'\x00', b'')
tcp_server_address = (tcp_host, tcp_port)
print(tcp_server_address)

tcp_packer = struct.Struct('f c f')
connection = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

connection.connect(tcp_server_address)

values = (7.0, b'-', 2.0)

send_n_receive_values(values, tcp_packer, connection)

time.sleep(2)

values = (9.0, b'+', 3.0)

send_n_receive_values(values, tcp_packer, connection)

time.sleep(2)

values = (2.0, b'*', 3.0)

send_n_receive_values(values, tcp_packer, connection)

time.sleep(2)

values = (9.0, b'/', 3.0)

send_n_receive_values(values, tcp_packer, connection)

time.sleep(2)

values = (1.0, b'+', 3.0)

send_n_receive_values(values, tcp_packer, connection)

connection.close()