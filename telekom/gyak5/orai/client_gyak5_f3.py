import socket

# Create a UDP socket
with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as sock, open('net.jpg', 'rb') as f:
    server_address = ('localhost', 10001)

    data = f.read(200)
    while data:
        sock.sendto(data, server_address)
        sock.recvfrom(20)
        data = f.read(200)
    sock.sendto("".encode(),server_address)
