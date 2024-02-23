import socket
import struct
import operator
import select

class CalculatorTCPSelectServer:
  def __init__(self, addr='localhost', port=10001, timeout=1):
    self.server = self.setupServer(addr, port)
    # Sockets from which we expect to read
    self.inputs = [ self.server ]
    # Wait for at least one of the sockets to be ready for processing
    self.timeout=timeout
    self.unpacker = struct.Struct('f c f')

  def setupServer(self, addr, port):
    # Create a TCP/IP socket
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setblocking(0)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    
    # Bind the socket to the port
    server_address = (addr, port)
    server.bind(server_address)
    
    # Listen for incoming connections
    server.listen(5)
    return server

  def handleNewConnection(self, sock):
    # A "readable" server socket is ready to accept a connection
    connection, client_address = sock.accept()
    connection.setblocking(0)	# or connection.settimeout(1.0)    
    self.inputs.append(connection)

  def applyOp(self, in1, op, in2):
    ops = {b'+': operator.add, b'-': operator.sub, b'*': operator.mul, b'/': operator.floordiv}
    return ops[op](in1, in2)

  def handleDataFromClient(self, sock):
        data = sock.recv(1024)
        data = data.strip()
        if data:
            unpacked_data = self.unpacker.unpack(data)
            print('Unpacked data: ' + str(unpacked_data))
            result = self.applyOp(*unpacked_data)
            print('Result: ' + str(result))
            sock.sendall(str(result).encode())
        else:
            # Interpret empty result as closed connection
            print('closing ' + str(sock.getpeername()) + ' after reading no data')
            # Stop listening for input on the connection
            self.inputs.remove(sock)
            sock.close()

  def handleInputs(self, readable):
    for sock in readable:
        if sock is self.server:
            self.handleNewConnection(sock)
        else:
            self.handleDataFromClient(sock)

  def handleExceptionalCondition(self, exceptional):
    for sock in exceptional:
      print('handling exceptional condition for ' + str(sock.getpeername()))
      # Stop listening for input on the connection
      self.inputs.remove(sock)
      sock.close()

  def handleConnections(self):
    while self.inputs:
      try:
        readable, writable, exceptional = select.select(self.inputs, [], self.inputs, self.timeout)
    
        if not (readable or writable or exceptional):
            # timed out, do some other work here
            continue
    
        self.handleInputs(readable)
        self.handleExceptionalCondition(exceptional)
      except KeyboardInterrupt:
        print("Close the system")
        for c in self.inputs:
            c.close()
        self.inputs = []

calculatorTCPSelectServer = CalculatorTCPSelectServer()
calculatorTCPSelectServer.handleConnections()