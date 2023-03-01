import sys
import json
from datetime import datetime
from typing import Union, List
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
    Recursively get the value of the first occurence of a key found by its
    address.

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
    Recursively get the value of every occurence of a key found by its address.

    Parameters
    ----------
    search_object : Union[dict, list]
        The (possibly nested) dictionary/list with dictionaries wherein the
        value of the occurences of the key will be searched for.
    address_of_key : list
        A list where every next element is the next index of the dictionary
        that leads to the key. In the case of nested dictionaries, the list has
        length > 1.

    Returns
    -------
    The value of every occurence of the key. In the case of multiple
    occurences, it returns a list of values.

    """

    # Base case
    if not address_of_key:
        return search_object

    # Recursion
    if isinstance(search_object, dict):
        return get_all_values_of_key(search_object[address_of_key[0]], address_of_key[1:])
    else:
        value = []
        for obj in search_object:
            value.append(get_all_values_of_key(obj, address_of_key))
        return value


def filter_dict(dictionary: dict, key_address: list, filter_values: list, remove_match: bool):
    """
    Filter elements in or out of a dictionary containing lists and other
    possible types as values. Filtering is only on keys with values of type
    List[dict], and it occurs at a depth of 2 (dict[List[dict]]). The
    final key whose value will be evaluated is found by using their indexing
    address in the dictionary.

    Parameters
    ----------
    dictionary : dict
        The dictionary to be filtered.
    key_address : list
        A list where every next element is the next index of the dictionary
        that leads to the key.
    filter_values : list
        The values used to be matched against for equality.
    remove_match : bool
        Decides whether keys that match with the filter_values are supposed to
        be kept or to be removed from the dictionary (filtering in or filtering
                                                      out of dictionary.)

    Returns
    -------
    A filtered copy of the original dictionary.

    """

    if not isinstance(dictionary[key_address[0]], List):
        raise ValueError("Value of key must be a list of dictionaries!")
    if not dictionary[key_address[0]]:
        raise IndexError("The list is empty!")
    if not isinstance(dictionary[key_address[0]][0], dict):
        raise ValueError("The list must be a list of dictionaries!")

    # Iterate through every dictionary at depth 2 (dict[List[dict]]). Reversed
    # to not affect the position of the other elements when one is deleted.
    for pos, obj in reversed(list(enumerate(dictionary[key_address[0]]))):
        if isinstance(obj[key_address[1]], list):
            dictionary = filter_list(dictionary, key_address, pos, filter_values, remove_match)
        else:
            # Remove obj if it is in the list of values to be removed or if it
            # is not in the list of values to not be removed, i.e. to be kept.
            if is_match(obj[key_address[1]], filter_values) == remove_match:
                del dictionary[key_address[0]][pos]
    return dictionary


def filter_list(dictionary: dict, key_address: list, list_index: int, filter_values: list, remove_match: bool):
    """
    Filter a list in or out of a dictionary based on the value of its elements.
    The list is 3 indexes into the dictionary (type dict[List[dict[list]]]).

    Parameters
    ----------
    dictionary : dict
        The dictionary that contains the list nested into it.
    key_address : list
        A list where every next element is the next index of the dictionary
        that leads to the key.
    list_index : int
        The index of the dictionary in the list of dictionaries from which it
        might be removed.
    filter_values : list
        The values used to be matched against for equality.
    remove_match : bool
        Decides whether keys that match with the filter_values are supposed to
        be kept or to be removed from the dictionary (filtering in or filtering
                                                      out of dictionary.)

    Returns
    -------
    A filtered copy of the original dictionary.

    """

    remove_list = False
    for element in dictionary[key_address[0]][list_index][key_address[1]]:
        # remove_match = Remove if there is a match (filter out)
        if is_match(element, filter_values) and remove_match:
            remove_list = True
            break
        # not remove_match = Keep if there is a match (filter in)
        elif is_match(element, filter_values) and not remove_match:
            remove_list = False
            break
        # remove_match = Remove if there is a match (filter out)
        elif not is_match(element, filter_values) and remove_match:
            remove_list = False
        # not remove_match = Keep if there is a match (filter in)
        elif not is_match(element, filter_values) and not remove_match:
            remove_list = True

    if remove_list:
        del dictionary[key_address[0]][list_index]
    return dictionary


def is_match(value, filter_values: list):
    return value in filter_values


# Test site
print(len(data['all_bugs']))
filtered_data = filter_dict(data,
                            key_addresses['all_bugs.componentsList'],
                            ["MXA902"], False)
print(len(filtered_data['all_bugs']))

# Analyzing performance
end_time = datetime.now()
print(end_time)

# Logging
output_file_path = get_parent_filepath(json_file_path)
print(f"output_file_path = {output_file_path}")
output_file = open(output_file_path + "Output Files\\json_reader_output.txt",'a')
output_file.write(f"{start_time} - {end_time} | json_file_path = {json_file_path} | args = {args}\n\n")
output_file.close()

print("Ran json_reader.py")
