import sys
import json
from datetime import datetime
from json_file_template import key_addresses


# Testing performance
start_time = datetime.now()
print(start_time)

json_file_path = sys.argv[1]
args = []
for arg in sys.argv[2:]:
    args.append(arg)

print("args:", args)

with open(json_file_path, 'r') as jsonFile:
    data = json.load(jsonFile)


def get_parent_directory(originalPath, depth=1):

    parentDirectory = originalPath.split("\\")
    return "\\".join(parentDirectory[:-(depth + 1)]) + "\\"


def get_value_of_key(searchObject, addressOfKey):
    if not addressOfKey:
        return searchObject
    if isinstance(searchObject, dict):
        return get_value_of_key(searchObject[addressOfKey[0]], addressOfKey[1:])
    elif isinstance(searchObject, list):
        value = []
        for obj in searchObject:
            value.append(get_value_of_key(obj, addressOfKey))
        return value
    # TODO: return what on else?


# Do something with the data.
# For now, rewrite it to an output file to test performance.
outputFilePath = get_parent_directory(json_file_path)
print(f"outputFilePath = {outputFilePath}")
outputFile = open(outputFilePath + "Output Files\\jsonReaderOutput.txt", 'a')

# Test get_value_of_key() and key_addresses
for (key, value) in key_addresses.items():
    if not (get_value_of_key(data, value) is None):
        print(f"{key} -- OK")
    else:
        print(f"{key} -- FAIL", file=sys.err)

# Testing performance
end_time = datetime.now()
print(end_time)

outputFile.write(f"{start_time} - {end_time} | json_file_path = {json_file_path} | args = {args}\n")
outputFile.close()

print("Ran json_reader.py")
