

digits = {'one': '1', 'two': '2', 'three': '3', 'four': '4', 'five': '5', 'six': '6', 'seven': '7', 'eight': '8', 'nine': '9'}

def func():
    text_file = open('day1\\data\\test-input-2','r')
    line_list = text_file.readlines()
    count = 0
    #for each line from the list, print the line
    for line in line_list:
        #print("the line: " + line)
        line = convertStringToDigit(line)
        val = int(getDigits(line))
        
        count = count + val
        print(line.strip() + " " + str(val) + " " + str(count))
    text_file.close() 


def convertStringToDigit(line):
    
    idx_map = {}
    leftIdx = len(line) - 1
    rightIdx = -1
    leftStr = ''
    rightStr = ''
    
    for i in digits:
        idx = line.find(i)
        if (line.find(i) >= 0):
            #print("found: ", i)
            if (idx < leftIdx):
                leftIdx = idx
                leftStr = i

            
    len_to_skip = len(leftStr)
    line = line[:leftIdx] + digits[leftStr] + line[leftIdx + len_to_skip:]

    print("after left replace: ", line)
    for i in digits:
        
        idx = line.rfind(i)
        if (idx >= 0):
            #print("found: ", i)
            if (idx > rightIdx):
                rightIdx = idx
                rightStr = i
    
    #line = line.replace(i, digits[i])
    
    if (rightStr != ""):
        rightIdx = rightIdx - len_to_skip + 1
        len_to_skip = len(rightStr)
        line = line[:rightIdx] + digits[rightStr] + line[rightIdx + len_to_skip:]
        print("after right replace: ", line)

    return line        
            

def getDigits(line):
    
    f =''
    l = ''
    for i in line:
        if i.isdigit():
            f = i

    line = line[::-1]
    for i in line:
        if i.isdigit():
            l = i

    return l+f


func()

