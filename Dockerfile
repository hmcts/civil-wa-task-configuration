# renovate: datasource=github-releases depName=microsoft/ApplicationInsights-Java
ARG APP_INSIGHTS_AGENT_VERSION=3.5.4

# Application image

FROM hmctspublic.azurecr.io/base/java:17-distroless

COPY lib/AI-Agent.xml /opt/app/
COPY build/libs/civil-wa-task-configuration.jar /opt/app/

EXPOSE 4550
CMD [ "civil-wa-task-configuration.jar" ]
