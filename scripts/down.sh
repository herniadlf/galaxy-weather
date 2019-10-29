#!/bin/bash
curl -X POST localhost:8080/actuator/shutdown
./scripts/clean-test-db.sh
echo ''
echo 'Done'