import socket

server_address = ("localhost", 10003)

if __name__ == '__main__':
    with socket.socket() as conn1:
        with socket.socket() as conn2:
            with socket.socket() as conn3:
                while True:
                    conn1.connect(server_address)
                    conn1.sendall(b'Hello Server1!')
                    data1 = conn1.recv(1024)
                    print(data1.decode())
                    #conn1.close()
                    conn2.connect(server_address)
                    conn2.sendall(b'Hello Server2!')
                    data2 = conn2.recv(1024)
                    print(data2.decode())
                    #conn2.close()
                    conn3.connect(server_address)
                    conn3.sendall(b'Hello Server3!')
                    data3 = conn3.recv(1024)
                    print(data3.decode())
                    #conn3.close()
