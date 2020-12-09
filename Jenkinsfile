pipeline {
    agent any
    parameters {
        string(name: 'tomcat_dev', defaultValue: '13.126.123.189', description: 'Staging server' )
        string(name: 'tomcat_prod', defaultValue: '15.206.148.111', description: 'Production server')        
    }
    tools {
        jdk 'localJDK'
    }
    triggers {
        pollSCM('* * * * *')
    }
    stages{
        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
            post {
                success {
                 echo 'Archiving Now...'
                 archiveArtifacts artifacts: '**/target/*.war'
                } 
            } 
            
        }
        stage('Deployment') {
            parallel {
                stage('Deploy to stage') {
                     steps {
                        sshagent(['tomcat-ec2']) {
                            sh "scp -o StrictHostKeyChecking=no **/*.war ec2-user@13.127.201.110:/var/lib/tomcat/webapps"
                        }   
                     }                
                }
               
            }
        }
    }
}