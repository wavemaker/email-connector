## Connector  Introduction

Connector is a Java based backend extension for WaveMaker applications. Connectors are built as Java modules & exposes java based SDK to interact with the connector implementation.
Each connector is built for a specific purpose and can be integrated with one of the external services. Connectors are imported & used in the WaveMaker application. Each connector runs on its own container thereby providing the ability to have it’s own version of the third party dependencies.

## Features of Connectors

1. Connector is a java based extension which can be integrated with external services and reused in many Wavemaker applications.
1. Each connector can work as an SDK for an external system.
1. Connectors can be imported once in a WaveMaker application and used many times in the applications by creating multiple instances.
1. Connectors are executed in its own container in the WaveMaker application, as a result there are no dependency version conflict issues between connectors.

## About Email Connector

## Email Connector Introduction
This connector will provides an easy apis to send bulk emails from WaveMaker application.

## Prerequisite

1. Email server with server host name & port no and email server credentials
1. Java 1.8 or above
1. Maven 3.1.0 or above
1. Any java editor such as Eclipse, Intelij..etc
1. Internet connection


## Build
You can build this connector using following command
```
mvn clean install
```

## Deploy
You can import connector dist/email.zip artifact in WaveMaker studio application using file upload option.
On after deploying email-connector in the WaveMaker studio application, make sure to update connector properties in the profile properties.Such as email server host name and port no, email server credentials

## Use email connector in WaveMaker

```

@Autowired
private EmailConnector emailConnector;

SimpleMailMessage message = new SimpleMailMessage();
message.setSubject("WaveMaker Invitation");
message.setTo("sender@gmail.com");
message.setFrom("receiver@gmail.com");
Map<String, String> props = new HashMap<>();
props.put("user", "John");
try {
     emailConnector.sendSimpleMailMessageWithTemplate(message, "templates/invitationtemplate", props);
} catch (EmailTemplateNotFoundException e) {
     throw new RuntimeException("Exception occurred while sending email",e);
}


Apart from above api, email-connector provides other apis to send emails, visit EmailConnector java class in api module.

```









