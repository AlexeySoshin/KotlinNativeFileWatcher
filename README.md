# Kotlin Native - File Watcher

Example project for building OSX command line utility using Kotlin Native <br />

The utility will watch specified file at specified intervals (minimal interval: 1 second),
and create a copy of it if the file was modified

Modification is decided by modified timestamp on the file, not by its contents.

## Building
```
./gradlew build
```

## Running
```
./build/konan/bin/macos_x64/file_watcher.kexe ./README.md 1
```
