# Simple chat

Simple text based chat based on Netty’s framework 4 version

## Table of Contents

- [Project Overview](#project-overview)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)

## Project Overview

Chat is a channel based communication tool, so our fancy chat implementation should support multiple channels for users
communications.
A user can only join one channel at a time, when they join another they leave their current channel.
Moreover, the same user can auth twice from different devices, and on both them able to receive messages.

## Prerequisites

To run this project, you need to have the following software installed:

- Java Development Kit (JDK) 17 or later
- Gradle build tool

## Installation

Step-by-step instructions on how to install and set up your project. Include any necessary commands or configurations.

1. Clone the repository:

   ```bash
   $ git clone https://github.com/nearbygems/chat.git
   $ cd chat

2. Build the project using Gradle:

   ```bash
   $ ./gradlew build

## Usage



## Configuration

The application is configured through the application.yml file.

   ```yaml
   bootstrap:
      parents: 1 # number of threads for parent EventLoopGroup
      children: 10 # number of threads for child EventLoopGroup
      timeout: 1000 # timeout for connection in millis
      backlog: 128 # maximum number of waiting connections
      nodelay: "true" # send data immediately
      keepAlive: "true" # check if connection is active
   
   server:
      host: "localhost"
      port: 8081

   executor:
      corePoolSize: 4 # executor core pool size
      delay: 500 # max time for task

   chat:
      clients: 3 # max clients in chat
      messages: 10 # number of last messages

   frame:
      length: 80960
