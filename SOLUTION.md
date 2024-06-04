SPW4 - Exercise 3
=================

Name: _____________

Effort in hours: __

## 1. Connect Four Web Application and CI/CD Pipeline

### Task 1.a

<!--- describe your solution here --->

### Task 1.b



<!--- describe your solution here --->

### Task 1.c

<!--- describe your solution here --->

## Sonarqube

Zum starten:
1. Linux Konsole öffnen (wsl)
```bash
sudo -i
cd /root
.\gitlab-runner run
```
2. Docker starten

3. Neue Konsole (normal) öffnen
```bash
docker run -d --name sonarqube --restart always --network runner-net -p 9000:9000 -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true sonarqube:10.4.1-community
```

4. Browser öffnen
````
http://localhost:9000/
````