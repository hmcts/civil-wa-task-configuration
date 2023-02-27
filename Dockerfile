# renovate: datasource=github-releases depName=microsoft/ApplicationInsights-Java
ARG APP_INSIGHTS_AGENT_VERSION=2.6.4

# Application image

FROM hmctspublic.azurecr.io/base/java:openjdk-11-distroless-1.2

COPY lib/AI-Agent.xml /opt/app/
COPY build/libs/civil-wa-task-configuration.jar /opt/app/

EXPOSE 4550
CMD [ "civil-wa-task-configuration.jar" ]
