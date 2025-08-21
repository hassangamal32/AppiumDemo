// Jenkinsfile
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Allure Report') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: 'test-output/allure-results']]
            }
        }
        stage('Archive Reports') {
            steps {
                archiveArtifacts artifacts: 'test-output/ExtentReport.html', allowEmptyArchive: true
            }
        }
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}