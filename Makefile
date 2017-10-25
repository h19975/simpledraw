# super simple makefile
# call it using 'make NAME=name_of_code_file_without_extension'
# (assumes a .java extension)
NAME = "A2Basic"
all:
	# (a bit of a hack to compile everything each time ...)
	@echo "Compiling..."
	javac -cp vecmath.jar *.java model/*.java view/*.java
run: all
ifeq ($0(OS), win)
	@echo "running on windows..."
	java -cp "vecmath.jar;." $(NAME)
else 
	@echo "Running ..."
	java -cp "vecmath.jar:." $(NAME)
endif
clean:
	rm -rf *.class
	rm -rf view/*.class
	rm -rf model/*.class
