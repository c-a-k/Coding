from collections import defaultdict #for dictionary
import re 
import string

class Kwic:

	def __init__(self, excluded, lines):
		self.excluded = excluded
		self.lines = lines
		self.outputlines = []

	def output(self):
		self.makelist()
		return self.outputlines

	def makelist(self):
		mydict = defaultdict(list)
		for line in self.lines:
			liszt = line.split() #divide line into words
			for word in liszt:
				if word.lower() not in self.excluded: #case-insensitive check if word is exclusion
					mydict[word.upper()].append(self.theanswer(line,word))
		for key in sorted(mydict.keys()): #run through key-sorted dictionary and print each element in the lists 
			for x in range(0,len(mydict[key])): self.outputlines.append(mydict[key][x])

	def theanswer(self,line,word):
		answer = re.sub(r'\b'+word, word.upper(), line) #makes the keyword uppercase
		rgx = r'(\b.{0,20})'+word.upper()+r'(\b.{0,'+str(31-len(word))+'}($|\s))' #regex to create formatted string
		answer = re.search(rgx,answer).group(0).strip() #sets the explicit regex as the answer
		thevoid = ' '*(29-len(re.search(r'(\b.{0,20})'+word.upper(), answer).group(0))+len(word)) #makes whitespace for keyword
		return (thevoid+answer) #returns the line with white space added to dictionary
