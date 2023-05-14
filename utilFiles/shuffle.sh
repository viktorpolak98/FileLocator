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

for f in "$root_path/*"; do
    allArr+=($f)
done

for file in "${allArr[@]}"; do
    if [[ $file == *.txt ]]; then
        fileArr+=($file)
    else
        dirrArr+=($file)
    fi
done