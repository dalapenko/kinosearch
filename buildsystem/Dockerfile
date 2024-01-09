FROM antonkazakov/otus

USER root

# Install Java17 && zip. Required by actual AndroidSDK && SDKMan

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get install -y zip && \
    apt-get clean;

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-arm64
ENV PATH ${PATH}:${JAVA_HOME}

# Install Android SDK
ARG ANDROID_SDK_VERSION=10406996
ENV ANDROID_SDK_ROOT /opt/android-sdk

RUN mkdir -p ${ANDROID_SDK_ROOT}/cmdline-tools && \
    wget https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip && \
    unzip *tools*linux*.zip -d ${ANDROID_SDK_ROOT}/cmdline-tools && \
    mv ${ANDROID_SDK_ROOT}/cmdline-tools/cmdline-tools ${ANDROID_SDK_ROOT}/cmdline-tools/tools && \
    rm *tools*linux*.zip

ENV PATH ${PATH}:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/cmdline-tools/tools/bin:${ANDROID_SDK_ROOT}/platform-tools:${ANDROID_SDK_ROOT}/emulator

RUN yes | sdkmanager --licenses && \
	sdkmanager "platforms;android-33" "platforms;android-34" "build-tools;33.0.2"

# Instal SDK man
RUN curl -s "https://get.sdkman.io" | bash

# Instal Gradle Profiler
RUN bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && \
    yes | sdk install gradleprofiler && \
    rm -rf $HOME/.sdkman/archives/* && \
    rm -rf $HOME/.sdkman/tmp/*"

USER jenkins