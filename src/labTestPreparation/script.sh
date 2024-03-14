# Command to allow mac to run file:
chmod +x compile.sh

# Compile the java files
# -d classFiles: specifies the directory where to put the generated class files
# -classpath "sourceFiles:lib/*": specifies the location of the source files and the jar files
javac -d my_compiled -classpath "ben_code:my_code" my_code/smu/bidding/App.java

java -classpath "ben_code:my_compiled" smu.bidding.App