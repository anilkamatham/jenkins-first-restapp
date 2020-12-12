pipeline {
    agent {label 'jenkins-slave1'}
    parameters {
        string(name: 'tomcat_dev', defaultValue: '52.66.69.64' description: 'staging server')        
    }
    triggers {
        pollSCM('* * * * *')
    }
    stages {
       stage('Build') {
           steps {
                echo 'compiling and testing the code...'
                sh 'mvn clean package'                
           }
           post {
                success {
                    echo 'Archieving the artifcats'
                    archieveArtifacts: '**/target/*.war'
                }
                failure {
                    echo 'failed to build the code'
                }
           }
       }
       stage('Deploy to stage') {
           steps {
               sshagent(['ec2-tomcat-stage']) {
                   sh 'scp -o StrictHostKeyChecking=no **/*.war tomcat-stage@52.66.69.64:/usr/share/tomcat/webapps'
               }
               success {
                   echo 'Successfully deployed to stage'                   
               }
               failure {
                   echo 'Failed to deployed to stage'
               }
           }
       }
    }
}