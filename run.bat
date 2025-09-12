@echo off
echo Compiling TicTacToeFX...
javac --module-path "C:\javafx-sdk-24\lib" --add-modules=javafx.controls -d . src\com\tictactoe\TicTacToeFX.java

echo Running TicTacToeFX...
java --module-path "C:\javafx-sdk-24\lib" --add-modules=javafx.controls com.tictactoe.TicTacToeFX
pause
