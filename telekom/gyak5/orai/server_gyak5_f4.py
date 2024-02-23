import select
import socket
import sys
import queue

class ChatServerUDP:
  def __init__(self, addr='localhost', port=10001, timeout=1):
    self.server, self.message_queues = self.setupServer(addr, port)
    self.run = True
    self.username = {}
    self.timeout=timeout

  def setupServer(self, addr, port):
    server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server.settimeout(1.0)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    
    server_address = (addr, port)
    server.bind(server_address)
    
    message_queues = queue.Queue()
    return server, message_queues

  def handleNewClient(self, data, client_address):
    name = data.strip().decode('utf-8')
    print('new client from ' + str(client_address) + " with username " + name)
    self.username[client_address] = name
    self.message_queues.put("["+self.username[client_address]+"] is LOGIN")

  def handleDataFromClient(self, data, client_address):
    data = data.strip().decode('utf-8')
    if data == "["+self.username[client_address]+"] is LOGOUT":
        print('closing ' + str(client_address) + ' after reading no data')
        self.message_queues.put(data)
        del self.username[client_address]
    else:
        # A readable client socket has data
        print('received "%s" from %s' % (data, client_address))
        self.message_queues.put("["+self.username[client_address]+"]: "+data)

  def handleInputs(self):
    try:
      data, client_address = self.server.recvfrom(1024)
      if client_address in self.username:
        self.handleDataFromClient(data, client_address)
      else:
        self.handleNewClient(data, client_address)
    except socket.timeout:
      pass

  def handleOutputs(self):
    while not self.message_queues.empty():
      try:
          next_msg = self.message_queues.get_nowait()
          print(next_msg)
      except queue.Empty:
          break
      else:
          for client_address in self.username:
              print('sending "%s" to %s' % (next_msg, client_address))
              self.server.sendto(next_msg.encode(), client_address)

  def handleConnections(self):
    while self.run:
      try:
        self.handleInputs()
        self.handleOutputs()
      except KeyboardInterrupt:
        print("Close the system")
        self.message_queues.put("SERVER STOP")
        self.handleOutputs()
        self.server.close()
        self.run = False

chatServer = ChatServerUDP()
chatServer.handleConnections()