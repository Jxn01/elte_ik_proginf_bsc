import socket
import struct
import sys
import time
import random

def main():
  if len(sys.argv) != 3:
    print('Használat: python3 client.py <hostname> <port>')
    sys.exit(1)
    
  packer = struct.Struct('1s i')
  answer = None
  guess, last_guess = 50, 0
  guess_operator = '<'
  upper_bound = 100
  lower_bound = 0
  
  client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  client.connect((sys.argv[1], int(sys.argv[2])))
  while True:
    if answer == 'V' or answer == 'Y' or answer == 'K':
      client.close()
      print('Kapcsolat bontva.', answer)
      sys.exit()
    #time.sleep(random.randint(1, 5))
    
    guess = (upper_bound + lower_bound) // 2
    
    if upper_bound == lower_bound:
      guess_operator = '='
      
    if guess == last_guess:
      guess_operator = '='
    
    last_guess = guess
    
    print(f"Távolság: {abs(lower_bound - upper_bound)}, Tipp: {guess}, Válasz: {answer}, Legutóbbi tipp: {last_guess}")
  
    client.sendall(packer.pack(guess_operator.encode(), guess))
    answer_bin = client.recv(1024)
    answer, _ = packer.unpack(answer_bin)
    answer = answer.decode()
    if answer == "N":
        lower_bound = guess
    elif answer == "I":
        upper_bound = guess
    
if __name__ == "__main__":
  main()