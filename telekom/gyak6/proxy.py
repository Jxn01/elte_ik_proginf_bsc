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
  print(client_data.decode())
  
  with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as proxy_from_server:
    proxy_from_server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    proxy_from_server.connect(server_addr)
    proxy_from_server.sendall(client_data)
    server_data = proxy_from_server.recv(1024)
    print(server_data.decode())
    proxy_from_server.close()
    
  proxy_from_client.sendall(server_data)
  proxy_from_client.close()

