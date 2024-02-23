import socket

server = socket.socket()

server_address = ('localhost', 10000)
server.bind(server_address)

# A listen(1) azt jelenti, hogy a háttérben 1 darab nem accept-tált connection lehet, az elsőt accept-álja, a másodikat még nem, de a háttérbe kerül, a harmadikat viszont már elutasítja; ha listen(2) van, akkor a második és a harmadik is háttérben várakozik, amit majd a ciklusmag elején az accept hívás fog elfogadni / beengedni
server.listen(1) # listen(1): 2 kliens ok, 3 kliens már nem ok

while True:
  client, client_addr = server.accept()
  with client:
    print("csatlakozott: ",client_addr)
    data = client.recv(200).decode()
    client.send(b'Hello kliens')
    client.close()
