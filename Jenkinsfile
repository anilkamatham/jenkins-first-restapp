pipeline {
    agent any {
        stages{
            stage('Build'){
                steps {
                    bat 'mvn clean package'
                }
                post {
                    echo 'Now Archiving...'
                    archiveArtifacts artifact: '**/targer/*.war'
                }             
            }
            stage('Deploying to stage') {
                steps {
                    build job: 'pipeline-deploy-to-local-stage-tomcat'
                }
            }
            stage('Deploying to prod') {
                steps {
                    timeout(time:5, unit:'DAYS') {
                        input message: 'Approve Production Deployment'
                    }
                    build job: 'pipeline-deploy-to-local-prod-tomcat'
                }
                post {
                    success {
                        echo 'Code deployed to production'                        
                    }
                    failure {
                        echo 'Deployment failed'
                    }

                }
            }
        }
    }
}