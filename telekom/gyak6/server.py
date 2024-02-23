import socket

server_addr = ('localhost',10000)
with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as server:
  server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
  server.bind(server_addr)
  server.listen(5)
  proxy_server, proxy_address = server.accept()
  data = proxy_server.recv(1024)
  print(data.decode())
  proxy_server.sendall(b'Hello proxy')
  proxy_server.close()
  server.close()