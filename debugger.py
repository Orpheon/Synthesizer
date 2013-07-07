# Program to open and analyse java buffer output in a language I know
from __future__ import division, print_function

import struct
from PIL import Image

data = ""
for i in range(1, 10):
    text = open("Buffer output {0}".format(i), "r")
    data += text.read()
    text.close()

image = Image.new("RGB", (int(len(data)/2)+1, 100))
offset = 1

while len(data) > 0:
    a = struct.unpack_from("<h", data)[0]
    print("First: ", a)
    a /= 32767.0 # Max short size ( = normalize the shorts from -1 to 1)
    print("Second: ", a)
    data = data[struct.calcsize("<h"):]
    #print("a", a)
    image.putpixel((offset, int(50 + 49*a)), (0, 255, 0))
    print(offset, int(50 + 49*a))
    offset += 1
    print("")


image.save("debug.png")

'''
text = open("Buffer output 1", "r")
data = text.read()
text.close()
image = Image.new("RGB", (int(len(data)/2)+1, 100))
offset = 1

while len(data) > 0:
    a = struct.unpack_from("<h", data)[0]
    print("First: ", a)
    a /= 32767.0 # Max short size ( = normalize the shorts from -1 to 1)
    print("Second: ", a)
    data = data[struct.calcsize("<h"):]
    #print(a)
    image.putpixel((offset, int(50 + 50*a)), (0, 255, 0))
    print(offset, int(50 + 50*a))
    offset += 1
    print("")

image.save("debug.png")'''
