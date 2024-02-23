import select
import socket
import struct
import operator
import sys

class CalculatorUDPServer:
  def __init__(self, addr='localhost', port=10002, timeout=1):
    self.server = self.setupServer(addr, port)
    self.run = True
    self.unpacker = struct.Struct('f c f')

  def setupServer(self, addr, port):
    server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server.settimeout(1.0)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server_address = (addr, port)
    server.bind(server_address)
    return server

  def applyOp(self, in1, op, in2):
    ops = {b'+': operator.add, b'-': operator.sub, b'*': operator.mul, b'/': operator.floordiv}
    return ops[op](in1, in2)

  def handleDataFromClient(self):
    while self.run:
      try:
        data, client_address = self.server.recvfrom(1024)
        unpacked_data = self.unpacker.unpack(data)
        print('Unpacked data: ' + str(unpacked_data))
        result = self.applyOp(*unpacked_data)
        print('Result: ' + str(result))
        self.server.sendto(str(result).encode(), client_address)
      except socket.timeout:
        pass

calculatorUDPServer = CalculatorUDPServer()
calculatorUDPServer.handleDataFromClient()