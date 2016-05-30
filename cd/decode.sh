openssl aes-256-cbc -K $encrypted_99e0f175f049_key -iv $encrypted_99e0f175f049_iv -in config.tar.gz.enc -out config.tar.gz -d

tar -zxvf config.tar.gz
