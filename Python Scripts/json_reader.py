import sys
import json
from datetime import datetime
from typing import Union
from json_file_template import key_addresses


# Test performance
start_time = datetime.now()
print(start_time)

json_file_path = sys.argv[1]
with open(json_file_path, 'r') as jsonFile:
    data = json.load(jsonFile)

args = []
for arg in sys.argv[2:]:
    args.append(arg)
print("args:", args)


def get_parent_filepath(original_path: str, depth=1):
    """
    Get a parent file path of the original file path.

    Parameters
    ----------
    original_path : str
        The original file path.
    depth : int, optional
        Number of directories up from the original file path. The default is 1.

    Returns
    -------
    A parent file path 'depth' directories up from the original file path.

    """

    parentDirectory = original_path.split("\\")
    return "\\".join(parentDirectory[:-(depth + 1)]) + "\\"


def get_value_of_key(search_object: Union[dict, list], address_of_key: list):
    """
    Get the value of the first occurence of a key found by its address.

    Parameters
    ----------
    search_object : Union[dict, list]
        The (possibly nested) dictionary/list with dictionaries wherein the
        value of the key will be searched for.
    address_of_key : list
        A list where every next element is the next index of the dictionary
        that leads to the key. In the case of nested dictionaries, the list has
        length > 1.

    Returns
    -------
        The value of the first occurence of the key.

    """

    # Base case
    if not address_of_key:
        return search_object

    # Recursion
    if isinstance(search_object, dict):
        return get_value_of_key(search_object[address_of_key[0]], address_of_key[1:])
    elif isinstance(search_object, list) and search_object:
        return get_value_of_key(search_object[0], address_of_key)
    return []


def get_all_values_of_key(search_object: Union[dict, list], address_of_key: list):
    """
    Get the value of every occurence of a key found by its address.

    Parameters
    ----------
    search_object : Union[dict, list]
        The (possibly nested) dictionary/list with dictionaries wherein the key
        will be searched for.
    address_of_key : list
        A list where every element is the index of the dictionary that leads to
        the key. In the case of nested dictionaries, the list has length > 1.

    Returns
    -------
    The value of every occurence of the key. In the case of multiple
    occurences, it returns a list of values.

    """
    if not address_of_key:
        return search_object
    if isinstance(search_object, dict):
        return get_all_values_of_key(search_object[address_of_key[0]], address_of_key[1:])
    elif isinstance(search_object, list):
        value = []
        for obj in search_object:
            value.append(get_all_values_of_key(obj, address_of_key))
        return value


def filter_dict(dictionary: dict, key_address: list, filter_values: list, remove_match: bool):
    # TODO: class description

    for pos, obj in reversed(list(enumerate(dictionary[key_address[0]]))):
        if isinstance(obj[key_address[1]], list):
            dictionary = filter_list(dictionary, key_address, pos, filter_values, remove_match)
        else:
            # Remove if obj is in the list of values to be filtered out, or
            # if it is not in the list of values to not be filtered out.
            if (obj[key_address[1]] in filter_values) == remove_match:
                del dictionary[key_address[0]][pos]
    return dictionary


def filter_list(dictionary: dict, key_address: list, list_index: int,
                filter_values: list, remove_match: bool):
    # TODO: add class description

    remove_list = False
    for value in dictionary[key_address[0]][list_index][key_address[1]]:
        if is_match(value, filter_values) and remove_match:
            remove_list = True
            break
        elif is_match(value, filter_values) and not remove_match:
            remove_list = False
            break
        elif not is_match(value, filter_values) and remove_match:
            remove_list = False
        elif not is_match(value, filter_values) and not remove_match:
            remove_list = True

    if remove_list:
        del dictionary[key_address[0]][list_index]
    return dictionary


def is_match(value, filter_values: list):
    return value in filter_values


print(len(data['all_bugs']))
filtered_data = filter_dict(dictionary=data,
                            key_address=key_addresses['all_bugs.componentsList'],
                            filter_values=["MXA902"],
                            remove_match=False)
print(get_all_values_of_key(filtered_data, key_addresses['all_bugs.componentsList']))
print(len(filtered_data['all_bugs']))

# Testing performance
end_time = datetime.now()
print(end_time)

# Logging
output_file_path = get_parent_filepath(json_file_path, 8)
print(f"output_file_path = {output_file_path}")
output_file = open(output_file_path + "json_reader_output.txt", 'a')
output_file.write(f"{start_time} - {end_time} | json_file_path = {json_file_path} | args = {args}\n\n")
output_file.close()

print("Ran json_reader.py")
