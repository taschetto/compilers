current_dir = $(shell pwd)
jflex = java -jar $(current_dir)/../../lib/jflex-1.5.0.jar

all: flex project

flex:
	$(jflex) src/asdrclass/AsdrClass.flex

project:
	ant

clean:
	ant clean
