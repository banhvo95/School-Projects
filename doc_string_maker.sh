#!/bin/bash
# To run this script you only need 1 arugment.
# it needs to by a python file
# run ./doc_string_maker.sh test_python.py to run the example
# should request two missing doc strings
# test_python.py will have the inputted strings if you hit "y"
#

if [ $# -eq 0 ]
then
	echo "No arguments supplied"
	exit 9
fi

#function to count the number of leading spaces
function output_char
{
	space_counter=0
	text=""$1""
	letters='a-zA-Z'
	for i in $(seq 1 ${#text})
	do
		#echo "Letter $i: ${text:i-1:1}"	
		if [[ ${text:i-1:1} = " " ]];
		then	
			space_counter=$((space_counter+1))
		else
			break;
		fi
	done
	echo "$space_counter"
}


#Grab Line number with a fuction definition
# Two outside functions
line_no=$( egrep -n '[ ]*(def)[ ][a-z|A-Z}_]*[\(][a-z|A-Z|_|,|0-9]*[(\):)$]' $1 |cut -f1 -d: )


#reverse list so you process from the bottom change line numbers
rev_list=
for i in $line_no
do
	rev_list="$i $rev_list"
done
for i in $rev_list; do
	next_line=$( expr $i + 1 )
	line_text=$(echo "$a"|sed -n "$i"p $1)
	tab_amount=$(output_char "$line_text")
	#var
	next_doc=$(echo "$a"|sed -n "$next_line"p $1 | grep '"""')
	# check if any doc strings were found
	if [ -z "$next_doc" ] ;then
		printf "\n"
		printf "NO DOCSTRING FOUND AFTER \n$line_text"
		printf "\nDo you want to add a doc string?(y/n)\n"
		read input
		#loop to check user input
		while [ "$input" != "y" ] && [ "$input" != "n" ]
		do
			printf "\nInvalid input, Do you want to add a doc string?(y/n)\n"
			read input
		done
		#if user wants to process missing doc string
		if [ "$input" == "y" ]; then
			echo "Doing docstring stuff"
			echo "What is the summary statement?(in a sentence)"
			read summary
			#creates format for string
			doc_string=$(echo "\"\"\"$summary\"\"\"")
			new_indent=$(($tab_amount+4))
			whole_line=$(printf "%*s%s" $new_indent ' ' "$doc_string")
			#var
			commands=""$next_line"i"$whole_line""	
			echo "$commands"
			#external command
			sed -i "$commands" $1
		else
			echo "Skipping Docstring processing"
		fi
	fi


done
#ending
echo "All function definitions without a docstring have been processed."
printf "Adding timestamp to top of File\n"
DATE=$(date +%Y-%m-%d)
#output timestamp
sed -i "1i#$USER added doc strings at $DATE" $1
exit 1
