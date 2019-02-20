#include <stdio.h>
#include <string.h>
#define MAX_CHAR_LENGTH 21 //max length +1 for null character
#define MAX_LINE_LENGTH 71 //max length +1 for null character
#define MAX_LINES 100
#define MAX_INDEX_WORDS 100
#define MAX_EXCLUSIONS 20

struct index
{
	char keyword[MAX_CHAR_LENGTH];
	char key_line[MAX_LINE_LENGTH];
};

int main()
{
	char exclusions[MAX_EXCLUSIONS][MAX_CHAR_LENGTH]; //2-D Array representing exclusion words
	char lines[MAX_LINES][MAX_LINE_LENGTH]; //2-D Array representing lines and each line's characters
	char buffer[10];
	fgets(buffer, 10, stdin);
	fgets(buffer, 10, stdin); //skip first two lines of input (not important)
	int exclusive_count = 0;

	//assemble list of exclusion words
	for (int i=0; i<MAX_EXCLUSIONS; i++)
	{
		char word[MAX_CHAR_LENGTH];
		fgets(word, MAX_CHAR_LENGTH, stdin);
		if(word[0] == ':')
		{
			break;
		}
		word[strlen(word) - 1] = 0;
    	strncpy(exclusions[i],word,MAX_CHAR_LENGTH);
    	exclusive_count++;
	}	
    //assemble lines
    int line_count = -1;
    while(!feof(stdin))
	{
		line_count++;
		char line[MAX_LINE_LENGTH];
		fgets(line, MAX_LINE_LENGTH, stdin);
    	strncpy(lines[line_count],line,MAX_LINE_LENGTH);
	}	
    //build list of index words
    struct index word_list[MAX_INDEX_WORDS];
   // char *token;
    int list_count = 0;
    for(int i=0; i<line_count; i++)
    { //for each line of input
    	char buffer[MAX_LINE_LENGTH];
    	strncpy(buffer, lines[i], MAX_LINE_LENGTH);
    	char *token = strtok(buffer, " ");

    	while(token!=NULL)
    	{ //read through each word of line
    		int compare = 1;
    		
    		for(int j=0; j<exclusive_count; j++)
    		{ //compare to all exclusive words
    			char temp[MAX_CHAR_LENGTH];
    		strncpy(temp, token, MAX_CHAR_LENGTH);
    		char temp2[MAX_CHAR_LENGTH];
    		strncpy(temp2, exclusions[j], MAX_CHAR_LENGTH);
    		
    			int expected = strncmp(temp, temp2, sizeof(temp)); //this doesn't work for some reason
    			if(expected == 0)
    			{ //if the token is equal to an exclusion word
    				compare = 0;
    				break;
    			}
    		}
    		if(compare != 0)
    		{ //if the token is an index word
    			char temp3[MAX_LINE_LENGTH];
    			for (int k=0; k<MAX_LINE_LENGTH; k++) 
        	{
            	temp3[k] = lines[i][k];
        	}
    			strncpy(word_list[list_count].key_line,temp3, MAX_LINE_LENGTH);
    			strncpy(word_list[list_count].keyword,token, MAX_CHAR_LENGTH);
    			list_count++;
    		}
    		token = strtok(NULL, " \n");
    	}
    }
    //print word_list
    for (int i=0; i<list_count; i++)
	{
        for (int j=0; j<MAX_LINE_LENGTH; j++) 
        {
            printf("%c", word_list[i].key_line[j]);
        }
    }


	return 0;
}