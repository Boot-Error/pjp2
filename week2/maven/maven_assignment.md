# Assignment Maven 

1. Write shell scripts to list out all the mvn goals available
2. Write shell scripts to demonstrate leveraging local copy of maven within the project; and a `readme.md` on how this is beneficial.
3. Write shell scripts to extract and save the build logs to a file instead of console output
4. Write shell scripts to prevent maven from downloading dependencies every time/ Make maven work without internet.

## Solutions

1.
```
mvn help:describe -Dcmd=package
```

3.

```
mvn -l build.log package
```

4.
```
mvn -o package
```

