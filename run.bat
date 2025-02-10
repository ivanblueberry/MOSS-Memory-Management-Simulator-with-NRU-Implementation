@echo off
cls
echo Compilando archivos Java...
javac -nowarn *.java

echo.
echo Ejecutando el simulador...
java MemoryManagement commands memory.conf

pause
