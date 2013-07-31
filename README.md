# stu - Simple Test Utils

[![Build Status](https://buildhive.cloudbees.com/job/Seitenbau/job/stu/badge/icon)](https://buildhive.cloudbees.com/job/Seitenbau/job/stu/)

The project contains simple utils for JUnit and Co.

TODO project description.
# Documentation
To generate the documentation please invoke:

	gradlew asciidoctor

The documentation is generated to stu/documentation/html/stu.html.

# Eclipse 

To import the project into eclipse invoke:

	gradlew eclipse
	
Then the project can be imported as eclipse projects.
Please be sure to use the code-conventions-sb-testing.xml file from src/eclipse folder.

# IntelliJ

To import the project into IntelliJ IDEA  invoke:

    gradlew idea

Then the project can be open in IntelliJ.When you use IntelliJ version > 12, IntelliJ has build in
gradle support  then the project can direct be opened. When the workspace is ready the gradle task generateDataSetDsl should be invoked.
