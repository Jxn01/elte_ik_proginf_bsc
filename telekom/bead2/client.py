# Feladat1
# A paraméterben kapott bináris fájlokat olvassuk be és irassuk ki az első record tartalmát a standard outputra! (az unpack visszatérési értékét)
# - Parameter1 formatuma: 9 hosszú string, integer, float
# - Parameter2 formatuma: float, bool, karakter
# - Parameter3 formatuma: karakter, integer, 9 hosszú string
# - Parameter4 formatuma: float, 9 hosszú string, bool
# Feladat2
# Írd ki a stdout-ra (print) a következő értéket bináris formátumban (a pack visszatérési értéke)! A string hosszát a szöveg mögött lévő szám jelzi!
# Használandó struct paraméterek: f, i, c, ?, Xs (ahol a X a string hossza, pl: 3s)
# - "elso"(15), 83, True
# - 86.5, False, 'X'
# - 74, "masodik"(13), 93.9
# - 'Z', 105, "harmadik"(16)
# Script paraméterezése:
# python3 client.py < file1 > < file2 > < fil3 > < fil4 >
# pl: python3 client.py db1.bin db2.bin db3.bin db4.bin
# Példa kimenet:
# (b'F', b'123456789', 35)
# (35, 37.29999923706055, True)
# (True, b'123456789', b'F')
# (35, b'F', 37.29999923706055)
# b'elso\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00S\x00\x00\x00\x01'
# b'\x00\x00\xadB\x00X'
# b'J\x00\x00\x00masodik\x00\x00\x00\x00\x00\x00\x00\x00\x00\xcd\xcc\xbbB'
# b'Z\x00\x00\x00i\x00\x00\x00harmadik\x00\x00\x00\x00\x00\x00\x00\x00'

import struct
import sys

parameter_format_1 = "9sif"
parameter_format_2 = "f?c"
parameter_format_3 = "ci9s"
parameter_format_4 = "f9s?"

data1 = None
data2 = None
data3 = None
data4 = None

with open(sys.argv[1], "rb") as f:
    data = f.read()
    data1 = struct.unpack(parameter_format_1, data)
    
with open(sys.argv[2], "rb") as f:
    data = f.read()
    data2 = struct.unpack(parameter_format_2, data)
    
with open(sys.argv[3], "rb") as f:
    data = f.read()
    data3 = struct.unpack(parameter_format_3, data)
    
with open(sys.argv[4], "rb") as f:
    data = f.read()
    data4 = struct.unpack(parameter_format_4, data)
    
print(data1)
print(data2)
print(data3)
print(data4)

pack_format_1 = "15si?"
pack_format_2 = "f?c"
pack_format_3 = "i13sf"
pack_format_4 = "ci16s"

bin_data1 = struct.pack(pack_format_1, b"elso", 83, True)
bin_data2 = struct.pack(pack_format_2, 86.5, False, b'X')
bin_data3 = struct.pack(pack_format_3, 74, b"masodik", 93.9)
bin_data4 = struct.pack(pack_format_4, b'Z', 105, b"harmadik")

print(bin_data1)
print(bin_data2)
print(bin_data3)
print(bin_data4)
    
