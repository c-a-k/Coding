#!/usr/bin/python

import sys
import fileinput
from collections import defaultdict #for dictionary

def main():
	sys.stdin.readline()
	sys.stdin.readline() #skip first two lines of text
	exclusions = list()
	words = list()
	listno = 0
	for line in sys.stdin.readlines():
		if line == '::\n': #once we reach end of exclusions
			listno = 1
		if listno == 1:
			words.append(line) #store non-exclusion words
		if listno == 0:
			exclusions.append(line.lower()) #store exclusions

	exclusions = [x[:-1] for x in exclusions]
	words = [x[:-1] for x in words] #take \n out of words
	words.pop(0) #remove element containing ::
	lines = defaultdict(list)
	for line in words:
		index = 0 #for referencing index of word in list
		whitespace = 0 #for determining left aligned white space
		rightspace = 0 #for checking space to the right of list
		liszt = line.split() #divide line ino words
		for word in liszt:
			if word.lower() not in exclusions: #case-insensitive check if word is exclusion
				temp = liszt[:index] #for all elements before the keyword
				whitespace = 29 - sum(len(i) for i in temp) - len(temp)
				answer = liszt[:] #need to keep liszt for future, just make a copy of it
				ansindex = index #same for the index
				while whitespace < 9:
					whitespace += 1 + len(answer[0])
					answer.pop(0)
					ansindex -=1
					#if there's not enough whitespace then delete first word and move ansindex
				rightspace = whitespace + sum(len(i) for i in answer) + len(answer)
				while rightspace > 61:
					rightspace -= (len(answer[-1]) + 1)
					del answer[-1]
					#delete last word and adjust rightspace so no word crosses column 60
				thevoid = ' '*whitespace #creating the actual spaces
				answer[ansindex] = word.upper() #key for out dictionary (values are list)
				lines[answer[ansindex]].append(thevoid + format(' '.join(answer))) #join all words back together
			index +=1
	for key in sorted(lines.iterkeys()): #run through key-sorted dictionary and print each element in the lists 
		for x in range(0,len(lines[key])): print lines[key][x]		

if __name__ == "__main__":
	main()
