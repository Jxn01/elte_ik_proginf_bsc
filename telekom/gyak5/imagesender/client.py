import socket
import struct
import cv2

server_address = ("localhost", 10010)

with socket.socket(type = socket.SOCK_DGRAM) as client:
  pass