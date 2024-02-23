import sys
import struct


def query():
    if len(sys.argv) < 4:
        print(len(sys.argv))
        print("Nincs megadva elég argumentum!")
        quit()

    packer = struct.Struct('9sif?c10s')
    with open(sys.argv[1], 'rb') as db:
        db.seek((int(sys.argv[2]) - 1) * packer.size)
        line = packer.unpack(db.read(packer.size))
        for i in range(3, len(sys.argv)):
            try:
                print(line[int(sys.argv[i])].decode())
            except AttributeError:
                print(line[int(sys.argv[i])])


if __name__ == '__main__':
    query()
