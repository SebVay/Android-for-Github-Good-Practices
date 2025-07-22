#!/usr/bin/env bash

# This script runs Danger locally using Docker.
# It builds a Docker image from the Dockerfile in the same directory,
# runs a container from that image, copies the JSON output from the container
# to the host filesystem, and then cleans up the container.
#
# This allows you to run Danger without needing to install it directly on your system.
#
# Usage:
# ./run_danger_local.sh

# shellcheck disable=SC2164

script_path="$(cd "$(dirname "$0")" && pwd)"
project_root="$script_path"/../../../

cd "$project_root"

docker rm extract-json-container

docker build -f config/danger/local/Dockerfile -t local-danger-kotlin .
docker create --name extract-json-container local-danger-kotlin
docker cp extract-json-container:/output "$script_path"
docker rm extract-json-container

read -n 1 -s -r -p "Danger report generated at '$script_path/output' - Press any key to exit."