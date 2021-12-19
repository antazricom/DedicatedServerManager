pipeline {
    agent any
    def maven = "DebianLocalMaven3"

    stages {
        stage('Git') {
            steps {
                git 'https://github.com/antazricom/DedicatedServerManager.git'
            }
        }
        stage('Test') {
            steps {
                maven "mvn -Dspring.active.profiles=prod test -X -e"
            }
        }

        stage('Build') {
            steps {
                maven "mvn -Dskiptests=true -Dspring.active.profiles=prod clean package-X -e"
            }
        }

        stage('Run') {
            steps {
                maven "mvn -Dspring.active.profiles=prod springboot:run-X -e"
            }
        }
    }
}
