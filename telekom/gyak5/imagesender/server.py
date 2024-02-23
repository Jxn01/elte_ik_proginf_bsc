import socket
import struct
import cv2

server_address = ("localhost", 10010)

with socket.socket(type = socket.SOCK_DGRAM) as server:
    server.bind(server_address)
    cv2.namedWindow("Video", cv2.WINDOW_AUTOSIZE)
    data = None
    while data != b'':
      data, addr = server.recvfrom(200)
      server.sendto(b'OK', addr)
      
    decoded_image = cv2.imdecode(data, cv2.IMREAD_COLOR)
    cv2.imshow("Video", decoded_image)
