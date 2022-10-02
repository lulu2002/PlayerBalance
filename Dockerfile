FROM itzg/minecraft-server

RUN mkdir -p /plugins
COPY build/libs/playerbalance-1.0.0-SNAPSHOT-all.jar /plugins