FROM java:8-jre-alpine

COPY ${project.build.finalName}-swarm.jar /opt/${project.build.finalName}-swarm.jar
CMD java -Djboss.bind.address=0.0.0.0 -jar /opt/${project.build.finalName}-swarm.jar

EXPOSE 8080