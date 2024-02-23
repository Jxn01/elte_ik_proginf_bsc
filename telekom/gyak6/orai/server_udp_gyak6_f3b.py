import socket
import struct

class CalculatorUDPSelectServer:
  def __init__(self, addr='localhost', port=10000, tcp_addr = 'localhost', tcp_port = 10001, timeout=1):
    self.server = self.setupServer(addr, port, timeout)
    self.run = True
    self.tcp_addr = tcp_addr
    self.tcp_port = tcp_port
    self.packer = struct.Struct('15s i')

  def setupServer(self, addr, port, timeout):
    # Create a UDP socket
    server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server.settimeout(timeout)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    
    # Bind the socket to the port
    server_address = (addr, port)
    server.bind(server_address)
    
    return server

  def handleInputs(self):
    try:
      data, client_address = self.server.recvfrom(1024)
      if data in b'GET':
        self.server.sendto(self.packer.pack(self.tcp_addr.encode(), self.tcp_port), client_address)
      else:
        self.server.sendto(self.packer.pack(b'UNKNOWN REQUEST', 0), client_address)
    except socket.timeout:
      pass

  def handleConnections(self):
    while self.run:
      try:
        self.handleInputs()
      except KeyboardInterrupt:
        print("Close the system")
        self.run = False

calculatorUDPSelectServer = CalculatorUDPSelectServer()
calculatorUDPSelectServer.handleConnections()