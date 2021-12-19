pipeline {
    agent any

    stages {
        stage('Git') {
            steps {
                git 'https://github.com/antazricom/DedicatedServerManager.git'
            }
        }
        stage('Test') {
            steps {
                sh "mvn -Dspring.active.profiles=prod test"
            }
        }

        stage('Build') {
            steps {
                sh "mvn -Dskiptests=true -Dspring.active.profiles=prod clean package"
            }
        }

        stage('Run') {
            steps {
                sh "mvn -Dspring.active.profiles=prod springboot:run"
            }
        }
    }
}
