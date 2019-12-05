From openjdk:8
MAINTAINER yabx.co
RUN mkdir kyc 
WORKDIR kyc
RUN "pwd"
RUN "ls"
EXPOSE 8085
COPY kyc-1.0.jar /kyc-1.0.jar
ENTRYPOINT ["java","-jar", "kyc-1.0.jar"]
CMD ["-start"]
