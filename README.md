# GStain        
Simple Paint program made to be expanded upon by implementing useful design patterns


Version "0.3"

Project SDK:  Java "11.0.6"

OpenJFX:      Javafx Version "11.0.2"

# Javafx Setup
## VsCode
Add the following line to launch.json:
```
"vmArgs": "--module-path \"Path_to_javafx_lib_folder\" --add-modules javafx.controls,javafx.fxml"
```

Next, in settings.json, make sure ```"java.project.referencedLibraries"``` contains the correct path to the lib folder.