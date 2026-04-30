# Transfer
scp -i /home/thomas/.ssh/sparna-keypair-francfort.pem skos-testing-tool/target/skos-testing-tool-0.9.0.war ubuntu@92.243.27.145:~

# Redeploy
ssh -i /home/thomas/.ssh/sparna-keypair-francfort.pem ubuntu@92.243.27.145 'sudo su -c "\
service tomcat10-instance-2 stop
rm -rf /var/lib/tomcat10-instance-2/webapps/skos-testing-tool.war
rm -rf /var/lib/tomcat10-instance-2/webapps/skos-testing-tool
rm -rf /var/lib/tomcat10-instance-2/logs/*
cp /home/ubuntu/skos-testing-tool-0.9.0.war /var/lib/tomcat10-instance-2/webapps/skos-testing-tool.war
service tomcat10-instance-2 start"'