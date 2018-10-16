mkdir -p ./out
reset && javac -d out -classpath out -sourcepath . *.java 
java -cp out maocsp.Main hi.xml hi.xml.txt
