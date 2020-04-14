# Setup (*One time only*)

Used [systemd](https://www.freedesktop.org/wiki/Software/systemd/) to setup deployment

The files needed are `start-service.sh` and autohome.service
- `start-service.sh` contains the command to start the service and sets up [localtunnel](https://github.com/localtunnel/localtunnel) to expose the service over public internet
- `autohome.service` contains the setup required to onbard the `start-service.sh` executable to systemd. Instructions [here](https://www.linode.com/docs/quick-answers/linux/start-service-at-boot/)

### Steps to create autohome service
- make `start-service.sh` executable
```
sudo chmod +x start-service.sh
```
- copy `autohome.service` file to systemd service directory and give it permissions
```
sudo cp /opt/autohome/autohome.service /etc/systemd/system/
sudo chmod 644 /etc/systemd/system/autohome.service
```
- use the `enable` command to ensure that the service starts whenever the system boots
```
sudo systemctl enable myservice
```

# Deployment

- cd to autohome repo
- pull latest changes from master
- do a clean install
- restart the autohome service
```
cd /opt/git/repo/autohome
git pull
mvn clean install
sudo systemctl restart autohome
```
