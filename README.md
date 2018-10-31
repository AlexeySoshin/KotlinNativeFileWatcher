# Kotlin Native - File Watcher


## Building
`./gradlew build`

## Running
To run use `./gradlew run`

To change run arguments, change the `args` value in the `build.gradle.kts` file.

Alternatively you can run artifact directly:

    ./build/konan/bin/<target>/CsvParser.kexe ./European_Mammals_Red_List_Nov_2009.csv 4 100


It will print number of al unique ent

ries in fifth column
(Family, zero-based index) i n first 100 rows of the CSV file.

