#!/bin/bash
usage="$0 [database dir] [xml out dir] [midi out dir]"
args=$#

source /Users/grace.han/Documents/University/sem2/res/28789946_RESEARCH_NOTEBOOK/convertor/res/bin/activate
convertor=/Users/grace.han/Documents/University/sem2/res/28789946_RESEARCH_NOTEBOOK/convertor/generate.py

if [ $args -ne 3f ]; then
        echo $usage
else
    echo "generating xml from sqlite"
    for filename in $1/*.db; do
        fn=$(basename "${filename%.*}")
        python $convertor $filename $PWD/out/$fn.xml || continue
    done
     
    
    echo "sonification in progress!"
    for file in $PWD/out/*.xml; do
        fn=$(basename "${file%.*}")
        echo $fn
        java -cp maocsp/out maocsp.Main $file $3/$fn.txt || continue
    done
fi;
