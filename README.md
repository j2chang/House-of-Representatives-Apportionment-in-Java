# HW3-Apportionment-Refactored
3/18/2023 10:09 p.m.

## Authors

* Aaron Ko - wwr7nu

## Description on how to use your code
To clone the repository onto your local machine, copy the .git link of this repository. In IntelliJ, create a new project
from version control and then select GitHub. Once this is done, paste the link you had copied and use a personal
access token. Once the code is cloned and IntelliJ has finished doing its tasks do the following steps:

* Build the Jar file with ./gradlew build, this will generate a .jar file in build/libs/ called "Apportionment.jar"

* After building, to run the Jar file open terminal and type the following:

"java -jar Apportionment.jar filename"

* There are different ways to get different outputs by inputting the argument above with optional flags; the flags are:

* --reps integer - must be followed by a positive (non-zero, non-negative) integer.
* --format formatName - set the format name. Format choices:
   * --format alpha - print States (AlphabeticalApportionmentFormat)
    * --format benefit - prints States by benefit (RelativeBenefitFormat)
* --algorithm strategyName - set the Apportionment strategy
  * --algorithm hamilton - use Hamilton Apportionment algorithm
  * --algorithm jefferson  - use Jefferson Apportionment algorithm
  * --algorithm huntington - use Huntington-Hill Apportionment algorithm

* To use these flags, do the following:

"java -jar Apportionment.jar filename --reps integer --format formatName --algorithm strategyName"

* These "long" flags can also be substituted for "short flags"

* -r is short for --reps
* -f is short for --format
* -a is short for --algorithm

* Short flags can also be combined and alternatively used like this:

(e.g) -> "java -jar Apportionment.jar census2020.xlsx -rfa 1000 benefit hamilton"

If the Jar file cannot be found, add "build/libs/" in front of the .jar file name (e.g.: build/libs/Apportionment.jar)

* The Jar, when run, will print out the apportionment of representatives for the United States Congress for each state.