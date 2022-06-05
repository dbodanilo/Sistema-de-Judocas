#!/bin/bash

_PWD=`pwd`
CopyLibs="$_PWD/lib/org-netbeans-modules-java-j2seproject-copylibstask.jar"

CLASSPATH="$CLASSPATH:$CopyLibs:$_PWD/lib/junit-4.12.jar"
export CLASSPATH
echo "CLASSPATH:"$CLASSPATH

echo " "
echo "Compiling these projects:"
for i in * ; do
  if [ -d "$i" ] && [ ! "$i" = "lib" ] && [ ! "$i" = "glassfish3" ]; then
    cd "$i"

    if [ -f "manifest.mf" ]; then
        TARGET=jar
    else
        TARGET=dist
    fi
    TARGET=""

    echo " "
    echo "----- $i -----"
    ant -v -Dlibs.CopyLibs.classpath="$CopyLibs" \
    -lib "$_PWD/lib/junit-4.12.jar;$_PWD/lib/"
		$TARGET
    RET=$?
    if [ ! "$RET" = "0" ]; then
        exit $RET
    fi
    echo " "
    cd ..
  fi
done
