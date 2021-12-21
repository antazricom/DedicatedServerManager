pipeline {
    agent any
    tools {
        maven 'DebianLocalMaven3'
    }

    stages {
        stage('Build') {
            steps {
                sh "mvn clean package -Dspring.active.profiles=prod -X -e"
            }
        }

        stage('Run') {
            steps {
                sh "mvn spring-boot:run -Dspring.active.profiles=prod -X -e"
            }
        }
    }
}
