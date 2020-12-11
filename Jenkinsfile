pipeline {
    agent any
    parameters {
        string(name: 'tomcat_dev', defaultValue: '13.233.255.138', description: 'Staging server' ) 
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
                 
                     steps {
                        // sshagent(['tomcat-ec2']) {
                          //  sh "scp -o StrictHostKeyChecking=no **/*.war ec2-user@${params.tomcat_dev}:/usr/share/tomcat/webapps"
                       // } 
                       sh "scp -i /home/anilslave/slave/jenkins-master-slave-key-pair.pem **/*.war ec2-user@${params.tomcat_dev}:/usr/share/tomcat/webapps" 

                     }                
        }
    }
 }
