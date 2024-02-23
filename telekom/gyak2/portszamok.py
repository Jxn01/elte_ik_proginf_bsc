import socket
for port in range(1,101):
  try:
   name = socket.getservbyport(port)
   print(port, name)
  except Exception:
   pass
