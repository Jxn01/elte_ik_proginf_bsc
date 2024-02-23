import socket

server_address = ("localhost", 10003)

if __name__ == '__main__':
    with socket.socket() as server:
        server.bind(server_address)
        server.listen(1) # number of clients
        while True:
            conn, client_address = server.accept()
            print(client_address)
            with conn:
                data = conn.recv(1024)
                print(data.decode())
                conn.send(b'Hello Client!')
        server.close()
