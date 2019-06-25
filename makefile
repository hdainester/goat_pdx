run: jar
	java -jar goat_pdx.jar

jar: class manifest
	jar cfmv goat_pdx.jar manifest -C bin .

class: bin
	javac -d bin src/**/*.java

bin:
	mkdir bin

clean:
	rm -rf bin goat_pdx.jar