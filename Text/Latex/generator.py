from __future__ import division, print_function

import math

cut_freq_low = 200
cut_freq_high = 100

def lowpass(x):
    return (cut_freq_low*2*math.pi)**2/(x**2 + (cut_freq_low*2*math.pi)*x + (cut_freq_low*2*math.pi)**2)

def highpass(x):
    return x**2/(x**2 + (cut_freq_high*2*math.pi)*x + (cut_freq_high*2*math.pi)**2)

x = [i/500 for i in range(500*5)]
y = [lowpass(10**i) for i in x]

s = ""
for i in range(len(x)):
    s += str(x[i]) + "\t" + str(y[i]) + "\n"

text = open("lowpassfrequencyresponse.dat", "w")
text.write(s)
text.close()

y = [highpass(10**i) for i in x]

s = ""
for i in range(len(x)):
    s += str(x[i]) + "\t" + str(y[i]) + "\n"

text = open("highpassfrequencyresponse.dat", "w")
text.write(s)
text.close()
