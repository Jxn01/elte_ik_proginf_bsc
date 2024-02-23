import socket

proxy_addr = ('localhost',9000)
server_addr = ('localhost',10000)

client_data = None
server_data = None

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as proxy_from_client:
  proxy_from_client.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
  proxy_from_client.bind(proxy_addr)
  proxy_from_client.listen(5)
  client, client_address = proxy_from_client.accept()
  client_data = client.recv(1024)
  
  with socket.socket(type = socket.SOCK_DGRAM) as proxy_from_server:
    proxy_from_server.sendto(client_data, server_addr)
    server_data, addr = proxy_from_server.recvfrom(4096)
    proxy_from_server.close()
    
  client.sendall(server_data)
  proxy_from_client.close()

