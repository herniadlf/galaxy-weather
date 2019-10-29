#!/bin/bash
./gradlew build && ./gradlew test && java -jar build/libs/*.jar