openssl aes-256-cbc -K $encrypted_52c610782802_key -iv $encrypted_52c610782802_iv -in config.tar.gz.enc -out config.tar.gz -d

tar -zxvf config.tar.gz
