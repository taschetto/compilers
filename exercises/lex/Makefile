current_dir = $(shell pwd)
jflex = java -jar ../../lib/jflex-1.5.0.jar

all: jflex
	ant

jflex:
	$(jflex) src/docmodel/DocModel.flex
