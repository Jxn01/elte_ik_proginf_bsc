import socket
import struct

server_address = ("localhost", 10000)

packer = struct.Struct('i')
unpacker = struct.Struct('i 1s i')

with socket.socket(type = socket.SOCK_DGRAM) as server:
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server.bind(server_address)
    data, addr = server.recvfrom(4096)
    num1, operator, num2 = unpacker.unpack(data)
    operator = operator.decode()
    match operator:
        case "*":
            result = num1 * num2
        case "/":
            result = num1 / num2
        case "%":
            result = num1 % num2
        case "+":
            result = num1 + num2
        case "-":
            result = num1 - num2
        case _:
            result = 00000
            
    result = packer.pack(result)
    server.sendto(result, addr)
    server.close()
