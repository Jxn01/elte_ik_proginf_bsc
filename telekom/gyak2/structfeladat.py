import struct
import sys

# usage: 
# python structfeladat.py database.bin 3 5 1 4
# columns 6, 2 and 5 of row 4

col_dict = {0: 'TAJ', 1: 'kor', 2: 'testhomerseklet', 3: 'hazas', 4: 'neme', 5: 'kezeles kezdetenek ideje'}

database_file = sys.argv[1]
row_and_columns = list(map(int,sys.argv[2:]))

rownum = row_and_columns[0]
columns = row_and_columns[1:]

format_str = '9sif?c10s'
db_unpacker = struct.Struct(format_str)
row_unit_bytes = struct.calcsize(format_str) # this is important because of alignment requirements of int, float (these must start on an address divisible by 4)
# row_unit_bytes = db_unpacker.size # alternative

with open(database_file, 'rb') as f:
  f.seek(row_unit_bytes*rownum)
  bin_row = f.read(row_unit_bytes)
#  print(bin_row)
  row = db_unpacker.unpack(bin_row)

for colnum in columns:
  out = row[colnum]
  if type(out) is bytes:
    out = out.decode()
  print(col_dict[colnum], out)
