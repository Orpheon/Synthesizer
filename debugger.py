# Program to open and analyse java output in a language I know (and love)
from __future__ import division, print_function

import struct
from PIL import Image

text = open("output", "r")
data = text.read()
text.close()
image = Image.new("RGB", (int(len(data)/2)+1, 100))
offset = 1

while len(data) > 0:
    a = struct.unpack_from("<h", data)[0]
    a /= 32767.0 # Max short size ( = normalize the shorts from -1 to 1)
    data = data[struct.calcsize("<h"):]
    #print(a)
    image.putpixel((offset, int(50 + 50*a)), (0, 255, 0))
    print(offset, int(50 + 50*a))
    offset += 1

image.save("debug.png")
