FROM jenkins/jenkins:2.443-alpine-jdk17
LABEL maintainer="bernd.wahlen@k2interactive.de"
USER root
#otherwise aapt2 in android build process fails
RUN apk add gcompat libstdc++

ENV LC_ALL en_US.UTF-8
ENV TZ=CET

RUN addgroup jenkins root
ENV JAVA_HOME=/opt/java/openjdk
ENV ANDROID_HOME /home/android-sdk-linux/
ENV PATH=$PATH:${ANDROID_HOME}platforms:${ANDROID_HOME}cmdline-tools/bin
ENV JENKINS_OPTS --httpPort=7453
RUN jenkins-plugin-cli --plugins thinBackup authentication-tokens credentials junit javadoc ansicolor htmlpublisher extra-columns next-build-number

#install android sdk
RUN wget -O /home/commandlinetools-latest.zip https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip && unzip /home/commandlinetools-latest.zip -d /home/android-sdk-linux && rm -f /home/commandlinetools-latest.zip
RUN yes|sdkmanager --sdk_root=${ANDROID_HOME} "cmdline-tools;latest" "platform-tools" "platforms;android-34" "build-tools;34.0.0"

#add flutter
RUN apk add xz clang cmake ninja-build ninja pkgconf gtk+3.0-dev
RUN wget -O /home/flutter.tar.xz https://storage.googleapis.com/flutter_infra_release/releases/stable/linux/flutter_linux_3.16.9-stable.tar.xz && tar -xvf /home/flutter.tar.xz -C /home && rm /home/flutter.tar.xz
RUN chown -R root:root /home/flutter
ENV PATH=$PATH:/home/flutter/bin
RUN yes|flutter doctor --android-licenses
