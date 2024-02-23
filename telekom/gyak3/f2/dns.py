import socket

proxy_addr = ('localhost',9000)
dns_addr = ('localhost',11000)

with socket.socket(type = socket.SOCK_DGRAM) as dns:
  dns.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
  dns.bind(dns_addr)
  client, client_address = dns.accept()
  client_data = client.recv(1024)
  if client_data == b"GET":
    client.sendall(proxy_addr)
  else:
    client.sendall(b"ERROR")
  dns.close()
