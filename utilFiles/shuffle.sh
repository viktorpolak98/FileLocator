#!/bin/bash
#################################################################################################
# Run this script to shuffle files and folders created with Directory/TextFileGenerator classes #
#################################################################################################
echo -e "Enter the path to the folder containing files and folders you would like to shuffle (case sensitive)"
read -r root_path

echo -e "\nPath specified: $root_path\n"
# shellcheck disable=SC2086
allArr=()
dirrArr=()
fileArr=()

for f in $root_path/*; do
    allArr+=("$f")
done

for file in "${allArr[@]}"; do
    if [[ $file == *.txt ]]; then
        fileArr+=($file)
    else
        dirrArr+=($file)
    fi
done

arrLength="${#dirrArr[@]}"
((arrLength++))

#for file in "${fileArr[@]}"; do
#    location=$(($RANDOM%$arrLength-1))
#    mv $file ${dirrArr[$location]}
#done

echo "${dirrArr[@]}"

for (( i=0; i<arrLength-1; i++ )); do
    location=$((RANDOM%$"${#dirrArr[@]}"-1))
    dirrArr=("${dirrArr[@]:0:1}" "${dirrArr[@]:$((1+1))}")
done

echo "${dirrArr[@]}"

#for file in "${dirrArr[@]}"; do
#    location=$(($RANDOM%$arrLength-1))
#    mv $file ${dirrArr[$location]}
#done