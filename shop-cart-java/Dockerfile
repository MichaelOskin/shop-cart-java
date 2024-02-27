# Using the basic Ubuntu image
FROM ubuntu

# Setting the environment variable for the Gradle version
ENV GRADLE_VERSION=8.6

# Устанавливаем переменную окружения для пути Gradle
ENV GRADLE_HOME=/opt/gradle

# Добавляем Gradle в PATH
ENV PATH=${GRADLE_HOME}/bin:${PATH}

# Downloading and unpacking the Gradle
WORKDIR /opt

# Install the necessary packages, including curl and unzip

RUN apt-get update && \
    apt-get install -y curl unzip && \
    apt-get install -y openjdk-17-jdk && \
    apt-get clean;

# Installing Gradle and its variable
RUN curl -L https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -o gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle-${GRADLE_VERSION}-bin.zip \
    && rm gradle-${GRADLE_VERSION}-bin.zip \
    && mv gradle-${GRADLE_VERSION} ${GRADLE_HOME}

# Setting the working directory for the application
WORKDIR /app

# Copying the Gradle project files
COPY . .

# We are building the project. If you are using Docker: Please change the build file.gradle (delete "task script{}")
RUN gradle script
