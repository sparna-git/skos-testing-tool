# transfert de tout ça
scp target/skos-testing-tool-0.8.1.war admin@labs.sparna.fr:~

# deploiement de la config
ssh -t admin@labs.sparna.fr 'sudo bash -c "\
service tomcat7-instance0 stop;\
rm -rf /var/lib/tomcat7-instance0/webapps/skos-testing-tool;\
rm -rf /var/lib/tomcat7-instance0/webapps/skos-testing-tool.war;\
cp /home/admin/skos-testing-tool-0.8.1.war /var/lib/tomcat7-instance0/webapps;\
service tomcat7-instance0 start"'