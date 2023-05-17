FROM maven:3.9.1 as build
ENV HOME=/SpringDemoBot
RUN mkdir -p $HOME
WORKDIR $HOME

ADD pom.xml $HOME
ADD TgBd/pom.xml $HOME/TgBd/pom.xml
ADD TgService/pom.xml $HOME/TgService/pom.xml
ADD tgBdApi/pom.xml $HOME/tgBdApi/pom.xml

RUN mvn clean install

#RUN mvn -pl tgBdApi verify --fail-never
#ADD tgBdApi $HOME/tgBdApi
#RUN mvn -pl tgBdApi install
#
#RUN mvn -pl TgBd verify --fail-never
#ADD TgBd $HOME/TgBd
#RUN mvn -pl TgBd,tgBdApi install
#
#RUN mvn -pl TgService verify --fail-never
#ADD TgService $HOME/TgService
#RUN mvn -pl TgBd,TgService,tgBdApi install


