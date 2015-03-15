#!/bin/bash

cd src
idlj -fall Doodle.idl
javac *.java DoodleProject/*.java
cd ..
rm -rf orb.db
echo 'Starting orbd'
orbd -ORBInitialPort 1050 -ORBInitialHost localhost 