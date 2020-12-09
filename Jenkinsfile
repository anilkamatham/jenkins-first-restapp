pipeline {
    agent any
    parameters {
        string(name: 'tomcat_dev', defaultValue: '13.127.201.110', description: 'Staging server' )
        string(name: 'tomcat_prod', defaultValue: '15.206.75.233', description: 'Production server')        
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
                        // sshagent(['tomcat-ec2']) {
                          //  sh "scp -o StrictHostKeyChecking=no **/*.war ec2-user@${params.tomcat_dev}:/usr/share/tomcat/webapps"
                       // } 
                       sh "scp -i C:/Technology/Javaworkspaces/JenkinProjects/jenkins-tomcat-ec2 **/target/*.war ec2-user@${params.tomcat_dev}:/usr/share/tomcat/webapps" 

                     }                
                }
                stage('Deploy to prod') {
                    steps {
                       sshagent(['tomcat-ec2']){
                           sh "scp -o StrictHostKeyChecking=no **/*.war ec2-user@${params.tomcat_prod}:/usr/share/tomcat/webapps"
                    }
                }
               
            }
        }
    }
 }
}