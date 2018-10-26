#!/bin/bash
usage="$0 [database dir] [xml out dir] [midi out dir]"
args=$#

source /Users/grace.han/Documents/University/sem2/res/28789946_RESEARCH_NOTEBOOK/convertor/res/bin/activate
convertor=/Users/grace.han/Documents/University/sem2/res/28789946_RESEARCH_NOTEBOOK/convertor/generate.py
timeout="did not timeout :)"
if [ $args -ne 3 ]; then
        echo $usage
else
    echo "generating xml from sqlite"
    for filename in $1/*.db; do
        fn=$(basename "${filename%.*}")
        gtimeout 15m python $convertor $filename $PWD/out/$fn.xml || echo $fn timedout; timeout="timed out :(" || continue
        mail -s "grace.han $fn converted to xml and $timeout" ghan0004@student.monash.edu <<< "$fn sonified"
    done
     
    echo "sonification in progress!"
    for file in $PWD/out/*.xml; do
        fn=$(basename "${file%.*}")
        echo $fn
        java -cp maocsp/out maocsp.Main $file $3/$fn.txt || continue
        mail -s "grace.han $fn sonified!" ghan0004@student.monash.edu <<< "$fn sonified"

    done
fi;
