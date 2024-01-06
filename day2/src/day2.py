with open("day2\\data\\input") as file:
    lines = file.readlines()
 
 
# The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
# replacing all occurrences of colons and semicolons into the list
dict1 = {}
for i in lines:
    i = i.replace('Game', '')
    
    key, value = i.split(':', 1)
    key = key.strip()
    value = value.replace('\n', '')
    value = value.strip()
    value = value.replace(';', ',')
    dict1[key] = value
 
 
list_vals = []
for key, value in dict1.items():
    list_vals.append(value.split())
 
    
red_count = 0
green_count = 0
blue_count = 0
 
newList = []
for value in list_vals:
    for i in range(0, len(value), 2):
        number = int(value[i])
        color = value[i + 1].rstrip(',')
        if color == 'blue':
            blue_count += number
        if color == 'red':
            red_count += number
        if color == 'green':
            green_count += number
    
    newList.append([red_count, green_count,blue_count ])
    blue_count = 0
    green_count = 0
    red_count = 0
 
 
# The Elf would first like to know which games would have been possible if the bag contained only 12 red cubes, 13 green cubes, and 14 blue cubes?
# replacing all occurrences of colons and semicolons into the list
finalList = []
for x,y in enumerate(newList):
    if y[0] <= 12 and y[1] <= 13 and y[2] <= 14:
        finalList.append(x)
 
    
print(sum(finalList))