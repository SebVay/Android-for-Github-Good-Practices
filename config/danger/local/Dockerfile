FROM ghcr.io/danger/danger-kotlin:1.3.3

# Placeholder for local execution
ARG BITRISE_PUBLIC_INSTALL_PAGE_URL="127.0.0.1"

WORKDIR /code
COPY . /code

# Danger-kotlin requires a 'master' branch to exist for its local execution.
# This ensures that such a branch is created based on 'origin/main'.
RUN git branch master origin/main

WORKDIR /code/config/danger

RUN danger-kotlin local
RUN mkdir -p /output && mv danger_out.json /output/
