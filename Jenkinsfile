pipeline {
    agent any
    parameters {
        string(name: 'tomcat_dev', defaultValue: '13.126.123.189', description: 'Staging server' )
        string(name: 'tomcat_prod', defaultValue: '15.206.148.111', description: 'Production server')        
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
                           sh 'pwd'
                          sh "scp -i C:/Technology/Javaworkspaces/JenkinProjects/jenkins-tomcat-key-pair1.pem /target/RestApp.war ec2-user@${params.tomcat_dev}:/var/lib/tomcat/webapps" 
                     }                
                }
               
            }
        }
    }
}