# autohome
Home automation

## Deployment Steps

- Copy jar into server using command:
```
sudo scp /home/misdaque/IdeaProjects/autohome/target/autohome.jar aman@192.168.1.250:/opt/autohome
```

- Check whether service already running using command:
```
ps -ef | grep java
```
- If Running then kill the process first using command:
```
 kill -9 <PID>
```
- Start the process by using command:
```
nohup java -jar autohome.jar &
```
- to check deployment log use command:
```
tail -100f nohup.out
```
