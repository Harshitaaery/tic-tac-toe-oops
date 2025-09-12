@echo off
echo Compiling TicTacToeFX...

REM Compile all Java files in the package
javac --module-path "C:\javafx-sdk-24\lib" --add-modules javafx.controls -d . src\com\tictactoe\*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)

echo Running TicTacToeFX...
java --module-path "C:\javafx-sdk-24\lib" --add-modules javafx.controls com.tictactoe.TicTacToeFX

pause
