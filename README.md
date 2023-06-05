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

After connecting to the server, you will see a message.
```
telnet localhost 8081
Trying ::1...
telnet: connect to address ::1: Connection refused
Trying 127.0.0.1...
Connected to localhost.
Escape character is '^]'.
Welcome to chat!
Please, use /help command to find out the possibilities of the chat.
```
You can use the /help command to view the available commands.
```
/help
Hi, it's simple text based chat.
Chat can handle next commands:
/login <name> <password> - login with username and password;
/join <channel> - join channel by name, you can only join one channel at a time;
/leave - leave current channel;
/disconnect - close connection;
/list - list of existing channels;
/users - list of users in current channel;
<text message> - send message to current channel.
```
If you try to use a non-existent command, the chat will display next message.
```
/command
Chat does not support this command.
Please, use "/help" to see existing commands.
```
If you try to send a message before authentication, the chat will display an error.
```
some message
You haven't logged in yet.
```
The only available commands for non-authenticated users are /help and /disconnect.
```
/disconnect
Closing your connection.
Bye-Bye!
Connection closed by foreign host.
```
To log in, you need to execute the /login command followed by your username and password separated by a space. Otherwise, the chat will display an error.
```
/login
Please, write your login and password divided by space.
/login username
Please, write your login and password divided by space.
/login username password
Welcome, username!
You successfully registered.
```
If you try to log in again, the chat will display an error.
```
/login username2 password2
You've already logged in.
```
To view the available chat rooms, you need to execute the /list command.
```
/list
Chat list is empty.
```
To add a chat, you need to execute the /join command followed by the chat name. Otherwise, the chat will display an error.
```
/join
Please, write chat name without spaces.
/join some chat
Please, write chat name without spaces.
/join some_chat
Created new chat with name some_chat.
/list
Existing chats:
some_chat
```
To view the users in the chat, the /users command is used.
```
/users
Users in current chat:
username
```
A user can only be in one chat at a time. To join a different chat, you need to first leave the current chat by using the /leave command. After exiting, you can then join the new chat using the /join command with the appropriate chat name.
```
/join another_chat
You have to leave from previous chat.
/leave
You successfully leaved from some_chat.
/join another_chat
Created new chat with name another_chat.
/list
Existing chats:
another_chat
some_chat
```
To send a message in the chat, simply write the message without any preceding / symbol.
```
hi, everybody!
[11:40][username]: hi, everybody!
[11:41][another_username]: hi, username!
```
When reconnecting, you will be connected to the previous chat and will see the most recent messages in that chat.
```
/login username password
You successfully logged in.
Joining previous chat...
You successfully joined to another_chat.
Last 3 messages:
[11:43][username]: what's up?
[11:43][another_username]: i hate mondays
[11:43][username]: so am i
```
The chat has a limit on the number of connections it can handle.
```
/join another_chat
Chat have exceeded the connection limit.
Please, try later.
```
Therefore, if the connection limit is exceeded when reconnecting, you will not be able to join the chat.
```
/login username password
You successfully logged in.
Joining previous chat...
Chat have exceeded the connection limit.
Please, try later.
```



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
