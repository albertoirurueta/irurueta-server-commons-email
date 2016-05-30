openssl aes-256-cbc -K $encrypted_99e0f175f049_key -iv $encrypted_99e0f175f049_iv -in config.tar.gz.enc -out config.tar.gz -d

cp config/apache-mail.properties ./apache-mail.properties
cp config/aws-mail.properties ./aws-mail.properties
cp config/java-mail.properties ./java-mail.properties
