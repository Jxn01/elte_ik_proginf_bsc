import socket
with socket.socket(type = socket.SOCK_DGRAM) as server:
  
  data, address = server.recvfrom(4096)
  
  print("Incoming message from " + str(address))
  print("Message: " + data.decode("utf-8"))
  
  server.sendto(b'Hello kliens', address)
  
import socket
import struct
import sys
import random
import select
import json

server_ip = "localhost"
server_port = 10001

def main():
  if len(sys.argv) != 1:
    print('Használat: python3 patika_szerver.py')
    sys.exit(1)
    

  server = socket.socket(type = socket.SOCK_DGRAM)
  server.setblocking(0)
  server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)  
  server.bind((server_ip, server_port))  
  server.listen()
  sockets = [server]
  print("Orvos szerver online!")
  while True:
    try:
      readable, _, _ = select.select(sockets, [], [], 1)
      for sock in readable:
        if sock == server:
          client, client_address = sock.accept()
          sockets.append(client)
        else:
          bin_data = sock.recv(1024)
          if not bin_data:
            sock.close()
            sockets.remove(sock)
          else:
            server_response = ("NINCS".encode(), 0)
            illness = bin_data.decode("UTF-8")
            print("\nBeteg panasz érkezett!")
            print("Betegség:", illness)
            if illness in medicine_dict.keys():
              patika_port = 10001
              dose = random.randint(1, 3)
              server_response = (medicine_dict[illness].encode(), dose)
              print("A betegséghez tartozik gyógyszer az adatbázisban:", medicine_dict[illness])
              print("Adag:", dose)
            else:
              patika_port = -1
              print("A betegséghez NEM tartozik gyógyszer az adatbázisban.")
            sock.sendall(medicine_packer.pack(server_response[0], server_response[1], patika_port))
            print("Recept elküldve!")
    except KeyboardInterrupt:
      print("\nSzerver leáll...")
      for sock in sockets:
        sock.close()
      sockets = []
      sys.exit()
  
if __name__ == "__main__":
  main()