import socket

# Create a UDP socket
with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as sock, open('netOut.jpg', 'wb') as f:
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

    server_address = ('localhost', 10001)
    sock.bind(server_address)
    while True:
        data, client = sock.recvfrom(200)
        if not data:
            break
        sock.sendto("OK".encode(),client)
        f.write(data)
                