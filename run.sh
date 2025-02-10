#!/bin/bash
clear
echo "Compilando archivos Java..."
javac -nowarn *.java

echo ""
echo "Ejecutando el simulador..."
java MemoryManagement commands memory.conf
