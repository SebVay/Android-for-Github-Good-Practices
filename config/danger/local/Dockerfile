FROM ghcr.io/danger/danger-kotlin:1.3.3

WORKDIR /code
COPY . /code

# Danger-kotlin requires a 'master' branch to exist for its local execution.
# This section ensures that such a branch is created based on 'origin/main'.
# It then switches back to the original branch.
RUN git branch master origin/main

WORKDIR /code/config/danger

RUN danger-kotlin local
RUN mkdir -p /output && mv danger_out.json /output/
