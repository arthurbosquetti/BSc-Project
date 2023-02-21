# -*- coding: utf-8 -*-
"""
Created on Tue Feb 21 14:26:46 2023

@author: bosquea
"""

import sys

import json

with open(sys.argv[1], 'r') as fcc_file:
    fcc_data = json.load(fcc_file)

output_location = sys.argv[1].split("\\")
output_location = "\\".join(output_location[:-1]) + "\\"
print("Saving file to: " + output_location)

output_file = open(output_location + "untitled0_output.txt", "a")
output_file.write("I received these arguments: " + str(sys.argv) + "\n")
output_file.write("I then read this relevant data: " + str(fcc_data['certifications']) + "\n\n")
output_file.close()

print("Ran untitled0.py")
