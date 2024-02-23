import socket
import struct
import sys
import random
import select

def main():
  if len(sys.argv) != 3:
    print('Használat: python3 client.py <hostname> <port>')
    sys.exit(1)
    
  packer = struct.Struct('1s i')
  number = random.randint(1, 100)
  winner = False
  
  print(f"A kigondolt szám: {number}")

  server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  server.setblocking(0)
  server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)  
  server.bind((sys.argv[1], int(sys.argv[2])))  
  server.listen()
  sockets = [server]
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
            operator, guess = packer.unpack(bin_data)
            operator = operator.decode()
            answer = None
            
            if winner:
              answer = 'V'
            
            if not winner and operator == '=' and guess == number:
              winner = True
              answer = 'Y'
            elif not winner and operator == '=':
              answer = 'K'
              
            if not winner and operator == '<' and guess > number:
              answer = 'I'
            elif not winner and operator == '<':
              answer = 'N'
              
            if not winner and operator == '>' and guess < number:
              answer = 'I'
            elif not winner and operator == '>':
              answer = 'N'
              
            if answer:
              sock.sendall(packer.pack(answer.encode(), 0))
            print(f"{client_address[1]}: tipp: {guess} {operator} {number} válasz: {answer}")    
    except KeyboardInterrupt:
      print("\nSzerver leáll...")
      for sock in sockets:
        sock.close()
      sockets = []
      sys.exit()
  
if __name__ == "__main__":
  main()