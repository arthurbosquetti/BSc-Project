# -*- coding: utf-8 -*-
"""
Created on Wed Feb 22 09:49:33 2023

@author: bosquea

This file is a part of the work to process the JSON files that contain the raw
data.

"""

import sys
import json
from datetime import datetime

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
    This function generates a filePath for the same folder that contains the
    original input file.
    '''
    outputFilePath = inputFilePath.split("\\")
    return "\\".join(outputFilePath[:-1]) + "\\"


outputFilePath = getOutputFilePath(jsonFilePath)

outputFile = open(outputFilePath + "output_files\\jsonReaderOutput.txt", 'w')
outputFile.write(str(data))
outputFile.close()

'''

I should be able to, given certain data 'specifications in the arguments
(when calling the script), save that data (and only that data) in the output
file.


A second step will be to filter some of this data. Ex: only data for 902. Then
the script must remove the data related to other devices.


'''


print("Ran jsonReader.py")

print(datetime.now())

