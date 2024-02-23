import socket
import struct

server_address = ("localhost", 9000)

num1 = 2
num2 = 6
operator = "+"

print(str(num1) + " " + operator + " " + str(num2) + " = ...")

packer = struct.Struct('i 1s i')
unpacker = struct.Struct('i')

package = packer.pack(num1, operator.encode(), num2)

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as client:
    client.connect(server_address)
    client.sendall(package)
    result= client.recv(4096)
    data = unpacker.unpack(result)
    print(data[0])
    client.close()