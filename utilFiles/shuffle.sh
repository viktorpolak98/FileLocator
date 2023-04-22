#!/bin/bash
#################################################################################################
# Run this script to shuffle files and folders created with Directory/TextFileGenerator classes #
#################################################################################################
echo -e "Enter the path to the folder containing files and folders you would like to shuffle"
read -r root_path

echo -e "\nPath specified: $root_path"
# shellcheck disable=SC2086
ls $root_path

