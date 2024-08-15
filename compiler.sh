#!/bin/bash

javac -d ./bin/ ./src/module-info.java
javac -d ./bin/ ./src/model/*
javac -d ./bin/ ./src/controller/*
javac -d ./bin/ ./src/application/*