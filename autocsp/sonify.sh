#!/bin/bash
usage="$0 [xml out dir] [midi out dir] [mode]"
args=$#

source /Users/grace.han/Documents/University/sem2/res/28789946_RESEARCH_NOTEBOOK/convertor/res/bin/activate
convertor=/Users/grace.han/Documents/University/sem2/res/28789946_RESEARCH_NOTEBOOK/convertor/generate.py
timeout="did not timeout :)"
if [ $args -ne 3 ]; then
        echo $usage
else
    echo "sonification in progress!"
    for file in $PWD/$1/*.xml; do
        fn=$(basename "${file%.*}")
        echo $fn
        java -cp maocsp/out maocsp.Main $file $2/$fn$3.txt || continue
        mail -s "grace.han $fn sonified!" ghan0004@student.monash.edu <<< "$fn sonified"
    done
fi;
