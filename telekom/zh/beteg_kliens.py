import socket
import struct
import sys

server_ip = "localhost"
server_port = 10000

def main():
  if len(sys.argv) != 1:
    print('Használat: python3 beteg_kliens.py')
    sys.exit(1)
    
  medicine_packer = struct.Struct('13s2i')
  
  client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  client.connect((server_ip, server_port))
  print("Beteg kliens online!")
  while True:
    try:
      print("\nKérem gépelje be a betegségét:")
      illness = input()
      client.sendall(illness.encode("UTF-8"))
      print("Betegség elküldve az orvosnak.")
      answer_bin = client.recv(1024)
      print("Recept érkezett!")
      medicine, dosage, patika_port = medicine_packer.unpack(answer_bin)
      medicine = medicine.decode("UTF-8")
      print("Gyógyszer:", medicine, "\nAdag:", dosage, "\nPatika port:", patika_port)
      
      if patika_port != -1:
        
    except KeyboardInterrupt:
      print("\nKliens leáll...")
      client.close()
      sys.exit()
    
if __name__ == "__main__":
  main()