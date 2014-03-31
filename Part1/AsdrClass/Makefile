current_dir = $(shell pwd)

all: flex project

flex:
	java -jar $(current_dir)/../lib/jflex-1.5.0.jar src/asdrclass/AsdrClass.flex

project:
	ant

clean:
	ant clean
