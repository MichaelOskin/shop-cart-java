#!/bin/bash

print_title() {
    local title="$1"
    local width=$(tput cols)
    local padding=$((($width - ${#title} - 4) / 2))
    printf "\n/%*s%s%*s/\n" $padding "" "$title" $padding ""
}
clear

print_title "SHOPCART"

print_title "v1"



echo script:building application..
gradle clean build
sleep 3
echo script:jar
echo 
cd build/libs && ls
echo run application..
x-terminal-emulator -e java -jar shop-cart-java-1.0.jar
