From openjdk:8
MAINTAINER yabx.co
RUN mkdir kyc<br>
ADD kyc/target/kyc-1.0.jar kyc/kyc-1.0.jar <br>
WORKDIR kyc<br>
RUN "pwd"<br>
RUN "ls"<br>
EXPOSE 8085<br>
ENTRYPOINT ["java","-jar", "kyc-1.0.jar"]
CMD ["java","-jar","kyc-1.0.jar"]

