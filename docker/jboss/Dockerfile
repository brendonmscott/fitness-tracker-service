FROM jboss/wildfly

# Create admin user
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin --silent

# Add custom standalone.xml
ADD /config/standalone-fitness.xml /opt/jboss/wildfly/standalone/configuration/

EXPOSE 8080
EXPOSE 8787
EXPOSE 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-fitness.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug"]
