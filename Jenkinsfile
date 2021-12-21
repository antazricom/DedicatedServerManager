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

        stage('Copy JAR') {
            steps {
                sh "cp ./target/*.jar /opt/apps/"
            }
        }
    }
}
