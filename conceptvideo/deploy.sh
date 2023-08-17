sudo mkdir -p /server/log/relay_model
sudo chown -R brsweb:brsweb /server/log/relay_model
sudo rm -rf /server/relay_model
sudo mv /server/relay_model_build /server/relay_model
sudo chown -R brsweb:brsweb /server/relay_model
cd /server/relay_model
pm2 delete "RELAY-MODEL"
pm2 start /server/relay_model/relay_model_prd.json
pm2 save --force