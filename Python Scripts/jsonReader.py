# -*- coding: utf-8 -*-
"""
Created on Wed Feb 22 09:49:33 2023

@author: bosquea

This file is a part of the work to process JSON files that contain raw data.

"""

import sys
import json
from datetime import datetime

# Testing performance
print(datetime.now())

jsonFilePath = sys.argv[1]
args = []
for arg in sys.argv[2:]:
    args.append(arg)

print("args:", args)

with open(jsonFilePath, 'r') as jsonFile:
    data = json.load(jsonFile)
    

def getOutputFilePath(inputFilePath):
    '''
    This function generates a file path for the same folder that contains the
    original input file.
    '''
    outputFilePath = inputFilePath.split("\\")
    return "\\".join(outputFilePath[:-2]) + "\\"

# Do something with the data. 
# For now, rewrite it to an output file to test performance.
outputFilePath = getOutputFilePath(jsonFilePath)
outputFile = open(outputFilePath + "Output Files\\jsonReaderOutput.txt", 'w')
outputFile.write(str(data))
outputFile.close()

# Testing performance
print(datetime.now())

print("Ran jsonReader.py")
